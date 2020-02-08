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

import org.json.JSONException;
import org.json.JSONObject;


import java.text.SimpleDateFormat;
import java.util.Date;

public class AddTransAsSelf extends AppCompatActivity {
    String url = "http://androindian.com/apps/fm/api.php";
    String CorD=null;
    JSONObject jsonObject1 = null;
    public static final String MYPreferences="MyPrefs";
    public static final String Phone="phoneKey";
    EditText boughtdate,reason, amount;
    Button submit;
    ImageButton calender;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trans_as_self);
        reason = (EditText) findViewById(R.id.edit1);
        amount = (EditText) findViewById(R.id.edit2);
        boughtdate = (EditText) findViewById(R.id.bdate);
        spinner= (Spinner) findViewById(R.id.spin3);
        calender= (ImageButton) findViewById(R.id.cal1);
        submit= (Button) findViewById(R.id.but1);
        String[] ttype={"C","D"};
        ArrayAdapter<String> a=new ArrayAdapter<String>(AddTransAsSelf.this,android.R.layout.simple_expandable_list_item_1,ttype);
        spinner.setAdapter(a);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CorD=spinner.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final Dialog dialog=new Dialog(AddTransAsSelf.this);
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
                        boughtdate.setText(formatedDate);
                      dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dialy Transactions");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sharedPreferences = getSharedPreferences(MYPreferences, Context.MODE_PRIVATE);
        final String s10 = sharedPreferences.getString("phoneKey",null);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String r = reason.getText().toString().trim();
                String a = amount.getText().toString().trim();
                String sd = boughtdate.getText().toString().trim();
                String c=CorD.trim();
                JSONObject jsonObject2 = new JSONObject();
                try {
                    jsonObject2.put("action","add_trans_as_personal");
                    assert s10 != null;
                    jsonObject2.put("mobile_no",s10.trim());
                    jsonObject2.put("ttype","S");
                    jsonObject2.put("reason",r);
                    jsonObject2.put("amount",a);
                    jsonObject2.put("bdate",sd);
                    jsonObject2.put("gu_id",s10.trim());
                    jsonObject2.put("cr_or_dr",c);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                InsertSavings insertSavings = new InsertSavings();
                insertSavings.execute(jsonObject2.toString());
            }
        });
    }

    private class InsertSavings extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog = new ProgressDialog(AddTransAsSelf.this);
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
                String res1=jsonObject2.getString("call_back");
                if(a.equalsIgnoreCase("success")) {
                    Toast.makeText(AddTransAsSelf.this, "" + res1, Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(AddTransAsSelf.this,Self.class);
                    startActivity(intent);
                }
                    else if(a.equalsIgnoreCase("failed")) {
                    Toast.makeText(AddTransAsSelf.this, "" + res1, Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(AddTransAsSelf.this,"Surver error",Toast.LENGTH_LONG).show();
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
