package com.daejin.capstone.domain.project.dto.response;

import java.time.LocalDateTime;
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
public class ProjectDetailResponse {

  private Long projectId;
  private String title;
  private String summary;
  private String name;
  private LocalDateTime createdAt;
  private String categoryName;
  private List<String> techStacks;
  private String demoVideoUrl;

  private String thumbnailImageFileUrl;
  private List<String> addImageFilesUrl;
  private String presentationReportFileUrl;
  private String descriptionReportFileUrl;
  private String projectZipFileUrl;

  private boolean bookMarked;
  private boolean mine;
}
