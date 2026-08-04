package com.daejin.capstone.domain.notice.controller;

import com.daejin.capstone.domain.notice.batch.NoticeBatch;
import com.daejin.capstone.domain.notice.docs.NoticeControllerDocs;
import com.daejin.capstone.domain.notice.dto.NoticeBatchDto;
import com.daejin.capstone.domain.notice.dto.response.NoticePreviewResponse;
import com.daejin.capstone.domain.notice.service.NoticeService;
import com.daejin.capstone.global.common.response.ResponseDTO;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/home/notice")
public class NoticeController implements NoticeControllerDocs {

  private final NoticeService noticeService;

  @Override
  @GetMapping("/preview")
  public ResponseDTO<List<NoticePreviewResponse>> getNoticePreview() {

    List<NoticePreviewResponse> noticePreviewResponses = noticeService.getNoticePreview();
    return ResponseDTO.of(noticePreviewResponses, "공지사항 조회에 성공하였습니다.");

  }

}
