package com.daejin.capstone.domain.category.controller;

import com.daejin.capstone.domain.category.dto.response.CategoryResponse;
import com.daejin.capstone.domain.category.service.CategoryService;
import com.daejin.capstone.global.common.response.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;

  @Tag(name = "분야 조회")
  @Operation(summary = "웹/앱/게임/임베디드/보안 분야를 반환합니다.")
  @GetMapping("/home/category")
  public ResponseDTO<List<CategoryResponse>> getCategories() {

    List<CategoryResponse> response = categoryService.getCategories();

    return ResponseDTO.of(response, "분야 조회에 성공하였습니다.");

  }


}
