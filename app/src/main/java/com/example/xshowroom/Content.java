package com.example.xshowroom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class Content extends AppCompatActivity {
   Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    TabsPageAdapter tabsPageAdapter;
    ProgressDialog dialog;
    ArrayList<Lead> leads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        Bundle extras = getIntent().getExtras();
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        tabLayout=(TabLayout) findViewById(R.id.tabLayout);
        viewPager=(ViewPager)findViewById(R.id.viewPager2);
        tabsPageAdapter=new TabsPageAdapter(getSupportFragmentManager());

        tabsPageAdapter.AddFragment(new UpcomingFragment(),"Upcoming");
        tabsPageAdapter.AddFragment(new Completed(),"Completed");

        viewPager.setAdapter(tabsPageAdapter);
        tabLayout.setupWithViewPager(viewPager);

        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.action_logout)
        {
            SharedPreferences sharedPreferences=getSharedPreferences("SharedPre",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Userid","");
            editor.putBoolean("Token",false);
            editor.apply();
            Intent intent = new Intent(Content.this,MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
    public void AddCustomer(View view)
    {
        Intent intent = new Intent(Content.this,Customer.class);
        startActivity(intent);
    }
    public void Dashboard(View view)
    {
        Intent intent = new Intent(Content.this,Dashboard.class);
        startActivity(intent);
    }

}
