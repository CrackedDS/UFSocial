package com.example.deepa.ufsocial;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class MyProfile extends Fragment {

    public MyProfile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        /*
        final Button buttonSignUp = (Button) view.findViewById(R.id.buttonMyProfile);
        buttonSignUp.setOnClickListener(new View.OnClickListener(){
            View parent = (View) buttonSignUp.getParent();
            EditText editMinCommonInterest = (EditText) parent.findViewById(R.id.editMinCommonInterest);
            //EditText editTextConfirmPassword = (EditText) findViewById(R.id.editTextSignUpConfirmPassword);
            Spinner education = (Spinner) parent.findViewById(R.id.spinner2);
            @Override
            public void onClick(View view){
//                Toast.makeText(MyProfile.this, "msg"+String.valueOf(interests.getSelectedItem()),Toast.LENGTH_LONG).show();
                Log.d("hi from ", "myprofile");
            }
        });
        */

        return view;
    }

}
