package com.example.docs.test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class RestAPICaller {
    public static JSONObject restCall(String paramUrl) {
        try {
            URL url = new URL(paramUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            if (conn.getResponseCode() != 200) {
                System.out.println("Failed: HTTP error code : " + conn.getResponseCode());
                throw new RuntimeException("Failed: HTTP error code : " + conn.getResponseCode());
            } else {
                // System.out.println("발송 성공");
            }
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = br.readLine()) != null) {
                // System.out.println(line);
                sb.append(line);
            }
            br.close();
            conn.disconnect();
            return (JSONObject) (new JSONParser().parse(sb.toString()));
        } catch (Exception e) {
            System.out.println("RestCall Fail : " + e.getMessage());
            return null;
        }
    }
}
