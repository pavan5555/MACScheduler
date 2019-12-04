package com.utamscs.pavan.mac_scheduler;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class util extends Application {

    private static Context appContext;
    private DbHelper mydb;

    public util(Context context){
        this.appContext = context;
        mydb = new DbHelper(context);
    }


    public boolean checkuserlogin(){

        if(appContext != null){
            SharedPreferences RPREF = appContext.getSharedPreferences("Usr_prefs", 0);
            return RPREF.getBoolean("lflag",false);
        }
        else{
            return false;
        }
    }

    public User getLoggedinUser(){
        if(appContext != null){
            SharedPreferences RPREF = appContext.getSharedPreferences("Usr_prefs", 0);
            String lusername = RPREF.getString("luser",null);
            Log.i("username:::" ,""+lusername);
            User user = null;
            user = mydb.getuserbyUsername(lusername);
            return user;
        }
        else{
            Log.i("here in null","here in null");
            return null;
        }
    }

    public void clearsession(){
        SharedPreferences RPREF = appContext.getSharedPreferences("Usr_prefs", 0);
        SharedPreferences.Editor editor = RPREF.edit();
        editor.clear();
        editor.apply();
    }
}
