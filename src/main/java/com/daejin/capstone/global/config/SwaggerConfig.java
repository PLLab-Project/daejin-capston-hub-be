package com.daejin.capstone.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    servers = {
        @Server(url = "https://api.woojins-house.com", description = "prod"),
        @Server(url = "http://localhost:8080", description = "local")
    }
)
@Configuration
public class SwaggerConfig {

  @Bean
  public GroupedOpenApi authApi() {
    return GroupedOpenApi.builder()
        .group("🔐 로그인")
        .pathsToMatch("/auth/**")
        .addOpenApiCustomizer(openApi -> openApi.info(new Info()
            .title("🔐 로그인")
            .description("""
              ## 아래 프롬프트는 현재 API 에 대한 설명글 입니다.
              <details>
              <summary><b>프롬프트</b> (클릭하여 펼치기)</summary>
              
              ```
                # JWT 기반 인증/인가 흐름 정보
                
                ## 서버 정보
                
                운영: https://api.woojins-house.com
                
                ## 토큰 개념
                
                Access Token: 인증이 필요한 모든 API 호출 시 헤더에 포함. 유효기간 1시간.
                Refresh Token: Access Token 만료 시 재발급받기 위한 토큰. 유효기간 7일.
                
                ## 인증 헤더 형식
                
                Authorization: Bearer {token}
                
                ## 응답 공통 구조
                
                {"data": {}, "localDateTime": "2026-08-04T09:39:07", "message": "호출이 성공하였습니다.", "responseCode": 200, "statusCode": "SUCCESS"}
                
                ## responseCode 규칙
                
                200: 성공
                401: 헤더에 토큰이 없거나 형식이 잘못됨 (Bearer 누락 등)
                1001 (ACCESS_TOKEN_INVALID): 액세스 토큰 무효 → /auth/refresh 호출해서 재발급
                1002 (REFRESH_TOKEN_INVALID): 리프레시 토큰 무효 → 재로그인 필요 → 로그인 완료 후 발급되는 엑세스, 리프레시 토큰으로 전부 교체
                
                # API 목록
                
                ## 1. 로그인: POST /auth/login
                
                학번과 비밀번호로 대진대학교 서버에 로그인 요청. (/auth/login api에는 헤더에 토큰 미포함)
                
                Request Body: {"stdNum": "20211476", "password": "password"}
                stdNum: 학번, password: 대진대 포털 비밀번호
                
                Response 케이스:
                
                ① 로그인 성공 / 기존 유저
                {"data": {"accessToken": "eyJhbGciOi...", "refreshToken": "eyJhbGciOi...", "loginStatus": true, "newUser": false, "remainingTries": null, "role": "MEMBER"}, "responseCode": 200, "statusCode": "SUCCESS"}
                → 두 토큰 저장 후 홈 화면으로 이동.
                
                ② 로그인 성공 / 새로운 유저
                {"data": {"accessToken": "eyJhbGciOi...", "refreshToken": "eyJhbGciOi...", "loginStatus": true, "newUser": true, "remainingTries": null, "role": "MEMBER"}, "responseCode": 200, "statusCode": "SUCCESS"}
                → newUser: true면 회원가입 화면으로 이동. 받은 accessToken을 회원가입 API 헤더에 사용.
                
                ③ 로그인 실패 / 비밀번호 불일치
                {"data": {"accessToken": null, "refreshToken": null, "loginStatus": false, "newUser": false, "remainingTries": "4"}}
                → remainingTries만큼 시도 남음.
                
                ④ 로그인 실패 / 존재하지 않는 학번
                {"data": {"accessToken": null, "refreshToken": null, "loginStatus": false, "newUser": false, "remainingTries": null}}
                → loginStatus: false이고, remainingTries: null이면 학번 자체가 존재하지 않음.
                
                응답 필드:
                accessToken: 인증 API 호출 시 Authorization: Bearer {token} 헤더에 포함
                refreshToken: 액세스 토큰 만료 시 재발급용
                loginStatus: 로그인 성공 여부 (true: 성공 / false: 실패)
                newUser: true면 회원가입 필요, false면 기존 유저
                remainingTries: 로그인 실패 시 남은 시도 횟수.
                "role": 로그인 시 해당 사용자 권한을 나타냄 MEMBER, ADMIN 존재 / ADMIN 일 경우 관리자 \s
                
                ## 2. 회원가입: POST /auth/signup
                
                이름/이메일 입력받아 회원가입. 로그인 응답의 accessToken을 헤더에 포함해야 함.
                
                (/auth/signup api에는 헤더에 엑세스 토큰 포함)
                
                Header: Authorization: Bearer {accessToken}
                Request Body: {"name": "홍준표", "email": "test@test.test"}
                name: 회원가입에 필요한 이름, email: 자신의 이메일
                
                Response 케이스:
                
                ① 회원가입 성공 (200)
                {"data": null, "message": "회원가입 성공", "responseCode": 200, "statusCode": "SUCCESS"}
                
                ② 액세스 토큰 무효 (1001)
                {"responseCode": 1001, "statusCode": "ACCESS_TOKEN_INVALID", "message": "엑세스 토큰이 유효하지 않습니다.", "data": null}
                → /auth/refresh 호출해서 새 액세스 토큰 받고 이 API 재시도.
                
                ③ 인증 헤더 없음/형식 오류 (401)
                {"responseCode": 401, "statusCode": "UNAUTHORIZED", "message": "인증이 필요합니다.", "data": null}
                → Authorization 헤더 자체가 누락됐거나 Bearer 접두사가 빠짐.
                
                ## 3. 액세스 토큰 재발급: POST /auth/refresh
                
                액세스 토큰이 무효(1001) 응답을 받았을 때 호출. 헤더에 리프레시 토큰 포함.
                
                (/auth/refresh api에는 헤더에 리프레시 토큰 포함)
                
                Header: Authorization: Bearer {refreshToken}
                Request Body: 없음
                
                Response 케이스:
                
                ① 재발급 성공 (200)
                {"data": {"accessToken": "eyJhbGciOi..."}, "message": "엑세스 토큰 발급 성공", "responseCode": 200, "statusCode": "SUCCESS"}
                → 새 accessToken 저장 후 원래 실패했던 API 재시도.
                
                ② 리프레시 토큰 무효 (1002)
                {"responseCode": 1002, "statusCode": "REFRESH_TOKEN_INVALID", "message": "리프레시 토큰이 유효하지 않습니다.", "data": null}
                → 재로그인 필수. 저장된 토큰 전부 삭제하고 로그인 화면으로 이동.
                
                ③ 인증 헤더 없음/형식 오류 (401)
                {"responseCode": 401, "statusCode": "UNAUTHORIZED", "message": "인증이 필요합니다.", "data": null}
                
                # 전체 흐름
                
                케이스 A (신규 유저 회원가입): POST /auth/login → loginStatus: true, newUser: true, 토큰 발급 → POST /auth/signup (Authorization: Bearer {accessToken}, 이름/이메일) → 회원가입 완료 → 이후 인증 API는 accessToken을 헤더에 담아 호출
                
                케이스 B (기존 유저 로그인): POST /auth/login → loginStatus: true, newUser: false, 토큰 발급 → 이후 인증 API는 accessToken을 헤더에 담아 호출
                
                케이스 C (로그인 실패 / 비밀번호 틀림): POST /auth/login → loginStatus: false, remainingTries: "4" → 요청이 시도 횟수가 4회 남은거임
                
                케이스 D (로그인 실패 / 학번 없음): POST /auth/login → loginStatus: false, remainingTries: null → "존재하지 않는 학번" 임
                
                케이스 E (Access Token 만료): 일반 API 호출 → responseCode: 1001 → POST /auth/refresh (Authorization: Bearer {refreshToken}) → 새 accessToken 발급 → 원래 요청 재시도
                
                케이스 F (Refresh Token까지 무효): POST /auth/refresh → responseCode: 1002 → 저장된 토큰 전부 삭제 + 로그인 화면으로 이동 후 재발급받은 엑세스, 리프레시 토큰으로 전부 교체
                
                케이스 G (헤더 자체가 문제): responseCode: 401 → Authorization 헤더 자체 문제 (누락, Bearer 접두사 빠짐 등). 프론트 코드 오류일 가능성이 높음
                
                # 프론트 구현 시 주의사항
                
                모든 인증 API 호출 시 응답의 responseCode를 확인해서 분기: 1001이면 refresh 호출 후 원래 요청 재시도, 1002면 로그인 페이지로 이동, 로그인 응답의 newUser 값으로 회원가입 화면 이동 여부 결정.
              ```
              
              </details>
              """)
        ))
        .build();
  }

  @Bean
  public GroupedOpenApi homeApi() {
    return GroupedOpenApi.builder()
        .group("🏠 홈")
        .pathsToMatch("/home/**")
        .addOpenApiCustomizer(openApi -> openApi.info(new Info()
            .title("🏠 홈")
            .description(
                """
                ## 아래 프롬프트는 현재 API 에 대한 설명글 입니다.
                
                <details>
                <summary><b>프롬프트</b> (클릭하여 펼치기)</summary>
                
                ```
                ㅇ
                ```
                
                </details>
                """)
        ))
        .build();
  }

  @Bean
  public GroupedOpenApi mypageApi() {
    return GroupedOpenApi.builder()
        .group("👤 마이페이지")
        .pathsToMatch("/mypage/**")
        .addOpenApiCustomizer(openApi -> openApi.info(new Info()
            .title("👤 마이페이지")
            .description(
                """
                ## 아래 프롬프트는 현재 API 에 대한 설명글 입니다.
                
                <details>
                <summary><b>프롬프트</b> (클릭하여 펼치기)</summary>
                
                ```
                ㅇ
                ```
                
                </details>
                """)
          ))
        .build();
  }

  @Bean
  public GroupedOpenApi myprojectApi() {
    return GroupedOpenApi.builder()
        .group("🎨 내 작품")
        .pathsToMatch("/")
        .addOpenApiCustomizer(openApi -> openApi.info(new Info()
            .title("🎨 내 작품")
            .description(
                """
                ## 아래 프롬프트는 현재 API 에 대한 설명글 입니다.
                
                <details>
                <summary><b>프롬프트</b> (클릭하여 펼치기)</summary>
                
                ```
                ㅇ
                ```
                
                </details>
                """)
        ))
        .build();
  }

  @Bean
  public GroupedOpenApi starApi() {
    return GroupedOpenApi.builder()
        .group("⭐ 즐겨찾기")
        .pathsToMatch("/bookmark/**")
        .addOpenApiCustomizer(openApi -> openApi.info(new Info()
            .title("⭐ 즐겨찾기")
            .description(
                """
                ## 아래 프롬프트는 현재 API 에 대한 설명글 입니다.
                
                <details>
                <summary><b>프롬프트</b> (클릭하여 펼치기)</summary>
                
                ```
                ㅇ
                ```
                
                </details>
                """)
        ))
        .build();
  }

  @Bean
  public GroupedOpenApi adminApi() {
    return GroupedOpenApi.builder()
        .group("🛠️ 관리자")
        .pathsToMatch("/admin/**")
        .addOpenApiCustomizer(openApi -> openApi.info(new Info()
            .title("🛠️ 관리자")
            .description(
                """
                ## 아래 프롬프트는 현재 API 에 대한 설명글 입니다.
                
                <details>
                <summary><b>프롬프트</b> (클릭하여 펼치기)</summary>
                
                ```
                ㅇ
                ```
                
                </details>
                """)
        ))
        .build();
  }

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .components(new Components()
            .addSecuritySchemes("bearerAuth",
                new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("Bearer")
                    .bearerFormat("JWT")
                    .in(SecurityScheme.In.HEADER)
                    .name("Authorization"))
        )
        .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
        .info(apiInfo());
  }

  private Info apiInfo() {
    return new Info()
        .title("capstone API")
        .description("대진대 졸업작품 서비스 서버 입니다..")
        .version("1.0.0");
  }

}
