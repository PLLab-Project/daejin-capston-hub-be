package com.daejin.capstone.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequestDto {

  @Schema(example = "20211476")
  private String stdNum;

  @Schema(example = "password")
  private String password;

  @Builder
  private LoginRequestDto(String stdNum, String password) {
    this.stdNum = stdNum;
    this.password = password;
  }

  public static LoginRequestDto of(String stdNum, String password) {
    return LoginRequestDto.builder()
        .stdNum(stdNum)
        .password(password)
        .build();
  }
}
