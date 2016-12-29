package com.simplyblood.admin.simplyblood;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputType;
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
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.ContentValues.TAG;

/**
 * Created by Akshit jain on 10-12-2016.
 */


public class insertion extends Activity {
    private EditText ot, first, middle, last, dob, pwd, phone, email,cnf, addr1, addr2, country, state, city, emerc, emerp, prdate;
    SharedPreferences sharedpreferences;
    private DatePickerDialog toDatePickerDialog;
    private Button setdate;
    private int pYear;
    private int pMonth;
    private int pDay;
    CountryCodePicker ccp;
    static final int DATE_DIALOG_ID = 0;

    private SimpleDateFormat dateFormatter;

    private DatePickerDialog.OnDateSetListener pDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    pYear = year;
                    pMonth = monthOfYear;
                    pDay = dayOfMonth;
                    updateDisplay();
                    displayToast();
                }
            };
    private void updateDisplay() {
        dob.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(pYear ).append("-")
                        .append(pMonth+1).append("-")
                        .append(pDay).append(" "));
    }
    private void displayToast() {
        Toast.makeText(this, new StringBuilder().append(dob.getText()),  Toast.LENGTH_SHORT).show();

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);




        ot = (EditText) findViewById(R.id.editText9);
        first = (EditText) findViewById(R.id.editText);

        middle = (EditText) findViewById(R.id.editText2);
        last = (EditText) findViewById(R.id.editText3);
        dob = (EditText) findViewById(R.id.editText7);
        email = (EditText) findViewById(R.id.editText12);
        pwd = (EditText) findViewById(R.id.editText5);
        cnf = (EditText) findViewById(R.id.editText6);
      setdate =(Button) findViewById(R.id.button7);

        setdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
        /** Get the current date */
        final Calendar cal = Calendar.getInstance();
        pYear = cal.get(Calendar.YEAR);
        pMonth = cal.get(Calendar.MONTH);
        pDay = cal.get(Calendar.DAY_OF_MONTH);

        /** Display the current date in the TextView */


    }
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        pDateSetListener,
                        pYear, pMonth, pDay);
        }
        return null;
    }




    public void saveinfo(View view) {
        String otp = ot.getText().toString();
        String FirstName = first.getText().toString();
        String MiddleName = middle.getText().toString();
        String LastName = last.getText().toString();
        String DateOfBirth = dob.getText().toString();

        String Confirm =  cnf.getText().toString();
        /*String AdressLine1  = addr1.getText().toString();
        String AdressLine2 = addr2.getText().toString();
        String Country = country.getText().toString();
        String State = state.getText().toString();
        String City = city.getText().toString();
        String EmergencyContactName = emerc.getText().toString();
        String EmergencyContactNumber = emerp.getText().toString();
        String ImportantDate= prdate.getText().toString();*/
        String EMail = email.getText().toString();
        String Password = pwd.getText().toString();
        SharedPreferences sharedpreferences = getSharedPreferences(login2.MyPREFERENCES, Context.MODE_PRIVATE);
        String ContactNumber= sharedpreferences.getString("ContactNumber","");
        Log.d(TAG,ContactNumber);


        if(!isValidfname(FirstName)){
            first.setError("Enter correct first name");
        }
        else if(!isValidlname(LastName)){
            last.setError("Enter correct last name");
        }

        else if (!isValidPassword(Password)) {
            pwd.setError("Invalid Password");}
        else if (!isValidconfirm(Confirm)){
            cnf.setError("Password does not match");
        }



       else if (!isValidEmail(EMail)) {
            email.setError("Invalid Email");

        } else {
            BackGroundTask backGroundTask = new BackGroundTask();
            backGroundTask.execute(FirstName, MiddleName, LastName, Password, DateOfBirth, otp,ContactNumber, EMail);
            finish();
        }
    }


    class BackGroundTask extends AsyncTask<String, Void, String> {

        String add_url;

        @Override
        protected void onPreExecute() {
            add_url = "http://simplyblood.com/connection.php";
        }


        protected String doInBackground(String... args) {
            String FirstName, UserName, LastName, MiddleName, Password, DateOfBirth, otp, EMail,ContactNumber;
            FirstName = args[0];
            MiddleName = args[1];

            LastName = args[2];

            Password = args[3];
            DateOfBirth = args[4];
            otp = args[5];
            ContactNumber = args[6];
            EMail = args[7];
            try {
                URL url = new URL(add_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data_string = URLEncoder.encode("FirstName", "UTF-8") + "=" + URLEncoder.encode(FirstName, "UTF-8") + "&" +
                        URLEncoder.encode("MiddleName", "UTF-8") + "=" + URLEncoder.encode(MiddleName, "UTF-8") + "&" +
                        URLEncoder.encode("LastName", "UTF-8") + "=" + URLEncoder.encode(LastName, "UTF-8") + "&" +
                        URLEncoder.encode("Password", "UTF-8") + "=" + URLEncoder.encode(Password, "UTF-8") + "&" +
                        URLEncoder.encode("DateOfBirth", "UTF-8") + "=" + URLEncoder.encode(DateOfBirth, "UTF-8") + "&" +
                        URLEncoder.encode("otp", "UTF-8") + "=" + URLEncoder.encode(otp, "UTF-8") + "&" +
                        URLEncoder.encode("ContactNumber", "UTF-8") + "=" + URLEncoder.encode(ContactNumber, "UTF-8")+"&"+
                        URLEncoder.encode("EMail", "UTF-8") + "=" + URLEncoder.encode(EMail, "UTF-8");

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


        @Override
        protected void onPostExecute(String result) {
            String s = result.trim();

            if (s.equalsIgnoreCase("a")) {

                Toast.makeText(getApplicationContext(), "Successfully registered", Toast.LENGTH_LONG).show();


            } else {
                Toast.makeText(getApplicationContext(), "Registration unsuccessful, Check your details", Toast.LENGTH_LONG).show();
            }
            /*SharedPreferences sharedpreferences = getSharedPreferences(otpverif.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(insertion.this,MainActivity.class);
            startActivity(intent);*/
        }
    }

    private boolean isValidEmail(String EMail) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(EMail);
        return matcher.matches();
    }

    // validating password with retype password
    protected boolean isValidPassword(String Password) {
        if (Password != null && Password.length() > 4) {
            return true;
        }
        return false;





    }
    private boolean isValidfname(String FirstName)
    {
        if(FirstName.length()!=0)
        {return true;
    }
        return false;

}
private boolean isValidlname(String LastName)
{
    if(LastName.length()!=0)
    {
        return true;
    }
    return false;
}
    private boolean isValiduser(String UserName)
    {

        if(UserName.length()>5&&UserName.length()<13){
            String user = "^[A-Za-z0-9]+$";
            Pattern pattern = Pattern.compile(user);
            Matcher matcher = pattern.matcher(UserName);
            return matcher.matches();

}return false;
    }
    protected boolean isValidconfirm(String Confirm)
    {
        if(Confirm.equals(pwd.getText().toString())){
            return true;
        }
        return false;
    }
    private boolean isValidphone(String Phone)
    {
        if(Phone.length()!=10)
        {
            return false;
        }
        return true;
    }
    private boolean otp(String otp)
    {
        if(otp.length()<4)
        {
            return false;
        }
        return true;
    }
}