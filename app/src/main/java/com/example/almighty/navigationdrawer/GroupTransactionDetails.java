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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class GroupTransactionDetails extends AppCompatActivity {

    String url = "http://androindian.com/apps/fm/api.php";
    ArrayList<HashMap<String, String>> savingstypes;
    ArrayList<String> showsavings1;
    ArrayList<String> showsavings2;
    ArrayList<String> showsavings3;
    ArrayList<String> showsavings4;
    ArrayList<String> showsavings5;
    ArrayList<String> showsavings6;
    JSONObject jsonObject, jsonObject2,jsonObject3;

    ListView listView;
    public static final String MYPreferences="MyPrefs";
    ImageButton add_trans_in_group;
    Spinner gid,gname;
    String gidd;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_transaction_details);
        listView = (ListView) findViewById(R.id.lst4);
        add_trans_in_group= (ImageButton) findViewById(R.id.imagebutton4);
        gid = (Spinner) findViewById(R.id.gid);
        gname= (Spinner) findViewById(R.id.gnamee);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Group Transaction Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sharedPreferences = getSharedPreferences(MYPreferences, Context.MODE_PRIVATE);
        final String phoneNumber = sharedPreferences.getString("phoneKey", null);
        add_trans_in_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(GroupTransactionDetails.this,AddtransToGroup.class);
                startActivity(intent);
            }
        });
        JSONObject jsonObject_new2 = new JSONObject();
        try {
            jsonObject_new2.put("action", "get_groups");
            jsonObject_new2.put("member", phoneNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        GroupsDetailss shows = new GroupsDetailss();
        shows.execute(jsonObject_new2.toString());
        gname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gidd= gid.getItemAtPosition(i).toString();
                JSONObject jsonObject_new = new JSONObject();
                try {
                    jsonObject_new.put("action", "get_trans_from_group");
                    jsonObject_new.put("gu_id",gidd);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ShowSelfSaving shows2 = new ShowSelfSaving();
                shows2.execute(jsonObject_new.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




    }

    private class ShowSelfSaving extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog = new ProgressDialog(GroupTransactionDetails.this);

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


            return String.valueOf(jsonObject);
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject3=new JSONObject(jsonObject2.toString());
            String s2=jsonObject3.getString("response");
                progressDialog.dismiss();
                if(s2.equalsIgnoreCase("success")) {
                    progressDialog.dismiss();
                    showsavings1 = new ArrayList<>();
                    showsavings2 = new ArrayList<>();
                    showsavings3 = new ArrayList<>();
                    showsavings4 = new ArrayList<>();
                    showsavings5 = new ArrayList<>();
                    showsavings6 = new ArrayList<>();
                    try {

                        JSONArray city_jarray = jsonObject2.getJSONArray("data");
                        for (int i = 0; i < city_jarray.length(); i++) {
                            JSONObject city_obj = city_jarray.getJSONObject(i);

                            showsavings1.add(city_obj.getString("guid"));
                            showsavings2.add(city_obj.getString("mobile_no"));
                            showsavings3.add(city_obj.getString("reason"));
                            showsavings4.add(city_obj.getString("datetime"));
                            showsavings5.add(city_obj.getString("cr_or_dr"));
                            showsavings6.add(city_obj.getString("amount"));

                        }
                        listView.setAdapter(new CustomAdapter4(GroupTransactionDetails.this,showsavings1, showsavings2, showsavings3, showsavings4, showsavings5, showsavings6));
                        Log.i("ggg", "" + jsonObject2);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(GroupTransactionDetails.this,"No Transactions are done in Thos Group",Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private class GroupsDetailss extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog = new ProgressDialog(GroupTransactionDetails.this);

        @Override
        protected void onPreExecute() {
            progressDialog.setTitle("Loading..");
            progressDialog.setMessage("please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            jsonObject3 = JsonFunction.getJsonFromUrlparam(url, params[0]);
            Log.i("jsohhhn", "" + jsonObject3);
            progressDialog.dismiss();


            return String.valueOf(jsonObject3);
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject4=new JSONObject(jsonObject3.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            showsavings1 = new ArrayList<>();
            showsavings2 = new ArrayList<>();
            try {

                JSONArray city_jarray = jsonObject3.getJSONArray("data");
                for (int i = 0; i < city_jarray.length(); i++) {
                    JSONObject city_obj = city_jarray.getJSONObject(i);

                    showsavings1.add(city_obj.getString("gid"));
                    showsavings2.add(city_obj.getString("gname"));

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            ArrayAdapter arrayAdapter=new ArrayAdapter(GroupTransactionDetails.this,android.R.layout.simple_expandable_list_item_1,showsavings1);
            gid.setAdapter(arrayAdapter);
            ArrayAdapter arrayAdapter2=new ArrayAdapter(GroupTransactionDetails.this,android.R.layout.simple_expandable_list_item_1,showsavings2);
            gname.setAdapter(arrayAdapter2);

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

