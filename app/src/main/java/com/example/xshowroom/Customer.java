package com.example.xshowroom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class Customer extends AppCompatActivity {
    public ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

    }
    public void SaveCustomer(View view)
    {
        EditText editText = (EditText)findViewById(R.id.editText3);
        String name=editText.getText().toString();
        editText = (EditText)findViewById(R.id.editText4);
        String Address=editText.getText().toString();
        editText = (EditText)findViewById(R.id.editText9);
        String City=editText.getText().toString();
        editText = (EditText)findViewById(R.id.editText10);
        String State=editText.getText().toString();
        editText = (EditText)findViewById(R.id.editText11);
        String pincode=editText.getText().toString();
        editText = (EditText)findViewById(R.id.editText7);
        String Email=editText.getText().toString();
        editText = (EditText)findViewById(R.id.editText5);
        String Mobile=editText.getText().toString();
        editText = (EditText)findViewById(R.id.editText8);
        String Age=editText.getText().toString();
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        String sex=spinner.getSelectedItem().toString();

        if(name.equals("")||Address.equals("")||City.equals("")||State.equals("")||pincode.equals("")||Email.equals("")||
                Mobile.equals("")||Age.equals("")||sex.equals(""))
        {
            Toast.makeText(Customer.this,"Please field the empty field",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(Customer.this,catalogue.class);
            startActivity(intent);
        }
        else
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
                        Toast.makeText(Customer.this,"data saved successfully",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(Customer.this,catalogue.class);
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
