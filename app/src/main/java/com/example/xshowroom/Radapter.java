package com.example.xshowroom;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by CUBASTION on 16-03-2018.
 */

public class Radapter extends RecyclerView.Adapter<Radapter.Rviewholder> {

    public ArrayList<Bike> BikeList;
    Context context;

    public Radapter(Context context)
    {
        this.context=context;
    }

    public void setData(ArrayList<Bike> list)
    {
        this.BikeList=list;
        notifyDataSetChanged();
    }

    @Override
    public Rviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.item,parent,false);
        return new Rviewholder(view);

    }

    @Override
    public void onBindViewHolder(Rviewholder holder, int position) {

        Bike bike = BikeList.get(position);
        String text=bike.Brand+" "+bike.Model;
        holder.textView1.setText(text);
        holder.textView2.setText("\u20B9 "+bike.Price);
        String im=bike.Images.get(0);
        String url="http://ravi18.pythonanywhere.com/media/Images/" +im.substring(1,im.length()-1);
        new ImageLoadTask(url,holder.imageView).execute();

    }

    @Override
    public int getItemCount() {

        if(BikeList==null)
            return 0;
        else
        {

            return BikeList.size();}
    }

    public class Rviewholder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        ImageView imageView;
        TextView textView1;
        TextView textView2;

        public Rviewholder(View itemView) {
            super(itemView);
            textView1 = (TextView)itemView.findViewById(R.id.textView5);
            textView2=(TextView)itemView.findViewById(R.id.textView6);
            imageView = (ImageView)itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
          int position=getAdapterPosition();
          Bike bike=BikeList.get(position);
          ArrayList<String> images=bike.Images;

            Intent intent=new Intent(context,Product.class);
            intent.putExtra("images",images);
            intent.putExtra("brand",bike.Brand);
            intent.putExtra("color",bike.Color);
            intent.putExtra("engine",bike.Engine);
            intent.putExtra("electric",bike.ElectricStart);
            intent.putExtra("fuel",bike.Fuel);
            intent.putExtra("mileage",bike.Mileage);
            intent.putExtra("model",bike.Model);
            intent.putExtra("price",bike.Price);
            intent.putExtra("gears",bike.Gears);
            context.startActivity(intent);
        }
    }

    class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();


                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imageView.setImageBitmap(result);


        }

    }
}
