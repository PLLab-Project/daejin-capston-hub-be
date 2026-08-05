package com.daejin.capstone.domain.category.exception;

import com.daejin.capstone.global.exception.CustomException;
import com.daejin.capstone.global.exception.ErrorCode;

public class CategoryNotFoundException extends CustomException {

  public CategoryNotFoundException(ErrorCode message) {
    super(message);
  }
}
