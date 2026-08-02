package com.daejin.capstone.doamin.auth.entity;

import com.daejin.capstone.global.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
    name = "auth",
    indexes = {
        @Index(name = "idx_auth_uuid", columnList = "uuid")
    }
)
public class Auth extends BaseEntity {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String uuid;

  private String refreshToken;

  @Builder
  private Auth(Long id, String uuid, String refreshToken) {
    this.id = id;
    this.uuid = uuid;
    this.refreshToken = refreshToken;
  }

  public static Auth of(String uuid, String refreshToken) {
    return Auth.builder()
        .id(null)
        .uuid(uuid)
        .refreshToken(refreshToken)
        .build();
  }

  public static Auth createAuth(String refreshToken) {
    return Auth.builder()
        .id(null)
        .uuid(UUID.randomUUID().toString())
        .refreshToken(refreshToken)
        .build();
  }

}
