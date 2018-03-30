package com.example.xshowroom;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Product extends AppCompatActivity {
Toolbar toolbar;
ViewPager viewPager;
SwipeAdapter swipeAdapter;
ArrayList<String> images;
String Crn="1521099687";
 String AssetId="";
    Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        extras = getIntent().getExtras();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Xshowroom");
        setSupportActionBar(toolbar);
        TextView textView=(TextView)findViewById(R.id.textView3);
        textView.setText(extras.getString("brand")+" "+extras.getString("model"));
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
        textView=(TextView)findViewById(R.id.pricevalue);
        textView.setText(extras.getString("price"));
        AssetId=extras.getString("assetId");


        viewPager=(ViewPager)findViewById(R.id.viewpager);
        images=extras.getStringArrayList("images");
        swipeAdapter=new SwipeAdapter(this,images);
        viewPager.setAdapter(swipeAdapter);



    }
    public void CreateLead(View view)
    {
        Intent intent = new Intent(this,CreateLead.class);
        intent.putExtra("Crn",Crn);
        intent.putExtra("AssetId",AssetId);
        intent.putExtra("Revenue",extras.getString("price"));
        intent.putExtra("Oname",extras.getString("brand")+" "+extras.getString("model"));
        startActivity(intent);
    }
}
