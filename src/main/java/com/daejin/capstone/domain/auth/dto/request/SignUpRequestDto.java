package com.daejin.capstone.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpRequestDto {

  @Schema(example = "홍준표")
  private String name;

  @Schema(example = "test@test.test")
  private String email;

}
