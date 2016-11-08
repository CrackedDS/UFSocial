package com.example.deepa.ufsocial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ForgetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        //set action bar text
        setTitle("Help");

        //clear error message
        final TextView textViewErrorMessage = (TextView) findViewById(R.id.textViewForgetPasswordErrorMessage);
        textViewErrorMessage.setText("");

        //button Next
        Button buttonNext = (Button) findViewById(R.id.buttonForgetPasswordNext);
        buttonNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                EditText editTextEmail = (EditText) findViewById(R.id.editTextForgetPasswordEmail);

                if (editTextEmail.getText().toString().isEmpty()) {
                    textViewErrorMessage.setText("Please enter Email.");
                    return;
                }

                Intent intentToResetPassword = new Intent(ForgetPassword.this, ResetPassword.class);
                intentToResetPassword.putExtra("email", editTextEmail.getText().toString());
                startActivity(intentToResetPassword);
                finish();
            }
        });

    }
}
