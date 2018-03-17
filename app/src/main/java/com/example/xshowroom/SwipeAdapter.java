package com.example.xshowroom;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Student on 17-03-2018.
 */

public class SwipeAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    ArrayList<String> images;
    ProgressDialog dialog;

    public SwipeAdapter(Context ctx,ArrayList<String> list)
    {
        context=ctx;
        images=list;
    }

    @Override
    public int getCount() {
        if(images==null)
            return 0;
        else
        {
            return images.size();}
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemview = layoutInflater.inflate(R.layout.swipe,container,false);
        ImageView imageView = (ImageView)itemview.findViewById(R.id.imageView2);
        String im=images.get(position);
        String url="http://ravi18.pythonanywhere.com/media/Images/" +im.substring(1,im.length()-1);
        Toast.makeText(context,url,Toast.LENGTH_LONG).show();

        new ImageLoadTask(url,imageView).execute();
        container.addView(itemview);

        return itemview;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
      container.removeView((LinearLayout)object);
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

            Toast.makeText(context,"s",Toast.LENGTH_LONG).show();
        }

    }


}
