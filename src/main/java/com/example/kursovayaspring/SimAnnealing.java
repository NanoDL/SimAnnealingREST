package com.example.kursovayaspring;

import com.example.kursovayaspring.model.Project;
import com.example.kursovayaspring.model.Task;

import java.io.IOException;
import java.util.*;

public class SimAnnealing {
    //private static final int NUM_TASKS = 10;
    private static final int MAX_TEMP = 100;
    private static final int MIN_TEMP = 1;
    private static final double COOLING_RATE = 0.002;

    private static int[] startOrder = {1,2,3,4,5,6,7};
    private static int[][] dependencies = {
            {1,3}, {3,5}, {2,4}, {4,6}, {5,7}, {6,7}
    };
    private static int[] durations =  {10, 5, 8, 6, 12, 7, 9,};
    private static int[] resources = {2, 3, 2, 4, 2, 3, 4,};

    private static int maxResources;
    //private static List<Task> tasks;

    public static int Simulation(Project project) throws IOException {
        System.out.println(project.toString());
        Task task = (project.getTaskList()).get(0);
        System.out.println(task.toString());
        System.out.println(project.getTaskList().indexOf(task));
        double temp = MAX_TEMP;
        int it = 1;
        while (temp > MIN_TEMP) {
            System.out.println("while");
            int currentDuration = calculateDuration(project);//
            var newTasks = generateNewOrder(project.getTaskList());

            int newDuration = calculateDuration(newTasks,project.getMaxResourses());
            System.out.println(newDuration);
            if (acceptanceProbability(currentDuration, newDuration, temp) >= Math.random()) {
                project.setTaskList(newTasks);
            }

            temp =  (temp * (1 - COOLING_RATE));
            //temp = (temp - 0.0001);
            //temp = (int) (MAX_TEMP * 0.1 / it);
            it++;
        }
        return calculateDuration(project);
    }



    public static double acceptanceProbability(int currentDuration, int newDuration,double temp){
        if (newDuration<currentDuration) {
            return 1.0;
        }

        return Math.exp((double) -( newDuration- currentDuration)/temp);
    }

    // доделать генерацию и протестить
    public static int[] generateNewOrder(int[] order){
        int [] newOrder = order.clone();
        swap(newOrder,new Random().nextInt(newOrder.length), new Random().nextInt(newOrder.length));
        while (!isValid(newOrder)){
            swap(newOrder,new Random().nextInt(newOrder.length), new Random().nextInt(newOrder.length));
        }
        //System.out.println("Вышел");
        return newOrder;
    }

    public static List<Task> generateNewOrder (List<Task> tasks) {
        List<Task> newTasks = new ArrayList<>(tasks);
        Collections.swap(newTasks,new Random().nextInt(newTasks.size()), new Random().nextInt(newTasks.size()));
        while (!isValid(newTasks)){
            Collections.swap(newTasks,new Random().nextInt(newTasks.size()), new Random().nextInt(newTasks.size()));
        }

        return newTasks;
    }

    public static void swap(int[] arr, int i, int j){
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    public static int calculateDuration(int [] order){
        int currentBusyResources = 0;
        int projectDuration = 0;
        //List<Task> scheduledTasks = new ArrayList<>();

        for (int task : order) {
            if (currentBusyResources + resources[task-1] <= maxResources) {
                currentBusyResources += resources[task-1];
                projectDuration = Math.max(projectDuration, durations[task-1]);
                // scheduledTasks.add(task);
            } else {
                currentBusyResources = resources[task-1];
                projectDuration += durations[task-1];
                // scheduledTasks.add(task);
            }
        }
        return projectDuration;
    }

    public static int calculateDuration (Project project){
        int currentBusyResources = 0;
        int projectDuration = 0;
        List<Task> tasks = project.getTaskList();
        System.out.println("calculateDuration");
        for (var task : tasks) {
            System.out.println("считаю длительность в функции Calculate duration");
            if (currentBusyResources + task.getResource() <= project.getMaxResourses()) {
                currentBusyResources += task.getResource();
                projectDuration = Math.max(projectDuration, task.getDuration());
                // scheduledTasks.add(task);
            } else {
                currentBusyResources = task.getResource();
                projectDuration += task.getDuration();
                // scheduledTasks.add(task);
            }
        }
        return projectDuration;
    }

    public static int calculateDuration (List<Task> tasks, int maxResources){
        int currentBusyResources = 0;
        int projectDuration = 0;
        //List<Task> tasks = project.getTaskList();

        for (var task : tasks) {
            if (currentBusyResources + task.getResource() <= maxResources) {
                currentBusyResources += task.getResource();
                projectDuration = Math.max(projectDuration, task.getDuration());
                // scheduledTasks.add(task);
            } else {
                currentBusyResources = task.getResource();
                projectDuration += task.getDuration();
                // scheduledTasks.add(task);
            }
        }
        return projectDuration;
    }

    public static boolean isValid(int[] order) {

        List<Integer> orderList = Arrays.stream(order).boxed().toList();
        for (int[] dep : dependencies) {
            int index1 = orderList.indexOf(dep[0]);
            int index2 = orderList.indexOf(dep[1]);
            if (index1>index2){

                return false;
            }

        }
        return true;
    }
    public static boolean isValid (List<Task> tasks){
        for (var task : tasks){
            int id1 = tasks.indexOf(task);
            for (var follow : task.getFollowers()){
                Task taskFind = tasks.stream()
                        .filter(t -> t.getTaskNumber() == follow)
                        .findFirst()
                        .orElse(null);
                if (id1 > tasks.indexOf(taskFind)){
                    return false;
                }
            }
        }
        return true;
    }
}