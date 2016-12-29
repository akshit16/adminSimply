package com.simplyblood.admin.simplyblood;



import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.content.ContentValues.TAG;


/**
 * Created by Akshit jain on 11-12-2016.
 */

public class UserProfile extends Activity {
    private EditText addr1, addr2, country, state, city, emerc, emerp, prdate, uname;
    Spinner bgroup;
    private DatePickerDialog toDatePickerDialog;
    private Button setdate;
    private int pYear;
    private int pMonth;
    private int pDay;

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
        prdate.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(pYear ).append("-")
                        .append(pMonth+1).append("-")
                        .append(pDay).append(" "));
    }
    private void displayToast() {
        Toast.makeText(this, new StringBuilder().append(prdate.getText()),  Toast.LENGTH_SHORT).show();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userprofile);
        bgroup = (Spinner)findViewById(R.id.spinner);
        addr1 = (EditText) findViewById(R.id.editText14);
        addr2 = (EditText) findViewById(R.id.editText18);
        country = (EditText) findViewById(R.id.editText21);
        state = (EditText) findViewById(R.id.editText22);
        city = (EditText) findViewById(R.id.editText23);
        emerc = (EditText) findViewById(R.id.editText24);
        emerp = (EditText) findViewById(R.id.editText27);
        prdate = (EditText) findViewById(R.id.editText28);
        setdate =(Button) findViewById(R.id.button10);

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







    public void updateInfo(View view) {
        String AddressLine1  = addr1.getText().toString();
        String AddressLine2 = addr2.getText().toString();
        String Country = country.getText().toString();
        String State = state.getText().toString();
        String City = city.getText().toString();
        String EmergencyContactName = emerc.getText().toString();
        String EmergencyContactNumber = emerp.getText().toString();
        String ImportantDate= prdate.getText().toString();
        String BloodGroup = bgroup.getSelectedItem().toString();


        SharedPreferences sharedpreferences = getSharedPreferences(login2.MyPREFERENCES, Context.MODE_PRIVATE);
        String  UserName= sharedpreferences.getString("UserName","");
        Log.d(TAG,UserName);

        if(!isValidaddr1(AddressLine1)){
            addr1.setError("Enter your address");
        }
       else if(!isValidemc(EmergencyContactName)){
            emerc.setError("Field cant be empty");
        }
       else if(!isValidemp(EmergencyContactNumber)){
            emerp.setError("Enter correct phone number");
        }

else{

        BackGroundTask backGroundTask = new BackGroundTask();
        backGroundTask.execute(BloodGroup,AddressLine1, AddressLine2, Country, State, City, EmergencyContactName, EmergencyContactNumber, ImportantDate,UserName);
        finish();
    }}
class BackGroundTask extends AsyncTask<String, Void, String> {

    String add_url;

    @Override
    protected void onPreExecute() {
        add_url = "http://simplyblood.com/updateandroid.php";
    }


    protected String doInBackground(String... args) {
        String BloodGroup,value,AddressLine1, AddressLine2, Country, State, City, EmergencyContactName, EmergencyContactNumber, ImportantDate, UserName;

        BloodGroup= args[0];
        AddressLine1 = args[1];

        AddressLine2 = args[2];
        Country = args[3];
        State = args[4];
        City = args[5];
        EmergencyContactName = args[6];
        EmergencyContactNumber = args[7];
        ImportantDate = args[8];
        UserName= args[9];

        try {
            URL url = new URL(add_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String data_string = URLEncoder.encode("BloodGroup", "UTF-8") + "=" + URLEncoder.encode(BloodGroup, "UTF-8") + "&" +
                    URLEncoder.encode("AddressLine1", "UTF-8") + "=" + URLEncoder.encode(AddressLine1, "UTF-8") + "&" +
                    URLEncoder.encode("AddressLine2", "UTF-8") + "=" + URLEncoder.encode(AddressLine2, "UTF-8") + "&" +
                    URLEncoder.encode("Country", "UTF-8") + "=" + URLEncoder.encode(Country, "UTF-8") + "&" +
                    URLEncoder.encode("State", "UTF-8") + "=" + URLEncoder.encode(State, "UTF-8") + "&" +
                    URLEncoder.encode("City", "UTF-8") + "=" + URLEncoder.encode(City, "UTF-8") + "&" +
                    URLEncoder.encode("EmergencyContactName", "UTF-8") + "=" + URLEncoder.encode(EmergencyContactName, "UTF-8") + "&" +
                    URLEncoder.encode("EmergencyContactNumber", "UTF-8") + "=" + URLEncoder.encode(EmergencyContactNumber, "UTF-8")+"&"+
                    URLEncoder.encode("ImportantDate","UTF-8")+"="+URLEncoder.encode(ImportantDate,"UTF-8")+"&"+
                    URLEncoder.encode("UserName","UTF-8")+"="+URLEncoder.encode(UserName,"UTF-8") ;

            bufferedWriter.write(data_string);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            ;
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
            Toast.makeText(getApplicationContext(), "Successfully updated", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(UserProfile.this, afterupdate.class);

            startActivity(intent);




        } else {
            Toast.makeText(getApplicationContext(), "Updation unsuccessful, Check you details", Toast.LENGTH_LONG).show();
        }



    }
}

private boolean isValidaddr1(String AddressLine1){
    if(AddressLine1.length()!=0){
        return true;
    }
    return false;
}
    private boolean isValidemc(String Emergency){
        if(Emergency.length()!=0){
            return true;
        }
        return false;
    }
    private boolean isValidemp(String contact){
        if(contact.length()!=10)
        {
            return false;
        }
        return true;
    }
}