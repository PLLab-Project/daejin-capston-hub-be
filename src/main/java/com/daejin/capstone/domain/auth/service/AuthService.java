package com.daejin.capstone.domain.auth.service;

import com.daejin.capstone.domain.auth.dto.request.LoginRequestDto;
import com.daejin.capstone.domain.auth.dto.response.DaejinLoginResponse;
import com.daejin.capstone.domain.auth.dto.response.LoginResponseDto;
import com.daejin.capstone.domain.auth.entity.Auth;
import com.daejin.capstone.domain.auth.repository.AuthRepository;
import com.daejin.capstone.domain.user.entity.User;
import com.daejin.capstone.domain.user.repository.UserRepository;
import com.daejin.capstone.global.security.jwt.JwtTokenProvider;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final AuthRepository authRepository;

  private final RestClient restClient;
  private final JwtTokenProvider jwtTokenProvider;

  private final String LOGIN_URI = "/subLogin/daejin/login.do";
  private final String FAIL_KEYWORD = "입력하신 계정정보가 올바르지 않습니다";
  private final String USER_NOT_FOUND_KEYWORD = "회원정보이(가) 존재 하지 않습니다";
  private final Pattern REMAINING_TRIES_PATTERN =
      Pattern.compile("(\\d+)회\\s*더\\s*잘못입력");

  @Transactional
  public LoginResponseDto daejinLogin(LoginRequestDto loginRequestDto) {
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

    String stdNum = loginRequestDto.getStdNum();

    DaejinLoginResponse daejinLoginResponse = parseResponse(response);

    // 로그인에 실패했을 경우
    if(!daejinLoginResponse.isStatus()) {
      LoginResponseDto loginResponseDto = LoginResponseDto.failSuc(daejinLoginResponse.getRemainingTries());
      return loginResponseDto;
    }

    //로그인에 성공했을 경우
    User user = userRepository.findByStdNum(stdNum).orElse(null);

    boolean isNewUser = false;

    if(user == null) {
      // 로그인에 성공했지만 서비스 회원가입 이력이 없을 경우
      user = userRepository.save(User.createMember(UUID.randomUUID().toString(), loginRequestDto.getStdNum(), null, null));
      isNewUser = true;
    } else {
      if(user.getEmail() == null || user.getName() == null) {
        isNewUser = true;
      }
    }

    String newAccessToken = jwtTokenProvider.createAccessToken(user.getUuid(), user.getRole());
    String newRefreshToken = jwtTokenProvider.createRefreshToken(user.getUuid(), user.getRole());

    authFlow(user, newRefreshToken);

    LoginResponseDto loginResponseDto = LoginResponseDto.createSuc(isNewUser, newAccessToken, newRefreshToken);
    return loginResponseDto;
  }

  private void authFlow(User user, String refreshToken) {

    Auth auth = authRepository.findByUuid(user.getUuid()).orElse(null);

    if(auth == null) {
      authRepository.save(Auth.of(user.getUuid(), refreshToken));
      return;
    }

    auth.updateRefreshToken(refreshToken);
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