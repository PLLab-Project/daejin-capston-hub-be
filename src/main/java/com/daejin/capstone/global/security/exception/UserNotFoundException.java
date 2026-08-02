package com.daejin.capstone.global.security.exception;


import com.daejin.capstone.global.exception.CustomException;
import com.daejin.capstone.global.exception.ErrorCode;

public class UserNotFoundException extends CustomException {

  public UserNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }
}
