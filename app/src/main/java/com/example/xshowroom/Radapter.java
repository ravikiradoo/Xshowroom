package com.example.xshowroom;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by CUBASTION on 16-03-2018.
 */

public class Radapter extends RecyclerView.Adapter<Radapter.Rviewholder> {

    public ArrayList<Bike> BikeList;

    public Radapter(ArrayList<Bike> list )
    {
        BikeList=list;
    }

    @Override
    public Rviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.item,parent,true);
        return new Rviewholder(view);

    }

    @Override
    public void onBindViewHolder(Rviewholder holder, int position) {

        Bike bike = BikeList.get(position);
        holder.textView1.setText(bike.Brand);
        holder.textView2.setText(bike.Price);

    }

    @Override
    public int getItemCount() {

        if(BikeList==null)
            return 0;
        else
            return BikeList.size();
    }

    public class Rviewholder extends RecyclerView.ViewHolder
    {

        ImageView imageView;
        TextView textView1;
        TextView textView2;

        public Rviewholder(View itemView) {
            super(itemView);
            textView1 = (TextView)itemView.findViewById(R.id.textView5);
            textView2=(TextView)itemView.findViewById(R.id.textView6);
            imageView = (ImageView)itemView.findViewById(R.id.imageView);
        }
    }
}