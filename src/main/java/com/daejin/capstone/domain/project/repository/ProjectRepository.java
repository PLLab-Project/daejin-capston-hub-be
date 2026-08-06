package com.daejin.capstone.domain.project.repository;

import com.daejin.capstone.domain.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectRepositoryCustom {

}
