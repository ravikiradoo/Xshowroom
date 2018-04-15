package com.example.xshowroom;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by CUBASTION on 30-03-2018.
 */

public class FragmentAdapter extends RecyclerView.Adapter<FragmentAdapter.viewholder> {

    public ArrayList<Lead> leads;
    Context context;
    public FragmentAdapter(Context context, ArrayList<Lead> leads)
    {
        this.context=context;
        this.leads=leads;

    }
    public void setData(ArrayList<Lead> list)
    {
        this.leads=list;
        Collections.sort(leads);
        notifyDataSetChanged();
    }

    @Override
    public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.item2,parent,false);
        return  new FragmentAdapter.viewholder(view);
    }

    @Override
    public void onBindViewHolder(viewholder holder, int position) {

        Lead lead=leads.get(position);
        String oname=lead.oname;
        holder.oname.setText(oname);
        holder.revenue.setText("\u20B9"+lead.revenue);
        holder.date.setText(lead.date);


    }



    @Override
    public int getItemCount() {

        if(leads==null)
        {
            return 0;
        }
        else
        {
            return leads.size();
        }
    }

    public  class viewholder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView oname;
        TextView revenue;
        TextView date;

        public viewholder(View itemView) {
            super(itemView);
            oname=(TextView)itemView.findViewById(R.id.oname);
            date=(TextView)itemView.findViewById(R.id.closingDate);
            revenue=(TextView)itemView.findViewById(R.id.revvalue);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        int pos=getAdapterPosition();
        Lead lead=leads.get(pos);
        Intent intent = new Intent(context,LeadDetails.class);
        intent.putExtra("lead",lead);
        context.startActivity(intent);



        }
    }
}
