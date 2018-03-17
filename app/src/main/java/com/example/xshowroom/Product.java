package com.example.xshowroom;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
        viewPager=(ViewPager)findViewById(R.id.viewpager);
        images=extras.getStringArrayList("images");
        swipeAdapter=new SwipeAdapter(this,images);
        viewPager.setAdapter(swipeAdapter);
    }
}
