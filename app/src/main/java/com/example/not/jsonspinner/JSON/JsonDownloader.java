package com.example.not.jsonspinner.JSON;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * Created by NOT on 31/01/2020.
 */

public class JsonDownloader extends AsyncTask<Void, Void, String> {

    private Context mContext;
    private String mJsonUrl;
    private Spinner mSpinner;
    private ProgressDialog mProgressDialog;

    public JsonDownloader(Context mContext, String mJsonUrl, Spinner mSpinner) {
        this.mContext = mContext;
        this.mJsonUrl = mJsonUrl;
        this.mSpinner = mSpinner;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return this.download();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle("Download JSON");
        mProgressDialog.setMessage("Downloading... Please wait");
        mProgressDialog.show();
    }

    @Override
    protected void onPostExecute(String jsonData) {
        mProgressDialog.dismiss();
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            public void run() {
//                mProgressDialog.dismiss();
//            }
//        }, 2000); // 3000 milliseconds delay

        if (jsonData.startsWith("Error")) {
            Toast.makeText(mContext, jsonData, Toast.LENGTH_SHORT).show();
        } else {

            //PARSE
            JsonParser jsonParser = new JsonParser(mContext, jsonData, mSpinner);
            jsonParser.execute();
        }
    }

    private String download() {
        Object connection = Connetor.connect(mJsonUrl);
        if (connection.toString().startsWith("Error")) {
            return connection.toString();
        }

        try {
            HttpURLConnection con = (HttpURLConnection) connection;
            if (con.getResponseCode() == con.HTTP_OK) {

                //GET INPUT FROM STREAM
                BufferedInputStream bis = new BufferedInputStream(con.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(bis));

                String line;
                StringBuffer jsonData = new StringBuffer();

                //READ
                while ((line = br.readLine()) != null) {
                    jsonData.append(line + "\n");
                }

                //CLOSE RESOURCES
                br.close();
                bis.close();

                //RETURN JSON
                return jsonData.toString();
            } else {
                return "Error " + con.getResponseMessage();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error " + e.getMessage();
        }
    }
}
