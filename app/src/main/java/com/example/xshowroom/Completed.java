package com.example.xshowroom;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by CUBASTION on 30-03-2018.
 */

public class Completed extends Fragment {

    ProgressDialog dialog;
    ArrayList<Lead> leads;
    RecyclerView recyclerView;
    FragmentAdapter fragmentAdapter;

    View view;
    public Completed()
    {
        leads=new ArrayList<Lead>();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.upcoming,container,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView2);
        fragmentAdapter=new FragmentAdapter(getContext(),leads);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(fragmentAdapter);
        new Leads().execute();
        return view;
    }

    class Leads extends AsyncTask<Void,Void,String> {

        @Override
        protected void onPreExecute() {

            dialog= ProgressDialog.show(getActivity(), "",
                    "Loading Please wait...", true);
        }



        @Override
        protected String doInBackground(Void ...params) {
            String jsonString=Networking.GetLeads();


            return jsonString;
        }

        @Override
        protected void onPostExecute(String s) {


            if(s!=null)
            {
                try {
                    if(s.equals(""))
                    {
                        Toast.makeText(getActivity(),"No Leads",Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        String jsonString="{ Data:"+s+"}";

                        JSONObject Data=new JSONObject(jsonString);
                        JSONArray jsonArray=Data.getJSONArray("Data");

                        for(int i=0;i<jsonArray.length();i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String oname=jsonObject.getString("OName");
                            String revenue=jsonObject.getString("Revenue");
                            String date=jsonObject.getString("DeliveryDate");
                            String crn=jsonObject.getString("Crn");
                            String Stage=jsonObject.getString("Stage");
                            String AssetId=jsonObject.getString("AssetId");
                            String mop=jsonObject.getString("MOP");
                            String pdate=jsonObject.getString("PurchaseDate");
                            Lead lead=new Lead(oname,revenue,date,Stage,mop,crn,AssetId,pdate);
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            Date strDate = sdf.parse(date);
                            if (new Date().after(strDate)) {
                                leads.add(lead);
                            }



                        }
                        fragmentAdapter.setData(leads);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
            else
            {
                Toast.makeText(getActivity(),"Something went wrong",Toast.LENGTH_LONG).show();
            }
            dialog.dismiss();
        }
    }
}
