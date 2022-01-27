package com.kanrisoft.kanri.space.domain;

import com.kanrisoft.kanri.board.domain.BoardId;
import com.kanrisoft.kanri.user.domain.UserId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpaceEntityTest {

    @Test
    void shouldCreateInstance() {
        var domainName = "DomainTest";
        var userId = UserId.of(2L);
        var entity = SpaceEntity.of(domainName, userId);

        assertAll(
                () -> assertEquals(domainName, entity.getDomainName()),
                () -> assertEquals(SpaceStatus.ACTIVE, entity.getStatus()),
                () -> assertNull(entity.getId()),
                () -> assertEquals(userId, entity.getCreatedBy())
        );
    }

    @Test
    void shouldBeAbleToAddUsers() {
        var domainName = "DomainTest";
        var createdBy = UserId.of(2L);
        var space = SpaceEntity.of(domainName, createdBy);
        var userToAdd = UserId.of(3L);
        assertFalse(space.containsUser(userToAdd));

        space.addUser(userToAdd);

        assertTrue(space.containsUser(userToAdd));
    }

    @Test
    void shouldBeAbleToRemoveUser() {
        var domainName = "DomainTest";
        var createdBy = UserId.of(2L);
        var space = SpaceEntity.of(domainName, createdBy);
        var addedUser = UserId.of(3L);
        space.addUser(addedUser);
        assertTrue(space.containsUser(addedUser));

        var result = space.removeUser(addedUser);

        assertTrue(result);
        assertFalse(space.containsUser(addedUser));
    }

    @Test
    void shouldBeAbleToAddBoards() {
        var domainName = "DomainTest";
        var createdBy = UserId.of(2L);
        var space = SpaceEntity.of(domainName, createdBy);
        var boardToAdd = BoardId.of(3L);
        assertFalse(space.containsBoard(boardToAdd));

        space.addBoard(boardToAdd);

        assertTrue(space.containsBoard(boardToAdd));
    }

    @Test
    void shouldBeAbleToRemoveBoards() {
        var domainName = "DomainTest";
        var createdBy = UserId.of(2L);
        var space = SpaceEntity.of(domainName, createdBy);
        var userToRemove = BoardId.of(3L);
        space.addBoard(userToRemove);
        assertTrue(space.containsBoard(userToRemove));

        space.removeBoard(userToRemove);

        assertFalse(space.containsBoard(userToRemove));
    }
}