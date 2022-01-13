package com.kanrisoft.kanri.board;

import com.kanrisoft.kanri.task.TaskId;
import com.kanrisoft.kanri.user.model.User;
import com.kanrisoft.kanri.user.model.UserId;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.scheduling.config.Task;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
class BoardEntity implements Board {
    @Id
    final Long id;

    String name;

    @CreatedBy
    UserId createdBy;

    @CreatedDate
    Instant createdDate;

    @LastModifiedBy
    UserId lastModifiedBy;

    @MappedCollection(keyColumn = "user_id")
    Map<UserId, BoardPermissions> members = new HashMap<>();

    Map<TaskId, Task> tasks;

    private BoardEntity(Long id) {
        this.id = id;
    }

    static BoardEntity create() {
        return new BoardEntity(null);
    }

    boolean addMember(User user, Set<Permission> permissions) {
        var userid = UserId.of(user.getId());
        members.putIfAbsent(userid, new BoardPermissions(permissions));
        return true;
    }

    boolean doesUserHavePermissionOf(User user, Permission permission) {
        var userid = UserId.of(user.getId());
        return members.get(userid).hasPermission(permission);
    }
}
