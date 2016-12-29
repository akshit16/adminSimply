package com.simplyblood.admin.simplyblood;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by Akshit jain on 16-12-2016.
 */

public class afterotp extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences(otpverif.MyPREFERENCES, Context.MODE_PRIVATE);
        String  ContactNumber= sharedPreferences.getString("ContactNumber","");
        Log.d(TAG,ContactNumber);
        if(ContactNumber!=null){
            Intent intent1 = new Intent(afterotp.this,insertion.class);
            startActivity(intent1);

        }
        else {
            Intent intent = new Intent(afterotp.this,MainActivity.class);
            startActivity(intent);
        }
    }
}