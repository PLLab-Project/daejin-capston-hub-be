package com.daejin.capstone.domain.project.repository;

import com.daejin.capstone.domain.project.dto.ProjectSearchCondition;
import com.daejin.capstone.domain.project.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectRepositoryCustom {

  Page<Project> search(ProjectSearchCondition condition, Pageable pageable);

}
