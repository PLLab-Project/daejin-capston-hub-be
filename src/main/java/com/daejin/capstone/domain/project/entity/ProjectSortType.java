package com.daejin.capstone.domain.project.entity;

public enum ProjectSortType {

  LATEST("createdAt"),
  NAME("user.name");

  private final String property;

  ProjectSortType(String property) {
    this.property = property;
  }

  public String getProperty() {
    return property;
  }


}
