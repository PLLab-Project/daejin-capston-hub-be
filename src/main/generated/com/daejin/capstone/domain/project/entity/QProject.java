package com.daejin.capstone.domain.project.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProject is a Querydsl query type for Project
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProject extends EntityPathBase<Project> {

    private static final long serialVersionUID = 329959052L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProject project = new QProject("project");

    public final com.daejin.capstone.global.common.entity.QBaseEntity _super = new com.daejin.capstone.global.common.entity.QBaseEntity(this);

    public final com.daejin.capstone.domain.category.entity.QCategory category;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath demoVideoUrl = createString("demoVideoUrl");

    public final StringPath description = createString("description");

    public final ListPath<com.daejin.capstone.domain.file.entity.File, com.daejin.capstone.domain.file.entity.QFile> files = this.<com.daejin.capstone.domain.file.entity.File, com.daejin.capstone.domain.file.entity.QFile>createList("files", com.daejin.capstone.domain.file.entity.File.class, com.daejin.capstone.domain.file.entity.QFile.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<ProjectStatus> projectStatus = createEnum("projectStatus", ProjectStatus.class);

    public final StringPath summary = createString("summary");

    public final ListPath<com.daejin.capstone.domain.techstack.entity.TechStack, com.daejin.capstone.domain.techstack.entity.QTechStack> techStacks = this.<com.daejin.capstone.domain.techstack.entity.TechStack, com.daejin.capstone.domain.techstack.entity.QTechStack>createList("techStacks", com.daejin.capstone.domain.techstack.entity.TechStack.class, com.daejin.capstone.domain.techstack.entity.QTechStack.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.daejin.capstone.domain.user.entity.QUser user;

    public QProject(String variable) {
        this(Project.class, forVariable(variable), INITS);
    }

    public QProject(Path<? extends Project> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProject(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProject(PathMetadata metadata, PathInits inits) {
        this(Project.class, metadata, inits);
    }

    public QProject(Class<? extends Project> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new com.daejin.capstone.domain.category.entity.QCategory(forProperty("category")) : null;
        this.user = inits.isInitialized("user") ? new com.daejin.capstone.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

