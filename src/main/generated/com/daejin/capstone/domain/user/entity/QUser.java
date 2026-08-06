package com.daejin.capstone.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1785646244L;

    public static final QUser user = new QUser("user");

    public final com.daejin.capstone.global.common.entity.QBaseEntity _super = new com.daejin.capstone.global.common.entity.QBaseEntity(this);

    public final ListPath<com.daejin.capstone.domain.bookmark.entity.Bookmark, com.daejin.capstone.domain.bookmark.entity.QBookmark> bookmarks = this.<com.daejin.capstone.domain.bookmark.entity.Bookmark, com.daejin.capstone.domain.bookmark.entity.QBookmark>createList("bookmarks", com.daejin.capstone.domain.bookmark.entity.Bookmark.class, com.daejin.capstone.domain.bookmark.entity.QBookmark.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final ListPath<com.daejin.capstone.domain.notice.entity.Notice, com.daejin.capstone.domain.notice.entity.QNotice> notices = this.<com.daejin.capstone.domain.notice.entity.Notice, com.daejin.capstone.domain.notice.entity.QNotice>createList("notices", com.daejin.capstone.domain.notice.entity.Notice.class, com.daejin.capstone.domain.notice.entity.QNotice.class, PathInits.DIRECT2);

    public final ListPath<com.daejin.capstone.domain.project.entity.Project, com.daejin.capstone.domain.project.entity.QProject> projects = this.<com.daejin.capstone.domain.project.entity.Project, com.daejin.capstone.domain.project.entity.QProject>createList("projects", com.daejin.capstone.domain.project.entity.Project.class, com.daejin.capstone.domain.project.entity.QProject.class, PathInits.DIRECT2);

    public final EnumPath<UserRole> role = createEnum("role", UserRole.class);

    public final StringPath stdNum = createString("stdNum");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final StringPath uuid = createString("uuid");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

