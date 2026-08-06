package com.daejin.capstone.domain.project.repository;

import com.daejin.capstone.domain.project.entity.Project;
import com.daejin.capstone.domain.project.entity.ProjectStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectRepositoryCustom {

  List<Project> findByProjectStatusAndTitleContainingIgnoreCase(
      ProjectStatus projectStatus, String title);

  List<Project> findByProjectStatus(ProjectStatus projectStatus);

}
