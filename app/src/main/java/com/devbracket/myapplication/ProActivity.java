package com.devbracket.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ProActivity extends AppCompatActivity {

    // Declaration
    TextView txt1,txt2,txt3;
    JSONObject js;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initializing
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                js = new JSONObject(getIntent().getExtras().get("data").toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        txt1 = findViewById(R.id.pro_email);
        txt2 = findViewById(R.id.pro_name);
        txt3 = findViewById(R.id.pro_pass);

        try {
            txt1.setText(js.getString("email"));
            txt2.setText(js.getString("username"));
            txt3.setText(js.getString("password"));
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}


