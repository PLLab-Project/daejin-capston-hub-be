package com.daejin.capstone.domain.user.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MypageResponse {

  private String name;
  private String stdNum;
  private String email;

  private List<MypageProjectResponse> myProjects;
  private List<MypageProjectResponse> myBookmarkProjects;
}
