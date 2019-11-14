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

public class fetch1 extends AsyncTask<Void, Void, Bitmap> {

    ImageView imgv,imgv1,imgv2,imgv3,imgv4,imgv5,imgv6,imgv7;
    String data="";
    String img,img1,img2,img3,img4,img5,img6,img7;
    Bitmap bitmap=null;
    Bitmap bitmap1=null;
    Bitmap bitmap2=null;
    Bitmap bmp = null;
    Bitmap bitmap3=null;
    Bitmap bitmap4=null;
    Bitmap bitmap5=null;
    Bitmap bitmap6=null;

    public fetch1(ImageView imgv,ImageView imgv1,ImageView imgv2,ImageView imgv3,ImageView imgv4,ImageView imgv5,ImageView imgv6,ImageView imgv7) {

        this.imgv = imgv;
        this.imgv1=imgv1;
        this.imgv2=imgv2;
        this.imgv3=imgv3;
        this.imgv4=imgv4;
        this.imgv5=imgv5;
        this.imgv6=imgv6;
        this.imgv7=imgv7;

    }

    @SuppressLint("WrongThread")
    @Override
    protected Bitmap doInBackground(Void... voids) {
        try {
            URL url= new URL("https://firebasestorage.googleapis.com/v0/b/vesatogofleet.appspot.com/o/androidTaskApp%2FcommodityList.json?alt=media&token=9b9e5427-8769-4dec-83c4-52afe727dbf9");
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
                img=jo.get("photo")+"\n";
                    try {

                        InputStream in = new java.net.URL(img).openStream();

                        bmp = BitmapFactory.decodeStream(in);

                    } catch (Exception e) {

                        Log.e("Error", e.getMessage());

                        e.printStackTrace();

                    }
                // return bmp;
            }

            for (int i=1;i<2;i++)
            {
                JSONObject  jo= (JSONObject) ja.get(i);

                img1=jo.get("photo")+"\n";
                try {

                    InputStream in = new java.net.URL(img1).openStream();

                    bitmap = BitmapFactory.decodeStream(in);

                } catch (Exception e) {

                    Log.e("Error", e.getMessage());

                    e.printStackTrace();

                }
            }

            for (int i=2;i<3;i++)
            {
                JSONObject  jo= (JSONObject) ja.get(i);

                img2=jo.get("photo")+"\n";
                try {

                    InputStream in = new java.net.URL(img2).openStream();

                    bitmap1 = BitmapFactory.decodeStream(in);

                } catch (Exception e) {

                    Log.e("Error", e.getMessage());

                    e.printStackTrace();

                }
            }

            for (int i=3;i<4;i++)
            {
                JSONObject  jo= (JSONObject) ja.get(i);
                img3=jo.get("photo")+"\n";
                try {

                    InputStream in = new java.net.URL(img3).openStream();

                    bitmap2 = BitmapFactory.decodeStream(in);

                } catch (Exception e) {

                    Log.e("Error", e.getMessage());

                    e.printStackTrace();

                }

            }
            for (int i=4;i<5;i++)
            {
                JSONObject  jo= (JSONObject) ja.get(i);
                img4=jo.get("photo")+"\n";
                try {

                    InputStream in = new java.net.URL(img4).openStream();

                    bitmap3 = BitmapFactory.decodeStream(in);

                } catch (Exception e) {

                    Log.e("Error", e.getMessage());

                    e.printStackTrace();

                }

            }
            for (int i=5;i<6;i++)
            {
                JSONObject  jo= (JSONObject) ja.get(i);
                img5=jo.get("photo")+"\n";
                try {

                    InputStream in = new java.net.URL(img5).openStream();

                    bitmap4 = BitmapFactory.decodeStream(in);

                } catch (Exception e) {

                    Log.e("Error", e.getMessage());

                    e.printStackTrace();

                }

            }
            for (int i=6;i<7;i++)
            {
                JSONObject  jo= (JSONObject) ja.get(i);
                img6=jo.get("photo")+"\n";
                try {

                    InputStream in = new java.net.URL(img6).openStream();

                    bitmap5 = BitmapFactory.decodeStream(in);

                } catch (Exception e) {

                    Log.e("Error", e.getMessage());

                    e.printStackTrace();

                }

            }
            for (int i=7;i<8;i++)
            {
                JSONObject  jo= (JSONObject) ja.get(i);
                img7=jo.get("photo")+"\n";
                try {

                    InputStream in = new java.net.URL(img7).openStream();

                    bitmap6 = BitmapFactory.decodeStream(in);

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
         MainActivity.imgl.setImageBitmap(bmp);
         MainActivity.img2.setImageBitmap(bitmap);
        MainActivity.img3.setImageBitmap(bitmap1);
        MainActivity.img4.setImageBitmap(bitmap2);
        MainActivity.img5.setImageBitmap(bitmap3);
        MainActivity.img6.setImageBitmap(bitmap4);
        MainActivity.img7.setImageBitmap(bitmap5);
        MainActivity.img8.setImageBitmap(bitmap6);

    }
}
