package com.daejin.capstone.domain.auth.controller;

import com.daejin.capstone.domain.auth.dto.request.LoginRequestDto;
import com.daejin.capstone.domain.auth.dto.response.DaejinLoginResponse;
import com.daejin.capstone.domain.auth.entity.Auth;
import com.daejin.capstone.domain.auth.service.AuthService;
import com.daejin.capstone.global.common.response.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

  private final AuthService authService;

  @PostMapping("/login")
  public ResponseDTO<?> login(@RequestBody LoginRequestDto loginRequestDto) {
    DaejinLoginResponse daejinLoginResponse = authService.daejinLogin(loginRequestDto);

    return ResponseDTO.of(daejinLoginResponse, "호출이 성공하였습니다.");

  }

}
