package com.example.almighty.navigationdrawer;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FMGroups extends AppCompatActivity {
    TextView group_details,Group_Transaction_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fmgroups);
        group_details= (TextView) findViewById(R.id.gd);
        Group_Transaction_details= (TextView) findViewById(R.id.gtd);
        group_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FMGroups.this,GroupsDetails.class);
                startActivity(intent);
            }
        });
        Group_Transaction_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FMGroups.this,GroupTransactionDetails.class);
                startActivity(intent);
            }
        });
    }
}
