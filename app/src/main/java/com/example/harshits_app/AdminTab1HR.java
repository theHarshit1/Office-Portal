package com.example.harshits_app;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class AdminTab1HR extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.admin_tab1_hr, container, false);
        Button employee=(Button) view.findViewById(R.id.button1);
        Button opening=(Button) view.findViewById(R.id.button2);

        employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),EmployeeRecord.class));
            }
        });

        return view;
    }
}
