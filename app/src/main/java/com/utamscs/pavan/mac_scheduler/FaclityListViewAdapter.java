package com.utamscs.pavan.mac_scheduler;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FaclityListViewAdapter extends BaseAdapter {


    LayoutInflater layoutinflater;
    Context context;
    private List<String> facilitylist;
    private ArrayList<String> facilityArrayList;
    private DbHelper mydb;
    private int pos;
    private String role;

    public FaclityListViewAdapter(Context context, List<String> facilitylist, String role) {
        this.layoutinflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.facilitylist = facilitylist;
        this.facilityArrayList = new ArrayList<String>();
        this.facilityArrayList.addAll(facilitylist);
        mydb = new DbHelper(context);
        this.role = role;
    }

    @Override
    public int getCount() {
        return facilityArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return facilityArrayList.get(position);
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
        public TextView facilityname;
        public Switch makefacunavil;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        this.pos = position;
        final ViewHolder holder;
        View customview = convertView;

        mydb.getWritableDatabase();
        if(customview == null){
            customview = layoutinflater.inflate(R.layout.facilityitem, null);
            holder=new ViewHolder();
            holder.facilityname = (TextView) customview.findViewById(R.id.fl_fname);
            holder. makefacunavil = customview.findViewById(R.id.fl_avil);
            customview.setTag(holder);
        }else{
            holder=(ViewHolder)customview.getTag();
        }


        if(role.equals("FacilityManager")){
            holder.makefacunavil.setVisibility(customview.VISIBLE);
        }

        holder.facilityname.setText(facilityArrayList.get(position));

        if(mydb.getfacilitystatus(facilityArrayList.get(position))){
            Log.i("Inside if","");
            holder. makefacunavil.setChecked(true);

        }
        else{
            Log.i("Inside else","");
            holder. makefacunavil.setChecked(false);

        }

        holder. makefacunavil.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                {

                    String status = "";
                    mydb.getWritableDatabase();
                    Log.i("Onclick else","");
                    if(isChecked){
                        status = "true";
                        mydb.makefacilityunavilable(facilityArrayList.get(position), status);
                        Toast.makeText(context,"Facility has been made available!",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        status = "false";
                        mydb.makefacilityunavilable(facilityArrayList.get(position), status);

                        Toast.makeText(context,"Facility has been made unavailable!",Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
        return customview;
    }
}
