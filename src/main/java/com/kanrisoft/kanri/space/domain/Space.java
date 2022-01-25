package com.kanrisoft.kanri.space.domain;

import com.kanrisoft.kanri.board.domain.BoardId;
import com.kanrisoft.kanri.user.domain.UserId;

import java.time.Instant;

public interface Space {
    Long getId();

    String getDomainName();

    SpaceStatus getStatus();

    UserId getCreatedBy();

    Instant getCreatedDate();

    UserId getLastModifiedBy();

    boolean addBoard(BoardId board);

    boolean removeBoard(BoardId board);

    boolean containsBoard(BoardId board);
}
