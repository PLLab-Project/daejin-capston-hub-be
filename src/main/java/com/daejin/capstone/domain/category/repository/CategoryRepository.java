package com.daejin.capstone.domain.category.repository;

import com.daejin.capstone.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
