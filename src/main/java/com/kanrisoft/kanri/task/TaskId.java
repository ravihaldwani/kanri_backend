package com.kanrisoft.kanri.task;

import lombok.Value;

@Value(staticConstructor = "of")
public class TaskId {
    Long id;
}
