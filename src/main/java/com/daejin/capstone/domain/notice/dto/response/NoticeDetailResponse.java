package com.daejin.capstone.domain.notice.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeDetailResponse {

  private Long id;
  private String title;
  private String contents;
  private LocalDateTime createdAt;
  private List<String> fileUrl;
}
