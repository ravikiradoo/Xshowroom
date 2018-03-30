package com.example.xshowroom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by CUBASTION on 30-03-2018.
 */

public class FragmentAdapter extends RecyclerView.Adapter<FragmentAdapter.viewholder> {

    ArrayList<Lead> leads;
    Context context;
    public FragmentAdapter(Context context, ArrayList<Lead> leads)
    {
        this.context=context;
        this.leads=leads;
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
        holder.revenue.setText(lead.revenue);
        holder.revenue.setText(lead.date);

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

    public static class viewholder extends RecyclerView.ViewHolder
    {
        TextView oname;
        TextView revenue;
        TextView date;
        public viewholder(View itemView) {
            super(itemView);
            oname=(TextView)itemView.findViewById(R.id.onamevalue);
            date=(TextView)itemView.findViewById(R.id.closingDate);
            revenue=(TextView)itemView.findViewById(R.id.revvalue);

        }
    }
}
