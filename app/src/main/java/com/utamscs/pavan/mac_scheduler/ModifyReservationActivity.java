package com.utamscs.pavan.mac_scheduler;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

public class ModifyReservationActivity extends AppCompatActivity {


    private DbHelper mydb;
    private Spinner facilityselector;
    private EditText dateselector;
    private Spinner timeselector;
    private EditText deposit;
    private EditText cardnum;
    private EditText cardname;
    private EditText cardexp;
    private EditText cardcvv;
    private EditText billAddress;
    private EditText billState;
    private EditText billCountry;
    private EditText billzipcode;
    private Button reserve;
    private DatePickerDialog datePicker;
    private TimePickerDialog timePicker;
    private util util;


    String facility;
    String time;
    int selectedday;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_reservation);
        util = new util(this);
        mydb = new DbHelper(this);
        boolean isuserloggedin = util.checkuserlogin();
        Log.i("here in userlogged in", ""+isuserloggedin);
        if(!isuserloggedin){
            Toast.makeText(getApplicationContext(),"User logged out",Toast.LENGTH_SHORT).show();
            Intent itt = new Intent(ModifyReservationActivity.this, LoginActivity.class);
            startActivity(itt);
        }




        facilityselector = (Spinner) findViewById(R.id.rsvmdyfacilitytype);
        dateselector = (EditText) findViewById(R.id.rsvmdydate);
        timeselector = (Spinner) findViewById(R.id.rsvmdytimeslot);
        deposit = (EditText) findViewById(R.id.rsvmdyDeposit);
        cardnum = findViewById(R.id.rsvmdycardnum);
        cardname = findViewById(R.id.rsvmdycardname);
        cardexp = findViewById(R.id.rsvmdycardexp);
        cardcvv = findViewById(R.id.rsvmdycardcvv);
        billAddress = findViewById(R.id.rsvmdyBillingInfo);
        billState = findViewById(R.id.rsvmdycardstate);
        billCountry = findViewById(R.id.rsvmdycardcountry);
        billzipcode = findViewById(R.id.rsvmdycardzicode);
        reserve = findViewById(R.id.rsveditbtn);


        /*Facility Dropdown */
        facilityselector = (Spinner) findViewById(R.id.rsvmdyfacilitytype);
        dateselector = (EditText) findViewById(R.id.rsvmdydate);
        timeselector = (Spinner) findViewById(R.id.rsvmdytimeslot);

        String reservationid = getIntent().getStringExtra("reservation");
        Log.i("reservationid", reservationid);
        mydb.getReadableDatabase();
        final Reservation reservation = mydb.getReservationbyid(reservationid);
        final List<String> facilities;
        facilities = mydb.getallfacilities();
        if (facilities.isEmpty() == false){
            ArrayAdapter<String> facilitiesdataadpater = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, facilities);
            facilityselector.setAdapter(facilitiesdataadpater);
            facilityselector.setSelection(facilities.indexOf(reservation.getFacilitytype()));
            facilityselector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    facility = facilities.get(position);
                    mydb.getReadableDatabase();
                    deposit = findViewById(R.id.rsvmdyDeposit);
                    deposit.setText(mydb.getfacilitydeposit(facility));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        /*Datepicker   */
        dateselector.setInputType(InputType.TYPE_NULL);
        dateselector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] date = reservation.getDate().split("/");
                int day = Integer.parseInt(date[0]);
                int month = Integer.parseInt(date[1]);
                int year = Integer.parseInt(date[2]);
                Calendar cldr = Calendar.getInstance();
                cldr.set(year, month, day);

                // date picker dialog
                datePicker = new DatePickerDialog(ModifyReservationActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dateselector.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                GregorianCalendar GregorianCalendar = new GregorianCalendar(year, monthOfYear, dayOfMonth-1);
                                selectedday = GregorianCalendar.get(Calendar.DAY_OF_WEEK);
                                final ArrayList<String> timeslots = new ArrayList<String>();
                                if(selectedday == 6 || selectedday == 7){
                                    for(int i=12; i<=24; i++){
                                        timeslots.add(i+":00");
                                    }
                                }
                                else {
                                    for (int i = 6; i <= 24; i++) {
                                        timeslots.add(i + ":00");
                                    }
                                }
                                ArrayAdapter<String> timeslotAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, timeslots);
                                timeselector.setAdapter(timeslotAdapter);
                                timeselector.setSelection(timeslots.indexOf(reservation.getTimeslot()));
                                timeselector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        time = timeslots.get(position);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }
                        }, year, month, day);

                datePicker.getDatePicker().setMinDate(cldr.getTimeInMillis());
                if(facilityselector.getSelectedItem().toString() != null && facilityselector.getSelectedItem().toString() != ""){
                    facility = facilityselector.getSelectedItem().toString();
                    if (facility.contains("Outside")){
                        cldr.add(Calendar.DAY_OF_MONTH, 7);
                    }
                    else{
                        cldr.add(Calendar.DAY_OF_MONTH, 1);
                    }
                }
                datePicker.getDatePicker().setMaxDate(cldr.getTimeInMillis());
                datePicker.show();
            }

        });
        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reservationid = getIntent().getStringExtra("reservation");
                HashMap<String, String> hash_map = new HashMap<String, String>();
                // Mapping int values to string keys
                hash_map.put("Multipurpose rooms", "MR");
                hash_map.put("Indoor basketball courts", "IBBC");
                hash_map.put("Volleyball courts", "IVBC");
                hash_map.put("Indoor soccer gymnasium", "SCG");
                hash_map.put("Racquetball courts", "RBC");
                hash_map.put("Badminton courts", "BMC");
                hash_map.put("Table Tennis", "TT");
                hash_map.put("Conference rooms", "CR");
                hash_map.put("Outdoor Volleyball Courts", "OVBC");
                hash_map.put("Outdoor Basketball Courts", "OBBC");

                Reservation reservation = new Reservation();
                mydb.getReadableDatabase();

                // Incrementing room Number
                String laestroom = "";
                int no = 0;
                String roomindex = hash_map.get(facility);
                if(mydb.getlatestroomavilable(facility) != null){
                    laestroom = mydb.getlatestroomavilable(facility);
                    no = Integer.parseInt(laestroom.split("-")[1]) + 1;
                }
                else{
                    no = 1;
                }
                String newroom = roomindex+"-"+no;

                int totalfacility = Integer.parseInt(mydb.areroomsavilable(facility));

                //checking if its valid reservation
                int i = Integer.parseInt(mydb.gettimeinterval(facility));
                boolean flag = mydb.isreservationvalid(facility, dateselector.getText().toString(), time, i);
                Reservation res = mydb.getReservationbyid(reservationid);
                if(res !=null && (flag || no <= totalfacility)){
                    reservation.setReservationid(reservationid);
                    reservation.setDeposit(deposit.getText().toString());
                    reservation.setCardname(cardname.getText().toString());
                    reservation.setCardnumber(cardnum.getText().toString());
                    reservation.setCardcvv(cardcvv.getText().toString());
                    reservation.setCardAddress(billAddress.getText().toString());
                    reservation.setCardstate(billState.getText().toString());
                    reservation.setCardcountry(billCountry.getText().toString());
                    reservation.setCardzipcode(billzipcode.getText().toString());
                    reservation.setDate(dateselector.getText().toString());
                    reservation.setFacilitytype(facility);
                    reservation.setTimeslot(time);
                    reservation.setCardexp(cardexp.getText().toString());
                    reservation.setReservedroom(newroom);
                    mydb.update_reservation(reservation);
                    Toast.makeText(getApplicationContext(),"Reservation "+reservationid+" Successfully updated!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"updating reservation failed!",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        String reservationid = getIntent().getStringExtra("reservation");
        mydb.getReadableDatabase();
        final Reservation reservation = mydb.getReservationbyid(reservationid);
        mydb = new DbHelper(this);
        deposit.setText(reservation.getDeposit());
        cardnum.setText(reservation.getCardnumber());
        cardcvv.setText(reservation.getCardcvv());
        cardexp.setText(reservation.getCardexp());
        cardname.setText(reservation.getCardname());
        billAddress.setText(reservation.getCardAddress());
        billState.setText(reservation.getCardstate());
        billCountry.setText(reservation.getCardcountry());
        billzipcode.setText(reservation.getCardzipcode());
    }
}
