package com.daejin.capstone.doamin.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
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
