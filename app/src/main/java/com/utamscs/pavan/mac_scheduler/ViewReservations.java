package com.utamscs.pavan.mac_scheduler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

public class ViewReservations extends AppCompatActivity {

    private ListView reservelistview;
    private ListAdapter listAdapter;
    private util util;
    private DbHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reservations);
        reservelistview = findViewById(R.id.rsvlist);
        util = new util(this);
        mydb = new DbHelper(this);
        mydb.getReadableDatabase();
        User user = null;
        user = util.getLoggedinUser();
        List<Reservation> reservations = null;
        if(user.getRole().equals("users")){
             reservations = mydb.getallReservations(user.getUsername(),null);
        }
        else{
            reservations = mydb.getallReservations(null,null);
        }
        listAdapter = new ReservationListViewAdapter(this, reservations, user.getRole());
        reservelistview.setAdapter(listAdapter);
        View emptyview = findViewById(R.id.em_message);
        emptyview.setVisibility(View.VISIBLE);
        reservelistview.setEmptyView(emptyview);
    }
}
