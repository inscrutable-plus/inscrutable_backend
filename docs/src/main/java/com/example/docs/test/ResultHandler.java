package com.example.docs.test;

import java.util.HashMap;
import java.util.Map;

public class ResultHandler {
    public static <T> Map<String, Object> formatResult(T result) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("result", result);
        ret.put("status", (result == null) ? "Bad Request" : "Success");
        return ret;
    }
}
