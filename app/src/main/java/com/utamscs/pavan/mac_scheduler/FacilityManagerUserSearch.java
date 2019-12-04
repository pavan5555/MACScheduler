package com.utamscs.pavan.mac_scheduler;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class FacilityManagerUserSearch extends AppCompatActivity {

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
        setContentView(R.layout.activity_facility_manager_user_search);
        util = new util(this);
        boolean isuserloggedin = util.checkuserlogin();
        Log.i("here in userlogged in", ""+isuserloggedin);
        if(!isuserloggedin){
            Toast.makeText(getApplicationContext(),"User logged out",Toast.LENGTH_SHORT).show();
            Intent itt = new Intent(FacilityManagerUserSearch.this, LoginActivity.class);
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


    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        User currentuser = mydb.getuserbyUsername(getIntent().getStringExtra("username"));
        int noshows = mydb.calculatenoshows(currentuser.getUsername());

        int noofviolations = mydb.calculateviolations(currentuser.getUsername());
        if(noshows < 4){
            email.setText("Not Revoked");
        }
        else{
            email.setText("Revoked");
        }
        username.setText(currentuser.getUsername());
        firstname.setText(currentuser.getFirst_name());
        lastname.setText(currentuser.getLast_name());
        role.setText(currentuser.getUta_id());
        utaid.setText(""+noshows);
        phone.setText(""+noofviolations);
        address.setText(currentuser.getStreetAddress());
        city.setText(currentuser.getEmail());
        state.setText(currentuser.getPhone());
    }













}
