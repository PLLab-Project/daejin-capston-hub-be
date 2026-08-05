package com.daejin.capstone.domain.project.service;

import com.daejin.capstone.domain.category.CategoryNotFoundException;
import com.daejin.capstone.domain.category.entity.Category;
import com.daejin.capstone.domain.category.repository.CategoryRepository;
import com.daejin.capstone.domain.file.entity.File;
import com.daejin.capstone.domain.file.entity.FileType;
import com.daejin.capstone.domain.file.repository.FileRepository;
import com.daejin.capstone.domain.project.dto.request.RegisterProjectRequest;
import com.daejin.capstone.domain.project.dto.response.RegisterProjectResponse;
import com.daejin.capstone.domain.project.entity.Project;
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

}
