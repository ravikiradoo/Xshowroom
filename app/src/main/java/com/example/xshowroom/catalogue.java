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
import java.util.Arrays;

public class catalogue extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ProgressDialog dialog;
    ArrayList<Bike> bikes;
    Radapter radapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogue);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Catalogue");
        setSupportActionBar(toolbar);
        bikes=new ArrayList<Bike>();
        recyclerView=(RecyclerView)findViewById(R.id.rv);
        recyclerView.hasFixedSize();
        radapter=new Radapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(radapter);
        new BikeData().execute();



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
                        String jsonString="{ Data:"+s+"}";

                        JSONObject Data=new JSONObject(jsonString);
                        JSONArray jsonArray=Data.getJSONArray("Data");

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

                            String im=jsonObject.getString("Images");
                            String im_string=im.substring(1,im.length()-1);
                            String image_array[]=im_string.split(",");
                            ArrayList<String> images = new ArrayList<String>(Arrays.asList(image_array));


                            Bike bike = new Bike(Model, Brand, Color, Mileage, Fuel, Electric_start, Engine, Price, images,Gears);
                            bikes.add(bike);

                        }

                        radapter.setData(bikes);
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
