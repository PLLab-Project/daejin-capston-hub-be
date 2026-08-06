package com.daejin.capstone.domain.project.service;

import com.daejin.capstone.domain.bookmark.repository.BookMarkRepository;
import com.daejin.capstone.domain.category.exception.CategoryNotFoundException;
import com.daejin.capstone.domain.category.entity.Category;
import com.daejin.capstone.domain.category.repository.CategoryRepository;
import com.daejin.capstone.domain.file.entity.File;
import com.daejin.capstone.domain.file.entity.FileType;
import com.daejin.capstone.domain.file.repository.FileRepository;
import com.daejin.capstone.domain.project.dto.ProjectSearchCondition;
import com.daejin.capstone.domain.project.dto.request.RegisterProjectRequest;
import com.daejin.capstone.domain.project.dto.response.ProjectDetailResponse;
import com.daejin.capstone.domain.project.dto.response.ProjectPreviewResponse;
import com.daejin.capstone.domain.project.dto.response.RegisterProjectResponse;
import com.daejin.capstone.domain.project.entity.Project;
import com.daejin.capstone.domain.project.exception.ProjectNotFoundException;
import com.daejin.capstone.domain.project.repository.ProjectRepository;
import com.daejin.capstone.domain.techstack.entity.TechStack;
import com.daejin.capstone.domain.techstack.repository.TechStackRepository;
import com.daejin.capstone.domain.user.entity.User;
import com.daejin.capstone.domain.user.repository.UserRepository;
import com.daejin.capstone.global.config.FileStorage;
import com.daejin.capstone.global.exception.ErrorCode;
import com.daejin.capstone.global.exception.UserNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProjectService {

  private final UserRepository userRepository;
  private final ProjectRepository projectRepository;
  private final CategoryRepository categoryRepository;
  private final TechStackRepository techStackRepository;
  private final BookMarkRepository bookMarkRepository;

  private final FileStorage fileStorage;
  private final FileRepository fileRepository;

  @Transactional
  public RegisterProjectResponse registerProject(RegisterProjectRequest request, MultipartFile thumbnailImageFile,
      List<MultipartFile> addImageFiles, MultipartFile presentationReportFile, MultipartFile descriptionReportFile,
      MultipartFile projectZipFile, String uuid) {

    User user = userRepository.findByUuid(uuid).orElseThrow(
        () -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND)
    );

    Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(
        () -> new CategoryNotFoundException(ErrorCode.CATEGORY_NOT_FOUND)
    );

    Project project = Project.of(user, category, request.getTitle(), request.getSummary(), request.getDemoVideoUrl(),
        request.getDescription());

    projectRepository.save(project);

    if(!request.getTechStacks().isEmpty()) {
      List<TechStack> techStacks = request.getTechStacks().stream()
          .map(stack -> {
            TechStack techStack = TechStack.builder()
                .project(project)
                .stackName(stack)
                .build();

            return techStack;
          }).toList();

      techStackRepository.saveAll(techStacks);
    }


    File thumbnailImageFileEntity = File.createProjectFile(project, fileStorage.store(thumbnailImageFile), thumbnailImageFile.getOriginalFilename(),
        FileType.IMAGE, true);
    List<File> adImageFileEntities = addImageFiles.stream()
        .map(imageFile -> {
          File file = File.createProjectFile(project, fileStorage.store(imageFile), imageFile.getOriginalFilename(),
              FileType.IMAGE, false);
          return file;
        }).toList();
    File presentationReportFileEntity = File.createProjectFile(project, fileStorage.store(presentationReportFile),
        presentationReportFile.getOriginalFilename(), FileType.PRESENTATION_REPORT, false);
    File descriptionReportFileEntity = File.createProjectFile(project, fileStorage.store(descriptionReportFile),
        descriptionReportFile.getOriginalFilename(), FileType.DESCRIPTION_REPORT, false);
    File projectZipFileEntity = File.createProjectFile(project, fileStorage.store(projectZipFile),
        projectZipFile.getOriginalFilename(), FileType.PROJECT_ZIP, false);

    fileRepository.save(thumbnailImageFileEntity);
    fileRepository.saveAll(adImageFileEntities);
    fileRepository.save(presentationReportFileEntity);
    fileRepository.save(descriptionReportFileEntity);
    fileRepository.save(projectZipFileEntity);

    return RegisterProjectResponse.builder()
        .projectId(project.getId())
        .build();

  }

  @Transactional
  public Page<ProjectPreviewResponse> searchProject(
      Pageable pageable, ProjectSearchCondition condition, String uuid) {
    System.out.println("uuid = " + uuid);

    User user = (uuid != null)
        ? userRepository.findByUuid(uuid).orElseThrow(
        () -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND))
        : null;

    Sort sort = Sort.by(
        condition.getDirection(),
        condition.getSortType().getProperty()
    );

    Pageable sortedPageable = PageRequest.of(
        pageable.getPageNumber(),
        pageable.getPageSize(),
        sort
    );

    Page<Project> projects = projectRepository.search(condition, sortedPageable);

    return projects.map(project -> toPreviewResponse(project, user));
  }

  private ProjectPreviewResponse toPreviewResponse(Project project, User user) {

    boolean isBookMarked = user == null ? false : bookMarkRepository.existsByUserAndProject(user, project);

    String thumbnailUrl = project.getFiles().stream()
        .filter(file -> file.getThumbnail())
        .findFirst()
        .map(File::getFileUrl)
        .orElse(null);





    return ProjectPreviewResponse.builder()
        .projectId(project.getId())
        .thumbnailUrl(thumbnailUrl)
        .title(project.getTitle())
        .summary(project.getSummary())
        .uploadUserName(project.getUser().getName())
        .createdAt(project.getCreatedAt().toString())
        .isBookmarked(isBookMarked)
        .build();
  }

  @Transactional
  public ProjectDetailResponse getProjectDetail(Long projectId, String uuid) {

    User user = (uuid != null)
        ? userRepository.findByUuid(uuid).orElseThrow(
        () -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND))
        : null;

    Project project = projectRepository.findById(projectId).orElseThrow(
        () -> new ProjectNotFoundException(ErrorCode.PROJECT_NOT_FOUND)
    );

    Category category = categoryRepository.findById(project.getCategory().getId()).orElseThrow(
        () -> new CategoryNotFoundException(ErrorCode.CATEGORY_NOT_FOUND)
    );

    boolean isBookMarked = user == null ? false : bookMarkRepository.existsByUserAndProject(user, project);
    boolean isMine = user == null ? false : user.getId() == project.getUser().getId();

    List<String> techStacks = techStackRepository.findByProject_Id(projectId).stream()
        .map(TechStack::getStackName)
        .toList();

    List<File> files = fileRepository.findByProject_Id(projectId);

    if(files.isEmpty()) {
      return ProjectDetailResponse.builder()
          .projectId(projectId)
          .title(project.getTitle())
          .summary(project.getSummary())
          .description(project.getDescription())
          .name(project.getUser().getName())
          .createdAt(project.getCreatedAt())
          .categoryName(category.getName())
          .techStacks(techStacks)
          .demoVideoUrl(project.getDemoVideoUrl())
          .bookMarked(isBookMarked)
          .mine(isMine)
          .build();
    }

    String thumbnailImageFileUrl = files.stream()
        .filter(File::getThumbnail)
        .findFirst()
        .map(File::getFileUrl)
        .orElse("null");

    List<String> addImageFilesUrl = files.stream()
        .filter(file -> !file.getThumbnail())
        .filter(file -> file.getType().equals(FileType.IMAGE))
        .map(File::getFileUrl)
        .toList();

    String presentationReportFileUrl = files.stream()
        .filter(file -> file.getType().equals(FileType.PRESENTATION_REPORT))
        .findFirst()
        .map(File::getFileUrl)
        .orElse("null");

    String descriptionReportFileUrl = files.stream()
        .filter(file -> file.getType().equals(FileType.DESCRIPTION_REPORT))
        .findFirst()
        .map(File::getFileUrl)
        .orElse("null");

    String projectZipFileUrl = files.stream()
        .filter(file -> file.getType().equals(FileType.PROJECT_ZIP))
        .findFirst()
        .map(File::getFileUrl)
        .orElse("null");

    return ProjectDetailResponse.builder()
        .projectId(projectId)
        .title(project.getTitle())
        .summary(project.getSummary())
        .description(project.getDescription())
        .name(project.getUser().getName())
        .createdAt(project.getCreatedAt())
        .categoryName(category.getName())
        .techStacks(techStacks)
        .demoVideoUrl(project.getDemoVideoUrl())

        .thumbnailImageFileUrl(thumbnailImageFileUrl)
        .addImageFilesUrl(addImageFilesUrl)
        .presentationReportFileUrl(presentationReportFileUrl)
        .descriptionReportFileUrl(descriptionReportFileUrl)
        .projectZipFileUrl(projectZipFileUrl)

        .bookMarked(isBookMarked)
        .mine(isMine)
        .build();
  }

}
