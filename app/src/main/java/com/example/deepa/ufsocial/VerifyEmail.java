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

public class VerifyEmail extends AppCompatActivity {
    MyService mService;
    boolean mBound = false;
    String message;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);

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
                new IntentFilter("verify"));

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

                JSONObject obj = new JSONObject();
                try {
                    obj.put("header", "enterCode");
                    obj.put("email", email);
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
                    Bundle bd = new Bundle();
                    bd.putString("email",  email);
                    bd.putString("NewUser", "Yes");
                    Intent intentToHome = new Intent(VerifyEmail.this, Home.class);
                    intentToHome.putExtras(bd);
                    startActivity(intentToHome);
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
