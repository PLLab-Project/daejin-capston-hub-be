package com.daejin.capstone.domain.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DaejinLoginResponse {

  private String remainingTries;
  
  private boolean status;

  @Builder
  private DaejinLoginResponse(String remainingTries, boolean status) {
    this.remainingTries = remainingTries;
    this.status = status;
  }

  public static DaejinLoginResponse createSuc() {
    return DaejinLoginResponse.builder()
        .remainingTries(null)
        .status(true)
        .build();
  }

  public static DaejinLoginResponse createFail(String remainingTries) {
    return DaejinLoginResponse.builder()
        .remainingTries(remainingTries)
        .status(false)
        .build();
  }



}
