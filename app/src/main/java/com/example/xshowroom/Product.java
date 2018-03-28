package com.example.xshowroom;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Product extends AppCompatActivity {
Toolbar toolbar;
ViewPager viewPager;
SwipeAdapter swipeAdapter;
ArrayList<String> images;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Xshowroom");
        setSupportActionBar(toolbar);
        TextView textView=(TextView)findViewById(R.id.textView3);
        textView.setText(extras.getString("brand"));
        textView=(TextView)findViewById(R.id.modelvalue);
        textView.setText(extras.getString("model"));
        textView=(TextView)findViewById(R.id.enginevalue);
        textView.setText(extras.getString("engine"));
        textView=(TextView)findViewById(R.id.mileagevalue);
        textView.setText(extras.getString("mileage"));
        textView=(TextView)findViewById(R.id.fuelvalue);
        textView.setText(extras.getString("fuel"));
        textView=(TextView)findViewById(R.id.colorvalue);
        textView.setText(extras.getString("color"));
        textView=(TextView)findViewById(R.id.gearsvalue);
        textView.setText(extras.getString("gears"));
        textView=(TextView)findViewById(R.id.electricvalue);
        textView.setText(extras.getString("electric"));
        textView=(TextView)findViewById(R.id.price);
        textView.setText(extras.getString("price"));


        viewPager=(ViewPager)findViewById(R.id.viewpager);
        images=extras.getStringArrayList("images");
        swipeAdapter=new SwipeAdapter(this,images);
        viewPager.setAdapter(swipeAdapter);



    }
}
