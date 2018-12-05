package com.example.harshits_app;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

/**
 * Created by Harshit on 14-11-2018.
 */

public class MonthlyAttendance extends AppCompatActivity {

    DatabaseReference ref;
    TableLayout tableLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monthly_attendance);
        tableLayout=findViewById(R.id.tableLayout);
        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        Calendar calendar=Calendar.getInstance();
        int month=calendar.get(Calendar.MONTH);
        ref=FirebaseDatabase.getInstance().getReference("attendance/"+uid+"/"+Integer.toString(month));
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot dates:dataSnapshot.getChildren()){
                        TableRow row=new TableRow(MonthlyAttendance.this);
                        TextView d=new TextView(MonthlyAttendance.this);
                        TextView in=new TextView(MonthlyAttendance.this);
                        TextView out=new TextView(MonthlyAttendance.this);
                        d.setTextSize(20);
                        in.setTextSize(20);
                        out.setTextSize(20);
                        d.setTextColor(Color.BLACK);
                        in.setTextColor(Color.BLACK);
                        out.setTextColor(Color.BLACK);
                        d.setBackgroundResource(R.drawable.cell_shape);
                        in.setBackgroundResource(R.drawable.cell_shape);
                        out.setBackgroundResource(R.drawable.cell_shape);
                        d.setText(dates.getKey());
                        in.setText(dates.child("in").getValue().toString());
                        if(dates.child("out").exists()){
                            out.setText(dates.child("out").getValue().toString());
                        }
                        d.setPadding(5,5,5,5);
                        in.setPadding(5,5,5,5);
                        out.setPadding(5,5,5,5);

                        row.addView(d,0);
                        row.addView(in,1);
                        row.addView(out,2);
                        row.addView(new TextView(MonthlyAttendance.this));
                        tableLayout.addView(row);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
