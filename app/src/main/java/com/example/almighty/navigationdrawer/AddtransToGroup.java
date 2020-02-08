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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddtransToGroup extends AppCompatActivity {
    String url = "http://androindian.com/apps/fm/api.php";
    JSONObject jsonObject1 = null;
    public static final String MYPreferences = "MyPrefs";
    public static final String Phone = "phoneKey";
    ArrayList<String> showsavings1;
    ArrayList<String> showsavings2;

    DatePicker datePicker;
    EditText boughtdate, reason, amount,gid2;
    JSONObject jsonObject3;
    Button submit, pickdate;
    AutoCompleteTextView actv;
    Spinner gid,gname;
    String s10 = null,gidd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtrans_to_group);
        reason = (EditText) findViewById(R.id.edit1);
        amount = (EditText) findViewById(R.id.edit2);
        boughtdate = (EditText) findViewById(R.id.bdate);
        gid = (Spinner) findViewById(R.id.gid);
        gname= (Spinner) findViewById(R.id.gnamee);
        submit = (Button) findViewById(R.id.but1);
        pickdate = (Button) findViewById(R.id.pd3);
        datePicker = (DatePicker) findViewById(R.id.dateselect);
        actv = (AutoCompleteTextView) findViewById(R.id.actv1);
        gid2= (EditText) findViewById(R.id.edtgid);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dialy Transactions");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sharedPreferences = getSharedPreferences(MYPreferences, Context.MODE_PRIVATE);
        s10 = sharedPreferences.getString("phoneKey", null);

        String[] ttype = {"C", "D"};
        ArrayAdapter a = new ArrayAdapter(AddtransToGroup.this, android.R.layout.simple_expandable_list_item_1, ttype);
        actv.setAdapter(a);

        JSONObject jsonObject_new = new JSONObject();
        try {
            jsonObject_new.put("action", "get_groups");
            jsonObject_new.put("member", s10);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        GroupsDetailss shows = new GroupsDetailss();
        shows.execute(jsonObject_new.toString());

        pickdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.setVisibility(View.VISIBLE);
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear() - 1900;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String formatedDate = sdf.format(new Date(year, month, day));
                boughtdate.setText(formatedDate);

            }
        });
        gname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gidd= gid.getItemAtPosition(i).toString();
                gid2.setText(gidd);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String r = reason.getText().toString().trim();
                String a = amount.getText().toString().trim();
                String sd = boughtdate.getText().toString().trim();
                String c = actv.getText().toString().trim();
                JSONObject jsonObject2 = new JSONObject();
                try {
                    jsonObject2.put("action", "add_trans_to_group");
                    assert s10 != null;
                    jsonObject2.put("mobile_no", s10.trim());
                    jsonObject2.put("ttype", "S");
                    jsonObject2.put("reason", r);
                    jsonObject2.put("amount", a);
                    jsonObject2.put("bdate", sd);
                    jsonObject2.put("cr_or_dr", c);
                    jsonObject2.put("gu_id",gidd);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                InsertSavings insertSavings = new InsertSavings();
                insertSavings.execute(jsonObject2.toString());
            }
        });

    }

    private class InsertSavings extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog = new ProgressDialog(AddtransToGroup.this);

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
                String res1 = jsonObject2.getString("call_back");
                if (a.equalsIgnoreCase("success")) {
                    Toast.makeText(AddtransToGroup.this, "" + res1, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AddtransToGroup.this, GroupTransactionDetails.class);
                    startActivity(intent);
                } else if (a.equalsIgnoreCase("failed")) {
                    Toast.makeText(AddtransToGroup.this, "" + res1, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AddtransToGroup.this, "Surver error", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

        private class GroupsDetailss extends AsyncTask<String, String, String> {
            ProgressDialog progressDialog = new ProgressDialog(AddtransToGroup.this);

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

                return String.valueOf(jsonObject3);
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
                ArrayAdapter arrayAdapter=new ArrayAdapter(AddtransToGroup.this,android.R.layout.simple_expandable_list_item_1,showsavings1);
                gid.setAdapter(arrayAdapter);
                ArrayAdapter arrayAdapter2=new ArrayAdapter(AddtransToGroup.this,android.R.layout.simple_expandable_list_item_1,showsavings2);
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
