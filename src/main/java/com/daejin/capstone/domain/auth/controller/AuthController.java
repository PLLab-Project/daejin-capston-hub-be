package com.daejin.capstone.domain.auth.controller;

import com.daejin.capstone.domain.auth.dto.request.LoginRequestDto;
import com.daejin.capstone.domain.auth.dto.response.DaejinLoginResponse;
import com.daejin.capstone.domain.auth.entity.Auth;
import com.daejin.capstone.domain.auth.service.AuthService;
import com.daejin.capstone.global.common.response.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
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
  @Tag(name = "로그인")
  @Operation(summary = "학번과 비밀번호 입력 시 대진대학교 서버에 로그인 요청을 보냅니다.")
  @io.swagger.v3.oas.annotations.parameters.RequestBody(
      required = true,
      content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = LoginRequestDto.class),
          examples = {
              @ExampleObject(
                  name = "요청 예시",
                  value = """
                      {
                        "stdNum": "20211476",
                        "password": "password"
                      }
                      """,
                  description = "stdNum: 학번 / password: 대진대 포털 비밀번호"
              ),
              @ExampleObject(
                  name = "응답 예시(200)",
                  value = """
                      {
                        "data": {
                          "remainingTries": "2",
                          "status": false
                        },
                        "localDateTime": "2026-08-03T19:06:29.964883",
                        "message": "호출이 성공하였습니다.",
                        "responseCode": 200,
                        "statusCode": "SUCCESS"
                      }
                      """,
                  description = "remainingTries: 로그인 실패 시 남은 시도 횟수 / status: 로그인 성공 여부"
              )
          }
      )
  )
  public ResponseDTO<DaejinLoginResponse> login(@RequestBody LoginRequestDto loginRequestDto) {
    DaejinLoginResponse daejinLoginResponse = authService.daejinLogin(loginRequestDto);

    return ResponseDTO.of(daejinLoginResponse, "호출이 성공하였습니다.");

  }

}
