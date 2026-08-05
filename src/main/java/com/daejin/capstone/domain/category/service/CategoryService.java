package com.daejin.capstone.domain.category.service;

import com.daejin.capstone.domain.category.dto.response.CategoryResponse;
import com.daejin.capstone.domain.category.entity.Category;
import com.daejin.capstone.domain.category.repository.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public List<CategoryResponse> getCategories() {
    List<Category> categories = categoryRepository.findAll();

    List<CategoryResponse> response = categories.stream()
        .map(category -> {
          CategoryResponse categoryResponse = CategoryResponse.builder()
              .categoryId(category.getId())
              .name(category.getName())
              .build();
          return categoryResponse;
        }).toList();

    return response;
  }

}
