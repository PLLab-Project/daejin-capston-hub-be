package com.daejin.capstone.domain.project.dto.request;

import java.util.List;
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
public class RegisterProjectRequest {

  private String title;
  private String summary;
  private Long categoryId;
  private List<String> techStacks;
  private String description;
  private String demoVideoUrl;
}
