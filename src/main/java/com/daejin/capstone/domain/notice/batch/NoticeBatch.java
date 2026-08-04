package com.daejin.capstone.domain.notice.batch;

import com.daejin.capstone.domain.notice.dto.NoticeBatchDto;
import com.daejin.capstone.domain.notice.service.NoticeService;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NoticeBatch {

  private final NoticeService noticeService;
  private List<NoticeBatchDto> noticeBatchDtos;

  @PostConstruct
  @Scheduled(cron = "0 0 3 * * *", zone = "Asia/Seoul")
  public void noticeBatches() {
    log.info("공지사항 배치 갱신 시작");
    this.noticeBatchDtos = noticeService.getDaejinNotice();
    for (NoticeBatchDto dto : this.noticeBatchDtos) {
      log.info(dto.getTitle() + " / " + dto.getLink() + " / " + dto.getCreatedAt().toString());
    }
    log.info("공지사항 배치 갱신 완료");
  }

  public List<NoticeBatchDto> get() {
    return this.noticeBatchDtos;
  }
}
