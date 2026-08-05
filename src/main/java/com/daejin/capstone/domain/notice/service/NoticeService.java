package com.daejin.capstone.domain.notice.service;

import com.daejin.capstone.domain.file.entity.File;
import com.daejin.capstone.domain.file.repository.FileRepository;
import com.daejin.capstone.domain.notice.batch.NoticeBatch;
import com.daejin.capstone.domain.notice.dto.NoticeBatchDto;
import com.daejin.capstone.domain.notice.dto.request.RegisterNoticeRequest;
import com.daejin.capstone.domain.notice.dto.response.NoticeDetailResponse;
import com.daejin.capstone.domain.notice.dto.response.NoticeFileResponse;
import com.daejin.capstone.domain.notice.dto.response.NoticePreviewResponse;
import com.daejin.capstone.domain.notice.entity.Notice;
import com.daejin.capstone.domain.notice.entity.NoticeType;
import com.daejin.capstone.domain.notice.exception.PostNotFoundException;
import com.daejin.capstone.domain.notice.repository.NoticeRepository;
import com.daejin.capstone.domain.user.entity.User;
import com.daejin.capstone.domain.user.repository.UserRepository;
import com.daejin.capstone.global.config.FileStorage;
import com.daejin.capstone.global.exception.ErrorCode;
import com.daejin.capstone.global.exception.UserNotFoundException;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeService {

  private final FileRepository fileRepository;
  private final NoticeRepository noticeRepository;
  private final RestClient computerRestClient;
  private final NoticeBatch noticeBatch;
  private final UserRepository userRepository;

  private final FileStorage fileStorage;

  public Page<NoticePreviewResponse> getNoticePreview(Pageable pageable) {
    List<NoticeBatchDto> noticeBatchDtos = noticeBatch.get();

    // 대진대 공지사항글
    List<NoticePreviewResponse> daejinNoticePreviewResponses = noticeBatchDtos.stream()
        .map(noticeBatchDto -> {
          NoticePreviewResponse noticePreviewResponse = NoticePreviewResponse.createNoFile(
              null,
              noticeBatchDto.getTitle(),
              noticeBatchDto.getCreatedAt(),
              noticeBatchDto.getLink(),
              NoticeType.DAEJIN
          );
          return noticePreviewResponse;
        })
        .sorted(Comparator.comparing(NoticePreviewResponse::getCreatedAt).reversed())
        .toList();


    //서비스 공지사항 글
    List<NoticePreviewResponse> serviceNoticePreviewResponses = noticeRepository.findAll()
        .stream()
        .map(notice -> {
          NoticePreviewResponse noticePreviewResponse = NoticePreviewResponse
              .builder()
              .id(notice.getId())
              .title(notice.getTitle())
              .link(null)
              .createdAt(notice.getCreatedAt())
              .noticeType(NoticeType.SERVICE)
              .hasFile(!notice.getFiles().isEmpty())
              .build();
          return noticePreviewResponse;
        })
        .sorted(Comparator.comparing(NoticePreviewResponse::getCreatedAt).reversed())
        .toList();


    // 합친 뒤 정렬
    List<NoticePreviewResponse> merged = Stream.concat(
            daejinNoticePreviewResponses.stream(),
            serviceNoticePreviewResponses.stream()
        )
        .toList();

    // 페이징 처리
    int start = (int) pageable.getOffset();
    int end = Math.min(start + pageable.getPageSize(), merged.size());

    List<NoticePreviewResponse> pagedContent = start >= merged.size()
        ? List.of()
        : merged.subList(start, end);

    return new PageImpl<>(pagedContent, pageable, merged.size());

  }

  public NoticeDetailResponse getNoticeDetail(Long id) {
    Notice notice = noticeRepository.findById(id).orElseThrow(
        () -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND)
    );

    List<NoticeFileResponse> files = fileRepository.findByNotice_Id(id).stream()
        .map(file -> {
          return NoticeFileResponse.builder()
              .originalName(file.getOriginalName())
              .fileUrl(file.getFileUrl())
              .build();
        })
        .toList();

    NoticeDetailResponse response = NoticeDetailResponse.builder()
        .id(id)
        .title(notice.getTitle())
        .contents(notice.getContents())
        .createdAt(notice.getCreatedAt())
        .files(files)
        .build();

    return response;

  }

  public List<NoticeBatchDto> getDaejinNotice() {

    String endPoint = "/bbs/ce/606/artclList.do";

    MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
    formData.add("srchWrd", "졸업");
    formData.add("isViewMine", "false");
    formData.add("srchColumn", "sj");

    ResponseEntity<String> response = computerRestClient.post()
        .uri(endPoint)
        .contentType(MediaType.valueOf("application/x-www-form-urlencoded;charset=UTF-8"))
        .header("User-Agent", "Mozilla/5.0 (Linux; Android 15; Pixel 9) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/150.0.0.0 Mobile Safari/537.36")
        .body(formData)
        .retrieve()
        .toEntity(String.class);

    return parseNotices(response.getBody());

  }

  @Transactional
  public void registerNotice(RegisterNoticeRequest request, String uuid, List<MultipartFile> files) {

    User user = userRepository.findByUuid(uuid).orElseThrow(
        () -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND)
    );

    Notice notice = Notice.of(user, null, request.getTitle(), request.getContents());
    noticeRepository.save(notice);

    if(files != null) {
      List<File> fileEntities = files.stream()
          .map(file -> {
            String originalFileName = file.getOriginalFilename();
            return File.createNoticeFile(notice, fileStorage.store(file), originalFileName);
          })
          .toList();

      fileRepository.saveAll(fileEntities);
    }
  }


  private List<NoticeBatchDto> parseNotices(String html) {
    List<NoticeBatchDto> result = new ArrayList<>();

    if (html == null || html.isBlank()) {
      return result;
    }

    Document doc = Jsoup.parse(html);
    int currentYear = Year.now().getValue();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    Elements rows = doc.select("table.board-table tbody tr");

    for (Element row : rows) {
      try {
        Element titleLink = row.selectFirst("td.td-subject a");
        if (titleLink == null) continue;

        Element strong = titleLink.selectFirst("strong");
        if (strong == null) continue;

        String titleText = strong.text().trim();
        String href = titleLink.attr("href");
        String fullLink = href.startsWith("http") ? href : "https://ce.daejin.ac.kr" + href;

        Element dateCell = row.selectFirst("td.td-date");
        if (dateCell == null) continue;

        LocalDate date = LocalDate.parse(dateCell.text().trim(), formatter);

        // 일반공지 여부 확인
        Element noticeSpan = row.selectFirst("td.td-num span");
        boolean isNotice = noticeSpan != null && "일반공지".equals(noticeSpan.text().trim());

        String displayTitle;
        if (isNotice) {
          // 일반공지는 년도 상관없이 전부 포함
          displayTitle = String.format("[ 일반공지 ] %s", titleText);
        } else {
          // 일반 게시글은 올해 것만 포함
          if (date.getYear() != currentYear) continue;
          displayTitle = titleText;
        }

        result.add(NoticeBatchDto.builder()
            .title(displayTitle)
            .link(fullLink)
            .createdAt(date.atStartOfDay())
            .build());

      } catch (Exception e) {
        log.warn("공지사항 파싱 중 오류 발생, 해당 행 스킵: {}", e.getMessage());
      }
    }

    log.info("파싱된 공지사항 건수: {} (일반공지 + 올해 {} 일반 게시글)", result.size(), currentYear);
    return result;
  }



}
