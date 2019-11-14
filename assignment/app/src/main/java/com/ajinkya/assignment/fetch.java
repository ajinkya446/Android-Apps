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

public class fetch extends AsyncTask<Void, Void, Bitmap> {
    String urls="";
    ImageView imgv;
    String data="";
    String parsedata="";
    String two="";
    String three="";
    String four="";
    String five="";
    String six="";
    String sev="";
    String eight="";
    String img;
    String single="";
    Bitmap bmp = null;

    public fetch(ImageView imgv) {

        this.imgv = imgv;

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

                    single=jo.get("commodityName")+"\n";
                    img=jo.get("photo")+"\n";
                }
                    parsedata=parsedata+single;

                for (int i=1;i<2;i++)
                {
                    JSONObject  jo= (JSONObject) ja.get(i);

                    two=jo.get("commodityName")+"\n";
                }

                for (int i=2;i<3;i++)
                {
                    JSONObject  jo= (JSONObject) ja.get(i);

                    three=jo.get("commodityName")+"\n";

                }

                for (int i=3;i<4;i++)
                {
                    JSONObject  jo= (JSONObject) ja.get(i);

                    four=jo.get("commodityName")+"\n";

                }
                for (int i=4;i<5;i++)
                {
                    JSONObject  jo= (JSONObject) ja.get(i);

                    five=jo.get("commodityName")+"\n";

                }
                for (int i=5;i<6;i++)
                {
                    JSONObject  jo= (JSONObject) ja.get(i);

                    six=jo.get("commodityName")+"\n";

                }
                for (int i=6;i<7;i++)
                {
                    JSONObject  jo= (JSONObject) ja.get(i);

                    sev=jo.get("commodityName")+"\n";

                }
                for (int i=7;i<8;i++)
                {
                    JSONObject  jo= (JSONObject) ja.get(i);

                    eight=jo.get("commodityName")+"\n";

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
        MainActivity.name.setText(this.parsedata);
        MainActivity.id2.setText(this.two);
        MainActivity.id3.setText(this.three);
        MainActivity.id4.setText(this.four);
        MainActivity.id5.setText(this.five);
        MainActivity.id6.setText(this.six);
        MainActivity.id7.setText(this.sev);
        MainActivity.id8.setText(this.eight);
       // MainActivity.imgl.setImageBitmap(bmp);
       // MainActivity.img2.setImageBitmap(bitmap);

    }
}
