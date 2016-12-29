package com.simplyblood.admin.simplyblood;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class otpverif extends Activity {
    private EditText username, phone;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverif);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        username = (EditText) findViewById(R.id.editText26);
        phone = (EditText) findViewById(R.id.editText29);
    }
    public void otpinfo(View view) {
        String UserName = username.getText().toString();
        String ContactNumber = phone.getText().toString();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("ContactNumber", ContactNumber );
        editor.commit();

        BackGroundTask backGroundTask = new BackGroundTask();
        backGroundTask.execute(UserName, ContactNumber);

        finish();
}
class BackGroundTask extends AsyncTask<String, Void, String> {

    String add_url;
    @Override
    protected void onPreExecute() {
        add_url = "http://simplyblood.com/otpfirst.php";
    }


    protected String doInBackground(String... args) {
        String UserName, ContactNumber;
        UserName = args[0];
        ContactNumber= args[1];
        try {
            URL url = new URL(add_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String data_string = URLEncoder.encode("UserName", "UTF-8") + "=" + URLEncoder.encode(UserName, "UTF-8") + "&" +
                    URLEncoder.encode("ContactNumber", "UTF-8") + "=" + URLEncoder.encode(ContactNumber, "UTF-8");
            bufferedWriter.write(data_string);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
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
        return null;
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
    protected void onPostExecute(String result) {
        String s = result.trim();

        if (s.equalsIgnoreCase("r")) {

            Toast.makeText(getApplicationContext(), "Select different user name", Toast.LENGTH_LONG).show();


        } else {
            Toast.makeText(getApplicationContext(), "OTP has been sent to your phone", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(otpverif.this,insertion.class);
            startActivity(intent);
        }

    }}}