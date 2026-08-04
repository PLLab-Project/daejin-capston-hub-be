package com.daejin.capstone.global.exception;


import com.daejin.capstone.global.common.response.ResponseDTO;
import com.daejin.capstone.global.security.exception.InvalidTypeJwtException;
import com.daejin.capstone.global.security.exception.TokenNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.Token;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

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
