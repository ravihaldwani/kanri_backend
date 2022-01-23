package com.kanrisoft.kanri.board.domian;

import com.kanrisoft.kanri.task.TaskId;
import com.kanrisoft.kanri.user.domain.UserId;
import org.springframework.scheduling.config.Task;

import java.time.Instant;
import java.util.Map;

public interface Board {
    Long getId();

    String getName();

    UserId getCreatedBy();

    Instant getCreatedDate();

    UserId getLastModifiedBy();

    Map<TaskId, Task> getTasks();
}
