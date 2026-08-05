package com.daejin.capstone.domain.notice.docs;

import com.daejin.capstone.domain.notice.dto.request.RegisterNoticeRequest;
import com.daejin.capstone.domain.notice.dto.response.NoticePreviewResponse;
import com.daejin.capstone.global.common.response.ResponseDTO;
import com.daejin.capstone.global.security.core.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface NoticeControllerDocs {


  @Tag(name = "공지사항")
  @Operation(summary = "공지사항 preview 조회 API 입니다. id 값이 null인 것은 대진대 공지사항 글 입니다.")
  ResponseDTO<List<NoticePreviewResponse>> getNoticePreview();

  @Tag(name = "공지등록")
  @Operation(summary = "공지등록 API 입니다. 파일 전송이 필요하므로 Multipart formdata 형식으로 요청이 필요합니다.")
  ResponseDTO<?> registerNotice(RegisterNoticeRequest request,
      List<MultipartFile> files, CustomUserDetails userDetails);

}
