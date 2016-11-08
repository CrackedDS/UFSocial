package com.example.deepa.ufsocial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //set action bar text
        setTitle("New User Sign Up");

        //clear error message
        final TextView textViewErrorMessage = (TextView) findViewById(R.id.textViewSignUpErrorMessage);
        textViewErrorMessage.setText("");

        //button Sign Up
        Button buttonSignUp = (Button) findViewById(R.id.buttonSignUpSignUp);
        buttonSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                EditText editTextEmail = (EditText) findViewById(R.id.editTextSignUpEmail);
                EditText editTextPassword = (EditText) findViewById(R.id.editTextSignUpPassword);
                EditText editTextConfirmPassword = (EditText) findViewById(R.id.editTextSignUpConfirmPassword);

                if (editTextEmail.getText().toString().isEmpty() || editTextPassword.getText().toString().isEmpty() || editTextConfirmPassword.getText().toString().isEmpty()) {
                    textViewErrorMessage.setText("Please enter all information.");
                    return;
                }
                if (editTextPassword.getText().toString().equals(editTextConfirmPassword.getText().toString()) == false) {
                    textViewErrorMessage.setText("Passwords entered are different.");
                    return;
                }

                if (editTextEmail.getText().toString().equalsIgnoreCase("neo@ufl.edu") && editTextPassword.getText().toString().equalsIgnoreCase("test")) {
                    Intent intentToVerifyEmail = new Intent(SignUp.this, VerifyEmail.class);
                    intentToVerifyEmail.putExtra("user_id", "10000001");
                    startActivity(intentToVerifyEmail);
                    finish();
                }

            }
        });


    }
}
