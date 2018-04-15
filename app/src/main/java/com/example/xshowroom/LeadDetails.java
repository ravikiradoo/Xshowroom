package com.example.xshowroom;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class LeadDetails extends AppCompatActivity {
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead_details);
       Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar2);
       toolbar.setTitle("Xshwowroom");
       setSupportActionBar(toolbar);

        Lead lead = (Lead)getIntent().getSerializableExtra("lead");

        TextView textView = (TextView)findViewById(R.id.OName);
        textView.setText(lead.oname);
        textView = (TextView)findViewById(R.id.cd);
        textView.setText("Closing@ "+lead.date);
        textView = (TextView)findViewById(R.id.rev);
        textView.setText("\u20B9"+lead.revenue);
        textView = (TextView)findViewById(R.id.pdate);
        textView.setText("Purchased@ "+lead.pdate);
        textView = (TextView)findViewById(R.id.asset);
        textView.setText("Model- "+lead.asset);
        textView = (TextView)findViewById(R.id.Status);
        textView.setText("Status- "+lead.stage);
        textView = (TextView)findViewById(R.id.mop);
        textView.setText(lead.mop);
        new CustomerData(lead.crn).execute();




    }
    class CustomerData extends AsyncTask<Void,Void,String> {

        String crn;
        public CustomerData(String crn)
        {
            this.crn=crn;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog= ProgressDialog.show(LeadDetails.this, "",
                            "Getting Details. Please wait...", true);
                }
            });

        }

        @Override
        protected String doInBackground(Void ...params) {
            String jsonString=Networking.GetCustData(crn);


            return jsonString;
        }

        @Override
        protected void onPostExecute(String s) {


            if(s!=null)
            {
                try {
                    if(s.equals(""))
                    {
                        Toast.makeText(LeadDetails.this,"Something went wrong",Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        JSONObject jsonObject=new JSONObject(s);

                        String name=jsonObject.getString("Name");
                        String Address=jsonObject.getString("Address")+","+jsonObject.getString("City")
                              +","  +jsonObject.getString("State");

                        String Email=jsonObject.getString("Email");
                        String phone=jsonObject.getString("Mobile");
                        String sex=jsonObject.getString("Sex");
                        TextView textView=(TextView)findViewById(R.id.name);
                        if(sex.equals("Male"))
                        textView.setText("Mr. "+name);
                        else
                            textView.setText("Mrs. "+name);

                        textView=(TextView)findViewById(R.id.address);
                        textView.setText(Address);
                        textView=(TextView)findViewById(R.id.email);
                        textView.setText(Email);
                        textView=(TextView)findViewById(R.id.phone);
                        textView.setText(phone);




                        }


                    }

                 catch (Exception e) {
                    e.printStackTrace();
                }

            }
            else
            {
                Toast.makeText(LeadDetails.this,"Something went wrong",Toast.LENGTH_LONG).show();
            }
            dialog.dismiss();
        }
    }

}
