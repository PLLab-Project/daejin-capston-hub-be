package com.daejin.capstone.domain.auth.docs;

import com.daejin.capstone.domain.auth.dto.request.LoginRequestDto;
import com.daejin.capstone.domain.auth.dto.request.SignUpRequestDto;
import com.daejin.capstone.domain.auth.dto.response.LoginResponseDto;
import com.daejin.capstone.global.common.response.ResponseDTO;
import com.daejin.capstone.global.security.core.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthControllerDocs {

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
                  description = """
        | 필드 | 값 | 설명 |
        |------|-----|------|
        | `stdNum` | `20211476` | 학번 |
        | `password` | `password` | 대진대 포털 비밀번호 |
        """
              ),
              @ExampleObject(
                  name = "응답 예시(로그인 성공 / 기존 유저)",
                  value = """
        {
          "data": {
            "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyVX...",
            "loginStatus": true,
            "newUser": false,
            "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJ1...",
            "remainingTries": null
          },
          "localDateTime": "2026-08-04T07:34:43.796112",
          "message": "호출이 성공하였습니다.",
          "responseCode": 200,
          "statusCode": "SUCCESS"
        }
        """,
                  description = """
        | 필드 | 값 | 설명 |
        |------|-----|------|
        | `accessToken` | `eyJhbGciOi...` | 로그인이 필요한 API 호출 시 헤더에 포함 (Authorization: Bearer token..) |
        | `loginStatus` | `true` | 로그인 성공 여부 (true: 성공 / false: 실패) |
        | `newUser` | `false` | 새로운 유저인지 (true: 회원가입 / false: 로그인) |
        | `refreshToken` | `eyJhbGciOi...` | 쿠키에 저장해야함, 액세스토큰 만료 시 재발급 API 헤더에 포함시켜서 요청 |
        | `remainingTries` | `null` | 남은 로그인 시도횟수, 로그인을 성공 하였으므로 null 반환 |
        """
              ),
              @ExampleObject(
                  name = "응답 예시(로그인 성공 / 새로운 유저)",
                  value = """
        {
          "data": {
            "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyVX...",
            "loginStatus": true,
            "newUser": true,
            "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyVX...",
            "remainingTries": null
          },
          "localDateTime": "2026-08-04T07:45:25.14153",
          "message": "호출이 성공하였습니다.",
          "responseCode": 200,
          "statusCode": "SUCCESS"
        }
        """,
                  description = """
        | 필드 | 값 | 설명 |
        |------|-----|------|
        | `accessToken` | `eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyVX...` | 회원가입 API 호출 시 헤더에 포함 (Authorization: Bearer token..) |
        | `loginStatus` | `true` | 로그인 성공 여부 (true: 성공 / false: 실패) |
        | `newUser` | `true` | 새로운 유저인지, true 이므로 회원가입 필요 (true: 회원가입 / false: 로그인) |
        | `refreshToken` | `eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyVX...` | 쿠키에 저장해야함, 액세스토큰 만료 시 재발급 API 헤더에 포함시켜서 요청 |
        | `remainingTries` | `null` | 남은 로그인 시도횟수, 로그인을 성공 하였으므로 null |
        """
              ),
              @ExampleObject(
                  name = "응답 예시(로그인 실패 / 비밀번호 불일치)",
                  value = """
        {
          "data": {
            "accessToken": null,
            "loginStatus": false,
            "newUser": false,
            "refreshToken": null,
            "remainingTries": "4"
          },
          "localDateTime": "2026-08-04T07:47:36.139679",
          "message": "호출이 성공하였습니다.",
          "responseCode": 200,
          "statusCode": "SUCCESS"
        }
        """,
                  description = """
        | 필드 | 값 | 설명 |
        |------|-----|------|
        | `accessToken` | `null` | 로그인을 실패하였으므로 null 반환 |
        | `loginStatus` | `false` | 로그인 성공 여부 (true: 성공 / false: 실패) |
        | `newUser` | `false` | 새로운 유저인지, 로그인 실패 시 기본값 false (true: 회원가입 / false: 로그인) |
        | `refreshToken` | `null` | 로그인을 실패하였으므로 null 반환 |
        | `remainingTries` | `"4"` | 남은 로그인 시도횟수 |
        """
              ),
              @ExampleObject(
                  name = "응답 예시(로그인 실패 / 잘못된 학번 입력)",
                  value = """
        {
          "data": {
            "accessToken": null,
            "loginStatus": false,
            "newUser": false,
            "refreshToken": null,
            "remainingTries": null
          },
          "localDateTime": "2026-08-04T07:49:54.983017",
          "message": "호출이 성공하였습니다.",
          "responseCode": 200,
          "statusCode": "SUCCESS"
        }
        """,
                  description = """
        | 필드 | 값 | 설명 |
        |------|-----|------|
        | `accessToken` | `null` | 로그인을 실패하였으므로 null 반환 |
        | `loginStatus` | `false` | 로그인 성공 여부 (true: 성공 / false: 실패) |
        | `newUser` | `false` | 새로운 유저인지, 로그인 실패 시 기본값 false (true: 회원가입 / false: 로그인) |
        | `refreshToken` | `null` | 로그인을 실패하였으므로 null 반환 |
        | `remainingTries` | `null` | 남은 로그인 시도횟수, 학번이 존재하지 않으므로 시도 횟수 null 반환 |
        """
              )
          }
      )
  )
  ResponseDTO<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto);


  @Tag(name = "시작하기")
  @Operation(summary = "회원가입 API 입니다. 이름과 이메일 기입이 필요하며 API 호출 시 헤더에 엑세스토큰을 포함시켜주세요.")
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
                            "name": "홍준표",
                            "email": "test@test.test"
                          }
                          """,
                  description = """
                                | 필드 | 값 | 설명 |
                                |------|-----|------|
                                | `name` | `홍준표` | 회원가입에 필요한 이름 |
                                | `email` | `test@test.test` | 자신의 이메일 |
                                """
              ),
              @ExampleObject(
                  name = "응답 예시(200)",
                  value = """
                            {
                              "data": null,
                              "localDateTime": "2026-08-04T09:39:07.763214",
                              "message": "회원가입 성공",
                              "responseCode": 200,
                              "statusCode": "SUCCESS"
                            }
                          """,
                  description = """
                                | 필드 | 값 | 설명 |
                                |------|-----|------|
                                | `data` | `null` | 단순 회원가입 API 이므로 반환값 없음 |
                                """
              ),
              @ExampleObject(
                  name = "응답 예시(401 / 엑세스 토큰이 유효하지 않을 경우)",
                  value = """
                            {
                              "localDateTime": "2026-08-04T09:41:11.917451",
                              "responseCode": 1001,
                              "statusCode": "ACCESS_TOKEN_INVALID",
                              "message": "엑세스 토큰이 유효하지 않습니다.",
                              "data": null
                            }
                          """,
                  description = """
                                | 필드 | 값 | 설명 |
                                |------|-----|------|
                                | `data` | `null` | 반환값 없음 |
                                | `responseCode` | `1001` | 엑세스 토큰이 유효하지 않을 겨우 1001, 엑세스 토큰 재발급 API 실행해야함 |
                                """
              ),
              @ExampleObject(
                  name = "응답 예시(401 / 엑세스 토큰을 포함하지 않았거나 잘못 들어갔을 경우)",
                  value = """
                            {
                              "localDateTime": "2026-08-04T09:37:04.402702",
                              "responseCode": 401,
                              "statusCode": "UNAUTHORIZED",
                              "message": "인증이 필요합니다.",
                              "data": null
                            }
                          """,
                  description = """
                                | 필드 | 값 | 설명 |
                                |------|-----|------|
                                | `data` | `null` | 반환값 없음 |
                                | `responseCode` | `401` | 401번 그대로 반환 |
                                """
              )
          }
      )
  )
  ResponseDTO<?> signUp(SignUpRequestDto signUpRequestDto, CustomUserDetails customUserDetails);


}
