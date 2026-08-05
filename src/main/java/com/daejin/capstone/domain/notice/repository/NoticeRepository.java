package com.daejin.capstone.domain.notice.repository;

import com.daejin.capstone.domain.notice.entity.Notice;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

  Optional<Notice> findById(Long id);

}
