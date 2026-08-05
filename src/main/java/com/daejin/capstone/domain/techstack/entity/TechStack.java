package com.daejin.capstone.domain.techstack.entity;

import com.daejin.capstone.domain.project.entity.Project;
import jakarta.persistence.Entity;
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
public class TechStack {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "project_id")
  private Project project;

  private String stackName;

  @Builder
  private TechStack(Long id, Project project, String stackName) {
    this.id = id;
    this.project = project;
    this.stackName = stackName;
  }

  public static TechStack from(Project project, String stackName) {
    return TechStack.builder()
        .id(null)
        .project(project)
        .stackName(stackName)
        .build();
  }

}
