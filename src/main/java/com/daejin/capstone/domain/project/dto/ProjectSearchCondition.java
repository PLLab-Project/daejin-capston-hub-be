package com.daejin.capstone.domain.project.dto;

import com.daejin.capstone.domain.project.entity.ProjectSortType;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.Explode;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@NoArgsConstructor
public class ProjectSearchCondition {

  @Schema(description = "검색 키워드 (제목 또는 업로더 이름)", example = "홍")
  private String keyword;

  @Schema(description = "연도", example = "2026")
  private String year;

  @Parameter(
      description = "분야 ID 목록",
      example = "1",
      style = ParameterStyle.FORM,
      explode = Explode.FALSE
  )
  private List<Long> categoryIds;

  @Schema(description = "정렬 기준", example = "NAME")
  private ProjectSortType sortType = ProjectSortType.LATEST;

  @Schema(description = "정렬 방향", example = "ASC")
  private Sort.Direction direction = Sort.Direction.DESC;
}
