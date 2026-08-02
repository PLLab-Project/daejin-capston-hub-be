package com.daejin.capstone.global.security.exception;


import com.daejin.capstone.global.exception.CustomException;
import com.daejin.capstone.global.exception.ErrorCode;

public class InvalidRefrashJwtException extends CustomException {

  public InvalidRefrashJwtException(ErrorCode errorCode) {
    super(errorCode);
  }
}
