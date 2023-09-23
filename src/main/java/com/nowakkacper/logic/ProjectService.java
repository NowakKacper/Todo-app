package com.nowakkacper.logic;

import com.nowakkacper.TaskConfigurationProperties;
import com.nowakkacper.model.Project;
import com.nowakkacper.model.ProjectRepository;
import com.nowakkacper.model.TaskGroupRepository;
import com.nowakkacper.model.projection.GroupReadModel;
import com.nowakkacper.model.projection.GroupTaskWriteModel;
import com.nowakkacper.model.projection.GroupWriteModel;
import com.nowakkacper.model.projection.ProjectWriteModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

//@Service
public class ProjectService {
    private final ProjectRepository repository;
    private final TaskGroupRepository taskGroupRepository;
    private final TaskConfigurationProperties config;
    private final TaskGroupService taskGroupService;

    public ProjectService(ProjectRepository repository,
                          TaskGroupRepository taskGroupRepository,
                          TaskConfigurationProperties config,
                          TaskGroupService taskGroupService) {
        this.repository = repository;
        this.taskGroupRepository = taskGroupRepository;
        this.config = config;
        this.taskGroupService = taskGroupService;
    }

    public List<Project> readAll(){
        return repository.findAll();
    }

    public Project save(ProjectWriteModel toSave){
        return repository.save(toSave.toProject());
    }

    public GroupReadModel createGroup(LocalDateTime deadline, int projectId){
        if(!config.getTemplate().isAllowMultipleTasks() && taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId)){
            throw new IllegalStateException("Only one undone group from project is allowed");
        }
        return repository.findById(projectId)
                .map(project -> {
                    var targetGroup = new GroupWriteModel();
                    targetGroup.setDescription(project.getDescription());
                    targetGroup.setTasks(
                            project.getSteps().stream()
                                    .map(projectStep -> {
                                        var task = new GroupTaskWriteModel();
                                        task.setDescription(projectStep.getDescription());
                                        task.setDeadline(deadline.plusDays(projectStep.getDaysToDeadline()));
                                        return task;
                                    }).collect(Collectors.toList())
                    );
                    return taskGroupService.createGroup(targetGroup, project);
                }).orElseThrow(() -> new IllegalArgumentException("Project with given id not found"));
    }
}
