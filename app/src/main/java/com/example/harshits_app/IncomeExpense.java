package com.example.harshits_app;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class IncomeExpense extends AppCompatActivity {

    Button add;
    EditText amount;
    TextView type;
    android.support.v7.widget.Toolbar toolbar;
    TableRow weekrow,monthrow;
    String path;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_expense);

        add=findViewById(R.id.add);
        type=findViewById(R.id.type);   //table heading income or expense
        amount=findViewById(R.id.amount);
        weekrow=findViewById(R.id.weekRow);
        monthrow=findViewById(R.id.monthRow);
        toolbar=findViewById(R.id.my_toolbar);

        Intent intent=getIntent();
        path=intent.getStringExtra("path");

        if(path.equals("finance/income/")){
            add.setText(R.string.addIncome);
            type.setText(R.string.income);
            toolbar.setTitle("Income");
        }
        else{
            add.setText(R.string.addExpense);
            type.setText(R.string.expense);
            toolbar.setTitle("Expense");
        }

        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);  //month number 0 to 11

        Date date = new Date();
        String strdateFormat = "yy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(strdateFormat);
        String formattedDate = dateFormat.format(date);

        ref=FirebaseDatabase.getInstance().getReference(path+Integer.toString(month)+"/"+formattedDate);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amt=amount.getText().toString();
                ref.setValue(amt);
                finish();
            }
        });

        showStats(month);
    }

    private void showStats(int month){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference(path+Integer.toString(month));
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    int count=1;
                    int mtotal=0;
                    int wtotal=0;
                    for (DataSnapshot dates:dataSnapshot.getChildren()) {
                        int amt = Integer.valueOf(dates.getValue().toString());
                        mtotal += amt;
                        if (count <= 7) {
                            wtotal += amt;
                        }
                        count++;
                    }
                    mtotal=mtotal/30;
                    wtotal=wtotal/7;
                        TextView w=new TextView(IncomeExpense.this);
                        TextView m=new TextView(IncomeExpense.this);
                        w.setText(Integer.toString(wtotal));
                        m.setText(Integer.toString(mtotal));
                        w.setTextSize(22);
                        m.setTextSize(22);
                        w.setPadding(5,5,5,5);
                        m.setPadding(5,5,5,5);
                        w.setBackgroundResource(R.drawable.cell_shape2);
                        m.setBackgroundResource(R.drawable.cell_shape2);
                        w.setTextColor(Color.BLACK);
                        m.setTextColor(Color.BLACK);
                        weekrow.addView(w);
                        monthrow.addView(m);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(IncomeExpense.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
