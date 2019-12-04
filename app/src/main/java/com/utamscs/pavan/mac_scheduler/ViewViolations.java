package com.utamscs.pavan.mac_scheduler;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ViewViolations extends BaseAdapter {

    LayoutInflater layoutInflater;
    Context context;
    private List<Reservation> reservationList;
    private ArrayList<Reservation> reservationArrayList;
    private DbHelper mydb;
    private int pos;
    private String role;


    public ViewViolations(Context context, List<Reservation> reservationList, String role) {
        this.context = context;
        this.reservationList = reservationList;
        this.reservationArrayList = new ArrayList<Reservation>();
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.reservationArrayList.addAll(reservationList);
        this.role = role;
        mydb = new DbHelper(context);
    }

    @Override
    public int getCount() {
        return reservationArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return reservationArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        {
            this.pos = position;
            View customview = convertView;
            if(customview == null){
                customview = layoutInflater.inflate(R.layout.activity_view_violations, null);
            }
            TextView reservationid = (TextView) customview.findViewById(R.id.vl_rvid);
            TextView facility = (TextView) customview.findViewById(R.id.vl_facname);
            TextView username = (TextView) customview.findViewById(R.id.vl_userName);
            TextView date = (TextView) customview.findViewById(R.id.vl_date);
            TextView time = (TextView) customview.findViewById(R.id.vl_time);
            TextView room = (TextView) customview.findViewById(R.id.vl_room);
            TextView descp = customview.findViewById(R.id.vl_descrp);


            reservationid.setText(reservationArrayList.get(position).getReservationid());
            facility.setText(reservationArrayList.get(position).getFacilitytype());
            username.setText(reservationArrayList.get(position).getUsername());
            date.setText(reservationArrayList.get(position).getDate());
            time.setText(reservationArrayList.get(position).getTimeslot());
            room.setText(reservationArrayList.get(position).getReservedroom());
            descp.setText(reservationArrayList.get(position).getViolation());

            return customview;
        }


    }
}
