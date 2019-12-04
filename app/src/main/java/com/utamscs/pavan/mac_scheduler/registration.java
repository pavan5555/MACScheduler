package com.utamscs.pavan.mac_scheduler;

import android.content.Intent;
import android.service.autofill.Validator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class registration extends AppCompatActivity {

  private DbHelper mydb;
  private static EditText username;
  private static EditText password;
  private static EditText firstname;
  private static EditText lastname;
  private static EditText role;
  private static EditText utaid;
  private static EditText phone;
  private static EditText email;
  private static EditText address;
  private static EditText city;
  private static EditText state;
  private static EditText zipcode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mydb = new DbHelper(this);

        Button perform_register = (Button) findViewById(R.id.register);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        firstname = findViewById(R.id.Firstname);
        lastname = findViewById(R.id.Lastname);
        role = findViewById(R.id.Role);
        utaid = findViewById(R.id.UTAID);
        phone = findViewById(R.id.Phone);
        email = findViewById(R.id.Email);
        address = findViewById(R.id.Address);
        city = findViewById(R.id.City);
        state = findViewById(R.id.State);
        zipcode = findViewById(R.id.Zipcode);


        perform_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser(v);
            }
        });


    }

    public void registerUser(View view){

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
            if(checkifexists == false){
                User user = new User();
                user.setUsername(s_username);
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
                mydb.register(user);
                Intent itt = new Intent(registration.this, LoginActivity.class);
                startActivity(itt);
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Username is used",Toast.LENGTH_SHORT).show();
            }

        }
    }

}
