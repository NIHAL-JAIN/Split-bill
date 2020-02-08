package com.example.almighty.navigationdrawer;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.almighty.navigationdrawer.ConnectionDetector.isConnectingToInternet;


public class CreateAcount extends AppCompatActivity {
    Button button1, register;
    EditText Name, phone_number, email, password, editText5, editText6;
    JSONObject jsonObject1 = null;
    String url = "http://androindian.com/apps/fm/api.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
       // button1 = (Button) findViewById(R.id.but11);
        register = (Button) findViewById(R.id.but12);
        Name = (EditText) findViewById(R.id.edt11);
        phone_number = (EditText) findViewById(R.id.edt12);
        email = (EditText) findViewById(R.id.edt13);
        password = (EditText) findViewById(R.id.edt14);
        editText5 = (EditText) findViewById(R.id.edt15);
        editText6 = (EditText) findViewById(R.id.edt16);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registration Page");


        /*button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateAcount.this, Loginpage.class);
                startActivity(intent);
                finish();
            }
        });*/

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isConnectingToInternet(CreateAcount.this)) {
                    String s1 = Name.getText().toString();
                    String s2 = phone_number.getText().toString();
                    String s3 = email.getText().toString();
                    String s4 = password.getText().toString();
                    String s5 = editText5.getText().toString();
                    String s6 = editText6.getText().toString();
                    if (s1.matches("")) {
                        Toast.makeText(CreateAcount.this, "Name should not be empty", Toast.LENGTH_SHORT).show();
                    } else {
                        if (s2.matches("")) {
                            Toast.makeText(CreateAcount.this, "Mobile number shouldnot be empty", Toast.LENGTH_SHORT).show();
                        } else {
                            if ((s2.startsWith("7")) || (s2.startsWith("8")) || (s2.startsWith("9"))) {
                                if (s4.equals(s5)) {
                                    if ((s2.length() <= 13) && (s2.length() >= 10)) {
                                        if (s4.length() >= 5) {
                                            if (s3.endsWith("@gmail.com")) {
                                                if (s6.endsWith("@gmail.com")) {
                                                    if (s3.equals(s6)) {
                                                        Toast.makeText(getApplicationContext(), "Recovery mail and email should not be same ", Toast.LENGTH_LONG).show();

                                                    } else {
                                                        String name = Name.getText().toString().trim();
                                                        String mobile = phone_number.getText().toString().trim();
                                                        String useremail = email.getText().toString().trim();
                                                        String userpassword = password.getText().toString().trim();
                                                        JSONObject jsonObject = new JSONObject();
                                                        try {
                                                            jsonObject.put("name", name);
                                                            jsonObject.put("mobile", mobile);
                                                            jsonObject.put("email", useremail);
                                                            jsonObject.put("pswrd", userpassword);
                                                            jsonObject.put("action", "register_user");
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                        Registration registration = new Registration();
                                                        registration.execute(jsonObject.toString());
                                                    }
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Enter a correct recovery email id", Toast.LENGTH_LONG).show();
                                                }
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Enter a correct email id", Toast.LENGTH_LONG).show();
                                            }
                                        } else {
                                            Toast.makeText(getApplicationContext(), "password min of 5 characters", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(), "please enter a valid phone no.", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "password not matched", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(CreateAcount.this, "Mobile no should start with 7,8,9 only", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }else {
                        AlertDialog.Builder alert=new AlertDialog.Builder(CreateAcount.this);
                        alert.setTitle("No internet access");
                        alert.setMessage("Do you want to exit");
                        alert.setCancelable(false);
                        alert.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        });
                        alert.show();
                    }

            }
        });

    }

    private class Registration extends AsyncTask<String,String,String>{
        ProgressDialog progressDialog=new ProgressDialog(CreateAcount.this);
        @Override
        protected  void onPreExecute(){
            super.onPreExecute();
            progressDialog.setTitle("Loading");
            progressDialog.setMessage("Please wait");
            progressDialog.show();
        }
        @Override
        protected String doInBackground(String... strings) {
            jsonObject1 = JsonFunction.getJsonFromUrlparam(url, strings[0]);
            Log.i("json",""+jsonObject1);
            return  String.valueOf(jsonObject1);
        }
        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            progressDialog.dismiss();
            try {
                JSONObject jsonObject2 = new JSONObject(jsonObject1.toString());
                String abc = jsonObject2.getString("response");
                if(abc.equalsIgnoreCase("failed")) {
                    String res1=jsonObject2.getString("user");
                    Toast.makeText(CreateAcount.this, "" + res1, Toast.LENGTH_LONG).show();
                }else  if(abc.equalsIgnoreCase("success")){
                    String res2=jsonObject2.getString("user");
                    Toast.makeText(CreateAcount.this, "" + res2, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), Loginpage.class);
                    startActivity(intent);
                    finish();
                }else {
                    String res3 = jsonObject2.getString("user");
                    Toast.makeText(CreateAcount.this, "" + res3, Toast.LENGTH_LONG).show();
                }
                } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
