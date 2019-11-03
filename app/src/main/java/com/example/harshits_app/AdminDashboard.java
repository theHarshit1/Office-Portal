package com.example.harshits_app;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AdminDashboard extends AppCompatActivity {

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

        Button requests=(Button) findViewById(R.id.requests);
        requests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboard.this,AdminRequests.class));
            }
        });

    }
    private void setUpViewPager(ViewPager viewPager){
        SectionsPagerAdapter adapter=new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AdminTab1HR(),"HR");
        adapter.addFragment(new AdminTab2Projects(),"Projects");
        adapter.addFragment(new AdminTab3Finance(),"Finance");
        viewPager.setAdapter(adapter);
    }
}
