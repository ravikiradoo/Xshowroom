package com.example.xshowroom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class MainActivity extends AppCompatActivity {
    public boolean isAuthenticated=false;
    public ProgressDialog dialog;
    public static  String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("SharedPre",MODE_PRIVATE);
        isAuthenticated=sharedPreferences.getBoolean("Token",false);
        userid=sharedPreferences.getString("Userid","");

        if(isAuthenticated)
        {
            Intent intent=new Intent(MainActivity.this,Content.class);
            intent.putExtra("UserId",userid);
            startActivity(intent);
        }


    }
    public  void SignIn(View view)
    {
        EditText editText1=(EditText)findViewById(R.id.editText);
        String Userid=editText1.getText().toString();
        EditText editText2=(EditText)findViewById(R.id.editText2);
        String password=editText2.getText().toString();
        String query="UserId="+Userid+"&Password="+password;

        new Login().execute(query);
    }

    class Login extends AsyncTask<String,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog=ProgressDialog.show(MainActivity.this, "",
                            "Signing in. Please wait...", true);
                }
            });

        }

        @Override
        protected String doInBackground(String... strings) {
            String jsonString=Networking.GetData(strings[0]);


            return jsonString;
        }

        @Override
        protected void onPostExecute(String s) {


            if(s!=null)
            {
                try {
                    if(s.equals(""))
                    {Toast.makeText(MainActivity.this,"Invalid User Detail",Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        JSONObject jsonObject = new JSONObject(s);
                        userid=jsonObject.getString("UserId");
                        isAuthenticated=jsonObject.getBoolean("token");
                        save(userid,isAuthenticated);
                        Intent intent=new Intent(MainActivity.this,Content.class);
                        intent.putExtra("UserId",userid);
                        startActivity(intent);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            dialog.dismiss();
        }
    }
    void save(String userid,Boolean Token)
    {
        SharedPreferences sharedPreferences = getSharedPreferences("SharedPre",MODE_PRIVATE);
       SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Userid",userid);
        editor.putBoolean("Token",Token);
        editor.apply();
    }
}

