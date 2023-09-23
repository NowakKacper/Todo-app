package com.nowakkacper.logic;

import com.nowakkacper.TaskConfigurationProperties;
import com.nowakkacper.model.ProjectRepository;
import com.nowakkacper.model.TaskGroupRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LogicConfiguration {
    @Bean
    ProjectService projectService(ProjectRepository repository,
                                  TaskGroupRepository taskGroupRepository,
                                  TaskConfigurationProperties taskConfigurationProperties,
                                  TaskGroupService taskGroupService){
        return new ProjectService(repository, taskGroupRepository, taskConfigurationProperties, taskGroupService);
    }
}
