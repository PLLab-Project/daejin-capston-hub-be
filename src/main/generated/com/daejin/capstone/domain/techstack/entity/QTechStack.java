package com.daejin.capstone.domain.techstack.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTechStack is a Querydsl query type for TechStack
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTechStack extends EntityPathBase<TechStack> {

    private static final long serialVersionUID = -1300654964L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTechStack techStack = new QTechStack("techStack");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.daejin.capstone.domain.project.entity.QProject project;

    public final StringPath stackName = createString("stackName");

    public QTechStack(String variable) {
        this(TechStack.class, forVariable(variable), INITS);
    }

    public QTechStack(Path<? extends TechStack> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTechStack(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTechStack(PathMetadata metadata, PathInits inits) {
        this(TechStack.class, metadata, inits);
    }

    public QTechStack(Class<? extends TechStack> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.project = inits.isInitialized("project") ? new com.daejin.capstone.domain.project.entity.QProject(forProperty("project"), inits.get("project")) : null;
    }

}

