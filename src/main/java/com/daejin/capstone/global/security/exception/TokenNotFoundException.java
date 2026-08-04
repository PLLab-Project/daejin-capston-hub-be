package com.daejin.capstone.global.security.exception;

import com.daejin.capstone.global.exception.CustomException;
import com.daejin.capstone.global.exception.ErrorCode;

public class TokenNotFoundException extends CustomException {

  public TokenNotFoundException(ErrorCode message) {
    super(message);
  }
}
