package com.daejin.capstone.domain.auth.service;

import com.daejin.capstone.domain.auth.dto.request.LoginRequestDto;
import com.daejin.capstone.domain.auth.dto.response.DaejinLoginResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

  private final RestClient restClient;

  private final String LOGIN_URI = "/subLogin/daejin/login.do";
  private final String FAIL_KEYWORD = "입력하신 계정정보가 올바르지 않습니다";
  private final String USER_NOT_FOUND_KEYWORD = "회원정보이(가) 존재 하지 않습니다";
  private final Pattern REMAINING_TRIES_PATTERN =
      Pattern.compile("(\\d+)회\\s*더\\s*잘못입력");

  public DaejinLoginResponse daejinLogin(LoginRequestDto loginRequestDto) {
    MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
    formData.add("layout", "");
    formData.add("pwdCrtfcNo", "");
    formData.add("pwdInputExcessYn", "");
    formData.add("userId2", "");
    formData.add("userId", loginRequestDto.getStdNum());
    formData.add("userPwd", loginRequestDto.getPassword());

    ResponseEntity<String> response = restClient.post()
        .uri(LOGIN_URI)
        .contentType(MediaType.valueOf("application/x-www-form-urlencoded;charset=UTF-8"))
        .header("User-Agent", "Mozilla/5.0 (Linux; Android 15; Pixel 9) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/150.0.0.0 Mobile Safari/537.36")
        .body(formData)
        .retrieve()
        .toEntity(String.class);

    return parseResponse(response);
  }

  private DaejinLoginResponse parseResponse(ResponseEntity<String> response) {
    String html = response.getBody();

    // 1. body가 없으면 (리다이렉트 등) 성공으로 간주
    if (html == null) {
      return DaejinLoginResponse.createSuc();
    }

    // 2. 회원 존재하지 않음 (우선순위 위)
    if (html.contains(USER_NOT_FOUND_KEYWORD)) {
      return DaejinLoginResponse.createUserNotFound(null);
    }

    // 3. 계정 정보 불일치 (비밀번호 틀림)
    if (html.contains(FAIL_KEYWORD)) {
      Matcher matcher = REMAINING_TRIES_PATTERN.matcher(html);
      String remainingTries = matcher.find() ? matcher.group(1) : "0";
      return DaejinLoginResponse.createFail(remainingTries);
    }

    // 4. 실패 키워드 없으면 성공
    return DaejinLoginResponse.createSuc();
  }
}