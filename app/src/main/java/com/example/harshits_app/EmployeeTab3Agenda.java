package com.example.harshits_app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmployeeTab3Agenda extends Fragment{

    LinearLayout root;
    ProgressBar bar;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.employee_tab3agenda, container, false);

        bar=(ProgressBar) view.findViewById(R.id.progressBar3);
        root=(LinearLayout) view.findViewById(R.id.agendaFeed);
        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("projects/"+uid);
        bar.setVisibility(View.VISIBLE);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                        LayoutInflater inflater1 = LayoutInflater.from(getContext());
                        View inflatedview=inflater1.inflate(R.layout.agenda_item,(ViewGroup)view,false);
                        TextView title=(TextView) inflatedview.findViewById(R.id.title);
                        TextView assignment=(TextView) inflatedview.findViewById(R.id.assignmentText);
                        TextView last=(TextView) inflatedview.findViewById(R.id.lastDateText);

                        String details=dataSnapshot.child("details").getValue().toString();
                        String lastd="last date: "+dataSnapshot.child("last date").getValue().toString();
                        String pname=dataSnapshot.child("project name").getValue().toString();
                        title.setText(pname);
                        assignment.setText(details);
                        last.setText(lastd);

                        root.addView(inflatedview);
                }

                bar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
}