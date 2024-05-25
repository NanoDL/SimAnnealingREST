package com.example.kursovayaspring;
import org.json.*;

public  class ParserJSONFromTXT {
    public static JSONObject parse(String str) throws JSONException {
        JSONObject json = new JSONObject();
        JSONArray tasks = new JSONArray();
        String[] lines = str.split("\n");
        int taskCount = Integer.parseInt(lines[0].split(" ")[0]);
        int resourseCount = Integer.parseInt(lines[0].split(" ")[1]);

        json.put("taskCount",taskCount);

        int maxResourses = Integer.parseInt(lines[1]);
        json.put("maxResourses",maxResourses);

        for (int i = 2, j=1;i<lines.length; i++,j++){
            String[] parts = lines[i].split("\\s+");
            JSONObject taskObj = new JSONObject();
            taskObj.put("taskNumber",j);
            taskObj.put("duration",Integer.parseInt(parts[0]));
            taskObj.put("resource",Integer.parseInt(parts[1]));
            int numFollowers =Integer.parseInt(parts[2]);
            taskObj.put("numFollowers",numFollowers);

            JSONArray followers = new JSONArray();
            int k = 3;
            while (numFollowers>0){
                followers.put(Integer.parseInt(parts[k]));
                k++;
                numFollowers--;
            }

            taskObj.put("followers", followers);
            tasks.put(taskObj);
        }
        json.put("tasks", tasks);


        return json;
    }
}
