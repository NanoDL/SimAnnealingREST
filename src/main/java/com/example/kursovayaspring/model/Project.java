package com.example.kursovayaspring.model;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table
@Transactional

public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_project")

    private int id;
    private String name;

    private int duration;
    private int maxResourses;
    private int taskCount;
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Task> taskList = new ArrayList<>();


    public void addTask(Task task){
        taskList.add(task);
        task.setProject(this);


    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", duration=" + duration +
                ", maxResourses=" + maxResourses +
                ", taskCount=" + taskCount +
                ", taskList=" + taskList +
                '}';
    }

    public void removeTask(Task task){
        taskList.remove(task);
        task.setProject(null);
    }
}
