package com.daejin.capstone.domain.user.entity;

import com.daejin.capstone.domain.bookmark.entity.Bookmark;
import com.daejin.capstone.domain.notice.entity.Notice;
import com.daejin.capstone.domain.project.entity.Project;
import com.daejin.capstone.global.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 255)
  private UUID uuid;

  @Column(length = 255)
  private String stdNum;

  @Enumerated(EnumType.STRING)
  private UserRole role;

  @Column(length = 30)
  private String name;

  @Column(length = 255)
  private String email;

  @OneToMany(mappedBy = "user")
  private List<Bookmark> bookmarks = new ArrayList<>();

  @OneToMany(mappedBy = "user")
  private List<Project> projects = new ArrayList<>();

  @OneToMany(mappedBy = "user")
  private List<Notice> notices = new ArrayList<>();

  @Builder
  private User(Long id, UUID uuid, String stdNum, UserRole role, String name, String email) {
    this.id = id;
    this.uuid = uuid;
    this.stdNum = stdNum;
    this.role = role;
    this.name = name;
    this.email = email;
  }

  public static User createMember(UUID uuid, String stdNum, String name, String email) {

    return User.builder()
        .id(null)
        .uuid(uuid)
        .stdNum(stdNum)
        .name(name)
        .email(email)
        .role(UserRole.member)
        .build();
  }

  public static User createGuest(UUID uuid, String stdNum, String name, String email) {

    return User.builder()
        .id(null)
        .uuid(uuid)
        .stdNum(stdNum)
        .name(name)
        .email(email)
        .role(UserRole.guest)
        .build();
  }

  public static User createProf(UUID uuid, String stdNum, String name, String email) {

    return User.builder()
        .id(null)
        .uuid(uuid)
        .stdNum(stdNum)
        .name(name)
        .email(email)
        .role(UserRole.prof)
        .build();
  }

}
