package com.ajinkya.assignment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class buyfetch extends AsyncTask<Void, Void, Bitmap> {
    String urls="";
    ImageView imgv;
    String data="";
    String cname="";
    String data_new="";
    String parsedata="";
    Bitmap bitmap=null;
    String img;
    String info;
    String name="";
    String single="";



    @SuppressLint("WrongThread")
    @Override
    protected Bitmap doInBackground(Void... voids) {
        try {
            URL url= new URL("https://firebasestorage.googleapis.com/v0/b/vesatogofleet.appspot.com/o/androidTaskApp%2FbuyerList.json?alt=media&token=3dcc96c2-9309-4873-868d-bf0023f6266c");
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
            InputStream inputStream= httpURLConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));


            String line="";
            while(line!=null){
                line=bufferedReader.readLine();
                data=data+line;
            }

            JSONArray ja = new JSONArray(data);

            for (int i=0;i<1;i++)
            {
                JSONObject  jo= (JSONObject) ja.get(i);

                single=jo.get("buyerName")+"\n";

/*
                try {

                    InputStream in = new java.net.URL(img).openStream();

                    bitmap = BitmapFactory.decodeStream(in);

                } catch (Exception e) {

                    Log.e("Error", e.getMessage());

                    e.printStackTrace();

                }
*/
            }



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onPostExecute(Bitmap aVoid) {
        super.onPostExecute(aVoid);
        MainActivity.bname.setText(single);
        //MainActivity.image1.setImageBitmap(bitmap);
        //MainActivity.cname.setText(cname);

    }
}
