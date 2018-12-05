package com.example.harshits_app;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

class AddDetails {

    private String uid;
    private DatabaseReference mref;

    AddDetails(FirebaseUser user) {
        this.uid =user.getUid();
        this.mref =FirebaseDatabase.getInstance().getReference("users/employee/"+uid);
    }
     void setValues(String name,String pos,Integer empid,String ref,Integer salary,String accno,String ifsc,String doj){
        mref.child("name").setValue(name);
        mref.child("position").setValue(pos);
        mref.child("employee id").setValue(empid);
        mref.child("reference").setValue(ref);
        mref.child("salary").setValue(salary);
        mref.child("account no").setValue(accno);
        mref.child("IFSC").setValue(ifsc);
        mref.child("date of joining").setValue(doj);
    }
}
