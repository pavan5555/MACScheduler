package com.utamscs.pavan.mac_scheduler;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends BaseAdapter {

   LayoutInflater layoutinflater;
   Context context;
   private List<User> userlist;
   private ArrayList<User> userArrayList;
   private DbHelper mydb;
   private int pos;
   private String role;


   public ListViewAdapter(Context context,  List<User> userlist, String role){
       this.context = context;
       this.userlist = userlist;
       this.userArrayList = new ArrayList<User>();
       layoutinflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       this.userArrayList.addAll(userlist);
       this.role = role;
       mydb = new DbHelper(context);
   }


    class ViewHolder{
        TextView username;
        TextView firstName;
        TextView lastName;
        TextView NoSHOW;
        Switch rvkstatus;
        Button edusrprofile;
    }

    @Override
    public int getCount() {
        return userArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return userArrayList.get(position);
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



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        this.pos = position;
        View customview = convertView;
        if(customview == null) {
            customview = layoutinflater.inflate(R.layout.userlistitem, null);
            holder = new ViewHolder();
            holder.username = customview.findViewById(R.id.ul_username);
            holder.firstName = customview.findViewById(R.id.ul_firstName);
            holder.lastName = customview.findViewById(R.id.ul_lastname);
            holder.rvkstatus = customview.findViewById(R.id.ul_revoke);
            holder.edusrprofile = customview.findViewById(R.id.ul_edit_usr);
            holder.NoSHOW = customview.findViewById(R.id.ul_noofshows);
            customview.setTag(holder);
        }
        else{
            holder = (ViewHolder)customview.getTag();
        }
        if(role.equals("Admin")){
            holder.edusrprofile.setVisibility(customview.VISIBLE);
            holder.rvkstatus.setVisibility(customview.VISIBLE);
            holder.edusrprofile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String selecteduser = userArrayList.get(pos).getUsername();
                    context.startActivity(new Intent(context, editUserProfile.class).putExtra("username",selecteduser));
                }
            });


        }
        if(role.equals("FacilityManager")){
            holder.edusrprofile.setText("View Profile");
            holder.edusrprofile.setVisibility(customview.VISIBLE);
            holder.edusrprofile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String selecteduser = userArrayList.get(pos).getUsername();
                    context.startActivity(new Intent(context, FacilityManagerUserSearch.class).putExtra("username",selecteduser));
                }
            });
        }

        holder.username.setText(userArrayList.get(position).getUsername());
        holder.firstName.setText(userArrayList.get(position).getFirst_name());
        holder.lastName.setText(userArrayList.get(position).getLast_name());
        holder.NoSHOW.setText(""+mydb.calculatenoshows(userArrayList.get(position).getUsername()));
        if(userArrayList.get(position).getUsrStatus().equals("revoked")){
            holder.rvkstatus.setChecked(true);
        }
        else{
            holder.rvkstatus.setChecked(false);
        }

        holder.rvkstatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                String status = "";
                mydb.getWritableDatabase();
                if(isChecked){
                    status = "revoked";
                    mydb.changeuserstatus(userArrayList.get(pos).getUsername(), status);
                    Toast.makeText(context,"User has been revoked successfully!",Toast.LENGTH_SHORT).show();
                }
                else{
                    status = "unrevoked";
                    mydb.changeuserstatus(userArrayList.get(pos).getUsername(), status);
                    Toast.makeText(context,"User has been un-revoked successfully!",Toast.LENGTH_SHORT).show();
                }

            }
        });



       return customview;
    }
}
