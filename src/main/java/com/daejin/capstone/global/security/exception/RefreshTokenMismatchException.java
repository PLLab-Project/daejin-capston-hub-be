package com.daejin.capstone.global.security.exception;

import com.daejin.capstone.global.exception.CustomException;
import com.daejin.capstone.global.exception.ErrorCode;

public class RefreshTokenMismatchException extends CustomException {

  public RefreshTokenMismatchException(ErrorCode message) {
    super(message);
  }
}
