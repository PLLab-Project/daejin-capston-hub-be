package com.daejin.capstone.domain.notice.entity;

import com.daejin.capstone.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(length = 255)
  private String title;

  @Lob
  private String contents;

  @Column(length = 255)
  private String fileUrl;

  @Builder
  private Notice(Long id, User user, String title, String contents, String fileUrl) {
    this.id = id;
    this.user = user;
    this.title = title;
    this.contents = contents;
    this.fileUrl = fileUrl;
  }

  public static Notice of(User user, String title, String contents, String fileUrl) {

    return Notice.builder()
        .id(null)
        .user(user)
        .title(title)
        .contents(contents)
        .fileUrl(fileUrl)
        .build();

  }


}
