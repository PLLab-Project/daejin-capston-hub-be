package com.daejin.capstone.global.security.exception;


import com.daejin.capstone.global.exception.CustomException;
import com.daejin.capstone.global.exception.ErrorCode;

public class InvalidAccessJwtException extends CustomException {

  public InvalidAccessJwtException(ErrorCode errorCode) {
    super(errorCode);
  }
}
