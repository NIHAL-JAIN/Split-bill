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
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class Show_Savings extends AppCompatActivity {

    String url = "http://androindian.com/apps/fm/api.php";
    Spinner spinner;
    ArrayList<HashMap<String, String>> savingstypes;
    ArrayList<String> showsavings1;
    ArrayList<String> showsavings2;
    ArrayList<String> showsavings3;
    ArrayList<String> showsavings4;
    ArrayList<String> savingstype;
    JSONObject jsonObject, jsonObject2;
    ListView listView;
    public static final String MYPreferences="MyPrefs";
    TextView testing;
    ImageButton add_savings;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__savings);
        spinner = (Spinner) findViewById(R.id.spin2);
        listView = (ListView) findViewById(R.id.lst1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        testing= (TextView) findViewById(R.id.test1);
        add_savings= (ImageButton) findViewById(R.id.imagebutton1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Savings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sharedPreferences = getSharedPreferences(MYPreferences, Context.MODE_PRIVATE);
        final String phoneNumber = sharedPreferences.getString("phoneKey", null);
        add_savings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Show_Savings.this,Savings.class);
                startActivity(intent);
            }
        });
        JSONObject savings_json = new JSONObject();
        try {
            savings_json.put("action", "get_saving_types");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Savingstype city = new Savingstype();
        city.execute(savings_json.toString());

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    JSONObject jsonObject_new = new JSONObject();
                    try {
                        jsonObject_new.put("action", "get_savings");
                        jsonObject_new.put("stype", "S");
                        jsonObject_new.put("user_id", phoneNumber);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ShowSaving shows = new ShowSaving();
                    shows.execute(jsonObject_new.toString());

                } else if(position==1){

                    //*savings_type_id=savingstypes.get(position).get("code")*//*
                    ;
                    JSONObject jsonObject_new = new JSONObject();
                    try {
                        jsonObject_new.put("action", "get_savings");
                        jsonObject_new.put("stype", "E");
                        jsonObject_new.put("user_id",phoneNumber);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    ShowSaving shows = new ShowSaving();
                    shows.execute(jsonObject_new.toString());


                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
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


    private class Savingstype extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog = new ProgressDialog(Show_Savings.this);

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading..");
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            JSONObject jsonObject = JsonFunction.getJsonFromUrlparam(url, params[0]);
            Log.i("json", "" + jsonObject);
            savingstypes = new ArrayList<>();
            savingstype = new ArrayList<>();
           /* savingstype.add("Choose savings type");*/
            try {
                if ("success".endsWith(jsonObject.getString("response"))) {

                    JSONArray city_jarray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < city_jarray.length(); i++) {
                        JSONObject city_obj = city_jarray.getJSONObject(i);
                        HashMap<String, String> citymap;
                        citymap = new HashMap<>();
                        citymap.put("code", city_obj.getString("code"));
                        citymap.put("name", city_obj.getString("name"));
                        savingstype.add(city_obj.getString("name"));
                        savingstypes.add(citymap);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            Log.i("citi_list", "" + savingstypes);
            spinner.setAdapter(new ArrayAdapter<>(Show_Savings.this, android.R.layout.simple_list_item_1, savingstype));

        }
    }

    private class ShowSaving extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog = new ProgressDialog(Show_Savings.this);

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
            try {

                JSONArray city_jarray = jsonObject2.getJSONArray("data");
                for (int i = 0; i < city_jarray.length(); i++) {
                    JSONObject city_obj = city_jarray.getJSONObject(i);

                    showsavings1.add(city_obj.getString("amount"));
                    showsavings2.add(city_obj.getString("datetime"));
                    showsavings3.add(city_obj.getString("enddate"));
                    showsavings4.add(city_obj.getString("reason"));

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


            listView.setAdapter(new CustomAdapter(Show_Savings.this, showsavings1, showsavings2, showsavings3, showsavings4));
            Log.i("ggg", "" + jsonObject2);

        }
    }
}














