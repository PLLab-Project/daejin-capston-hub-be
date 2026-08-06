package com.daejin.capstone.domain.project.controller;

import com.daejin.capstone.domain.project.dto.ProjectSearchCondition;
import com.daejin.capstone.domain.project.dto.request.RegisterProjectRequest;
import com.daejin.capstone.domain.project.dto.response.ProjectDetailResponse;
import com.daejin.capstone.domain.project.dto.response.ProjectPreviewResponse;
import com.daejin.capstone.domain.project.dto.response.RegisterProjectResponse;
import com.daejin.capstone.domain.project.service.ProjectService;
import com.daejin.capstone.global.common.response.ResponseDTO;
import com.daejin.capstone.global.dto.PageResponse;
import com.daejin.capstone.global.security.core.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ProjectController {

  private final ProjectService projectService;

  @Tag(name = "작품")
  @Operation(summary = "작품 등록에 사용하는 API 입니다. MULTIPART_FORM_DATA 형식으로 요청해야합니다. 등록이 완료되면 ID 가 반환됩니다.")
  @PostMapping(value = "/home/project", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseDTO<?> registerProject(
      @RequestPart("request")RegisterProjectRequest request,
      @RequestPart("thumbnail")MultipartFile thumbnailImageFile,
      @RequestPart(value = "addImage", required = false)List<MultipartFile> addImageFiles,
      @RequestPart("presentationReport")MultipartFile presentationReportFile,
      @RequestPart("descriptionReport")MultipartFile descriptionReportFile,
      @RequestPart("projectZip")MultipartFile projectZipFile,
      @AuthenticationPrincipal CustomUserDetails userDetails) {


    RegisterProjectResponse response = projectService.registerProject(request, thumbnailImageFile, addImageFiles, presentationReportFile,
        descriptionReportFile, projectZipFile, userDetails.getUuid());

    return ResponseDTO.of(response, "작품 등록에 성공하였습니다.");

  }

  @Tag(name = "작품")
  @Operation(summary = "작품 검색에 사용하는 API 입니다. 연도, 분야, 정렬 기준 검색이 가능합니다.")
  @GetMapping("/home/project/search")
  public ResponseDTO<PageResponse<ProjectPreviewResponse>> searchProject(
      @ParameterObject ProjectSearchCondition condition,
      @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
      @RequestParam(defaultValue = "0") int page,
      @Parameter(description = "페이지 크기", example = "8")
      @RequestParam(defaultValue = "8") int size,
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {

    String uuid = (userDetails != null) ? userDetails.getUuid() : null;

    Pageable pageable = PageRequest.of(page, size);
    Page<ProjectPreviewResponse> projectPreviewResponses =
        projectService.searchProject(pageable, condition, uuid);
    return ResponseDTO.of(
        PageResponse.from(projectPreviewResponses),
        "작품 목록 조회에 성공하였습니다."
    );
  }

  @Tag(name = "작품")
  @Operation(summary = "작품 상세조회 API 입니다.")
  @GetMapping("/home/project/detail/{projectId}")
  public ResponseDTO<ProjectDetailResponse> getProjectDetail(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @PathVariable Long projectId
  ) {

    String uuid = (userDetails != null) ? userDetails.getUuid() : null;

    ProjectDetailResponse response = projectService.getProjectDetail(projectId, uuid);
    return ResponseDTO.of(response, "조회에 성공하였습니다.");
  }


}
