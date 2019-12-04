package com.utamscs.pavan.mac_scheduler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class users extends AppCompatActivity {

    private util util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        util = new util(this);
        Button logout = findViewById(R.id.UsrLogout);
        Button viewprofile = findViewById(R.id.idusrvwprf);
        Button Editprofile = findViewById(R.id.idusredtprof);
        Button reservation = findViewById(R.id.idusrResv);
        Button Facilities = findViewById(R.id.idusrFaci);
        Button reports = findViewById(R.id.idusrReports);
        Button allreservations = findViewById(R.id.idusrResv2);


        /* View Profile */
        viewprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewprofile = new Intent(users.this, VIEWPROFILE.class);
                startActivity(viewprofile);
            }
        });
        /* Edit Profile */
        Editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Editprofile = new Intent(users.this, ViewProfileActivity.class);
                startActivity(Editprofile);
            }
        });
        /* Reservation */
        reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reservation = new Intent(users.this, ReservationActivity.class);
                startActivity(reservation);
            }
        });
        /* Facilities */
        Facilities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Facilities = new Intent(users.this, ViewFacilityHoursActivity.class);
                startActivity(Facilities);
            }
        });

        /* reports */
        reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reports = new Intent(users.this, showreports.class);
                startActivity(reports);
            }
        });

        allreservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reports = new Intent(users.this, ViewReservations.class);
                startActivity(reports);
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.clearsession();
                Intent LOGOUT_int = new Intent(users.this, LoginActivity.class);
                startActivity(LOGOUT_int);
            }
        });
    }
}
