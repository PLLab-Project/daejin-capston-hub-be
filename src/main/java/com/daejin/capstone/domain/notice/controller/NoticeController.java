package com.daejin.capstone.domain.notice.controller;

import com.daejin.capstone.domain.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NoticeController {

  private final NoticeService noticeService;



}
