package com.kanrisoft.kanri.task;

public record TaskId(Long id) {
    public static TaskId of(Long id) {
        return new TaskId(id);
    }
}
