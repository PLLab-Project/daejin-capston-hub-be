package com.daejin.capstone.global.security.config;

import com.daejin.capstone.domain.user.entity.UserRole;
import com.daejin.capstone.global.common.response.ResponseDTO;
import com.daejin.capstone.global.exception.ErrorCode;
import com.daejin.capstone.global.security.jwt.JwtTokenFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, ObjectMapper objectMapper,
      JwtTokenFilter jwtTokenFilter) throws Exception {

    // 인증 실패 시 응답 객체
    String invalidAuthenticationResponse = objectMapper
        .writeValueAsString(ResponseDTO.of(ErrorCode.UNAUTHORIZED));

    // 인가 실패 시 응답 객체
    String invalidAuthorizationResponse = objectMapper
        .writeValueAsString(ResponseDTO.of(ErrorCode.ACCESS_DENIED));

    http
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(
                "/users/**",
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/v3/api-docs/**",
                "/oauth2/**",
                "/login/**",
                "/auth/login",
                "/home/notice/**",
                "/files/**",
                "/home/project/search",
                "/home/project/detail/**",
                "/my-project/detail/**",
                "/home/category"
            ).permitAll()
            .requestMatchers(
                "/admin/**"
            ).hasRole(UserRole.PROF.toString())
            .anyRequest().authenticated()
        );

    http
        .exceptionHandling(e -> e
            .authenticationEntryPoint(((request, response, authException) -> {
              response.setContentType("application/json;charset=UTF-8");
              response.setStatus(HttpStatus.UNAUTHORIZED.value());
              response.setContentType("application/json");
              response.getWriter().write(invalidAuthenticationResponse);
            }
            ))

            .accessDeniedHandler((request, response, authException) -> {
              response.setContentType("application/json;charset=UTF-8");
              response.setStatus(HttpStatus.FORBIDDEN.value());
              response.setContentType("application/json");
              response.getWriter().write(invalidAuthorizationResponse);
            }));

    //jwt 필터추가
    http
        .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();


  }


}
