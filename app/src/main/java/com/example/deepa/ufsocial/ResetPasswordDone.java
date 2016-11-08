package com.example.deepa.ufsocial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ResetPasswordDone extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_done);

        //set action bar text
        setTitle("Help");

        //button Continue
        Button buttonContinue = (Button) findViewById(R.id.buttonResetPasswordDoneContinue);
        buttonContinue.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intentToSignIn = new Intent(ResetPasswordDone.this, SignIn.class);
                startActivity(intentToSignIn);
                finish();
            }
        });

    }
}
