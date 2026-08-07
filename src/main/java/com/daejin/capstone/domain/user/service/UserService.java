package com.daejin.capstone.domain.user.service;

import com.daejin.capstone.domain.bookmark.entity.Bookmark;
import com.daejin.capstone.domain.bookmark.repository.BookMarkRepository;
import com.daejin.capstone.domain.project.entity.Project;
import com.daejin.capstone.domain.project.repository.ProjectRepository;
import com.daejin.capstone.domain.user.dto.response.MypageProjectResponse;
import com.daejin.capstone.domain.user.dto.response.MypageResponse;
import com.daejin.capstone.domain.user.entity.User;
import com.daejin.capstone.domain.user.repository.UserRepository;
import com.daejin.capstone.global.exception.ErrorCode;
import com.daejin.capstone.global.exception.UserNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final BookMarkRepository bookMarkRepository;
  private final ProjectRepository projectRepository;

  public MypageResponse getMypageInfo(String uuid) {
    User user = userRepository.findByUuid(uuid).orElseThrow(
        () -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND)
    );

    List<Project> myProjectsEntities = projectRepository.findALlByUser(user);

    List<Project> myBookmarkProjects = bookMarkRepository.findALlByUser(user).stream()
        .map(Bookmark::getProject)
        .toList();

    MypageResponse response = MypageResponse.builder()
        .name(user.getName())
        .stdNum(user.getStdNum())
        .email(user.getEmail())
        .myProjects(myProjectsEntities.stream()
            .map(project -> MypageProjectResponse.builder()
                .projectId(project.getId())
                .title(project.getTitle())
                .build()).toList())
        .myBookmarkProjects(myBookmarkProjects.stream()
            .map(project -> MypageProjectResponse.builder()
                .projectId(project.getId())
                .title(project.getTitle())
                .build()).toList()
        ).build();

    return response;
  }

}
