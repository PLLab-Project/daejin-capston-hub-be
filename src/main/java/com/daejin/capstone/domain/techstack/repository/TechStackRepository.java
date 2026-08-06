package com.daejin.capstone.domain.techstack.repository;

import com.daejin.capstone.domain.techstack.entity.TechStack;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechStackRepository extends JpaRepository<TechStack, Long> {

  List<TechStack> findByProject_Id(Long projectId);

}
