package com.daejin.capstone.global.security.exception;


import com.daejin.capstone.global.exception.CustomException;
import com.daejin.capstone.global.exception.ErrorCode;

public class TokenExtractionException extends CustomException {

  public TokenExtractionException(ErrorCode errorCode) {
    super(errorCode);
  }
}
