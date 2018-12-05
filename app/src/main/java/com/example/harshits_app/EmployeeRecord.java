package com.example.harshits_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EmployeeRecord extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_record);

        Button createrec=(Button) findViewById(R.id.button1);
        Button viewrec=(Button) findViewById(R.id.button2);

        createrec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EmployeeRecord.this,CreateRecord.class));
            }
        });

        viewrec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EmployeeRecord.this,ViewRecord.class));
            }
        });
    }
}
