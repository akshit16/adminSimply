package com.simplyblood.admin.simplyblood;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Akshit jain on 29-12-2016.
 */

public class networkcheck extends Activity{
    Button b1,b2,b3;
    TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}}
