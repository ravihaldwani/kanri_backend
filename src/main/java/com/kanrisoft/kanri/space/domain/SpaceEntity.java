package com.kanrisoft.kanri.space.domain;

import com.kanrisoft.kanri.board.domain.BoardId;
import com.kanrisoft.kanri.user.domain.UserId;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@ToString
class SpaceEntity implements Space {
    @Id
    private final Long id;
    @Getter(AccessLevel.NONE)
    @MappedCollection
    private final Set<BoardId> boards;
    @Getter(AccessLevel.NONE)
    @MappedCollection
    private final Set<UserId> users;
    private String domainName;
    private SpaceStatus status;
    @CreatedBy
    @Embedded.Nullable(prefix = "created_by_")
    private UserId createdBy;
    @CreatedDate
    private Instant createdDate;
    @LastModifiedBy
    @Embedded.Nullable(prefix = "last_modified_by_")
    private UserId lastModifiedBy;

    private SpaceEntity(
            Long id,
            String domainName,
            SpaceStatus status,
            UserId createdBy,
            Instant createdDate,
            UserId lastModifiedBy,
            Set<BoardId> boards,
            Set<UserId> users
    ) {
        this.id = id;
        this.domainName = domainName;
        this.status = status;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.boards = boards;
        this.users = users;
    }

    static SpaceEntity of(String domainName, UserId userId) {
        return new SpaceEntity(null,
                domainName,
                SpaceStatus.ACTIVE,
                userId, Instant.now(), userId,
                new HashSet<>(),
                new HashSet<>()
        );
    }

    @Override
    public boolean containsUser(UserId userId) {
        return users.contains(userId);
    }

    @Override
    public boolean removeUser(UserId userId) {
        Objects.requireNonNull(userId);
        return users.remove(userId); // NOTE: check if we should return false if no user found
    }

    @Override
    public boolean addBoard(BoardId board) {
        Objects.requireNonNull(board);
        return this.boards.add(board);
    }

    @Override
    public boolean removeBoard(BoardId board) {
        Objects.requireNonNull(board);
        return this.boards.remove(board);
    }

    @Override
    public boolean addUser(UserId userId) {
        return this.users.add(userId);
    }

    @Override
    public boolean containsBoard(BoardId board) {
        Objects.requireNonNull(board);
        return this.boards.contains(board);
    }
}
