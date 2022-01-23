package com.kanrisoft.kanri.board;

public interface Board {
    Long getId();

    String getName();

    com.kanrisoft.kanri.user.domain.UserId getCreatedBy();

    java.time.Instant getCreatedDate();

    com.kanrisoft.kanri.user.domain.UserId getLastModifiedBy();

    java.util.Map<com.kanrisoft.kanri.task.TaskId, org.springframework.scheduling.config.Task> getTasks();
}
