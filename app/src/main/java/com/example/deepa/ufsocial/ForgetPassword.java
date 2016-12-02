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

public class ForgetPassword extends AppCompatActivity {

    MyService mService;
    boolean mBound = false;
    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        Intent intent = new Intent(this, MyService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                message = intent.getStringExtra("message");
            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("my-event"));

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
                } else {
                    JSONObject obj = new JSONObject();
                    try {
                        obj.put("header", "forgotPassword");
                        obj.put("email", editTextEmail.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        mService.trivialActions(obj);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Intent intentToResetPassword = new Intent(ForgetPassword.this, ResetPassword.class);
                intentToResetPassword.putExtra("email", editTextEmail.getText().toString());
                startActivity(intentToResetPassword);
                finish();
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
