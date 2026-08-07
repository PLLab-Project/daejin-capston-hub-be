package com.daejin.capstone.domain.bookmark.repository;

import com.daejin.capstone.domain.bookmark.entity.Bookmark;
import com.daejin.capstone.domain.project.entity.Project;
import com.daejin.capstone.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookMarkRepository extends JpaRepository<Bookmark, Long> {

  boolean existsByUserAndProject(User user, Project project);

  Optional<Bookmark> findByUserAndProject(User user, Project project);


  @Query("select b from Bookmark b "
      + "join fetch b.project "
      + "where b.user = :user")
  List<Bookmark> findALlByUser(@Param("user") User user);

}
