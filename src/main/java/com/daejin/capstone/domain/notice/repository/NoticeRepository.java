package com.daejin.capstone.domain.notice.repository;

import com.daejin.capstone.domain.notice.entity.Notice;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

  Optional<Notice> findById(Long id);

  Page<Notice> findByTitleContainingIgnoreCase(String title, Pageable pageable);

  List<Notice> findByTitleContainingIgnoreCase(String title);

}
