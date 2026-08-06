package com.daejin.capstone.domain.project.dto.response;

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
public class ProjectPreviewResponse {

  private Long projectId;
  private String thumbnailUrl;
  private String title;
  private String summary;
  private String uploadUserName;
  private String createdAt;

}
