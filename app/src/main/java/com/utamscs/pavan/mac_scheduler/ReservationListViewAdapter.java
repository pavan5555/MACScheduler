package com.utamscs.pavan.mac_scheduler;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ReservationListViewAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    Context context;
    private List<Reservation> reservationList;
    private ArrayList<Reservation> reservationArrayList;
    private DbHelper mydb;
    private int pos;
    private String role;

    public ReservationListViewAdapter(Context context, List<Reservation> reservationList, String role) {
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
    public int getViewTypeCount() {

        return getCount();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }


    class ViewHolder{
        TextView reservationid ;
        TextView facility ;
        TextView username ;
        TextView date ;
        TextView time ;
        TextView room ;
        Button cancel ;
        Button edit  ;
        Button report ;
        Button checkin;

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        this.pos = position;
        final ReservationListViewAdapter.ViewHolder holder;
        View customview = convertView;
        if(customview == null){
            customview = layoutInflater.inflate(R.layout.reservationitem, null);
            holder = new ViewHolder();
            holder.reservationid = (TextView) customview.findViewById(R.id.rl_rvid);
            holder.facility = (TextView) customview.findViewById(R.id.rl_facname);
            holder.username = (TextView) customview.findViewById(R.id.rl_userName);
            holder.date = (TextView) customview.findViewById(R.id.rl_date);
            holder.time = (TextView) customview.findViewById(R.id.rl_time);
            holder.room = (TextView) customview.findViewById(R.id.rl_room);
            holder.cancel = (Button) customview.findViewById(R.id.rl_cancel_reservation);
            holder.edit = (Button) customview.findViewById(R.id.rl_edit_reservation);
            holder.report = (Button) customview.findViewById(R.id.rl_report_reservation);
            holder.checkin = customview.findViewById(R.id.rl_checkin_reservation);
            customview.setTag(holder);


        }
        else{
            holder = (ViewHolder)customview.getTag();
        }


        holder.reservationid.setText(reservationArrayList.get(position).getReservationid());
        holder.facility.setText(reservationArrayList.get(position).getFacilitytype());
        holder.username.setText(reservationArrayList.get(position).getUsername());
        holder.date.setText(reservationArrayList.get(position).getDate());
        holder.time.setText(reservationArrayList.get(position).getTimeslot());
        holder.room.setText(reservationArrayList.get(position).getReservedroom());

        if(role.equals("FacilityManager")){
            if(reservationArrayList.get(position).getNousrshow() == null){
                holder.cancel.setVisibility(customview.VISIBLE);
                holder.report.setVisibility(customview.VISIBLE);
                holder.checkin.setVisibility(customview.VISIBLE);
            }
            else if (!reservationArrayList.get(position).getNousrshow().equals("Cancelled"))
            {
                holder.cancel.setVisibility(customview.VISIBLE);
                holder.report.setVisibility(customview.VISIBLE);
                holder.checkin.setVisibility(customview.VISIBLE);
            }

        }
        if(role.equals("users")){
            if(reservationArrayList.get(position).getNousrshow() == null){
                holder.cancel.setVisibility(customview.VISIBLE);
                holder.edit.setVisibility(customview.VISIBLE);
            }
            else if (!reservationArrayList.get(position).getNousrshow().equals("Cancelled"))
            {
                holder.cancel.setVisibility(customview.VISIBLE);
                holder.edit.setVisibility(customview.VISIBLE);
            }

        }

        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reservation reservation = mydb.getReservationbyid(reservationArrayList.get(position).getReservationid());
                reservation.setNousrshow("Cancelled");
                mydb.report_noshow(reservation);
                Toast.makeText(context,"Reservation "+reservation.getReservationid()+" has been successfully cancelled!",Toast.LENGTH_SHORT).show();
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ModifyReservationActivity.class).putExtra("reservation", reservationArrayList.get(position).getReservationid()));
            }
        });

        holder.report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, report_violation.class).putExtra("reservation",reservationArrayList.get(position).getReservationid()));
            }
        });

        holder.checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reservation reservation = mydb.getReservationbyid(reservationArrayList.get(pos).getReservationid());
                reservation.setNousrshow("checkedin");
                mydb.report_noshow(reservation);
                Toast.makeText(context,"User has been checked in!",Toast.LENGTH_SHORT).show();
            }
        });

        return customview;
    }
}
