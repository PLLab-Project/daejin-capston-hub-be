package com.daejin.capstone.domain.project.dto.request;

import com.daejin.capstone.domain.project.entity.ProjectStatus;
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
public class ProjectAdminReviewRequest {

  @Schema(
      description = "심사 상태",
      allowableValues = {"PENDING", "APPROVED", "REJECTED"},
      example = "APPROVED"
  )
  private ProjectStatus projectStatus;

}
