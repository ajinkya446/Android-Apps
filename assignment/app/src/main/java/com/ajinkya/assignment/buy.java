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

public class buy extends AsyncTask<Void, Void, Bitmap> {
    String data="";
    ImageView imgv;
    String imgn,imgm;
    String info,kg,price,day,day1,price1,kg1,day2,price2,kg2;
    Bitmap bitmapn,bitmapm;

    public buy(ImageView imgv) {

        this.imgv = imgv;

    }

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
                imgm=jo.get("photo")+"";

                JSONObject  job= jo.getJSONObject("cropInfo");
                info=job.get("crop")+"";
                imgn=job.get("photo")+"";

                //JSONObject  sku= jo.getJSONObject("price");
                JSONArray aj= jo.getJSONArray("price");
                for(int j=0;j<1;j++){
                    JSONObject  sku= (JSONObject) aj.get(j);
                    day=sku.get("date")+"";
                    price=sku.get("price")+"";
                    kg=sku.get("sku")+"";
                }

                for(int j=1;j<2;j++){
                    JSONObject  sku= (JSONObject) aj.get(j);
                    day1=sku.get("date")+"";
                    price1=sku.get("price")+"";
                    kg1=sku.get("sku")+"";
                }

                for(int j=2;j<3;j++){
                    JSONObject  sku= (JSONObject) aj.get(j);
                    day2=sku.get("date")+"";
                    price2=sku.get("price")+"";
                    kg2=sku.get("sku")+"";
                }

                try {

                    InputStream in = new java.net.URL(imgn).openStream();
                    InputStream in1 = new java.net.URL(imgm).openStream();

                    bitmapn = BitmapFactory.decodeStream(in);
                    bitmapm = BitmapFactory.decodeStream(in1);


                } catch (Exception e) {

                    Log.e("Error", e.getMessage());

                    e.printStackTrace();

                }

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
        MainActivity.cname.setText(info);
        MainActivity.pic.setImageBitmap(bitmapn);
        MainActivity.image1.setImageBitmap(bitmapm);
        MainActivity.day.setText(day);
        MainActivity.price.setText(price);
        MainActivity.kg.setText(kg);

        MainActivity.day1.setText(day1);
        MainActivity.price1.setText(price1);
        MainActivity.kg1.setText(kg1);

        MainActivity.day2.setText(day2);
        MainActivity.price2.setText(price2);
        MainActivity.kg2.setText(kg2);

    }
}
