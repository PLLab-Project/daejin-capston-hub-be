package com.daejin.capstone.domain.project.entity;

import io.swagger.v3.oas.annotations.media.Schema;

public enum ProjectStatus {
  PENDING,    // 대기
  APPROVED,   // 승인됨
  REJECTED    // 반려됨
}
