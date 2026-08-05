package com.daejin.capstone.domain.notice.dto.response;

import com.daejin.capstone.domain.notice.entity.NoticeType;
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

  private Long noticeId;
  private String title;
  private LocalDateTime createdAt;
  private String link;
  private NoticeType noticeType;
  private boolean hasFile;

  public static NoticePreviewResponse createNoFile(Long id, String title, LocalDateTime createdAt, String link, NoticeType noticeType) {
    return NoticePreviewResponse.builder()
        .noticeId(id)
        .title(title)
        .createdAt(createdAt)
        .hasFile(false)
        .link(link)
        .noticeType(noticeType)
        .build();
  }

  public static NoticePreviewResponse createYesFile(Long id, String title, LocalDateTime createdAt, String link, NoticeType noticeType) {
    return NoticePreviewResponse.builder()
        .title(title)
        .createdAt(createdAt)
        .hasFile(true)
        .link(link)
        .noticeType(noticeType)
        .build();
  }

}
