package com.simplyblood.admin.simplyblood;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class login extends ActionBarActivity {

    private EditText uname;
    private EditText pwd;

    public static final String USER_NAME = "USERNAME";

    String UserName;
    String Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        uname = (EditText) findViewById(R.id.editText10);
        pwd = (EditText) findViewById(R.id.editText13);
    }

    public void invkeLogin(View view) {
        UserName = uname.getText().toString();
        Password = pwd.getText().toString();
String type = "login";

        BackGroundWorker backGroundWorker = new BackGroundWorker();
        backGroundWorker.execute( type, UserName, Password);


    }


      public  class BackGroundWorker extends AsyncTask<String, Void, String> {

             Dialog loadingDialog;

          AlertDialog alertDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(login.this, "Please wait", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                String type = params[0];
                String url = "http://simplyblood.com/loginandroid.php";
                if (type.equals("login")) {
                    try {
                        String user = params[1];
                        String pass = params[2];
                        URL url1 = new URL(url);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection();
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setDoInput(true);
                        OutputStream outputStream = httpURLConnection.getOutputStream();
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                        String post_data = URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8") + "&" +
                                URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(pass, "UTF-8");
                        bufferedWriter.write(post_data);
                        bufferedWriter.flush();
                        bufferedWriter.close();
                        InputStream inputStream = httpURLConnection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                        String result = "";
                        String line = "";
                        while ((line = bufferedReader.readLine()) != null) {
                            result += line;
                        }
                        bufferedReader.close();
                        inputStream.close();
                        httpURLConnection.disconnect();
                        return result;
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
                return  null;
            }

                    @Override
            protected void onPostExecute(String result) {
                String s = result.trim();
                        loadingDialog.dismiss();
                        if(s.equalsIgnoreCase("success")){
                            Intent intent = new Intent(login.this, UserProfile.class);

                            finish();
                            startActivity(intent);
                        }else {
                            Toast.makeText(getApplicationContext(), "Invalid User Name or Password", Toast.LENGTH_LONG).show();
                        }


                    }


}}