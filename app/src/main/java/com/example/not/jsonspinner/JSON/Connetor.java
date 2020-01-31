package com.example.not.jsonspinner.JSON;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 1. Esta
 */

public class Connetor {
    public static Object connect(String jsonUrl) {
        try {
            URL url = new URL(jsonUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setConnectTimeout(15000);
            con.setReadTimeout(15000);
            con.setDoInput(true);

            return con;

        }catch (Exception e) {
            e.printStackTrace();
            return "Error "+e.getMessage();
        }
    }


}
