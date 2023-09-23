package com.nowakkacper.model.event;

import com.nowakkacper.model.Task;

import java.time.Clock;

public class TaskDone extends TaskEvent {
    TaskDone(Task source) {
        super(source.getId(), Clock.systemDefaultZone());
    }
}
