package com.daejin.capstone.domain.notice.controller;

import com.daejin.capstone.domain.notice.docs.NoticeControllerDocs;
import com.daejin.capstone.domain.notice.dto.request.RegisterNoticeRequest;
import com.daejin.capstone.domain.notice.dto.response.NoticeDetailResponse;
import com.daejin.capstone.domain.notice.dto.response.NoticePreviewResponse;
import com.daejin.capstone.domain.notice.service.NoticeService;
import com.daejin.capstone.global.common.response.ResponseDTO;
import com.daejin.capstone.global.dto.PageResponse;
import com.daejin.capstone.global.security.core.CustomUserDetails;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class NoticeController implements NoticeControllerDocs {

  private final NoticeService noticeService;

  @Override
  @GetMapping("/home/notice/search")
  public ResponseDTO<PageResponse<NoticePreviewResponse>> getNoticePreview(
      @Parameter(description = "제목으로 검색", example = "테스트")
      @RequestParam(defaultValue = "") String keyword,
      @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
      @RequestParam(defaultValue = "0") int page,
      @Parameter(description = "페이지 크기", example = "10")
      @RequestParam(defaultValue = "10") int size
  ) {
    Pageable pageable = PageRequest.of(page, size);
    Page<NoticePreviewResponse> noticePreviewResponses =
        noticeService.getNoticePreview(pageable, keyword);
    return ResponseDTO.of(
        PageResponse.from(noticePreviewResponses),
        "공지사항 목록 조회에 성공하였습니다."
    );
  }

  @Override
  @GetMapping("/home/notice/detail/{noticeId}")
  public ResponseDTO<NoticeDetailResponse> getNoticeDetail(@PathVariable Long noticeId) {
    NoticeDetailResponse noticeDetailResponse = noticeService.getNoticeDetail(noticeId);
    return ResponseDTO.of(noticeDetailResponse, "공지사항 상세 조회에 성공하였습니다.");
  }

  @Override
  @PostMapping(value = "/admin/notice", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseDTO<?> registerNotice(
      @RequestPart("request") RegisterNoticeRequest request,
      @RequestPart(value = "files", required = false) List<MultipartFile> files,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    noticeService.registerNotice(request, userDetails.getUuid(), files);
    return ResponseDTO.of("게시글 등록에 성공하였습니다.");
  }

}
