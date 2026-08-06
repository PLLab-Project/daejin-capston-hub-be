package com.daejin.capstone.domain.bookmark.service;

import com.daejin.capstone.domain.bookmark.dto.response.ToggleBookmarkResponse;
import com.daejin.capstone.domain.bookmark.entity.Bookmark;
import com.daejin.capstone.domain.bookmark.repository.BookMarkRepository;
import com.daejin.capstone.domain.notice.exception.PostNotFoundException;
import com.daejin.capstone.domain.project.entity.Project;
import com.daejin.capstone.domain.project.repository.ProjectRepository;
import com.daejin.capstone.domain.user.entity.User;
import com.daejin.capstone.domain.user.repository.UserRepository;
import com.daejin.capstone.global.exception.ErrorCode;
import com.daejin.capstone.global.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookmarkService {

  private final UserRepository userRepository;
  private final ProjectRepository projectRepository;
  private final BookMarkRepository bookMarkRepository;

  @Transactional
  public ToggleBookmarkResponse addBookmark(Long projectId, String uuid) {

    User user = userRepository.findByUuid(uuid).orElseThrow(
        () -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND)
    );

    Project project = projectRepository.findById(projectId).orElseThrow(
        () -> new PostNotFoundException(ErrorCode.PROJECT_NOT_FOUND)
    );

    boolean bookMarked = bookMarkRepository.existsByUserAndProject(user, project);
    Bookmark bookmark = bookMarkRepository.findByUserAndProject(user, project).orElse(null);

    if(bookMarked) {
      bookMarkRepository.delete(bookmark);
      return ToggleBookmarkResponse.builder()
          .bookMarked(false)
          .build();
    }

    Bookmark newBookmark = Bookmark.of(user, project);
    bookMarkRepository.save(newBookmark);

    return ToggleBookmarkResponse.builder()
        .bookMarked(true)
        .build();
  }

}
