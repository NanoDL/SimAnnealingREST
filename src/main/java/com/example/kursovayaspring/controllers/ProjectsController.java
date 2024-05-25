package com.example.kursovayaspring.controllers;

import com.example.kursovayaspring.SimAnnealing;
import com.example.kursovayaspring.model.Project;
import com.example.kursovayaspring.model.Task;
import com.example.kursovayaspring.services.ProjectsService;
import com.example.kursovayaspring.services.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/projects")
public class ProjectsController {
    @Autowired
    private ProjectsService projectsService;


    @GetMapping("/{id}")
    public ResponseEntity<Project> index(@PathVariable Long id){
        Project project = projectsService.findProjectById(id);
        return ResponseEntity.ok(project);

    }

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects(){
        List<Project> projects = projectsService.findAll();
        return ResponseEntity.ok(projects);

    }

    @PostMapping
    public ResponseEntity<Project> addProject(@RequestBody Project project, BindingResult result){
        projectsService.save(project);

        return ResponseEntity.ok(project);
    }


    @DeleteMapping
    public ResponseEntity<Object> deleteProject(@RequestParam Long id){
        if (!projectsService.existsProjectById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        projectsService.delete(id);
        return ResponseEntity.noContent().build();

    }

    @PostMapping("/{id}/tasks")
    public ResponseEntity<Object> addTask(@PathVariable Long id, @RequestBody Task task){

        projectsService.addTask(id,task);
        var project  = projectsService.findProjectById(id);
        return ResponseEntity.ok(project);
    }

    @DeleteMapping("/{id}/tasks/{taskId}")
    public ResponseEntity<Object> deleteTask(@PathVariable Long id, @PathVariable Long taskId){
        projectsService.removeTask(id,taskId);
        var project  = projectsService.findProjectById(id);
        return ResponseEntity.ok(project);
    }


    @GetMapping("/{id}/sim")
    public ResponseEntity<Object> simulation(@PathVariable Long id) throws IOException {
        Project project = projectsService.findProjectById(id);
        project.setDuration(SimAnnealing.Simulation(project));
        return ResponseEntity.ok(project);
    }
}
