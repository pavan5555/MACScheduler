package com.utamscs.pavan.mac_scheduler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ApplicationHomescreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_homescreen);

        Button login_btn = (Button) findViewById(R.id.loginbtn);
        Button register_btn = (Button) findViewById(R.id.registerbtn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login_int = new Intent(ApplicationHomescreen.this, LoginActivity.class );
                startActivity(login_int);
            }
        });
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register_int = new Intent(ApplicationHomescreen.this, registration.class);
                startActivity(register_int);
            }
        });

    }



}
