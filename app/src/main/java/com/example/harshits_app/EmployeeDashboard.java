package com.example.harshits_app;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

public class EmployeeDashboard extends AppCompatActivity {

    private SectionsPagerAdapter mSectionPagerAdapter;
    private ViewPager mviewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_tablayout);

        mSectionPagerAdapter =new SectionsPagerAdapter(getSupportFragmentManager());
        mviewPager=(ViewPager) findViewById(R.id.pager_container);
        setUpViewPager(mviewPager);

        TabLayout tabLayout=(TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mviewPager);


    }
    private void setUpViewPager(ViewPager viewPager){
        SectionsPagerAdapter adapter=new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new EmployeeTab1Media(),"Media");
        adapter.addFragment(new EmployeeTab2Attendance(),"Attendance");
        adapter.addFragment(new EmployeeTab3Agenda(),"Agenda");
        viewPager.setAdapter(adapter);
    }
}
