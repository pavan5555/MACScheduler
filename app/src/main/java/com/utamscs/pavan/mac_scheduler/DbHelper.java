package com.utamscs.pavan.mac_scheduler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "MACScheduler.db";

    public DbHelper(Context context){
        super(context, DB_NAME, null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users"+
                    "(username text primary key, password text, firstName text, lastName text, role text, utaid text, phone text, email text, streetAddress text, city text, state text, zipcode text, usrStatus text)");
        db.execSQL("create table facilities"+
                "(facname text  primary key, facnumber text, factype text, deposit text, duration text, interval text, facavilable text)");
        db.execSQL("insert into facilities "+
                   "(facname, facnumber, factype, deposit, duration, interval, facavilable)"+
                "values('Multipurpose rooms', '4' , 'Indoor', '50', 'Same day', '1', 'true')," +
                "('Indoor basketball courts', '5', 'Indoor', '25', 'Same day', '1', 'true')," +
                "('Volleyball courts', '9','Indoor', '25', 'Same day', '1', 'true')," +
                "('Indoor soccer gymnasium', '1', 'Indoor', '50', 'Same day', '2', 'true')," +
                "('Racquetball courts', '5', 'Indoor', '25', 'Same day', '0', 'true')," +
                "('Badminton courts', '10', 'Indoor', '25', 'Same day', '0', 'true')," +
                "('Table Tennis', '1', 'Indoor', '50', 'Same day', '0', 'true')," +
                "('Conference rooms', '5', 'Indoor', '50', 'Same day', '1', 'true')," +
                "('Outdoor Volleyball Courts', '2', 'Outdoor', '100', '7 day', '2', 'true')," +
                "('Outdoor Basketball Courts', '2', 'Outdoor', '100', '7 day', '2', 'true')");

        db.execSQL("create table reservations" +
                "(reservationid text primary key, username text, reservedDate text, reservedfacility text, " +
                "reservedtime text, reservedroom text,resvationdeposit text, rsvcard text, rsvcardname text, rsvcardexp text, " +
                "rsvcardcvv text, rsvbillingaddress text, rsvbillstate text, rsvbillcountry text, " +
                "rsvbillzipcode text, rsvusrshowstatus text, rsvviolation text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS facilities");
        db.execSQL("DROP TABLE IF EXISTS reservations");
    }





    public void register(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username",user.getUsername());
        cv.put("password",user.getPassword());
        cv.put("firstName",user.getFirst_name());
        cv.put("lastName",user.getLast_name());
        cv.put("role",user.getRole());
        cv.put("utaid",user.getUta_id());
        cv.put("phone",user.getPhone());
        cv.put("email",user.getEmail());
        cv.put("streetAddress",user.getStreetAddress());
        cv.put("city",user.getCity());
        cv.put("state",user.getState());
        cv.put("zipcode",user.getZipcode());
        cv.put("usrStatus","");
        Log.i("here",user.getCity());
        db.insert("users",null,cv);
        db.close();
    }

    public void update_profile(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("password",user.getPassword());
        cv.put("firstName",user.getFirst_name());
        cv.put("lastName",user.getLast_name());
        cv.put("role",user.getRole());
        cv.put("utaid",user.getUta_id());
        cv.put("phone",user.getPhone());
        cv.put("email",user.getEmail());
        cv.put("streetAddress",user.getStreetAddress());
        cv.put("city",user.getCity());
        cv.put("state",user.getState());
        cv.put("zipcode",user.getZipcode());
        int i = db.update("users",cv,"username = ?", new String[]{user.getUsername()});
        Log.i("update", ""+i);
        db.close();
    }

    public void changeuserstatus(String username, String updated_status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("usrStatus", updated_status);
        db.update("users",cv,"username = ?", new String[]{username});
        db.close();
    }



    public void remove_user(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("users","username = ?", new  String[]{username});
        db.close();
 }

    public boolean isusernameavialable(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        String[]  columns ={
                "username"
        };
        String query = "username = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query("users",columns,query,selectionArgs, null,null, null);
        int count = cursor.getCount();
        Log.i("count",""+count);
        cursor.close();
        db.close();
        if(count > 0){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean login(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        String[]  columns ={
                "username",
                "utaid"
        };
        String query = "username = ? and password = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query("users",columns,query,selectionArgs, null,null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        if(count == 1){
            return true;
        }
        else {
            return false;
        }
    }

    public User getuserbyUsername(String username){

        User user = new User();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {
                "username",
                "password",
                "firstName",
                "lastName",
                "role",
                "utaid",
                "phone",
                "email",
                "streetAddress",
                "city",
                "state",
                "zipcode"
        };

        String query = "username = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query("users",columns,query,selectionArgs,null,null,null,null);
        int count = cursor.getCount();
        if (count > 0){
            cursor.moveToFirst();
            user.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            user.setFirst_name(cursor.getString(cursor.getColumnIndex("firstName")));
            user.setLast_name(cursor.getString(cursor.getColumnIndex("lastName")));
            user.setRole(cursor.getString(cursor.getColumnIndex("role")));
            user.setUta_id(cursor.getString(cursor.getColumnIndex("utaid")));
            user.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
            user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            user.setStreetAddress(cursor.getString(cursor.getColumnIndex("streetAddress")));
            user.setCity(cursor.getString(cursor.getColumnIndex("city")));
            user.setState(cursor.getString(cursor.getColumnIndex("state")));
            user.setZipcode(cursor.getString(cursor.getColumnIndex("zipcode")));
            db.close();
            return user;
        }
        else{
            db.close();
            return null;
        }

    }

    public List<User> getUsersbyquery(String query){
        SQLiteDatabase db = this.getReadableDatabase();
        List<User> userlist = new ArrayList<User>();
        String sql_query = "select username, firstName, lastName, usrStatus from users where username like ? and role = ?";
        String[] selectionArgs = {"%"+query+"%", "users"};
        Cursor cursor = db.rawQuery(sql_query,selectionArgs);
        if (cursor.moveToFirst()) {
            do {
                User usr = new User();
                usr.setUsername(cursor.getString(0));
                usr.setFirst_name(cursor.getString(1));
                usr.setLast_name(cursor.getString(2));
                usr.setUsrStatus(cursor.getString(3));
                userlist.add(usr);
            } while (cursor.moveToNext());
        }
        db.close();
        return userlist;
    }

    public int getCountofUsersbyquery(String query){
        SQLiteDatabase db = this.getReadableDatabase();
        List<User> userlist = new ArrayList<User>();
        String sql_query = "select username, firstName, lastName, usrStatus from users where username like ? and role = ?";
        String[] selectionArgs = {"%"+query+"%", "users"};
        Cursor cursor = db.rawQuery(sql_query,selectionArgs);
        if (cursor.moveToFirst()) {
            do {
                User usr = new User();
                usr.setUsername(cursor.getString(0));
                usr.setFirst_name(cursor.getString(1));
                usr.setLast_name(cursor.getString(2));
                usr.setUsrStatus(cursor.getString(3));
                userlist.add(usr);
            } while (cursor.moveToNext());
        }
        db.close();
        return cursor.getCount();
    }


    public boolean checkifadmin(String current_user){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns={
                "role"
        };
        String query = "username = ?";
        String role = "";
        String[] selectionArgs = {current_user};
        Cursor cursor = db.query("users",columns,query,selectionArgs,null,null,null,null);
        int count = cursor.getCount();
        if (count > 0){
            cursor.moveToFirst();
            role = cursor.getString(cursor.getColumnIndex("role"));
            if(role.equals("Admin")){
                return true;
            }
            else{
                return false;
            }
        }
        return false;
    }

    public void revokeorunrevoke(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("usrStatus", user.getUsrStatus());
        db.insert("users",null,cv);
        db.close();
      }

    public List<String> getallfacilities(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> facilities = new ArrayList<String>();
        String[] columns={
                "facname"
        };
        Cursor cursor = db.query("facilities",columns,null,null,null,null,null,null);
        if (cursor.moveToFirst()) {
            do{
                facilities.add(cursor.getString(cursor.getColumnIndex("facname")));
            }while (cursor.moveToNext());
        }
        db.close();
        return facilities;
    }

    public List<String> getallavilablefacilities(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> facilities = new ArrayList<String>();
        String query = "facavilable = ?";
        String[] selection = new String[]{"true"};
        String[] columns={
                "facname"
        };
        Cursor cursor = db.query("facilities",columns,query,selection,null,null,null,null);
        if (cursor.moveToFirst()) {
            do{
                facilities.add(cursor.getString(cursor.getColumnIndex("facname")));
            }while (cursor.moveToNext());
        }
        db.close();
        return facilities;
    }




    public void makefacilityunavilable(String facility, String torf){
        Log.i("facility value!!!!!!!!", torf);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("facavilable", torf);
        Log.i("facility!!!!!!!!!!", facility);
        int i = db.update("facilities",cv,"facname = ?", new String[]{facility});
        Log.i("update res!!", ""+i);
        db.close();
    }

    public boolean getfacilitystatus(String facility){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns={
                "facavilable"
        };
        String query = "facname = ?";
        String role = "";
        String[] selectionArgs = {facility};
        Cursor cursor = db.query("facilities",columns,query,selectionArgs,null,null,null,null);
        int count = cursor.getCount();
            cursor.moveToFirst();
            role = cursor.getString(cursor.getColumnIndex("facavilable"));
            if(role.equals("true")){
                db.close();
                return true;
            }
            else{
                db.close();
                return false;
            }
    }

    public String getfacilitydeposit(String facility){

        SQLiteDatabase db = this.getReadableDatabase();
        String deposit = "";
        String[] columns={
                "deposit"
        };
        String query = "facname = ?";
        String[] selectionargs = new String[]{facility};
        Cursor cursor = db.query("facilities",columns,query,selectionargs,null,null,null,null);
        if (cursor.moveToFirst()) {
            do{
                deposit = cursor.getString(cursor.getColumnIndex("deposit"));
            }while (cursor.moveToNext());
        }
        db.close();
        return deposit;
    }


    public void reserve(Reservation reservation){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("reservationid", reservation.getReservationid());
        cv.put("username", reservation.getUsername());
        cv.put("reservedfacility", reservation.getFacilitytype());
        cv.put("reservedDate", reservation.getDate());
        cv.put("reservedtime", reservation.getTimeslot());
        cv.put("reservedroom", reservation.getReservedroom());
        cv.put("resvationdeposit", reservation.getDeposit());
        cv.put("rsvcard",reservation.getCardnumber());
        cv.put("rsvcardname", reservation.getCardname());
        cv.put("rsvcardexp", reservation.getCardexp());
        cv.put("rsvcardcvv", reservation.getCardcvv());
        cv.put("rsvbillingaddress", reservation.getCardAddress());
        cv.put("rsvbillstate", reservation.getCardstate());
        cv.put("rsvbillcountry", reservation.getCardcountry());
        cv.put("rsvbillzipcode", reservation.getCardzipcode());
        db.insert("reservations",null,cv);
        db.close();
    }



    public void update_reservation(Reservation reservation){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("reservedfacility", reservation.getFacilitytype());
        cv.put("reservedDate", reservation.getDate());
        cv.put("reservedtime", reservation.getTimeslot());
        cv.put("reservedroom", reservation.getReservedroom());
        cv.put("resvationdeposit", reservation.getDeposit());
        cv.put("rsvcard",reservation.getCardnumber());
        cv.put("rsvcardname", reservation.getCardname());
        cv.put("rsvcardexp", reservation.getCardexp());
        cv.put("rsvcardcvv", reservation.getCardcvv());
        cv.put("rsvbillingaddress", reservation.getCardAddress());
        cv.put("rsvbillstate", reservation.getCardstate());
        cv.put("rsvbillcountry", reservation.getCardcountry());
        cv.put("rsvbillzipcode", reservation.getCardzipcode());
        int i = db.update("reservations",cv,"reservationid = ?", new String[]{reservation.getReservationid()});
        db.close();
    }

    public void report_violation(Reservation reservation){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("rsvviolation", reservation.getViolation());
        int i = db.update("reservations",cv,"reservationid = ?", new String[]{reservation.getReservationid()});
        db.close();
    }

    public void report_noshow(Reservation reservation){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("rsvusrshowstatus", reservation.getNousrshow());
        int i = db.update("reservations",cv,"reservationid = ?", new String[]{reservation.getReservationid()});
        db.close();
    }

    public Reservation getReservationbyid(String reservationid){
      Reservation reservation = new Reservation();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {
                "reservationid",
                "username",
                "reservedfacility",
                "reservedDate",
                "reservedtime",
                "reservedroom",
                "resvationdeposit",
                "rsvcard",
                "rsvcardname",
                "rsvcardexp",
                "rsvcardcvv",
                "rsvbillingaddress",
                "rsvbillstate",
                "rsvbillcountry",
                "rsvbillzipcode",
                "rsvusrshowstatus",
                "rsvviolation"
        };

        String query = "reservationid = ?";
        String[] selectionArgs = new String[]{reservationid};
        Cursor cursor = db.query("reservations",columns,query,selectionArgs,null,null,null,null);
        int count = cursor.getCount();
        if (count > 0){
            cursor.moveToFirst();
            reservation.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            reservation.setReservationid(cursor.getString(cursor.getColumnIndex("reservationid")));
            reservation.setFacilitytype(cursor.getString(cursor.getColumnIndex("reservedfacility")));
            reservation.setDate(cursor.getString(cursor.getColumnIndex("reservedDate")));
            reservation.setTimeslot(cursor.getString(cursor.getColumnIndex("reservedtime")));
            reservation.setReservedroom(cursor.getString(cursor.getColumnIndex("reservedroom")));
            reservation.setDeposit(cursor.getString(cursor.getColumnIndex("resvationdeposit")));
            reservation.setCardnumber(cursor.getString(cursor.getColumnIndex("rsvcard")));
            reservation.setCardname(cursor.getString(cursor.getColumnIndex("rsvcardname")));
            reservation.setCardexp(cursor.getString(cursor.getColumnIndex("rsvcardexp")));
            reservation.setCardcvv(cursor.getString(cursor.getColumnIndex("rsvcardcvv")));
            reservation.setCardAddress(cursor.getString(cursor.getColumnIndex("rsvbillingaddress")));
            reservation.setCardstate(cursor.getString(cursor.getColumnIndex("rsvbillstate")));
            reservation.setCardcountry(cursor.getString(cursor.getColumnIndex("rsvbillcountry")));
            reservation.setCardzipcode(cursor.getString(cursor.getColumnIndex("rsvbillzipcode")));
            reservation.setNousrshow(cursor.getString(cursor.getColumnIndex("rsvusrshowstatus")));
            reservation.setViolation(cursor.getString(cursor.getColumnIndex("rsvviolation")));
            db.close();
            return reservation;
        }
        else{
            db.close();
            return null;
        }
    }

    public List<Reservation> getallReservations(String username, String factype){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Reservation> Reservationlist = new ArrayList<Reservation>();
        String sql_query = "select reservationid, username, reservedfacility, reservedDate, reservedtime, reservedroom, rsvusrshowstatus from reservations";
        String[] selectionArgs = null;
        if (username != null && username != "" ){
            String condition = " where username = ?";
            sql_query += condition;
            selectionArgs = new String[]{username};
        }
        else if (factype != null && factype !=""){
            String condition = " inner join facilities on reservations.reservedfacility = facilities.facname where facilities.factype = ?";
            sql_query += condition;
            selectionArgs = new String[]{factype};
        }

        Cursor cursor = db.rawQuery(sql_query,selectionArgs);
        if (cursor.moveToFirst()) {
            do {
                Reservation reservation = new Reservation();
                reservation.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                reservation.setReservationid(cursor.getString(cursor.getColumnIndex("reservationid")));
                reservation.setFacilitytype(cursor.getString(cursor.getColumnIndex("reservedfacility")));
                reservation.setDate(cursor.getString(cursor.getColumnIndex("reservedDate")));
                reservation.setTimeslot(cursor.getString(cursor.getColumnIndex("reservedtime")));
                reservation.setReservedroom(cursor.getString(cursor.getColumnIndex("reservedroom")));
                reservation.setNousrshow(cursor.getString(cursor.getColumnIndex("rsvusrshowstatus")));
                Reservationlist.add(reservation);
            } while (cursor.moveToNext());
        }
        db.close();
        return Reservationlist;
    }

    public String getlatestroomavilable(String Facility){
        SQLiteDatabase db = this.getReadableDatabase();
        String room = "";
        String sql_query = "select max(reservedroom) from reservations where reservedfacility = ?";
        String[] selectionArgs = new String[]{Facility};
        Cursor cursor = db.rawQuery(sql_query, selectionArgs);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            room = cursor.getString(0);
            db.close();
            return room;
        }
        else{
            db.close();
            return null;
        }
    }

    public String areroomsavilable(String facility){
        SQLiteDatabase db = this.getReadableDatabase();
        String room = "";
        String sql_query = "select facnumber from facilities where facname = ?";
        String[] selectionargs = new String[]{facility};
        Cursor cursor = db.rawQuery(sql_query, selectionargs);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            room = cursor.getString(0);
            db.close();
            return room;
        }
        else{
            db.close();
            return null;
        }


    }



    public String gettimeinterval(String facility){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql_query  = "select interval from facilities where facname = ?";
        String temp = "";
        String[] selectionArgs = new String[]{facility};
        Cursor cursor = db.rawQuery(sql_query, selectionArgs);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            temp = cursor.getString(0);
            db.close();
            return temp;
        }
        else{
            db.close();
            return null;
        }


    }




    public boolean isreservationvalid(String Facility, String date, String time, int interval){
        SQLiteDatabase db = this.getReadableDatabase();

        String sql_query = "select * from reservations where reservedfacility = ? and reservedDate = ? and reservedtime between ? and ?";
        String[] selectionArgs  = null;
            if(interval == 1){
                String[] splittime = time.split(":");
                int timet = Integer.parseInt(splittime[0]) + 1;
                String newtime = ""+timet+":"+splittime[1];
                selectionArgs = new String[]{Facility, date, time, newtime};
            }
            else if(interval == 2){
                String[] splittime = time.split(":");
                int timet = Integer.parseInt(splittime[0]) + 2;
                String newtime = ""+timet+":"+splittime[1];
                selectionArgs = new String[]{Facility, date, time, newtime};
            }
            else{
                String newtime = "";
                String[] splittime = time.split(":");
                int time1 = Integer.parseInt(splittime[0]);
                int timet = Integer.parseInt(splittime[1]);
                if(timet == 30){
                    timet = 0;
                    time1 = time1 + 1;
                    newtime = ""+time1+":"+timet+"0";
                }
                else{
                    timet = timet + 30;
                    newtime = ""+time1+":"+timet;
                }
                selectionArgs = new String[]{Facility, date, time, newtime};
            }
            Cursor cursor = db.rawQuery(sql_query, selectionArgs);
            if(cursor.getCount() > 0){
                db.close();
                return false;
            }
            else{
                db.close();
                return true;
            }
    }


    public int calculatenoshows(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from reservations where username = ? and rsvusrshowstatus = ?";
        String[] selectionargs = new String[]{username, "NOSHOW"};
        Cursor cursor = db.rawQuery(sql, selectionargs);
        return cursor.getCount();
    }

    public int calculateviolations(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from reservations where username = ? and rsvviolation is not null";
        String[] selectionargs = new String[]{username};
        Cursor cursor = db.rawQuery(sql, selectionargs);
        return cursor.getCount();
    }

    public List<Reservation> getnoshows(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Reservation> Reservationlist = new ArrayList<Reservation>();
        String sql_query = "select reservationid, rsvviolation, username, reservedfacility, reservedDate, reservedtime, reservedroom from reservations where username = ? and  rsvviolation is not null";
        String[] selectionArgs = new String[]{username};
        Cursor cursor = db.rawQuery(sql_query,selectionArgs);
        if (cursor.moveToFirst()) {
            do {
                Reservation reservation = new Reservation();
                reservation.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                reservation.setReservationid(cursor.getString(cursor.getColumnIndex("reservationid")));
                reservation.setFacilitytype(cursor.getString(cursor.getColumnIndex("reservedfacility")));
                reservation.setDate(cursor.getString(cursor.getColumnIndex("reservedDate")));
                reservation.setTimeslot(cursor.getString(cursor.getColumnIndex("reservedtime")));
                reservation.setReservedroom(cursor.getString(cursor.getColumnIndex("reservedroom")));
                reservation.setViolation(cursor.getString(cursor.getColumnIndex("rsvviolation")));
                Reservationlist.add(reservation);
            } while (cursor.moveToNext());
        }
        db.close();
        return Reservationlist;
    }
}
