package com.daejin.capstone.domain.notice.controller;

import com.daejin.capstone.domain.notice.batch.NoticeBatch;
import com.daejin.capstone.domain.notice.docs.NoticeControllerDocs;
import com.daejin.capstone.domain.notice.dto.NoticeBatchDto;
import com.daejin.capstone.domain.notice.dto.request.RegisterNoticeRequest;
import com.daejin.capstone.domain.notice.dto.response.NoticeDetailResponse;
import com.daejin.capstone.domain.notice.dto.response.NoticePreviewResponse;
import com.daejin.capstone.domain.notice.service.NoticeService;
import com.daejin.capstone.global.common.response.ResponseDTO;
import com.daejin.capstone.global.security.core.CustomUserDetails;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class NoticeController implements NoticeControllerDocs {

  private final NoticeService noticeService;

  @Override
  @GetMapping("/home/notice/preview")
  public ResponseDTO<List<NoticePreviewResponse>> getNoticePreview() {

    List<NoticePreviewResponse> noticePreviewResponses = noticeService.getNoticePreview();
    return ResponseDTO.of(noticePreviewResponses, "공지사항 목록 조회에 성공하였습니다.");

  }

  @Override
  @GetMapping("/home/notice/detail/{id}")
  public ResponseDTO<NoticeDetailResponse> getNoticeDetail(@PathVariable Long id) {
    NoticeDetailResponse noticeDetailResponse = noticeService.getNoticeDetail(id);
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
