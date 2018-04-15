package com.example.xshowroom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class Customer extends AppCompatActivity {
    public ProgressDialog dialog;
    String crn;
    TreeMap<String,ArrayList<String>> hashmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
try
{
    AssetManager assetManager = getAssets();
    InputStream fis = assetManager.open("citymap.txt");
    ObjectInputStream ois = new ObjectInputStream(fis);
    hashmap = (TreeMap<String, ArrayList<String>>) ois.readObject();
    ois.close();
    fis.close();
    final Spinner statespinner=(Spinner)findViewById(R.id.spinner3);
    final ArrayList<String> cities=new ArrayList<String>();

    final ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cities);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    final Spinner spinner=(Spinner)findViewById(R.id.spinner4);
    spinner.setPrompt("Select City");
    spinner.setAdapter(adapter);



    statespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1,
                                   int arg2, long arg3) {

            cities.clear();
            String state=statespinner.getSelectedItem().toString();

            cities.addAll(hashmap.get(state));
            Collections.sort(cities);
           adapter.notifyDataSetChanged();

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            Toast.makeText(Customer.this,"Hello",Toast.LENGTH_LONG).show();

        }
    });
}
catch (Exception e)
{
    Toast.makeText(this,e+"",Toast.LENGTH_LONG).show();
}

        }



    public void SaveCustomer(View view)
    {
        boolean valid=true;
        Pattern pattern;
        EditText editText = (EditText)findViewById(R.id.editText3);
        String name=editText.getText().toString();
        pattern=Pattern.compile("^([a-zA-z]|\\s)+$");
        if(name.isEmpty() || !pattern.matcher(name).matches() )
        {
            valid=false;
            editText.setError("");
        }
        editText = (EditText)findViewById(R.id.editText4);
        String Address=editText.getText().toString();
        if(Address.isEmpty())
        {
            valid=false;
            editText.setError("Please enter a valid address");
        }
        Spinner spinner = (Spinner)findViewById(R.id.spinner4);
        String City=spinner.getSelectedItem().toString();
         spinner = (Spinner)findViewById(R.id.spinner3);
        String State=spinner.getSelectedItem().toString();
        editText = (EditText)findViewById(R.id.editText11);
        String pincode=editText.getText().toString();
        pattern=Pattern.compile("^[1-9][0-9]{5}$");
        if(pincode.isEmpty() || !pattern.matcher(pincode).matches() )
        {
            valid=false;
            editText.setError("Pincode should be a valid 6 digit number");
        }
        editText = (EditText)findViewById(R.id.editText7);
        String Email=editText.getText().toString();

        pattern=Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        if(Email.isEmpty()|| !pattern.matcher(Email).matches())
        {
            valid=false;
            editText.setError("Please Enter a valid email");
        }
        editText = (EditText)findViewById(R.id.editText5);
        String Mobile=editText.getText().toString();
        pattern=Pattern.compile("^[1-9][0-9]{9}$");
        if(Mobile.isEmpty() || !pattern.matcher(Mobile).matches())
        {
            valid=false;
            editText.setError("Please Enter a valid 10 digit mobile number");
        }


        editText = (EditText)findViewById(R.id.editText8);
        String Age=editText.getText().toString();
        pattern=Pattern.compile("^([0-9][0-9]?)$");
        if(Age.isEmpty()|| !pattern.matcher(Age).matches())
        {
            valid=false;
            editText.setError("Age should be a valid 1 or 2 digit number");
        }
         spinner = (Spinner)findViewById(R.id.spinner);
        String sex=spinner.getSelectedItem().toString();

        if(valid)
        {
            String query="Name="+name+"&Address="+Address+"&City="+City+"&State="+State+"&pincode="+pincode+"&Email="+Email+"&Mobile="
                    +Mobile+"&Age="+Age+"&Sex="+sex+"&Pincode="+pincode;
            new CustomerData().execute(query);

        }


    }


    class CustomerData extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog= ProgressDialog.show(Customer.this, "",
                            "Saving details. Please wait...", true);
                }
            });

        }

        @Override
        protected String doInBackground(String... strings) {
            String jsonString=Networking.GetCustomerData(strings[0]);


            return jsonString;
        }

        @Override
        protected void onPostExecute(String s) {


            if(s!=null)
            {
                try {
                    if(s.equals(""))
                    {
                        Toast.makeText(Customer.this,"Somthing went wrong",Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        JSONObject jsonObject=new JSONObject(s);
                        Toast.makeText(Customer.this,"data saved successfully",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(Customer.this,catalogue.class);
                        intent.putExtra("Crn",jsonObject.getString("Crn"));
                        startActivity(intent);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            dialog.dismiss();
        }
    }


}
