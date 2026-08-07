package com.daejin.capstone.domain.user.controller;

import com.daejin.capstone.domain.user.dto.response.MypageResponse;
import com.daejin.capstone.domain.user.service.UserService;
import com.daejin.capstone.global.common.response.ResponseDTO;
import com.daejin.capstone.global.security.core.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @Tag(name = "마이페이지")
  @Operation(summary = "마이페이지에 표시되는 내 정보를 조회합니다.")
  @GetMapping("/mypage/me")
  public ResponseDTO<MypageResponse> getMypageInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {

    MypageResponse mypageResponse = userService.getMypageInfo(userDetails.getUuid());

    return ResponseDTO.of(mypageResponse, "내정보 조회에 성공하였습니다.");

  }

}
