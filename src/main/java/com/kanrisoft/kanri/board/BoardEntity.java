package com.kanrisoft.kanri.board;

import com.kanrisoft.kanri.task.TaskId;
import com.kanrisoft.kanri.user.domain.UserId;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.scheduling.config.Task;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
class BoardEntity implements Board {
    @Id
    private final Long id;
    @MappedCollection(keyColumn = "user_id")
    private final Map<UserId, BoardPermissions> members = new HashMap<>();
    private String name;
    @CreatedBy
    private UserId createdBy;
    @CreatedDate
    private Instant createdDate;
    @LastModifiedBy
    private UserId lastModifiedBy;
    private Map<TaskId, Task> tasks;

    private BoardEntity(Long id) {
        this.id = id;
    }

    static BoardEntity create() {
        return new BoardEntity(null);
    }

    boolean addMember(@NotNull UserId userid, Set<Permission> permissions) {
        members.putIfAbsent(userid, new BoardPermissions(permissions));
        return true;
    }

    boolean doesUserHavePermissionOf(@NotNull UserId userid, Permission permission) {
        return members.get(userid).hasPermission(permission);
    }
}
