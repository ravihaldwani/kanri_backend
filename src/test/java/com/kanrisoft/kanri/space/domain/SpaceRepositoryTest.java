package com.kanrisoft.kanri.space.domain;

import com.kanrisoft.kanri.board.BoardId;
import com.kanrisoft.kanri.user.DatabaseIntegration;
import com.kanrisoft.kanri.user.domain.UserId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SpaceRepositoryTest extends DatabaseIntegration {
    @Autowired
    SpaceRepository repository;

    @Test
    void shouldSaveSpaceEntity() {
        var userId = UserId.of(1212L);
        var space = SpaceEntity.of("testspace", userId);
        space.addBoard(BoardId.of(1L));

        var saved = repository.save(space);

        assertNotNull(saved.getId());
    }

    @Test
    void shouldRetrieveEntity() {
        var userId = UserId.of(1212L);
        var space = SpaceEntity.of("testspace", userId);
        var boardId = BoardId.of(1L);
        space.addBoard(boardId);

        var saved = repository.save(space);

        var retrieved = repository.findById(saved.getId());

        assertTrue(retrieved.isPresent());
        assertTrue(retrieved.get().containsBoard(boardId));
    }
}