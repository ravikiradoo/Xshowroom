package com.example.xshowroom;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by CUBASTION on 12-03-2018.
 */

public class Networking {

    private static String login_url ="http://ravi18.pythonanywhere.com/api/login";
    private static String  customer_url =  "http://ravi18.pythonanywhere.com/api/customer";
    private static String  bike_url =  "http://ravi18.pythonanywhere.com/api/bikes";
    private static String  lead_url =  "http://ravi18.pythonanywhere.com/api/createLead";
    private static String  get_lead_url =  "http://ravi18.pythonanywhere.com/api/Leads";
   static URL url;

    public static String GetData(String query)
    {
        String result="";

        try {
            url=new URL(login_url);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("POST");
            Writer writer = new OutputStreamWriter(urlConnection.getOutputStream());
            writer.write(query);
            writer.flush();
            writer.close();
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line="";
            while ((line=bufferedReader.readLine())!=null)
            {
                result=result+line;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String GetCustomerData(String query)
    {
        String result="";

        try {
            url=new URL(customer_url);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("POST");
            Writer writer = new OutputStreamWriter(urlConnection.getOutputStream());
            writer.write(query);
            writer.flush();
            writer.close();
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line="";
            while ((line=bufferedReader.readLine())!=null)
            {
                result=result+line;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String GetBikeData()
    {
        String result="";

        try {
            url=new URL(bike_url);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line="";
            while ((line=bufferedReader.readLine())!=null)
            {
                result=result+line;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static String GetLeads()
    {
        String result="";

        try {
            url=new URL(get_lead_url);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line="";
            while ((line=bufferedReader.readLine())!=null)
            {
                result=result+line;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static String CreateLead(String query)
    {
        String result="";

        try {
            url=new URL(lead_url);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("POST");
            Writer writer = new OutputStreamWriter(urlConnection.getOutputStream());
            writer.write(query);
            writer.flush();
            writer.close();
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line="";
            while ((line=bufferedReader.readLine())!=null)
            {
                result=result+line;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
