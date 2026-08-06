package com.daejin.capstone.domain.project.dto.response;

import java.time.LocalDateTime;
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
public class ProjectAdminPreviewResponse {

  private Long projectId;
  private String title;
  private LocalDateTime createdAt;

}
