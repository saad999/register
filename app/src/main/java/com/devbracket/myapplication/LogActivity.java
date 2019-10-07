package com.devbracket.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class LogActivity extends AppCompatActivity {

    // Declaration
    private static final String GET_USER = "https://go2gether.000webhostapp.com/api/get.php";
    private static final String LOG = "https://go2gether.000webhostapp.com/api/login.php";
    private static final String LOG1 = "http://192.168.43.94/Andro/v1/index.php";
    private static final String LOgg = "http://192.168.43.94/Andro/v1/gget.php";
    Button btn;
    EditText ed1, ed2;
    TextView txt1, txt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        // Initializing
        btn = findViewById(R.id.log_btn);
        ed1 = findViewById(R.id.log_email);
        ed2 = findViewById(R.id.log_pass);
        txt1 = findViewById(R.id.forget_pass);
        txt2 = findViewById(R.id.new_acc);


        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogActivity.this, RegActivity.class));
                finish();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Go go = new Go(LogActivity.this);
                go.execute();
            }
        });
    }

    private class Go extends AsyncTask<String, Integer, String> {

        Context context;
        String txt1 = "", txt2 = "";
        ProgressDialog progressDialog;

        public Go(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Login...");
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(LOG1 + "?e=" + ed1.getText().toString());
                URLConnection connection = url.openConnection();
                InputStreamReader inputStream = new InputStreamReader(connection.getInputStream());
                BufferedReader reader = new BufferedReader(inputStream);
                String ln;
                while ((ln = reader.readLine()) != null) {
                    txt1 += ln;
                }

                URL ur = new URL(LOgg + "?e=" + ed1.getText().toString());
                URLConnection conn = ur.openConnection();
                InputStreamReader input = new InputStreamReader(conn.getInputStream());
                BufferedReader read = new BufferedReader(input);
                String line;
                while ((line = read.readLine()) != null) {
                    txt2 += line;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            progressDialog.dismiss();
            if (txt1.equals("not reg")) {
                Toast.makeText(context, txt1, Toast.LENGTH_SHORT).show();
            } else if (!ed2.getText().toString().trim().equals(txt1)) {
                Toast.makeText(context, "password not correct", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(LogActivity.this, ProActivity.class);
                intent.putExtra("data", txt2);
                startActivity(intent);
            }
            super.onPostExecute(s);
        }
    }
}


