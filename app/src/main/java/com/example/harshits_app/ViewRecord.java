package com.example.harshits_app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

public class ViewRecord extends AppCompatActivity implements Serializable {

    Spinner searchtype;
    Button search;
    EditText searchbar;
    DatabaseReference mref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);

        searchbar=(EditText) findViewById(R.id.searchbar);
        search=(Button) findViewById(R.id.search);
        searchtype=(Spinner) findViewById(R.id.searchtype);
        mref=FirebaseDatabase.getInstance().getReference("users/employee");
    }

    @Override
    protected void onStart() {
        super.onStart();
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String stype=searchtype.getSelectedItem().toString();
                final String value=searchbar.getText().toString();
                mref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean f=false;

                        setContentView(R.layout.activity_display_record);
                        TableLayout root=(TableLayout) findViewById(R.id.root);

                        int i=0,empcount=1; //i = rowcount  empcount = no. of employee
                        for(DataSnapshot employee : dataSnapshot.getChildren()){
                            if(employee.child(stype).getValue().toString().equals(value)){
                                f=true;

                                TextView no=new TextView(getApplicationContext());
                                no.setText("Employee "+Integer.toString(empcount)+":");
                                no.setTypeface(null,Typeface.BOLD);
                                no.setTextSize(25);
                                no.setBackgroundResource(R.drawable.cell_shape_head1);
                                root.addView(no,i);
                                empcount++;
                                i++;

                                for(DataSnapshot attributes:employee.getChildren()) {
                                    String key = attributes.getKey().toString();
                                    String val = attributes.getValue().toString();
                                    final TableRow row=new TableRow(getApplicationContext());
                                    final TextView t1 = new TextView(getApplicationContext());
                                    final TextView t2 = new TextView(getApplicationContext());
                                    t1.setBackgroundResource(R.drawable.cell_shape1);
                                    t2.setBackgroundResource(R.drawable.cell_shape1);
                                    t1.setGravity(Gravity.CENTER);
                                    t2.setGravity(Gravity.CENTER);
                                    t1.setPadding(10,10,10,10);
                                    t2.setPadding(10,10,10,10);
                                    t1.setText(key);
                                    t2.setText(val);
                                    t1.setTextSize(22);
                                    t2.setTextSize(22);
                                    t1.setTypeface(null,Typeface.BOLD);
                                    row.addView(t1);
                                    row.addView(t2);
                                    root.addView(row,i);
                                    i++;
                                }
                            }
                        }
                        if(!f){
                            Toast.makeText(ViewRecord.this,"Check the search value",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
