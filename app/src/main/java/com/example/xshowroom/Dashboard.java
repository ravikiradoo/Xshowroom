package com.example.xshowroom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class Dashboard extends AppCompatActivity {
    ArrayList<BarEntry> bars;
    ArrayList<String> xaxis;
    Toolbar toolbar;
    ArrayList<String> Years;
    ProgressDialog dialog;
    String query="";
    String year="";
    String type="";
    BarChart barChart;
    String label="";
    String Title="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("DashBoard");
        setSupportActionBar(toolbar);
        barChart=(BarChart)findViewById(R.id.barchart);
        bars =new ArrayList<BarEntry>();
        xaxis=new ArrayList<>();
        Years = new ArrayList<String>();


        for(int i=2000;i<=2030;i++)
        {
            Years.add(i+"");
        }
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Years);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner spinner=(Spinner)findViewById(R.id.spinner);
        final  Spinner spinner1=(Spinner)findViewById(R.id.spinner2);
        spinner.setPrompt("Select Year");
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {


                 year=spinner.getSelectedItem().toString();

                if(type.equals("Lead Analysis"))
                {
                    query="http://ravi18.pythonanywhere.com/api/LeadsByYear?q="+year+",LA";
                    label="Total leads in the month";
                    Title = "Lead Analysis of year "+year;
                    new GetChartData().execute(query);

                }
                if(type.equals("Total Revenue Analysis"))
                {
                    query="http://ravi18.pythonanywhere.com/api/LeadsByYear?q="+year+",TRA";
                    label = "Total revenue in the month";
                    Title = "Revenue Analysis of year "+year;
                    new GetChartData().execute(query);

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {



                type=spinner1.getSelectedItem().toString();
                if(type.equals("Lead Analysis"))
                {
                    query="http://ravi18.pythonanywhere.com/api/LeadsByYear?q="+year+",LA";
                    Title = "Lead Analysis of year "+year;
                    label = "Total revenue in the month";
                    new GetChartData().execute(query);

                }
                if(type.equals("Total Revenue Analysis"))
                {
                    query="http://ravi18.pythonanywhere.com/api/LeadsByYear?q="+year+",TRA";
                    label = "Total revenue in the month";
                    Title = "Revenue Analysis of year "+year;
                    new GetChartData().execute(query);

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });


    }

    class GetChartData extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog= ProgressDialog.show(Dashboard.this, "",
                            "Fetching Data. Please wait...", true);
                }
            });

        }

        @Override
        protected String doInBackground(String... strings) {
            String jsonString=Networking.ChartingData(strings[0]);


            return jsonString;
        }

        @Override
        protected void onPostExecute(String s) {


            if(s!=null)
            {
                try {
                    if(s.equals(""))
                    {
                        Toast.makeText(Dashboard.this,"Somthing went wrong",Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        s="{ Data: "+s+"}";
                        JSONObject jsonObject=new JSONObject(s);
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        bars.clear();
                        xaxis.clear();
                        for (int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject object =  jsonArray.getJSONObject(i);
                             Float value = Float.parseFloat(object.getString("total"));
                             bars.add(new BarEntry(i,value));
                             xaxis.add(object.getString("Month"));


                        }
                        TextView textView = (TextView)findViewById(R.id.textView);
                        textView.setText(Title);
                        BarDataSet barDataSet = new BarDataSet(bars,label);
                        BarData barData = new BarData(barDataSet);
                        barData.setValueFormatter(new LargeValueFormatter());
                        Description description = new Description();
                        description.setText("");

                        barChart.setData(barData);
                        barChart.getBarData().setBarWidth(0.5f);
                        barChart.setDescription(description);

                        barChart.invalidate();

                        XAxis xAxis = barChart.getXAxis();
                        xAxis.setGranularity(1f);

                        xAxis.setGranularityEnabled(true);
                        xAxis.setDrawGridLines(true);
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(xaxis));
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);



                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            dialog.dismiss();
        }
    }

}
