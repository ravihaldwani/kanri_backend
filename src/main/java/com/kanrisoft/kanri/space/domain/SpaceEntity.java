package com.kanrisoft.kanri.space.domain;

import com.kanrisoft.kanri.board.domain.BoardId;
import com.kanrisoft.kanri.user.domain.UserId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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
    @MappedCollection
    private final Set<BoardId> boards;
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
            Set<BoardId> boards
    ) {
        this.id = id;
        this.domainName = domainName;
        this.status = status;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.boards = boards;
    }

    static SpaceEntity of(String domainName, UserId id) {
        return new SpaceEntity(null, domainName, SpaceStatus.ACTIVE, id, Instant.now(), id, new HashSet<>());
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
    public boolean containsBoard(BoardId board) {
        Objects.requireNonNull(board);
        return this.boards.contains(board);
    }
}
