package com.example.deepa.ufsocial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ResetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        //set action bar text
        setTitle("Help");

        //clear error message
        final TextView textViewErrorMessage = (TextView) findViewById(R.id.textViewResetPasswordErrorMessage);
        textViewErrorMessage.setText("");

        //button Reset
        Button buttonReset = (Button) findViewById(R.id.buttonResetPasswordReset);
        buttonReset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                EditText editTextPassword = (EditText) findViewById(R.id.editTextResetPasswordNewPassword);
                EditText editTextConfirmPassword = (EditText) findViewById(R.id.editTextResetPasswordConfirmPassword);
                EditText editTextConfirmationCode = (EditText) findViewById(R.id.editTextResetPasswordConfirmationCode);

                if (editTextPassword.getText().toString().isEmpty() || editTextConfirmPassword.getText().toString().isEmpty() || editTextConfirmationCode.getText().toString().isEmpty()) {
                    textViewErrorMessage.setText("Please enter all information.");
                    return;
                }
                if (editTextPassword.getText().toString().equals(editTextConfirmPassword.getText().toString()) == false) {
                    textViewErrorMessage.setText("Passwords entered are different.");
                    return;
                }

                if (editTextConfirmationCode.getText().toString().equals("1234") == false) {
                    textViewErrorMessage.setText("Confirmation Code is incorrect.");
                    return;
                }

                Intent intentToResetPasswordDone = new Intent(ResetPassword.this, ResetPasswordDone.class);
                startActivity(intentToResetPasswordDone);
                finish();
            }
        });





    }
}
