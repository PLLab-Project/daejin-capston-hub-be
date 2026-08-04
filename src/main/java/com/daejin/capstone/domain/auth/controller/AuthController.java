package com.daejin.capstone.domain.auth.controller;

import com.daejin.capstone.domain.auth.docs.AuthControllerDocs;
import com.daejin.capstone.domain.auth.dto.request.LoginRequestDto;
import com.daejin.capstone.domain.auth.dto.request.SignUpRequestDto;
import com.daejin.capstone.domain.auth.dto.response.LoginResponseDto;
import com.daejin.capstone.domain.auth.service.AuthService;
import com.daejin.capstone.global.common.response.ResponseDTO;
import com.daejin.capstone.global.security.core.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController implements AuthControllerDocs {

  private final AuthService authService;

  @Override
  @PostMapping("/login")
  public ResponseDTO<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
    LoginResponseDto loginResponseDto = authService.daejinLogin(loginRequestDto);

    return ResponseDTO.of(loginResponseDto, "호출이 성공하였습니다.");

  }

  @Override
  @PostMapping("/signup")
  public ResponseDTO<?> signUp(@RequestBody SignUpRequestDto signUpRequestDto,
      @AuthenticationPrincipal CustomUserDetails customUserDetails) {

    String uuid = customUserDetails.getUuid();

    authService.signUp(uuid, signUpRequestDto);
    return ResponseDTO.of("회원가입 성공");
  }

}
