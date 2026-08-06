package com.daejin.capstone.domain.project.entity;

import com.daejin.capstone.domain.category.entity.Category;
import com.daejin.capstone.domain.file.entity.File;
import com.daejin.capstone.domain.techstack.entity.TechStack;
import com.daejin.capstone.domain.user.entity.User;
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
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project extends BaseEntity {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id")
  private Category category;

  @Column(length = 255)
  private String title;

  @Lob
  private String summary;

  @Lob
  private String description;

  @Column(length = 255)
  private String demoVideoUrl;

  @Column(length = 10)
  @Enumerated(EnumType.STRING)
  private ProjectStatus projectStatus;


  @OneToMany(mappedBy = "project")
  private List<TechStack> techStacks = new ArrayList<>();

  @OneToMany(mappedBy = "project")
  private List<File> files = new ArrayList<>();

  @Builder
  private Project(Long id, User user, Category category, String title,
      String summary, String demoVideoUrl, String description, ProjectStatus projectStatus) {
    this.id = id;
    this.user = user;
    this.category = category;
    this.title = title;
    this.summary = summary;
    this.demoVideoUrl = demoVideoUrl;
    this.description = description;
    this.projectStatus = projectStatus;
  }

  public static Project of(User user, Category category, String title, String summary,
      String demoVideoUrl, String description) {
    return Project.builder()
        .id(null)
        .user(user)
        .category(category)
        .title(title)
        .summary(summary)
        .demoVideoUrl(demoVideoUrl)
        .description(description)
        .projectStatus(ProjectStatus.PENDING)
        .build();
  }
}
