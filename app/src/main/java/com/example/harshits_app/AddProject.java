package com.example.harshits_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddProject extends AppCompatActivity {

    EditText project,details,lastdate,assignto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
        Button add= (Button) findViewById(R.id.add);
        project=(EditText) findViewById(R.id.project);
        details=(EditText) findViewById(R.id.details);
        lastdate=(EditText) findViewById(R.id.lastdate);
        assignto=(EditText) findViewById(R.id.assignto);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String pname,det,lastd,empid;
                pname=project.getText().toString();
                det=details.getText().toString();
                lastd=lastdate.getText().toString();
                empid=assignto.getText().toString();

                DatabaseReference ref=FirebaseDatabase.getInstance().getReference("users/employee");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean f=false;
                        for (DataSnapshot employee:dataSnapshot.getChildren()){
                            if(employee.child("employee id").getValue().toString().equals(empid)){
                                f=true;
                                String uid=employee.getKey();
                                DatabaseReference reference=FirebaseDatabase.getInstance().getReference("projects/"+uid);
                                reference.child("details").setValue(det);
                                reference.child("last date").setValue(lastd);
                                reference.child("project name").setValue(pname);
                                Toast.makeText(AddProject.this,"Project has been added",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                        if(!f){
                            Toast.makeText(AddProject.this,"Check the employee id",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(AddProject.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}
