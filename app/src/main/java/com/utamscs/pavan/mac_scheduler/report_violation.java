package com.utamscs.pavan.mac_scheduler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class report_violation extends AppCompatActivity {
    private RadioGroup rg;
    private EditText Drep;
    private RelativeLayout layout;
    private RadioButton rbutton;
    private Button button;
    private DbHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_violation);
        mydb = new DbHelper(this);
        rg = findViewById(R.id.rprdgrp);
        layout = findViewById(R.id.rprlayout2);
        Drep = findViewById(R.id.rp_descr);
        button = findViewById(R.id.rp_submit);
        rbutton = findViewById(R.id.rdbtn_violation);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rdbtn_violation){
                    Log.i("herre!", "here");
                    layout.setVisibility(View.VISIBLE);
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedRadioButtonId = rg.getCheckedRadioButtonId();
                mydb.getWritableDatabase();
                String rsvid = getIntent().getStringExtra("reservation");
                switch (checkedRadioButtonId){
                    case R.id.rdbtn_noshw:
                        if(rsvid != null){
                            Reservation rsv = mydb.getReservationbyid(rsvid);
                            rsv.setNousrshow("NOSHOW");
                            mydb.report_noshow(rsv);
                            Toast.makeText(getApplicationContext(),"Noshow has been reported!",Toast.LENGTH_SHORT).show();
                            break;
                        }
                    case R.id.rdbtn_violation:
                        if(rsvid != null) {
                            Reservation rsv = mydb.getReservationbyid(rsvid);
                            rsv.setViolation(Drep.getText().toString());
                            mydb.report_violation(rsv);
                            Toast.makeText(getApplicationContext(),"Violation has been reported",Toast.LENGTH_SHORT).show();
                            break;
                        }

                    case -1:
                        Toast.makeText(v.getContext(), "Please select an option",Toast.LENGTH_SHORT).show();
                        break;

                }

            }
        });
    }
}
