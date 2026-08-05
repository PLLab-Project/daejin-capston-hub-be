package com.daejin.capstone.domain.file.entity;

import com.daejin.capstone.domain.notice.entity.Notice;
import com.daejin.capstone.domain.project.entity.Project;
import com.daejin.capstone.global.common.entity.BaseEntity;
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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File extends BaseEntity {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "project_id")
  private Project project;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "notice_id")
  private Notice notice;

  @Column(length = 255)
  private String fileUrl;

  @Column(length = 20)
  @Enumerated(EnumType.STRING)
  private FileType type;

  private Boolean thumbnail;

  @Builder
  private File(Long id, Project project, Notice notice, String fileUrl, FileType type, Boolean thumbnail) {
    this.id = id;
    this.project = project;
    this.notice = notice;
    this.fileUrl = fileUrl;
    this.type = type;
    this.thumbnail = thumbnail;
  }

  public static File of(Project project, Notice notice, String fileUrl, FileType type, Boolean thumbnail) {
    return File.builder()
        .id(null)
        .project(project)
        .notice(notice)
        .fileUrl(fileUrl)
        .type(type)
        .thumbnail(thumbnail)
        .build();
  }

  public static File createProjectFile(Project project, String fileUrl, FileType type, Boolean thumbnail) {
    return File.builder()
        .id(null)
        .project(project)
        .notice(null)
        .fileUrl(fileUrl)
        .type(type)
        .thumbnail(thumbnail)
        .build();
  }

  public static File createNoticeFile(Notice notice, String fileUrl) {
    return File.builder()
        .id(null)
        .project(null)
        .notice(notice)
        .fileUrl(fileUrl)
        .type(FileType.GENERAL)
        .thumbnail(false)
        .build();
  }


}
