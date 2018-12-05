package com.example.harshits_app;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.BinderThread;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateRecord extends AppCompatActivity {

    FirebaseAuth mAuth2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_record);
        final EditText val[]=new EditText[8];
        val[0]=(EditText) findViewById(R.id.name);
        val[1]=(EditText) findViewById(R.id.position);
        val[2]=(EditText) findViewById(R.id.empid);
        val[3]=(EditText) findViewById(R.id.reference);
        val[4]=(EditText) findViewById(R.id.salary);
        val[5]=(EditText) findViewById(R.id.accno);
        val[6]=(EditText) findViewById(R.id.ifsc);
        val[7]=(EditText) findViewById(R.id.doj);

        Button add=(Button) findViewById(R.id.add);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean fieldsOK = validate(val); //check if any fields are empty
                if(!fieldsOK){
                    return;
                }


                FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                        .setDatabaseUrl("[https://login-8a1d0.firebaseio.com/]")
                        .setApiKey("AIzaSyD14eWF_H7y0Y6G7jJYgeZuXGF63AL7Of8")
                        .setApplicationId("login-8a1d0").build();

                //create a new firebase auth instance
                try { FirebaseApp myApp = FirebaseApp.initializeApp(getApplicationContext(), firebaseOptions, "AnyAppName");
                    mAuth2 = FirebaseAuth.getInstance(myApp);
                } catch (IllegalStateException e){
                    mAuth2 = FirebaseAuth.getInstance(FirebaseApp.getInstance("AnyAppName"));
                }

                //use empid to form email for employees
                String email=val[2].getText().toString()+"@company.com";

                mAuth2.createUserWithEmailAndPassword(email, "123456")
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user=mAuth2.getCurrentUser();
                                    AddDetails details=new AddDetails(user);
                                    details.setValues(val[0].getText().toString(),val[1].getText().toString(),Integer.valueOf(val[2].getText().toString()),val[3].getText().toString(),Integer.valueOf(val[4].getText().toString()),val[5].getText().toString(),val[6].getText().toString(),val[7].getText().toString());
                                    Toast.makeText(CreateRecord.this,"Added successfully",Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(CreateRecord.this,AddIDproof.class);
                                    intent.putExtra("userid",user.getUid());
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(CreateRecord.this,"Authentication failed",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });


    }


    private boolean validate(EditText[] fields){
        for(int i = 0; i < fields.length; i++){
            EditText currentField = fields[i];
            if(currentField.getText().toString().length() <= 0){
                currentField.setError("This Field cannot be empty");
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
