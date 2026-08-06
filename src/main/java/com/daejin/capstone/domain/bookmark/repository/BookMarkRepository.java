package com.daejin.capstone.domain.bookmark.repository;

import com.daejin.capstone.domain.bookmark.entity.Bookmark;
import com.daejin.capstone.domain.project.entity.Project;
import com.daejin.capstone.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookMarkRepository extends JpaRepository<Bookmark, Long> {

  boolean existsByUserAndProject(User user, Project project);

}
