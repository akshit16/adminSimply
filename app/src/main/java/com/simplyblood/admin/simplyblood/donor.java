package com.simplyblood.admin.simplyblood;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;

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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.ContentValues.TAG;

public class donor extends AppCompatActivity {
    private EditText  unit, name, no, adr1, adr2,  cntry, stat, cty, pin;
    Spinner bgrp;
    Button search;
    SharedPreferences sharedpreferences;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor);




        bgrp = (Spinner) findViewById(R.id.spinner2);
        unit = (EditText) findViewById(R.id.editText30);

        name = (EditText) findViewById(R.id.editText31);
        no = (EditText) findViewById(R.id.editText32);
        adr1 = (EditText) findViewById(R.id.editText33);
        adr2 = (EditText) findViewById(R.id.editText34);
        cntry= (EditText) findViewById(R.id.editText35);
        stat = (EditText) findViewById(R.id.editText36);
        cty  =(EditText) findViewById(R.id.editText37);
        pin = (EditText) findViewById(R.id.editText38);
        search = (Button) findViewById(R.id.button13);}



    public void searchD(View view) {
        String BloodGroup = bgrp.getSelectedItem().toString();
        String Units = unit.getText().toString();
        String ContactName = name.getText().toString();
        String ContactNumber = no.getText().toString();
        String Addressline1 = adr1.getText().toString();
        String Addressline2 = adr2.getText().toString();

        String country = cntry.getText().toString();
        String state = stat.getText().toString();
        String city = cty.getText().toString();
        /*String AdressLine1  = addr1.getText().toString();



        String EmergencyContactName = emerc.getText().toString();
        String EmergencyContactNumber = emerp.getText().toString();
        String ImportantDate= prdate.getText().toString();*/
        String pincode = pin.getText().toString();







            BackGroundTask backGroundTask = new BackGroundTask();
            backGroundTask.execute(BloodGroup, Units, ContactName, ContactNumber,Addressline1,Addressline2, country, state,city,pincode );
            finish();

    }


    class BackGroundTask extends AsyncTask<String, Void, String> {

        String add_url;

        @Override
        protected void onPreExecute() {
            add_url = "http://simplyblood.com/donorandroid.php";
        }


        protected String doInBackground(String... args) {
            String BloodGroup, Units, ContactName, ContactNumber,Addressline1,Addressline2, country, state,city,pincode;
            BloodGroup = args[0];
            Units = args[1];

            ContactName = args[2];

            ContactNumber = args[3];
            Addressline1 = args[4];
            Addressline2 = args[5];
            country = args[6];
            state = args[7];
            city = args[8];
            pincode=args[9];

            try {
                URL url = new URL(add_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data_string = URLEncoder.encode("BloodGroup", "UTF-8") + "=" + URLEncoder.encode(BloodGroup, "UTF-8") + "&" +
                        URLEncoder.encode("Units", "UTF-8") + "=" + URLEncoder.encode(Units, "UTF-8") + "&" +
                        URLEncoder.encode("ContactName", "UTF-8") + "=" + URLEncoder.encode(ContactName, "UTF-8") + "&" +
                        URLEncoder.encode("ContactNumber", "UTF-8") + "=" + URLEncoder.encode(ContactNumber, "UTF-8") + "&" +
                        URLEncoder.encode("Addressline1", "UTF-8") + "=" + URLEncoder.encode(Addressline1, "UTF-8") + "&" +
                        URLEncoder.encode("Addressline2", "UTF-8") + "=" + URLEncoder.encode(Addressline2, "UTF-8") + "&" +
                        URLEncoder.encode("country", "UTF-8") + "=" + URLEncoder.encode(country, "UTF-8")+"&"+
                        URLEncoder.encode("state", "UTF-8") + "=" + URLEncoder.encode(state, "UTF-8")+"&"+
                URLEncoder.encode("city", "UTF-8") + "=" + URLEncoder.encode(city, "UTF-8")+"&"+
                        URLEncoder.encode("pincode", "UTF-8") + "=" + URLEncoder.encode(pincode, "UTF-8");

                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                //String result = "";
                //String line = "";
                //while ((line = bufferedReader.readLine()) != null) {
                  //  result += line;
                //}
                bufferedReader.close();
                inputStream.close();

                httpURLConnection.disconnect();


                return "data received";
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


        @Override
        protected void onPostExecute(String result) {



                Toast.makeText(getApplicationContext(), "Donors have been notified", Toast.LENGTH_LONG).show();
            }
            /*SharedPreferences sharedpreferences = getSharedPreferences(otpverif.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(insertion.this,MainActivity.class);
            startActivity(intent);*/
        }



}



