package com.example.kursovayaspring.services;

import com.example.kursovayaspring.SimAnnealing;
import com.example.kursovayaspring.model.Project;
import com.example.kursovayaspring.model.Task;
import com.example.kursovayaspring.repository.ProjectsRepository;
import com.example.kursovayaspring.repository.TasksRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ProjectsService {
    @Autowired
    private ProjectsRepository projectsRepository;
    @Autowired
    private TasksRepository tasksRepository;
    public void save(Project project){

        for (Task task : project.getTaskList()){

           // project.addTask(task);
            task.setProject(project);
            task.calculateNumFollowers();
        }
        projectsRepository.save(project);
    }

    public void delete(Long id){

        projectsRepository.deleteById(id);
    }

    public void addTask(Long id, Task task){
        Project project = projectsRepository.findById(id).orElse(null);
        project.addTask(task);
        tasksRepository.save(task);
    }
    public void removeTask(Long id, Long taskId){
        Project project = projectsRepository.findById(id).orElse(null);
        Task task = tasksRepository.findById(taskId).orElse(null);
        project.removeTask(task);
        tasksRepository.delete(task);

    }

    public Project findProjectById(Long id){
        return projectsRepository.findById(id).orElseThrow();
    }

    public boolean existsProjectById(Long id){
        return projectsRepository.existsById(id);
    }

    public List<Project> findAll(){
        return projectsRepository.findAll();
    }

    @Transactional
    public Project updateProjDuration(Long id,Project project, int duration) throws IOException {
        var updProj = projectsRepository.findById(id).orElseThrow();
        updProj.setName(project.getName());
        updProj.setDuration(duration);
        updProj.setTaskList(project.getTaskList());
        updProj.setTaskCount(project.getTaskCount());
        updProj.setMaxResourses(project.getMaxResourses());

        updProj = projectsRepository.save(updProj);
        return updProj;
    }

    public Project updateProj(Long id, Project project){
        var updProj = projectsRepository.findById(id).orElseThrow();
        updProj.setName(project.getName());
        updProj.setDuration(project.getDuration());
        updProj.setTaskList(project.getTaskList());
        updProj.setTaskCount(project.getTaskCount());
        updProj.setMaxResourses(project.getMaxResourses());

        updProj = projectsRepository.save(updProj);
        return updProj;

    }
}
