package com.example.not.jsonspinner.JSON;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by NOT on 31/01/2020.
 */

public class JsonParser extends AsyncTask<Void, Void, Boolean> {
    private Context mContext;
    private String mJsonUrl;
    private Spinner mSpinner;
    private ProgressDialog mProgressDialog;
    private ArrayList<String> users = new ArrayList<>();

    public JsonParser(Context mContext, String mJsonUrl, Spinner mSpinner) {
        this.mContext = mContext;
        this.mJsonUrl = mJsonUrl;
        this.mSpinner = mSpinner;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        return this.parse();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle("Parse JSON");
        mProgressDialog.setMessage("Parsing... Please wait");
        mProgressDialog.show();

    }

    @Override
    protected void onPostExecute(Boolean isParsed) {
        super.onPostExecute(isParsed);
        mProgressDialog.dismiss();

        if(isParsed) {

            //BIND SPINNER
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
                    android.R.layout.simple_list_item_1, users);
            mSpinner.setAdapter(adapter);

        }else {
            Toast.makeText(mContext, "Unable to Parse", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean parse() {
        try {
            JSONArray jsonArray = new JSONArray(mJsonUrl);
            JSONObject jsonObject;

            users.clear();

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                users.add(name);
            }

            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }
}
