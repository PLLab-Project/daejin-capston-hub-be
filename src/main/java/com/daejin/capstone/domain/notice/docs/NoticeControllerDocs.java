package com.daejin.capstone.domain.notice.docs;

import com.daejin.capstone.domain.notice.dto.response.NoticePreviewResponse;
import com.daejin.capstone.global.common.response.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

public interface NoticeControllerDocs {


  @Tag(name = "공지사항")
  @Operation(summary = "공지사항 preview 조회 API 입니다. id 값이 null인 것은 대진대 공지사항 글 입니다.")
  ResponseDTO<List<NoticePreviewResponse>> getNoticePreview();

}
