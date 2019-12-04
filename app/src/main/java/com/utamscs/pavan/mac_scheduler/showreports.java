package com.utamscs.pavan.mac_scheduler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

public class showreports extends AppCompatActivity {


    private DbHelper mydb;
    private util util;
    private ListView reports;
    private ListView violations;
    private ListAdapter listAdapter;
    private ListView reservelistview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showreports);
        mydb = new DbHelper(this);
        util = new util(this);
        reservelistview = findViewById(R.id.rl_noshows);
        User user = new User();
        user = util.getLoggedinUser();
        List<Reservation> reservations =  mydb.getnoshows(user.getUsername().toString());
        listAdapter = new ViewViolations(this, reservations, user.getRole());
        reservelistview.setAdapter(listAdapter);
        View emptyview = findViewById(R.id.em_message);
        emptyview.setVisibility(View.VISIBLE);
        reservelistview.setEmptyView(emptyview);




    }
}