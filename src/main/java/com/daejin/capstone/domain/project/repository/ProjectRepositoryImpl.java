package com.daejin.capstone.domain.project.repository;

import com.daejin.capstone.domain.project.dto.ProjectSearchCondition;
import com.daejin.capstone.domain.project.entity.Project;
import com.daejin.capstone.domain.project.entity.QProject;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@RequiredArgsConstructor
public class ProjectRepositoryImpl implements ProjectRepositoryCustom {

  private final JPAQueryFactory queryFactory;


  @Override
  public Page<Project> search(ProjectSearchCondition condition, Pageable pageable) {
    QProject project = QProject.project;

    List<Project> content = queryFactory
        .selectFrom(project)
        .where(
            keywordContains(condition.getKeyword()),
            yearEq(condition.getYear()),
            categoryIn(condition.getCategoryIds())
        )
        .orderBy(buildOrder(pageable))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long total = queryFactory
        .select(project.count())
        .from(project)
        .where(
            keywordContains(condition.getKeyword()),
            yearEq(condition.getYear()),
            categoryIn(condition.getCategoryIds())
        )
        .fetchOne();

    return new PageImpl<>(content, pageable, total == null ? 0 : total);
  }

  private BooleanExpression yearEq(String year) {
    if (year == null || year.isBlank()) return null;
    try {
      int y = Integer.parseInt(year);
      LocalDateTime start = LocalDateTime.of(y, 1, 1, 0, 0);
      LocalDateTime end = LocalDateTime.of(y + 1, 1, 1, 0, 0);
      return QProject.project.createdAt.goe(start)
          .and(QProject.project.createdAt.lt(end));
    } catch (NumberFormatException e) {
      return null;
    }
  }

  private BooleanExpression categoryIn(List<Long> fieldIds) {
    if (fieldIds == null || fieldIds.isEmpty()) return null;
    return QProject.project.category.id.in(fieldIds);
  }

  private BooleanExpression keywordContains(String keyword) {
    if(keyword == null || keyword.isBlank()) {
      return null;
    }

    QProject project = QProject.project;
    return project.title.containsIgnoreCase(keyword)
        .or(project.user.name.containsIgnoreCase(keyword));
  }

  private OrderSpecifier<?> buildOrder(Pageable pageable) {
    QProject project = QProject.project;

    if (pageable.getSort().isEmpty()) {
      return project.createdAt.desc();
    }

    Sort.Order order = pageable.getSort().iterator().next();
    boolean asc = order.isAscending();

    return switch (order.getProperty()) {
      case "user.name" -> asc ? project.title.asc() : project.title.desc();
      case "createdAt" -> asc ? project.createdAt.asc() : project.createdAt.desc();
      default -> project.createdAt.desc();
    };
  }




}
