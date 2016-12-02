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

public class SignIn extends AppCompatActivity {

    MyService mService;
    boolean mBound = false;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Intent intent = new Intent(this, MyService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                message = intent.getStringExtra("message");
            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("signin"));

        //set action bar text
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
                    textViewErrorMessage.setText("Please enter email and password.");
                    return;
                }

                JSONObject obj = new JSONObject();
                try {
                    obj.put("header", "testAuth");
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

                if(message.equals("authorized")) {
                    Bundle bd = new Bundle();
                    bd.putString("UserID", "10000001");
                    bd.putString("NewUser", "No");
                    Intent intentToHome = new Intent(SignIn.this, Home.class);
                    intentToHome.putExtras(bd);
                    startActivity(intentToHome);
                    finish();
                }
                else {
                    textViewErrorMessage.setText("Failed to sign in. Email or Password is incorrect.");
                }
            }
        });

        //button Sign Up
        Button buttonSignUp = (Button) findViewById(R.id.buttonSignInSignUp);
        buttonSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intentToSignUp = new Intent(SignIn.this, SignUp.class);
                startActivity(intentToSignUp);
            }
        });

        //link Forget Password
        TextView textViewForgetPassword = (TextView) findViewById(R.id.textViewSignInForgetPassword);
        textViewForgetPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                Intent intentToForgetPassword = new Intent(SignIn.this, ForgetPassword.class);
                startActivity(intentToForgetPassword);
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
