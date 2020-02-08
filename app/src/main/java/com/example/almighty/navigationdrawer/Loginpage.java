package com.example.almighty.navigationdrawer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Loginpage extends AppCompatActivity {
    Button button2;
    TextView textView1,textView2;
    EditText phone,password;
    JSONObject jsonObject1 = null;
    String url = "http://androindian.com/apps/fm/api.php";
    public static final String MYPreferences="MyPrefs";
    public static final String Phone="phoneKey";
    public static final String Name="username";
    public static final String Email="useremail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        textView2= (TextView) findViewById(R.id.signup);
        button2= (Button) findViewById(R.id.login);
        textView1= (TextView) findViewById(R.id.txt21);
        phone= (EditText) findViewById(R.id.phno);
        password= (EditText) findViewById(R.id.password);
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent21=new Intent(Loginpage.this,CreateAcount.class);
                startActivity(intent21);
                finish();
            }
        });
      /*  if (isConnectingToInternet(Loginpage.this)) {*/
            SharedPreferences sharedPreferences = getSharedPreferences(MYPreferences, Context.MODE_PRIVATE);
            String s10 = sharedPreferences.getString("phoneKey", null);

            if (s10 != null) {
                Intent intent = new Intent(Loginpage.this, FinanceManagement.class);
                Log.i("s1", s10);
                startActivity(intent);
                finish();
            } else {
                    button2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            String s21 = phone.getText().toString().trim();
                            String s22 = password.getText().toString().trim();
                            if (s21.matches("")) {
                                Toast.makeText(Loginpage.this, "phone number should not be empty", Toast.LENGTH_LONG).show();
                            } else {
                                if (s22.matches("")) {
                                    Toast.makeText(Loginpage.this, "password number should not be empty", Toast.LENGTH_LONG).show();
                                } else {
                                  /*  SharedPreferences sharedPreferences = getSharedPreferences(MYPreferences, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    String s1 = phone.getText().toString().trim();
                                    String s2 = password.getText().toString().trim();
                                    editor.putString(Phone, s1);
                                    editor.putString(Password, s2);
                                    editor.apply();
                                    String s25 = phone.getText().toString().trim();
                                    String s26 = password.getText().toString().trim();
                                    JSONObject jsonObject = new JSONObject();
                                    try {
                                        jsonObject.put("mobile", s25);
                                        jsonObject.put("pswrd", s26);
                                        jsonObject.put("action", "login_user");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    Login login = new Login();
                                    login.execute(jsonObject.toString());*/
                                    final ProgressDialog dialog=new ProgressDialog(Loginpage.this);
                                    dialog.setMessage("Loading");
                                    dialog.show();

                                    Retrofit retrofit=new Retrofit.Builder()
                                            .baseUrl("http://androindian.com/apps/fm/")
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();
                                    RestApi rest=retrofit.create(RestApi.class);
                                    User user=new User(phone.getText().toString().trim(),password.getText().toString().trim(),"login_user");
                                    Call<Model> result=rest.getJson(user);
                                    result.enqueue(new Callback<Model>() {
                                        @Override
                                        public void onResponse(Call<Model> call, Response<Model> response) {
                                            dialog.dismiss();
                                            Model mm=response.body();
                                            Toast.makeText(Loginpage.this,mm.getResponse(),Toast.LENGTH_LONG).show();
                                            if(mm.getResponse().equals("success")) {
                                                SharedPreferences sharedPreferences = getSharedPreferences(MYPreferences, Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.putString(Phone, mm.getData().getMobile());
                                                editor.putString(Email, mm.getData().getEmail());
                                                editor.putString(Name, mm.getData().getName());
                                                editor.apply();
                                                Intent intent = new Intent(Loginpage.this, FinanceManagement.class);
                                                startActivity(intent);
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Model> call, Throwable t) {


                                        }
                                    });

                                }
                            }
                        }
                    });
            }
       /* }else {
            AlertDialog.Builder alert = new AlertDialog.Builder(Loginpage.this);
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
                        }*/


        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent23=getIntent();
                String s24=intent23.getStringExtra("RECOVERYMAIL");
                Intent intent=new Intent(Loginpage.this,Recovery.class);
                intent23.putExtra("email",s24);
                startActivity(intent);
                finish();
            }
        });
    }
/*
    private class Login extends AsyncTask<String,String,String> {
        ProgressDialog progressDialog=new ProgressDialog(Loginpage.this);
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
                if(abc.equalsIgnoreCase("success")) {
                   Intent intent=new Intent(Loginpage.this,FinanceManagement.class);
                    startActivity(intent);
                    finish();
                }else if(abc.equalsIgnoreCase("failed")) {
                    Toast.makeText(Loginpage.this, "no data found", Toast.LENGTH_LONG).show();
                    SharedPreferences sharedPreferences=getSharedPreferences(MYPreferences, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                }else if(abc.equalsIgnoreCase("error")){
                    Toast.makeText(Loginpage.this, "error", Toast.LENGTH_LONG).show();
                    SharedPreferences sharedPreferences=getSharedPreferences(MYPreferences, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
*/
}
