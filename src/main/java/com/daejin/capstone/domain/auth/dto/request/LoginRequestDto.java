package com.daejin.capstone.domain.auth.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequestDto {
  private String stdNum;
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
