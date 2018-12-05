package com.example.harshits_app;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class AdminTab3Finance extends Fragment {

    public final static int REQUEST_CODE_INCOME = 1;
    public final static int REQUEST_CODE_EXPENSE = 2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.admin_tab3_finance, container, false);

        Button income=(Button) view.findViewById(R.id.button1);
        Button expense=(Button) view.findViewById(R.id.button3);

        income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),IncomeExpense.class);
                intent.putExtra("path","finance/income/");
                startActivityForResult(intent,REQUEST_CODE_INCOME);
            }
        });
        expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getActivity(),IncomeExpense.class);
                intent.putExtra("path","finance/expense/");
                startActivityForResult(intent,REQUEST_CODE_EXPENSE);

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
