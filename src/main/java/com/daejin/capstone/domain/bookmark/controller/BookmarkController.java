package com.daejin.capstone.domain.bookmark.controller;

import com.daejin.capstone.domain.bookmark.dto.response.ToggleBookmarkResponse;
import com.daejin.capstone.domain.bookmark.service.BookmarkService;
import com.daejin.capstone.global.common.response.ResponseDTO;
import com.daejin.capstone.global.security.core.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmark")
public class BookmarkController {

  private final BookmarkService bookmarkService;

  @Tag(name = "즐겨찾기")
  @Operation(summary = "작품 즐겨찾기 API 입니다. 토글 방식이며 응답으로 바뀐 상태를 반환합니다.")
  @PostMapping("/{projectId}/toggle")
  public ResponseDTO<ToggleBookmarkResponse> addBookmark(
      @PathVariable Long projectId,
      @AuthenticationPrincipal CustomUserDetails userDetails) {

    String uuid = userDetails.getUuid();

    ToggleBookmarkResponse toggleBookmarkResponse = bookmarkService.addBookmark(projectId, uuid);

    return ResponseDTO.of(toggleBookmarkResponse, "요청에 성공하였습니다.");

  }




}
