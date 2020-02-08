package com.example.almighty.navigationdrawer;


import android.app.Dialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Savings extends AppCompatActivity {
    EditText startdate,enddate,reason, amount;
    Button submit1, submit2;
    ImageButton calender2,calender3;

    String url = "http://androindian.com/apps/fm/api.php";
    JSONObject jsonObject1 = null;
    Spinner spinner;
    ArrayList<HashMap<String, String>> savingstypes;
    ArrayList<String> savingstype;
    public static final String MYPreferences="MyPrefs";
    DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings);
        reason = (EditText) findViewById(R.id.edit1);
        amount = (EditText) findViewById(R.id.edit2);
        startdate = (EditText) findViewById(R.id.sdate);
        enddate = (EditText) findViewById(R.id.edate);
        spinner = (Spinner) findViewById(R.id.spin1);
        submit1 = (Button) findViewById(R.id.but1);
        submit2 = (Button) findViewById(R.id.but2);
        datePicker= (DatePicker) findViewById(R.id.dateselect);
        calender2= (ImageButton) findViewById(R.id.cal2);
        calender3= (ImageButton) findViewById(R.id.cal3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Savings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        calender2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(Savings.this);
                dialog.setContentView(R.layout.custom);
                final DatePicker datePicker= (DatePicker) dialog.findViewById(R.id.dateselect);
                Button button= (Button) dialog.findViewById(R.id.cbut);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int day = datePicker.getDayOfMonth();
                        int month = datePicker.getMonth();
                        int year = datePicker.getYear()-1900;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String formatedDate = sdf.format(new Date(year, month, day));
                        startdate.setText(formatedDate);
                        dialog.dismiss();
                        enddate.requestFocus();
                    }
                });
                dialog.show();
            }
        });

        calender3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(Savings.this);
                dialog.setContentView(R.layout.custom);
                final DatePicker datePicker= (DatePicker) dialog.findViewById(R.id.dateselect);
                Button button= (Button) dialog.findViewById(R.id.cbut);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int day = datePicker.getDayOfMonth();
                        int month = datePicker.getMonth();
                        int year = datePicker.getYear()-1900;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String formatedDate = sdf.format(new Date(year, month, day));
                        enddate.setText(formatedDate);
                        dialog.dismiss();
                    }
                });
                dialog.show();


            }
        });


        SharedPreferences sharedPreferences = getSharedPreferences(MYPreferences, Context.MODE_PRIVATE);
        final String s10 = sharedPreferences.getString("phoneKey",null);

        JSONObject savings_json = new JSONObject();
        try {
            savings_json.put("action", "get_saving_types");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Savingstype city = new Savingstype();
        city.execute(savings_json.toString());
        submit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String r = reason.getText().toString().trim();
                String a = amount.getText().toString().trim();
                String sd = startdate.getText().toString().trim();
                String ed = enddate.getText().toString().trim();
                JSONObject jsonObject2 = new JSONObject();
                try {
                    jsonObject2.put("action","put_saving");
                    assert s10 != null;
                    jsonObject2.put("mobile",s10.trim());
                    jsonObject2.put("stype","S");
                    jsonObject2.put("reason",r);
                    jsonObject2.put("amount",a);
                    jsonObject2.put("start_date",sd);
                    jsonObject2.put("end_date",ed);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                InsertSavings insertSavings = new InsertSavings();
                insertSavings.execute(jsonObject2.toString());
            }
        });
        submit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.setVisibility(View.GONE);
                String r = reason.getText().toString().trim();
                String a = amount.getText().toString().trim();
                String sd = startdate.getText().toString().trim();
                String ed = enddate.getText().toString().trim();
                JSONObject jsonObject2 = new JSONObject();
                try {
                    jsonObject2.put("action","put_saving");
                    jsonObject2.put("mobile",s10);
                    jsonObject2.put("stype","E");
                    jsonObject2.put("reason",r);
                    jsonObject2.put("amount",a);
                    jsonObject2.put("start_date",sd);
                    jsonObject2.put("end_date",ed);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                InsertSavings insertSavings = new InsertSavings();
                insertSavings.execute(jsonObject2.toString());
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0) {
                    submit1.setVisibility(View.VISIBLE);
                    submit2.setVisibility(View.GONE);

                }else if(position==1){
                    submit2.setVisibility(View.VISIBLE);
                    submit1.setVisibility(View.GONE);
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }

    private class Savingstype extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog = new ProgressDialog(Savings.this);

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading..");
            progressDialog.setIndeterminate(true);
            progressDialog.show();
            progressDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {
            JSONObject jsonObject = JsonFunction.getJsonFromUrlparam(url, params[0]);
            Log.i("json", "" + jsonObject);
            savingstypes = new ArrayList<>();
            savingstype = new ArrayList<>();
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
            spinner.setAdapter(new ArrayAdapter<>(Savings.this, android.R.layout.simple_list_item_1, savingstype));

        }
    }

    private class InsertSavings extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog = new ProgressDialog(Savings.this);
        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading..");
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            progressDialog.setMessage("Loading.....");
            progressDialog.setCancelable(false);
            progressDialog.show();
            jsonObject1 = JsonFunction.getJsonFromUrlparam(url, params[0]);
            Log.i("json", "" + jsonObject1);
            return String.valueOf(jsonObject1);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            try {
                JSONObject jsonObject2 = new JSONObject(jsonObject1.toString());
                String a = jsonObject2.getString("response");
                if(a.equalsIgnoreCase("success")) {
                    String res1=jsonObject2.getString("call_back");
                    Toast.makeText(Savings.this,""+res1,Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(Savings.this,Show_Savings.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(Savings.this,"Surver error",Toast.LENGTH_LONG).show();
                }
            }
            catch(JSONException e){
                e.printStackTrace();
            }


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




