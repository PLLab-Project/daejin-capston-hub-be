package com.daejin.capstone.domain.notice.entity;

import com.daejin.capstone.domain.file.entity.File;
import com.daejin.capstone.domain.user.entity.User;
import com.daejin.capstone.global.common.entity.BaseEntity;
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
public class Notice extends BaseEntity {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "notice")
  private List<File> files = new ArrayList<>();

  @Column(length = 255)
  private String title;

  @Lob
  private String contents;

  @Builder
  private Notice(Long id, User user, List<File> files, String title, String contents) {
    this.id = id;
    this.user = user;
    this.files = files;
    this.title = title;
    this.contents = contents;
  }

  public static Notice of(User user, List<File> files, String title, String contents) {

    return Notice.builder()
        .id(null)
        .files(files)
        .user(user)
        .title(title)
        .contents(contents)
        .build();

  }


}
