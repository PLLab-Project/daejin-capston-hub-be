package com.daejin.capstone.domain.notice.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
public class NoticePreviewResponse {

  private String title;
  private LocalDateTime createdAt;
  private boolean hasFile;

  public static NoticePreviewResponse createNoFile(String title, LocalDateTime createdAt) {
    return NoticePreviewResponse.builder()
        .title(title)
        .createdAt(createdAt)
        .hasFile(false)
        .build();
  }

  public static NoticePreviewResponse createYesFile(String title, LocalDateTime createdAt) {
    return NoticePreviewResponse.builder()
        .title(title)
        .createdAt(createdAt)
        .hasFile(true)
        .build();
  }

}
