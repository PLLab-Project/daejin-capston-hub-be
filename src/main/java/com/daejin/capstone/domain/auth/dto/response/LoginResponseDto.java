package com.daejin.capstone.domain.auth.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponseDto {

  private String remainingTries;
  private boolean loginStatus;
  private boolean isNewUser;
  private String accessToken;
  private String refreshToken;

  @Builder
  private LoginResponseDto(String remainingTries, boolean loginStatus, boolean isNewUser, String accessToken, String refreshToken) {
    this.remainingTries = remainingTries;
    this.loginStatus = loginStatus;
    this.isNewUser = isNewUser;
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }

  public static LoginResponseDto createSuc(boolean isNewUser, String accessToken, String refreshToken) {
    return LoginResponseDto.builder()
        .remainingTries(null)
        .loginStatus(true)
        .isNewUser(isNewUser)
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
  }

  public static LoginResponseDto failSuc(String remainingTries) {
    return LoginResponseDto.builder()
        .remainingTries(remainingTries)
        .loginStatus(false)
        .isNewUser(false)
        .accessToken(null)
        .refreshToken(null)
        .build();
  }

}
