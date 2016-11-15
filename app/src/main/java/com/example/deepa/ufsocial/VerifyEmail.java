package com.example.deepa.ufsocial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class VerifyEmail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);

        //set action bar text
        setTitle("New User Sign Up");

        //clear error message
        final TextView textViewErrorMessage = (TextView) findViewById(R.id.textViewVerifyEmailErrorMessage);
        textViewErrorMessage.setText("");

        //button Confirm
        Button buttonConfirm = (Button) findViewById(R.id.buttonVerifyEmailConfirm);
        buttonConfirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                EditText editTextConfirmationCode = (EditText) findViewById(R.id.editTextVerifyEmailConfirmationCode);

                if (editTextConfirmationCode.getText().toString().equals("1234") == false) {
                    textViewErrorMessage.setText("Confirmation Code is incorrect.");
                    return;
                }

                Intent intentToHome = new Intent(VerifyEmail.this, Home.class);
                intentToHome.putExtra("user_id", "10000001");
                startActivity(intentToHome);
                finish();
            }
        });


    }
}
