package com.example.deepa.ufsocial;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUp extends AppCompatActivity {
    MyService mService;
    boolean mBound = false;
    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Intent intent = new Intent(this, MyService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                message = intent.getStringExtra("message");
            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("signup"));

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
                EditText editTextName = (EditText) findViewById(R.id.editTextSignUpName);

                if (editTextName.getText().toString().isEmpty() || editTextEmail.getText().toString().isEmpty() || editTextPassword.getText().toString().isEmpty() || editTextConfirmPassword.getText().toString().isEmpty()) {
                    textViewErrorMessage.setText("Please enter all information.");
                    return;
                }
                if (editTextPassword.getText().toString().equals(editTextConfirmPassword.getText().toString()) == false) {
                    textViewErrorMessage.setText("Passwords entered are different.");
                    return;
                }

                JSONObject obj = new JSONObject();
                try {
                    obj.put("header", "createAccount");
                    obj.put("name", editTextName.getText().toString());
                    obj.put("email", editTextEmail.getText().toString());
                    obj.put("password", editTextPassword.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    mService.trivialActions(obj);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (message.equals("true")) {
                    Intent intentToVerifyEmail = new Intent(SignUp.this, VerifyEmail.class);
                    intentToVerifyEmail.putExtra("user_id", "10000001");
                    startActivity(intentToVerifyEmail);
                    finish();
                } else {
                    textViewErrorMessage.setText("Email ID already registered. Go back to access Forgot Password!");
                    return;
                }

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.LocalBinder binder = (MyService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };
}
