package com.daejin.capstone.domain.project.exception;

import com.daejin.capstone.global.exception.CustomException;
import com.daejin.capstone.global.exception.ErrorCode;

public class ProjectNotFoundException extends CustomException {

  public ProjectNotFoundException(ErrorCode message) {
    super(message);
  }
}
