package com.utamscs.pavan.mac_scheduler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

public class ViewFacilityHoursActivity extends AppCompatActivity {

    private ListView facilitylist;
    private ListAdapter lstadapter;
    private util util;
    private DbHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_facility_hours);
        facilitylist = findViewById(R.id.facilityList);
        util = new util(this);
        mydb = new DbHelper(this);
        mydb.getReadableDatabase();
        List<String> flist = mydb.getallfacilities();
        User user = null;
        user = util.getLoggedinUser();
        lstadapter = new FaclityListViewAdapter(this, flist, user.getRole());
        facilitylist.setAdapter(lstadapter);
    }
}
