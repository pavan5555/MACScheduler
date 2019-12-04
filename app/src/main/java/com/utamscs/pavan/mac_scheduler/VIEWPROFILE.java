package com.utamscs.pavan.mac_scheduler;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class VIEWPROFILE extends AppCompatActivity {



    private DbHelper mydb;
    private TextView username;
    private TextView password;
    private TextView firstname;
    private TextView lastname;
    private TextView role;
    private TextView utaid;
    private TextView phone;
    private TextView email;
    private TextView address;
    private TextView city;
    private TextView state;
    private TextView zipcode;
    private TextView noshows;

    private util util;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewprofile);
        util = new util(this);
        boolean isuserloggedin = util.checkuserlogin();
        Log.i("here in userlogged in", ""+isuserloggedin);
        if(!isuserloggedin){
            Toast.makeText(getApplicationContext(),"User logged out",Toast.LENGTH_SHORT).show();
            Intent itt = new Intent(VIEWPROFILE.this, LoginActivity.class);
            startActivity(itt);
        }
        //util = new util(this);
        mydb = new DbHelper(this);
        username= findViewById(R.id.vup_username);
        firstname = findViewById(R.id.vup_Firstname);
        lastname = findViewById(R.id.vup_Lastname);
        role = findViewById(R.id.vup_Role);
        utaid = findViewById(R.id.vup_UTAID);
        phone = findViewById(R.id.vup_Phone);
        email = findViewById(R.id.vup_Email);
        address = findViewById(R.id.vup_Address);
        city = findViewById(R.id.vup_City);
        state = findViewById(R.id.vup_State);
        zipcode = findViewById(R.id.vup_Zipcode);
        noshows = findViewById(R.id.vup_noshows);

    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        User currentuser = util.getLoggedinUser();
        username.setText(currentuser.getUsername());
        firstname.setText(currentuser.getFirst_name());
        lastname.setText(currentuser.getLast_name());
        role.setText(currentuser.getRole());
        utaid.setText(currentuser.getUta_id());
        phone.setText(currentuser.getPhone());
        email.setText(currentuser.getEmail());
        address.setText(currentuser.getStreetAddress());
        city.setText(currentuser.getCity());
        state.setText(currentuser.getState());
        zipcode.setText(currentuser.getZipcode());
        noshows.setText(""+mydb.calculatenoshows(currentuser.getUsername()));
    }

}
