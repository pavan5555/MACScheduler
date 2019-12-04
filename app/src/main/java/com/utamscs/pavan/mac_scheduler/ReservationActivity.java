package com.utamscs.pavan.mac_scheduler;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

public class ReservationActivity extends AppCompatActivity {

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
    int selectedday;
    String time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        util = new util(this);
        mydb = new DbHelper(this);
        /*Facility Dropdown */
        facilityselector = (Spinner) findViewById(R.id.rsvfacilitytype);
        dateselector = (EditText) findViewById(R.id.rsvdate);
        timeselector = (Spinner) findViewById(R.id.rsvtimeslot);
        deposit = (EditText) findViewById(R.id.rsvDeposit);
        cardnum = findViewById(R.id.rsvcardnum);
        cardname = findViewById(R.id.rsvcardname);
        cardexp = findViewById(R.id.rsvcardexp);
        cardcvv = findViewById(R.id.rsvcardcvv);
        billAddress = findViewById(R.id.rsvBillingInfo);
        billState = findViewById(R.id.rsvcardstate);
        billCountry = findViewById(R.id.rsvcardcountry);
        billzipcode = findViewById(R.id.rsvcardzicode);
        reserve = findViewById(R.id.rsvbtnreserve);

        User user = util.getLoggedinUser();
        mydb.getReadableDatabase();

        if(user.getUsrStatus() != null){
            if(user.getUsrStatus().equals("revoked"));
            Intent intent = new Intent(ReservationActivity.this, users.class);
            startActivity(intent);
        }
        if( mydb.calculatenoshows(user.getUsername())  > 3){
            Intent intent = new Intent(ReservationActivity.this, users.class);
            startActivity(intent);
        }

        final List<String> facilities;
        facilities = mydb.getallavilablefacilities();
        if (facilities.isEmpty() == false){
            ArrayAdapter<String> facilitiesdataadpater = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, facilities);
            facilityselector.setAdapter(facilitiesdataadpater);
            facilityselector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    facility = facilities.get(position);
                    mydb.getReadableDatabase();
                    deposit = findViewById(R.id.rsvDeposit);
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
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datePicker = new DatePickerDialog(ReservationActivity.this,
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

                Log.i("facility", facility);
                datePicker.getDatePicker().setMinDate(cldr.getTimeInMillis());
                if(facilityselector.getSelectedItem().toString() != ""){
                    facility = facilityselector.getSelectedItem().toString();
                    if (facility.contains("Outdoor")){
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
                String reservationid = generatereservationid();
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

                Log.i("no of facilities", ""+totalfacility);

                //checking if its valid reservation
                int i = Integer.parseInt(mydb.gettimeinterval(facility));
                boolean flag = mydb.isreservationvalid(facility, dateselector.getText().toString(), time, i);
                Reservation res = mydb.getReservationbyid(reservationid);
                if(res ==null && (flag || no <= totalfacility)){
                    reservation.setReservationid(reservationid);
                    reservation.setUsername(util.getLoggedinUser().getUsername());
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
                    mydb.reserve(reservation);
                    Toast.makeText(getApplicationContext(),"Reservation Successfully created!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Reservation failed!",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public String generatereservationid(){
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(4);
        for (int i = 0; i < 4; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }
        return "RSV"+sb.toString();
    }

}
