package com.nowakkacper.logic;

import com.nowakkacper.model.TaskGroup;
import com.nowakkacper.model.TaskGroupRepository;
import com.nowakkacper.model.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaskGroupServiceTest {

    @Test
    @DisplayName("should throw IllegalStateException when undone tasks")
    void toggleGroup_undoneTasks_throwsIllegalStateException() {
        //given
        TaskRepository mockTaskRepository = taskRepositoryReturning(true);
        //system under test
        var toTest = new TaskGroupService(null, mockTaskRepository);
        //when
        var exception = catchThrowable(() -> toTest.toggleGroup(1));
        //then
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("undone tasks");
    }

    @Test
    @DisplayName("should throw IllegalArgumentException when no undone tasks but no group for a given id")
    void toggleGroup_wrongId_throwsIllegalArgumentException() {
        //given
        TaskRepository mockTaskRepository = taskRepositoryReturning(false);
        //and
        TaskGroupRepository mockRepository = mock(TaskGroupRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        //system under test
        var toTest = new TaskGroupService(mockRepository, mockTaskRepository);
        //when
        var exception = catchThrowable(() -> toTest.toggleGroup(1));
        //then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id not found");
    }

    @Test
    @DisplayName("should toggle group")
    void toggleGroup_workAsExpected(){
        TaskRepository mockTaskRepository = taskRepositoryReturning(false);
        //and
        TaskGroup group = new TaskGroup();
        var beforeToggle = group.isDone();
        //and
        TaskGroupRepository mockRepository = mock(TaskGroupRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.of(group));
        //system under test
        var toTest = new TaskGroupService(mockRepository, mockTaskRepository);
        //when
        toTest.toggleGroup(1);
        //then
        assertThat(group.isDone()).isEqualTo(!beforeToggle);
    }

    private TaskGroup taskGroupWith() {
        var result = mock(TaskGroup.class);
        when(result.isDone()).thenReturn(false);
        return result;
    }

    private static TaskRepository taskRepositoryReturning(boolean value) {
        TaskRepository mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.existsByDoneIsFalseAndGroup_Id(anyInt())).thenReturn(value);
        return mockTaskRepository;
    }
}