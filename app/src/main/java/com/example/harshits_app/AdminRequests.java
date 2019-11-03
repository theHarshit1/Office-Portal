package com.example.harshits_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminRequests extends AppCompatActivity {

    DatabaseReference ref;
    ScrollView root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_requests);
        root=findViewById(R.id.requestFeed);
        ref=FirebaseDatabase.getInstance().getReference("requests/leave");
    }

    @Override
    protected void onStart() {
        super.onStart();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot uid:dataSnapshot.getChildren()){
                    String userid=uid.getKey();

                    LayoutInflater inflater=LayoutInflater.from(AdminRequests.this);
                    View oneRequest=inflater.inflate(R.layout.list_item,root,false);
                    addDetails(userid,oneRequest);
                    String date=uid.child("date").getValue().toString();
                    String reason=uid.child("reason").getValue().toString();
                    String duration=uid.child("duration").getValue().toString();

                    TextView t1,t2,t3;
                    t1=oneRequest.findViewById(R.id.date);
                    t2=oneRequest.findViewById(R.id.reason);
                    t3=oneRequest.findViewById(R.id.duration);

                    t1.setText(date);
                    t2.setText(reason);
                    t3.setText(duration);

                    root.addView(oneRequest);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addDetails(String uid, final View view){
        DatabaseReference mref=FirebaseDatabase.getInstance().getReference("users/employee/"+uid);
        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name=dataSnapshot.child("name").getValue().toString();
                String empid=dataSnapshot.child("employee id").getValue().toString();
                TextView t1=view.findViewById(R.id.name);
                TextView t2=view.findViewById(R.id.empid);

                t1.setText(name);
                t2.setText(empid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
