package com.example.harshits_app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TodaysAttendance extends AppCompatActivity {

    private String uid;
    private int f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todays_attendance);

        uid=FirebaseAuth.getInstance().getCurrentUser().getUid();

        Button markin=(Button) findViewById(R.id.markInTime);
        Button markout=(Button) findViewById(R.id.markOutTime);

        markin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = new Date();
                String strTimeFormat = "hh:mm a";
                SimpleDateFormat timeFormat = new SimpleDateFormat(strTimeFormat);
                String formattedTime= timeFormat.format(date);

                String strdateFormat= "yy-MM-dd";
                SimpleDateFormat dateFormat = new SimpleDateFormat(strdateFormat);
                String formattedDate= dateFormat.format(date);
                Toast.makeText(TodaysAttendance.this,formattedDate+formattedTime,Toast.LENGTH_LONG).show();

                Calendar calendar=Calendar.getInstance();
                int month=calendar.get(Calendar.MONTH);  //month number 0 to 11
                DatabaseReference reference=FirebaseDatabase.getInstance().getReference("attendance/"+uid+"/"+Integer.toString(month)+"/"+formattedDate+"/in");
                reference.setValue(formattedTime);
                Toast.makeText(TodaysAttendance.this,"In time registered", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        markout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                int month = calendar.get(Calendar.MONTH);  //month number 0 to 11

                Date date = new Date();
                String strdateFormat = "yy-MM-dd";
                SimpleDateFormat dateFormat = new SimpleDateFormat(strdateFormat);
                String formattedDate = dateFormat.format(date);

                final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("attendance/" + uid+"/"+Integer.toString(month)+"/"+formattedDate);
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (dataSnapshot.exists()) { //if IN time exists
                                Date d=new Date();
                                String strTimeFormat = "hh:mm a";
                                SimpleDateFormat timeFormat = new SimpleDateFormat(strTimeFormat);
                                String formattedTime = timeFormat.format(d);

                                ref.child("out").setValue(formattedTime);
                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(TodaysAttendance.this,MainActivity.class));
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(TodaysAttendance.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    });
            }
        });
    }

}
