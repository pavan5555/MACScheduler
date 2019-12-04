package com.utamscs.pavan.mac_scheduler;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private static EditText username;
    private static EditText password;
    private DbHelper mydb;
    public static SharedPreferences s_pref;
    private util util;


@Override
protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    Button loginbtn = (Button) findViewById(R.id.username_sign_in_button);
    Button backtoregister = (Button) findViewById(R.id.backtoregister);
    username = findViewById(R.id.l_username);
    password = findViewById(R.id.l_password);
    util = new util(this);
    mydb = new DbHelper(this);
    loginbtn.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            perform_user_login(v);
        }
    });
    backtoregister.setOnClickListener(new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            Intent register_int = new Intent(LoginActivity.this, registration.class);
            startActivity(register_int);
        }
    });
}
    private void perform_user_login(View V){
        String l_username = username.getText().toString();
        String l_password = password.getText().toString();
        boolean checklogin = false;
        if(l_username.equals("")){
            Toast.makeText(getApplicationContext(),"Username is empty",Toast.LENGTH_SHORT).show();
        }
        else if(l_password.equals("")){
            Toast.makeText(getApplicationContext(),"Password is empty",Toast.LENGTH_SHORT).show();
        }
        else {
             mydb.getReadableDatabase();
             Log.i("username ", l_username);
             Log.i("password", l_password);
             checklogin = mydb.login(l_username,l_password);

             if(checklogin == false){
                 Toast.makeText(getApplicationContext(),"Username or Password is incorrect",Toast.LENGTH_SHORT).show();
             }
             else{
                 User current_user = null;
                 s_pref = getApplicationContext().getSharedPreferences("Usr_prefs", 0); // 0 - for private mode
                 SharedPreferences.Editor editor = s_pref.edit();
                 editor.putBoolean("lflag",true);
                 editor.putString("luser", l_username);
                 editor.apply();
                 User lguser = null;
                 lguser = util.getLoggedinUser();
                 switch (lguser.getRole()){

                     case "Admin":
                         Intent adm_int = new Intent(LoginActivity.this, admin.class);
                         startActivity(adm_int);
                         break;


                     case "FacilityManager":
                         Intent fm_int = new Intent(LoginActivity.this, FacilityManger.class);
                         startActivity(fm_int);
                         break;


                     case "users":
                         Intent user_int = new Intent(LoginActivity.this, users.class);
                         startActivity(user_int);
                         break;


                 }

             }





        }



    }



}


