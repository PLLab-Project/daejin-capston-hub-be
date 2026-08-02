package com.daejin.capstone.domain.file.repository;

import com.daejin.capstone.domain.file.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {

}
