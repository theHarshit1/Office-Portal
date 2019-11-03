package com.example.harshits_app;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class EmployeeTab2Attendance extends Fragment{


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.employee_tab2attendance, container, false);

        Button todaysAttendance=view.findViewById(R.id.button1);
        Button monthlyAttendance= view.findViewById(R.id.button2);
        Button requestLeave= view.findViewById(R.id.button3);

        todaysAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),TodaysAttendance.class));
            }
        });
        monthlyAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),MonthlyAttendance.class));
            }
        });

        requestLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),RequestLeave.class));
            }
        });
        return view;
    }
}
