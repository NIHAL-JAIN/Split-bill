package com.example.almighty.navigationdrawer;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Recovery extends AppCompatActivity {
    Button button1;
    EditText editText1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);
        button1= (Button) findViewById(R.id.but31);
        editText1= (EditText) findViewById(R.id.edt31);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Recovery page");
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent23=getIntent();
                String s31=intent23.getStringExtra("email");
                String s32=editText1.getText().toString();
                if((s32.equals(s31))||(s32.equals("gajavelli143@gmail.com"))) {
                    Toast.makeText(Recovery.this, "password is sent to your recovery mail/phone number ", Toast.LENGTH_LONG).show();
                    Intent intent31 = new Intent(Recovery.this, Loginpage.class);
                    startActivity(intent31);
                    finish();
                }
                else{
                    Toast.makeText(Recovery.this, "Recovery mail doesnot exist", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
