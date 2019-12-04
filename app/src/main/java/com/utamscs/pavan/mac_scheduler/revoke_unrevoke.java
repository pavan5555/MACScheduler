package com.utamscs.pavan.mac_scheduler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class revoke_unrevoke extends AppCompatActivity {

    private DbHelper mydb;
    private util util;
    private ListAdapter lstadapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revoke_unrevoke);
        mydb = new DbHelper(this);
        util = new util(this);
        mydb.getReadableDatabase();
        listView = findViewById(R.id.rv_usrsearchList);
        boolean isuserloggedin = util.checkuserlogin();
        if(!isuserloggedin){
            Toast.makeText(getApplicationContext(),"User logged out",Toast.LENGTH_SHORT).show();
            Intent itt = new Intent(revoke_unrevoke.this, LoginActivity.class);
            startActivity(itt);
        }

        SearchView usrrv_search = findViewById(R.id.rv_usrsearch);
        usrrv_search.setIconifiedByDefault(false);
        CharSequence usrrv_search_query = usrrv_search.getQuery();
        usrrv_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                perform_usr_search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                perform_usr_search(newText);
                return false;
            }
        });
    }

    private void perform_usr_search(String query){
        if(query != ""){
            List<User> retrived_user_list;
            int retrived_user_list_count = mydb.getCountofUsersbyquery(query);
            Log.println(1,"count of users", ""+retrived_user_list_count);
            if(retrived_user_list_count > 0 ){
                retrived_user_list = mydb.getUsersbyquery(query);
                User user = null;
                user = util.getLoggedinUser();
                lstadapter = new ListViewAdapter(this, retrived_user_list, user.getRole());
                listView.setAdapter(lstadapter);
            }
            else{
                View emptyview = findViewById(R.id.em_message);
                emptyview.setVisibility(View.VISIBLE);
                listView.setEmptyView(emptyview);
            }
        }
    }
}
