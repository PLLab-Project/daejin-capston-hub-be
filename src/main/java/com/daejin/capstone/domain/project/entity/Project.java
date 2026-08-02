package com.daejin.capstone.domain.project.entity;

import com.daejin.capstone.domain.bookmark.entity.Bookmark;
import com.daejin.capstone.domain.category.entity.Category;
import com.daejin.capstone.domain.techstack.entity.TechStack;
import com.daejin.capstone.domain.user.entity.User;
import jakarta.persistence.Access;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project  {

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

  @Column(length = 255)
  private String demoVideoUrl;


  @OneToMany(mappedBy = "project")
  private List<TechStack> techStacks = new ArrayList<>();

  @Builder
  private Project(Long id, User user, Category category, String title,
      String summary, String demoVideoUrl) {
    this.id = id;
    this.user = user;
    this.category = category;
    this.title = title;
    this.summary = summary;
    this.demoVideoUrl = demoVideoUrl;
  }

  public static Project of(User user, Category category, String title, String summary, String demoVideoUrl) {
    return Project.builder()
        .id(null)
        .user(user)
        .category(category)
        .title(title)
        .summary(summary)
        .demoVideoUrl(demoVideoUrl)
        .build();
  }
}
