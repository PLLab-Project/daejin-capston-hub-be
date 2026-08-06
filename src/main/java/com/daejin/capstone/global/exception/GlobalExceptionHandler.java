package com.daejin.capstone.global.exception;


import com.daejin.capstone.domain.category.exception.CategoryNotFoundException;
import com.daejin.capstone.domain.notice.exception.PostNotFoundException;
import com.daejin.capstone.domain.project.exception.ProjectNotFoundException;
import com.daejin.capstone.global.common.response.ResponseDTO;
import com.daejin.capstone.global.security.exception.InvalidTypeJwtException;
import com.daejin.capstone.global.security.exception.TokenNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(CategoryNotFoundException.class)
  public ResponseEntity<ResponseDTO> categoryNotFoundException(CategoryNotFoundException e) {
    log.error("저장된 해당 분야가 없습니다.", e);
    return ResponseEntity
        .status(ErrorCode.CATEGORY_NOT_FOUND.getActualStatusCode())
        .body(ResponseDTO.of(ErrorCode.CATEGORY_NOT_FOUND));
  }

  @ExceptionHandler(ProjectNotFoundException.class)
  public ResponseEntity<ResponseDTO> projectNotFoundException(ProjectNotFoundException e) {
    log.error("저장된 작품이 없습니다.", e);
    return ResponseEntity
        .status(ErrorCode.PROJECT_NOT_FOUND.getActualStatusCode())
        .body(ResponseDTO.of(ErrorCode.PROJECT_NOT_FOUND));
  }

  @ExceptionHandler(PostNotFoundException.class)
  public ResponseEntity<ResponseDTO> postNotFoundException(PostNotFoundException e) {
    log.error("해당 게시글이 존재하지 않습니다.", e);
    return ResponseEntity
        .status(ErrorCode.POST_NOT_FOUND.getActualStatusCode())
        .body(ResponseDTO.of(ErrorCode.POST_NOT_FOUND));
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ResponseDTO> userNotFoundException(UserNotFoundException e) {
    log.error("유저를 찾을 수 없습니다.", e);
    return ResponseEntity
        .status(ErrorCode.USER_NOT_FOUND.getActualStatusCode())
        .body(ResponseDTO.of(ErrorCode.USER_NOT_FOUND));
  }

  @ExceptionHandler(TokenNotFoundException.class)
  public ResponseEntity<ResponseDTO> tokenNotFoundException(TokenNotFoundException e) {
    log.error("토큰이 존재하지 않습니다.", e);
    return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body(ResponseDTO.of(ErrorCode.UNAUTHORIZED));
  }

  @ExceptionHandler(InvalidTypeJwtException.class)
  public ResponseEntity<ResponseDTO> invalidTypeJwtException(InvalidTypeJwtException e) {
    log.error("jwt타입이 일치하지 않습니다.", e);
    return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body(ResponseDTO.of(ErrorCode.REFRESH_TOKEN_INVALID));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ResponseDTO> handleException(Exception e) {
    log.error("처리되지 않은 예외 발생", e);
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ResponseDTO.of(ErrorCode.INTERNAL_SERVER_ERROR));
  }

}
