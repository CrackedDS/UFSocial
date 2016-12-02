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

public class ResetPassword extends AppCompatActivity {
    MyService mService;
    boolean mBound = false;
    String message;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        Intent intent1 = getIntent();
        email = intent1.getStringExtra("email");

        Intent intent = new Intent(this, MyService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                message = intent.getStringExtra("message");
            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("resetpass"));

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
                JSONObject obj = new JSONObject();
                try {
                    obj.put("header", "resetPassword");
                    obj.put("email", email);
                    obj.put("password", editTextPassword.getText().toString());
                    obj.put("code", editTextConfirmationCode.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    mService.trivialActions(obj);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (message.equals("true")) {
                    Intent intentToResetPasswordDone = new Intent(ResetPassword.this, ResetPasswordDone.class);
                    startActivity(intentToResetPasswordDone);
                    finish();
                } else {
                    textViewErrorMessage.setText("Confirmation Code is incorrect.");
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
