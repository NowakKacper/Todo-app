package com.nowakkacper.logic;

import com.nowakkacper.model.Project;
import com.nowakkacper.model.TaskGroup;
import com.nowakkacper.model.TaskGroupRepository;
import com.nowakkacper.model.TaskRepository;
import com.nowakkacper.model.projection.GroupReadModel;
import com.nowakkacper.model.projection.GroupWriteModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskGroupService {
    private final TaskGroupRepository repository;
    private final TaskRepository taskRepository;

    public TaskGroupService(TaskGroupRepository repository, TaskRepository taskRepository) {
        this.repository = repository;
        this.taskRepository = taskRepository;
    }

    public GroupReadModel createGroup(GroupWriteModel source){
        return createGroup(source, null);
    }

    GroupReadModel createGroup(GroupWriteModel source, Project project) {
        TaskGroup result = repository.save(source.toGroup(project));
        return new GroupReadModel(result);
    }

    public List<GroupReadModel> readAll(){
        return repository.findAll().stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toList());
    }

    public void toggleGroup(int groupId){
        if(taskRepository.existsByDoneIsFalseAndGroup_Id(groupId)){
            throw new IllegalStateException("Group has undone tasks. Done all the tasks first.");
        }
        TaskGroup result = repository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("TaskGroup with given id not found"));
        result.setDone(!result.isDone());
        repository.save(result);
    }
}
