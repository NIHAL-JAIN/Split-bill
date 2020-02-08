package com.example.almighty.navigationdrawer;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class GroupsDetails extends AppCompatActivity {

    String url = "http://androindian.com/apps/fm/api.php";
    Spinner spinner;
    ArrayList<String> showsavings1;
    ArrayList<String> showsavings2;
    ArrayList<String> showsavings3;
    ArrayList<String> showsavings4;
    ArrayList<String> showsavings5;
    JSONObject jsonObject, jsonObject2;

    ListView listView;
    public static final String MYPreferences="MyPrefs";
    ImageButton create_new_group;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grops_details);
        listView = (ListView) findViewById(R.id.lst1);
        create_new_group= (ImageButton) findViewById(R.id.imagebutton3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Group Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sharedPreferences = getSharedPreferences(MYPreferences, Context.MODE_PRIVATE);
        final String phoneNumber = sharedPreferences.getString("phoneKey", null);
        create_new_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(GroupsDetails.this,CreateNewGroup.class);
                startActivity(intent);
            }
        });


        JSONObject jsonObject_new = new JSONObject();
        try {
            jsonObject_new.put("action", "get_groups");
            jsonObject_new.put("member", phoneNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        GroupsDetailss shows = new GroupsDetailss();
        shows.execute(jsonObject_new.toString());

    }

    private class GroupsDetailss extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog = new ProgressDialog(GroupsDetails.this);

        @Override
        protected void onPreExecute() {
            progressDialog.setTitle("Loading..");
            progressDialog.setMessage("please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            jsonObject2 = JsonFunction.getJsonFromUrlparam(url, params[0]);
            Log.i("jsohhhn", "" + jsonObject2);
            showsavings1 = new ArrayList<>();
            showsavings2 = new ArrayList<>();
            showsavings3 = new ArrayList<>();
            showsavings4 = new ArrayList<>();
            showsavings5 = new ArrayList<>();
            try {

                JSONArray city_jarray = jsonObject2.getJSONArray("data");
                for (int i = 0; i < city_jarray.length(); i++) {
                    JSONObject city_obj = city_jarray.getJSONObject(i);

                    showsavings1.add(city_obj.getString("gname"));
                    showsavings2.add(city_obj.getString("datetime"));
                    showsavings3.add(city_obj.getString("gusers"));
                    showsavings4.add(city_obj.getString("gadmin"));
                    showsavings5.add(city_obj.getString("gid"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return String.valueOf(jsonObject);
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();


            listView.setAdapter(new CustomAdapter3(GroupsDetails.this, showsavings5,showsavings1, showsavings2, showsavings3, showsavings4));
            Log.i("ggg", "" + jsonObject2);

        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // I do not want this...
                // Home as up button is to navigate to Home-Activity not previous acitivity
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

}
