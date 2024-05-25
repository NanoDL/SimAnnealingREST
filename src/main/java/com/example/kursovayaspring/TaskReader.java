package com.example.kursovayaspring;


import com.example.kursovayaspring.model.Task;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class TaskReader {
    public static List<Task> readTasksFromFile (String filename) throws IOException {
        List<Task> tasks = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine(); // читаем первую строку
            int numTasks = Integer.parseInt(line.split(" ")[0]); // получаем количество задач
            int numResources = Integer.parseInt(line.split(" ")[1]); // получаем количество ресурсов
            line = br.readLine(); // читаем вторую строку
            int maxResourceLimit = Integer.parseInt(line); // получаем максимальное ограничение по ресурсу
            for (int i = 0; i < numTasks; i++) {
                line = br.readLine(); // читаем следующую строку
                String[] tokens = line.split("\\s+"); // разбиваем строку по пробелам
                int id = i+1;
                int duration = Integer.parseInt(tokens[0]);
                int resource = Integer.parseInt(tokens[1]);
                int numFollowers = Integer.parseInt(tokens[2]);
                List<Integer> followers = new ArrayList<>();
                for (int j = 3; j < tokens.length; j++) {
                    followers.add(Integer.parseInt(tokens[j]));
                }
                //tasks.add(new Task(id, duration, resource, numFollowers, followers));
            }
        }
        return tasks;
    }
    public static int readMaxResources (String filename) throws IOException {
        int maxResourceLimit;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))){
            String line = br.readLine(); // читаем первую строку
            line = br.readLine(); // читаем вторую строку
            maxResourceLimit = Integer.parseInt(line); // получаем максимальное ограничение по ресурсу
        }
        return maxResourceLimit;
    }
}