package com.daejin.capstone.domain.file.repository;

import com.daejin.capstone.domain.file.entity.File;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {

  List<File> findByNotice_Id(Long noticeId);
}
