package com.example.xshowroom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class catalogue extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ProgressDialog dialog;
    ArrayList<Bike> bikes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogue);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Catalogue");
        setSupportActionBar(toolbar);
        bikes=new ArrayList<Bike>();
        recyclerView=(RecyclerView)findViewById(R.id.rv);
        new BikeData().execute();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new Radapter(bikes));

    }
    class BikeData extends AsyncTask<Void,Void,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog= ProgressDialog.show(catalogue.this, "",
                            "Saving details. Please wait...", true);
                }
            });

        }

        @Override
        protected String doInBackground(Void ...params) {
            String jsonString=Networking.GetBikeData();


            return jsonString;
        }

        @Override
        protected void onPostExecute(String s) {


            if(s!=null)
            {
                try {
                    if(s.equals(""))
                    {
                        Toast.makeText(catalogue.this,"Somthing went wrong",Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        
                        JSONObject Data  = new JSONArray(s);
                        JSONArray jsonArray=new JSONArray(Data);
                        Toast.makeText(catalogue.this,s,Toast.LENGTH_LONG).show();
                        for(int i=0;i<jsonArray.length();i++) {
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            String Brand = jsonObject.getString("Brand");
                            String Color = jsonObject.getString("Color");
                            String Price = jsonObject.getString("Price");
                            String Model = jsonObject.getString("Bike_Model");
                            String Mileage = jsonObject.getString("Mileage");
                            String Fuel = jsonObject.getString("Fuel_type");
                            String Gears = jsonObject.getString("Gears");
                            String Engine = jsonObject.getString("Engine");
                            String Electric_start = jsonObject.getString("Electric_start");
                            ArrayList<String> images = null;

                            Bike bike = new Bike(Model, Brand, Color, Mileage, Fuel, Electric_start, Engine, Price, images);
                            bikes.add(bike);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            else
            {
                Toast.makeText(catalogue.this,"Somthing went wrong",Toast.LENGTH_LONG).show();
            }
            dialog.dismiss();
        }
    }


}
