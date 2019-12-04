package com.utamscs.pavan.mac_scheduler;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ViewProfileActivity extends AppCompatActivity {

    private DbHelper mydb;
    private TextInputEditText username;
    private TextInputEditText password;
    private TextInputEditText firstname;
    private TextInputEditText lastname;
    private TextInputEditText role;
    private TextInputEditText utaid;
    private TextInputEditText phone;
    private TextInputEditText email;
    private TextInputEditText address;
    private TextInputEditText city;
    private TextInputEditText state;
    private TextInputEditText zipcode;

    private util util;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        util = new util(this);
        boolean isuserloggedin = util.checkuserlogin();
        Log.i("here in userlogged in", ""+isuserloggedin);
        if(!isuserloggedin){
            Toast.makeText(getApplicationContext(),"User logged out",Toast.LENGTH_SHORT).show();
            Intent itt = new Intent(ViewProfileActivity.this, LoginActivity.class);
            startActivity(itt);
        }
        //util = new util(this);
        mydb = new DbHelper(this);
        Button perform_update = (Button) findViewById(R.id.updateProf);
        username= findViewById(R.id.vp_username);
        password = findViewById(R.id.vp_password);
        firstname = findViewById(R.id.vp_Firstname);
        lastname = findViewById(R.id.vp_Lastname);
        role = findViewById(R.id.vp_Role);
        utaid = findViewById(R.id.vp_UTAID);
        phone = findViewById(R.id.vp_Phone);
        email = findViewById(R.id.vp_Email);
        address = findViewById(R.id.vp_Address);
        city = findViewById(R.id.vp_City);
        state = findViewById(R.id.vp_State);
        zipcode = findViewById(R.id.vp_Zipcode);

        perform_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateprofile(v);
            }
        });

    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        User currentuser = util.getLoggedinUser();
        username.setText(currentuser.getUsername());
        password.setText(currentuser.getPassword());
        firstname.setText(currentuser.getFirst_name());
        lastname.setText(currentuser.getLast_name());
        role.setText(currentuser.getRole());
        utaid.setText(currentuser.getUta_id());
        phone.setText(currentuser.getPhone());
        email.setText(currentuser.getEmail());
        address.setText(currentuser.getStreetAddress());
        city.setText(currentuser.getCity());
        state.setText(currentuser.getState());
        zipcode.setText(currentuser.getZipcode());
    }

    private void updateprofile(View v){

        {
            String s_username = username.getText().toString();
            String s_password = password.getText().toString();
            String s_firstname = firstname.getText().toString();
            String s_lastname = lastname.getText().toString();
            String s_role = role.getText().toString();
            String s_utaid = utaid.getText().toString();
            String s_phone = phone.getText().toString();
            String s_email = email.getText().toString();
            String S_address = address.getText().toString();
            String s_city = city.getText().toString();
            String s_state = state.getText().toString();
            String s_zipcode = zipcode.getText().toString();

            boolean incorrectdata = false;

            if (s_username.equals(""))
            {
                incorrectdata = true;
                Toast.makeText(getApplicationContext(),"Username is empty",Toast.LENGTH_SHORT).show();
            }
            else if (s_password.equals("")){
                incorrectdata = true;
                Toast.makeText(getApplicationContext(),"Password is empty",Toast.LENGTH_SHORT).show();
            }
            else if (s_firstname.equals("")){
                incorrectdata = true;
                Toast.makeText(getApplicationContext(),"Firstname is empty",Toast.LENGTH_SHORT).show();
            }
            else if (s_lastname.equals("")){
                incorrectdata = true;
                Toast.makeText(getApplicationContext(),"Lastname is empty",Toast.LENGTH_SHORT).show();
            }
            else if (s_role.equals("")){
                incorrectdata = true;
                Toast.makeText(getApplicationContext(),"Role is empty",Toast.LENGTH_SHORT).show();
            }
            else if (s_utaid.equals("")){
                incorrectdata = true;
                Toast.makeText(getApplicationContext(),"UTA ID is empty",Toast.LENGTH_SHORT).show();
            }
            else if (s_phone.equals("")){
                incorrectdata = true;
                Toast.makeText(getApplicationContext(),"Phone is empty",Toast.LENGTH_SHORT).show();
            }
            else if (s_email.equals("")){
                incorrectdata = true;
                Toast.makeText(getApplicationContext(),"Email is empty",Toast.LENGTH_SHORT).show();
            }
            else if (S_address.equals("")){
                incorrectdata = true;
                Toast.makeText(getApplicationContext(),"Address is empty",Toast.LENGTH_SHORT).show();
            }
            else if (s_city.equals("")){
                incorrectdata = true;
                Toast.makeText(getApplicationContext(),"city is empty",Toast.LENGTH_SHORT).show();
            }
            else if (s_state.equals("")){
                incorrectdata = true;
                Toast.makeText(getApplicationContext(),"state is empty",Toast.LENGTH_SHORT).show();
            }
            else if (s_zipcode.equals("")){
                incorrectdata = true;
                Toast.makeText(getApplicationContext(),"Zipcode is empty",Toast.LENGTH_SHORT).show();
            }

            if(incorrectdata == false){
                boolean checkifexists = mydb.isusernameavialable(s_username);
                Log.i("exits", ""+checkifexists);
                if(checkifexists == true){
                    User user = util.getLoggedinUser();
                    user.setPassword(s_password);
                    user.setFirst_name(s_firstname);
                    user.setLast_name(s_lastname);
                    user.setRole(s_role);
                    user.setUta_id(s_utaid);
                    user.setPhone(s_phone);
                    user.setEmail(s_email);
                    user.setStreetAddress(S_address);
                    user.setCity(s_city);
                    user.setState(s_state);
                    user.setZipcode(s_zipcode);
                    mydb.update_profile(user);
                    Toast.makeText(getApplicationContext(),"Profile Successfully Updated",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Invalid Username",Toast.LENGTH_SHORT).show();
                }

            }
        }





    }







}
