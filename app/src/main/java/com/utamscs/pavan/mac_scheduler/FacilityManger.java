package com.utamscs.pavan.mac_scheduler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FacilityManger extends AppCompatActivity {


    private util util;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facility_manger);
        util = new util(this);
        Button logout = findViewById(R.id.FmLogout);
        Button managerusersearch = findViewById(R.id.idfmVwUsers);
        Button Editprofile = findViewById(R.id.idfmedPrf);
        Button Facilities = findViewById(R.id.idfmVwFacilities);
        Button Reservations = findViewById(R.id.idfmVwResv);
        Button viewprofile = findViewById(R.id.idfmvwPrf);

        /*View profile*/
        viewprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewprofile = new Intent(FacilityManger.this, VIEWPROFILE.class);
                startActivity(viewprofile);
            }
        });

        /*Edit profile*/
        Editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editprofile = new Intent(FacilityManger.this, ViewProfileActivity.class);
                startActivity(editprofile);
            }
        });

        /*Facilities*/
        Facilities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facilities = new Intent(FacilityManger.this, ViewFacilityHoursActivity.class);
                startActivity(facilities);
            }
        });

        /*Reservations*/
        Reservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rsv = new Intent(FacilityManger.this, ViewReservations.class);
                startActivity(rsv);
            }
        });


        managerusersearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent revoke = new Intent(FacilityManger.this, revoke_unrevoke.class);
                startActivity(revoke);
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.clearsession();
                Intent LOGOUT_int = new Intent(FacilityManger.this, LoginActivity.class);
                startActivity(LOGOUT_int);
            }
        });
    }
}
