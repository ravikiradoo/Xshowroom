package com.example.xshowroom;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CreateLead extends AppCompatActivity {
    ProgressDialog dialog;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lead);
        TextView textView=(TextView)findViewById(R.id.crnvalue);
        textView.setText(extras.getString("Crn"));
        textView=(TextView)findViewById(R.id.assetvalue);
        textView.setText(extras.getString("AssetId"));
        textView=(TextView)findViewById(R.id.revenuevalue);
        textView.setText("₹"+extras.getString("Revenue"));
        String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        textView=(TextView)findViewById(R.id.PDate);
        textView.setText(date);
        textView=(TextView)findViewById(R.id.Ddate);
        textView.setText(date);
        ImageView imageView=(ImageView)findViewById(R.id.imageView4);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar=Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog=new DatePickerDialog(CreateLead.this,android.R.style.Theme_Holo_Light_Dialog,
                        onDateSetListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        onDateSetListener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1++;
                TextView textView1=(TextView)findViewById(R.id.Ddate);
                textView1.setText(i2+"/"+i1+"/"+i);
            }
        };
    }
    public void CreateLead(View view)
    {
        TextView textView=(TextView)findViewById(R.id.crnvalue);
        String crn=textView.getText().toString();
        textView=(TextView)findViewById(R.id.assetvalue);
        String asset=textView.getText().toString();
        textView=(TextView)findViewById(R.id.revenuevalue);
        String revenue=textView.getText().toString();
        String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        textView=(TextView)findViewById(R.id.PDate);
        textView=(TextView)findViewById(R.id.Ddate);
        String Ddate=textView.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("SharedPre",MODE_PRIVATE);
        String  userid=sharedPreferences.getString("Userid","");
        Spinner spinner=(Spinner)findViewById(R.id.spinner2);
        String mop=spinner.getSelectedItem().toString();
        String query="Account="+userid+"&Crn="+crn+"&Revenue="+revenue+"&Stage=open&AssetId="+asset+"&PurchaseDate="+date
                +"&DeliveryDate="+Ddate+"&MOP="+mop;
        new Lead().execute(query);
    }

    class Lead extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog= ProgressDialog.show(CreateLead.this, "",
                            "Creating Lead. Please wait...", true);
                }
            });

        }

        @Override
        protected String doInBackground(String... strings) {
            String jsonString=Networking.CreateLead(strings[0]);


            return jsonString;
        }

        @Override
        protected void onPostExecute(String s) {


            if(s!=null)
            {
                try {
                    if(s.equals(""))
                    {
                        Toast.makeText(CreateLead.this,"Sonething went wrong",Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(CreateLead.this);
                        alertBuilder.setCancelable(true);
                        alertBuilder.setTitle("Lead Created Successfully");
                        alertBuilder.setMessage("Do you want to go Home Screen?");
                        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               Intent intent = new Intent(CreateLead.this,Content.class);
                                startActivity(intent);
                            }
                        });
                        alertBuilder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        AlertDialog alert = alertBuilder.create();
                        alert.show();


                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            dialog.dismiss();
        }
    }

}
