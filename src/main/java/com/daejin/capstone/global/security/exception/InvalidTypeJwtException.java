package com.daejin.capstone.global.security.exception;

import com.daejin.capstone.global.exception.CustomException;
import com.daejin.capstone.global.exception.ErrorCode;

public class InvalidTypeJwtException extends CustomException {
  public InvalidTypeJwtException(ErrorCode errorCode) {
    super(errorCode);
  }
}
