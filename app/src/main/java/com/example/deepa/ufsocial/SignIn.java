package com.example.deepa.ufsocial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        setTitle("Welcome to " + getString(R.string.app_name));

        //clear error message
        final TextView textViewErrorMessage = (TextView) findViewById(R.id.textViewSignInErrorMessage);
        textViewErrorMessage.setText("");

        //button Sign In
        Button buttonSignIn = (Button) findViewById(R.id.buttonSignInSignIn);
        buttonSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                EditText editTextEmail = (EditText) findViewById(R.id.editTextSignInEmail);
                EditText editTextPassword = (EditText) findViewById(R.id.editTextSignInPassword);

                if (editTextEmail.getText().toString().isEmpty() || editTextPassword.getText().toString().isEmpty()) {
                    textViewErrorMessage.setText("Please enter both email and password.");
                    return;
                }

                if(editTextEmail.getText().toString().equalsIgnoreCase("neo@ufl.edu") && editTextPassword.getText().toString().equals("test"))
                {

                    Intent intentToUserSuggestion = new Intent(SignIn.this, ResetPasswordDone.class);
                    intentToUserSuggestion.putExtra("user_id", "10000001");
                    startActivity(intentToUserSuggestion);
                    finish();
                }
                else
                {
                    textViewErrorMessage.setText("Failed to sign in. Email or Password is incorrect.");
                }
            }
        });











    }
}
