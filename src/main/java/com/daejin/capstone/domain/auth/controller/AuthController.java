package com.daejin.capstone.domain.auth.controller;

import com.daejin.capstone.domain.auth.dto.LoginRequestDto;
import com.daejin.capstone.global.common.response.ResponseDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @PostMapping("/login")
  public ResponseDTO<?> login(@RequestBody LoginRequestDto loginRequestDto) {

  }

}
