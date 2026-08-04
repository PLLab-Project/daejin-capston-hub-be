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
        .build();
  }

  @Bean
  public GroupedOpenApi homeApi() {
    return GroupedOpenApi.builder()
        .group("🏠 홈")
        .pathsToMatch("/")
        .build();
  }

  @Bean
  public GroupedOpenApi mypageApi() {
    return GroupedOpenApi.builder()
        .group("👤 마이페이지")
        .pathsToMatch("/")
        .build();
  }

  @Bean
  public GroupedOpenApi myprojectApi() {
    return GroupedOpenApi.builder()
        .group("🎨 내 작품")
        .pathsToMatch("/")
        .build();
  }

  @Bean
  public GroupedOpenApi starApi() {
    return GroupedOpenApi.builder()
        .group("⭐ 즐겨찾기")
        .pathsToMatch("/")
        .build();
  }

  @Bean
  public GroupedOpenApi adminApi() {
    return GroupedOpenApi.builder()
        .group("🛠️ 관리자")
        .pathsToMatch("/")
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
