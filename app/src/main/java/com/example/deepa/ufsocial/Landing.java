package com.example.deepa.ufsocial;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Landing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        //hide action bar
        getSupportActionBar().hide();

        //stay at this activity for 1.5s and then show Sign In
        Handler h = new Handler(){
            @Override
            public void handleMessage(Message msg){
                Intent intentToSignIn = new Intent(Landing.this, SignIn.class);
                startActivity(intentToSignIn);
                finish();
            }
        };
        h.sendEmptyMessageDelayed(0, 1500);
    }
}
