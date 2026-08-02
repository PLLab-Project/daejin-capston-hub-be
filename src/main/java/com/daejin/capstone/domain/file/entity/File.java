package com.daejin.capstone.domain.file.entity;

import com.daejin.capstone.domain.project.entity.Project;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "project_id")
  private Project project;

  @Column(length = 255)
  private String fileUrl;

  @Enumerated(EnumType.STRING)
  private FileType type;

  private Boolean thumbnail;

  @Builder
  private File(Long id, Project project, String fileUrl, FileType type, Boolean thumbnail) {
    this.id = id;
    this.project = project;
    this.fileUrl = fileUrl;
    this.type = type;
    this.thumbnail = thumbnail;
  }

  public static File of(Project project, String fileUrl, FileType type, Boolean thumbnail) {
    return File.builder()
        .id(null)
        .project(project)
        .fileUrl(fileUrl)
        .type(type)
        .thumbnail(thumbnail)
        .build();
  }


}
