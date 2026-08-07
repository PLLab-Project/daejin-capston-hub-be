package com.daejin.capstone.domain.bookmark.entity;

import com.daejin.capstone.domain.project.entity.Project;
import com.daejin.capstone.domain.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.awt.print.Book;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "project_id")
  private Project project;

  @Builder
  private Bookmark(Long id, User user, Project project) {
    this.id = id;
    this.user = user;
    this.project = project;
  }

  public static Bookmark of(User user, Project project) {
    return Bookmark.builder()
        .id(null)
        .user(user)
        .project(project)
        .build();
  }

}
