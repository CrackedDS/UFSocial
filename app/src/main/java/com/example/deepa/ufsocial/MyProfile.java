package com.example.deepa.ufsocial;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MyProfile extends AppCompatActivity {

    private Spinner spinner1;
    public MyProfile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_my_profile);


        Button buttonSignUp = (Button) findViewById(R.id.buttonMyProfile);
        buttonSignUp.setOnClickListener(new View.OnClickListener(){

            Spinner interests = (Spinner) findViewById(R.id.spinner1);
            EditText editCrossPathNumber = (EditText) findViewById(R.id.editCrossPathNumber);
            EditText editMinCommonInterest = (EditText) findViewById(R.id.editMinCommonInterest);
            //EditText editTextConfirmPassword = (EditText) findViewById(R.id.editTextSignUpConfirmPassword);
            Spinner education = (Spinner) findViewById(R.id.spinner2);
            @Override
            public void onClick(View view){

                Toast.makeText(MyProfile.this, "msg"+String.valueOf(interests.getSelectedItem()),Toast.LENGTH_LONG).show();

            }
        });

    }

}
