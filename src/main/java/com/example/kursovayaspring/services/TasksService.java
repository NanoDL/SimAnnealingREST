package com.example.kursovayaspring.services;

import com.example.kursovayaspring.model.Project;
import com.example.kursovayaspring.model.Task;
import com.example.kursovayaspring.repository.TasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TasksService {
    @Autowired
    private TasksRepository tasksRepository;
    @Autowired
    private ProjectsService projectsService;
    public Task createTask(Task task, Long projectId) {
        Project project = projectsService.findProjectById(projectId);
        task.setProject(project);
        return tasksRepository.save(task);
    }
}
