package com.example.kursovayaspring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.xml.bind.annotation.XmlTransient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
@Transactional
public class Task {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    int taskNumber;
    int duration;
    int resource;
    int numFollowers;

    @ElementCollection
    List<Integer> followers;

    @JsonIgnore
    //@XmlTransient
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_project")
    private Project project;


    @Override
    public String toString() {
        return "Task{" +
                "followers=" + followers +
                ", numFollowers=" + numFollowers +
                ", resource=" + resource +
                ", duration=" + duration +
                ", taskNumber=" + taskNumber +
                ", id=" + id +
                '}';
    }

    public void calculateNumFollowers(){
        this.numFollowers=followers.size();
    }

}
