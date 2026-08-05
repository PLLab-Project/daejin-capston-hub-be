package com.daejin.capstone.domain.notice.exception;

import com.daejin.capstone.global.exception.CustomException;
import com.daejin.capstone.global.exception.ErrorCode;

public class PostNotFoundException extends CustomException {

  public PostNotFoundException(ErrorCode message) {
    super(message);
  }
}
