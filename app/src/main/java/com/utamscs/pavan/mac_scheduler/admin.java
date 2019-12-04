package com.utamscs.pavan.mac_scheduler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class admin extends AppCompatActivity {

    private util util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        util = new util(this);
        Button logout = findViewById(R.id.AdmLogout);
        Button rv_unrvbtn = findViewById(R.id.Admuservunrv);
        Button ediprofile = findViewById(R.id.AdmEditProfile);
        Button viewprofile = findViewById(R.id.AdmViewProfile);

        /*view profile*/
        viewprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewprofile = new Intent(admin.this, VIEWPROFILE.class);
                startActivity(viewprofile);
            }
        });

        /*edit profile*/
        ediprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editprofile = new Intent(admin.this, ViewProfileActivity.class);
                startActivity(editprofile);
            }
        });

        /*Mange user profile*/
        rv_unrvbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Manageusers = new Intent(admin.this, revoke_unrevoke.class);
                startActivity(Manageusers);

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.clearsession();
                Intent LOGOUT_int = new Intent(admin.this, LoginActivity.class);
                startActivity(LOGOUT_int);
            }
        });
    }
}
