package com.simplyblood.admin.simplyblood;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {
    Button b1,b2,b3;
    TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedpreferences = getSharedPreferences(login2.MyPREFERENCES, Context.MODE_PRIVATE);
        String  UserName= sharedpreferences.getString("UserName","");
        Log.d(TAG,UserName);
        SharedPreferences sharedPreference = getSharedPreferences(login2.MyPREFERENCES, Context.MODE_PRIVATE);
        String  ContactNumber= sharedPreference.getString("ContactNumber","");
        Log.d(TAG,ContactNumber);


        if(UserName.equals("")){
            setContentView(R.layout.content_main);
        }
     //   else if(ContactNumber.equals("")){
       // setContentView(R.layout.content_main);
        //}
        //else if(ContactNumber.equals(ContactNumber)){
          //  Intent intent1 = new Intent(MainActivity.this,insertion.class);
            //startActivity(intent1);
        //}


        else{
Intent intent = new Intent(MainActivity.this,afterupdate.class);
            startActivity(intent);



            }

        setContentView(R.layout.content_main);
        b1 = (Button) findViewById(R.id.button);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        textview = (TextView) findViewById(R.id.t1);
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null&& networkInfo.isConnected()){
            textview.setVisibility(View.INVISIBLE);
        }
        else{
            b1.setEnabled(false);
            b2.setEnabled(false);
            b3.setEnabled(false);
        }
}


    public void insertData(View view){
        startActivity(new Intent(this,otpverif.class));
    }
    public void loginData( View view) { startActivity(new Intent(this, login2.class));}
    public void donorData(View view){startActivity(new Intent(this,donor.class));}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



}

