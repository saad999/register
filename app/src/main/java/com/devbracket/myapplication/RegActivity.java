package com.devbracket.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;

public class RegActivity extends AppCompatActivity {

    // Declaration
    private static final String REG_USER = "https://go2gether.000webhostapp.com/api/go.php";
    Button btn;
    EditText ed1, ed2, ed3;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        // Initializing
        btn = findViewById(R.id.reg_btn);
        ed1 = findViewById(R.id.reg_name);
        ed2 = findViewById(R.id.reg_pass);
        ed3 = findViewById(R.id.reg_email);
        txt = findViewById(R.id.all_acc);

        final String name = ed1.getText().toString();
        final String email = ed3.getText().toString();
        final String pass = ed2.getText().toString();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (email.matches("^(.+)@(.+)$")) {
                    if (name.matches("[a-zA-Z0-9\\._\\-]{3,}")) {
                        if (pass.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")) {

                        }else {
                            Toast.makeText(RegActivity.this,"8 char password!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegActivity.this,"Invalid Username!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegActivity.this,"Invalid Email!", Toast.LENGTH_SHORT).show();
                }*/
                Go go = new Go(RegActivity.this);
                go.execute();
            }
        });

        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegActivity.this,LogActivity.class));
            }
        });
    }

    private class Go extends AsyncTask<String, Integer, String> {
        Context context;
        String txt = "";
        ProgressDialog progressDialog;

        public Go(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Registering...");
            progressDialog.show();
            progressDialog.setCancelable(false);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(REG_USER
                        + "?u=" + ed1.getText().toString()
                        + "&p=" + ed2.getText().toString()
                        + "&e=" + ed3.getText().toString());
                URLConnection connection = url.openConnection();
                InputStreamReader inputStream = new InputStreamReader(connection.getInputStream());
                BufferedReader reader = new BufferedReader(inputStream);

                String ln;
                while ((ln = reader.readLine()) != null) {
                    txt += ln;
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
            Toast.makeText(context, txt, Toast.LENGTH_SHORT).show();
            super.onPostExecute(s);
        }
    }
}


