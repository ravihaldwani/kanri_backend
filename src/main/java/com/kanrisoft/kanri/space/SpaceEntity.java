package com.kanrisoft.kanri.space;

import com.kanrisoft.kanri.board.BoardId;
import com.kanrisoft.kanri.user.domain.UserId;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
public class SpaceEntity implements Space {
    @Id
    Long id;

    String domainName;

    @CreatedBy
    UserId createdBy;

    SpaceStatus status;

    @CreatedDate
    Instant createdDate;

    @LastModifiedBy
    UserId lastModifiedBy;

    @MappedCollection
    Set<BoardId> boards;
}
