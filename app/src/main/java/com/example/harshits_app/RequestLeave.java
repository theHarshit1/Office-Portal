package com.example.harshits_app;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Harshit on 14-11-2018.
 */

public class RequestLeave extends AppCompatActivity{

    ToggleButton togglebutton;
    EditText datetext,reason;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave_request);
        togglebutton=(ToggleButton) findViewById(R.id.toggleButton);
        datetext=findViewById(R.id.date);

        reason=(EditText) findViewById(R.id.leaveReason);
        Button submit=(Button) findViewById(R.id.submit);
        togglebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!togglebutton.isChecked())
                {
                    togglebutton.setBackgroundColor(Color.parseColor("#FF6EBF3F"));
                }
                else
                {
                    togglebutton.setBackgroundColor(Color.parseColor("FFF57F17"));
                }
            }
        });

        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        datetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DatePickerDialog(RequestLeave.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reasonstr = reason.getText().toString();
                String time = togglebutton.getText().toString();
                String dateofleave = datetext.getText().toString();
                if (!reasonstr.equals("") || !dateofleave.equals("")) {
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    DatabaseReference ref =FirebaseDatabase.getInstance().getReference("requests/leave/"+uid);
                    ref.child("date").setValue(dateofleave);
                    ref.child("reason").setValue(reasonstr);
                    ref.child("duration").setValue(time);
                }
                Toast.makeText(RequestLeave.this,"request registered",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        datetext.setText(sdf.format(myCalendar.getTime()));
    }
}
