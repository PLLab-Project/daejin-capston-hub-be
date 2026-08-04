package com.daejin.capstone.domain.notice.batch;

import com.daejin.capstone.domain.notice.dto.NoticeBatchDto;
import com.daejin.capstone.domain.notice.service.NoticeService;
import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class NoticeBatch {

  private List<NoticeBatchDto> noticeBatchDtos;
  private final RestClient computerRestClient;

  @PostConstruct
  @Scheduled(cron = "0 0 3 * * *", zone = "Asia/Seoul")
  public void noticeBatches() {
    log.info("공지사항 배치 갱신 시작");
    this.noticeBatchDtos = this.getDaejinNotice();
    for (NoticeBatchDto dto : this.noticeBatchDtos) {
      log.info(dto.getTitle() + " / " + dto.getLink() + " / " + dto.getCreatedAt().toString());
    }
    log.info("공지사항 배치 갱신 완료");
  }

  public List<NoticeBatchDto> get() {
    return this.noticeBatchDtos;
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
