package com.example.docs.test;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@SuppressWarnings(value = "unchecked")
public class DataParser {

    public static List<Problem> parseProblems(JSONObject res) {
        List<Problem> result = new ArrayList<>();
        // Precondition : res[items] is Iterable
        Iterable<JSONObject> items = (JSONArray) res.get("items");

        if (items == null)
            return result;
        for (JSONObject rawObject : items) {
            try {
                Problem problem = new Problem();
                problem.setProblemId(Integer.parseInt(rawObject.get("problemId").toString()));
                problem.setTitle(rawObject.get("titleKo").toString());
                problem.setLevel(Integer.parseInt(rawObject.get("level").toString()));
                problem.setSolveCount(Integer.parseInt(rawObject.get("acceptedUserCount").toString()));
                problem.setAverageTry(Float.parseFloat(rawObject.get("averageTries").toString()));
                result.add(problem);
            } catch (NumberFormatException e) {
                // TODO: Exception Handling
            }
        }

        return result;
    }

    public static String getSolvedClass(JSONObject res) {
        String s = res.get("class").toString();

        if (!res.get("classDecoration").equals("none")) {
            s += "+";
        }

        if (res.get("classDecoration").equals("gold")) {
            s += "+";
        }

        return s;
    }

}
