package com.ajinkya.missingir.Model;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import androidx.appcompat.widget.AppCompatEditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class addDetails extends AsyncTask<String, Void, String> {
    Context context;
    AlertDialog alertDialog;
    String username;

    public addDetails(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Registration Status");
    }

    @Override
    protected void onPostExecute(String s) {
        if(s.equals("true")){
            alertDialog.setMessage(s);
            alertDialog.show();

        }else
        {
            alertDialog.setMessage(s);
            alertDialog.show();
        }

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String LOAD_URL = "http://3.132.214.190/missing/api/insert.php";

        if (type.equals("insert")) {
            try {
                username = params[2];
                String rname = params[1];
                String email = params[3];
                String dob = params[4];
                String gender = params[5];
                String designation = params[6];
                String mobile = params[7];
                String password = params[8];

                URL url = new URL(LOAD_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String POST_DATA = URLEncoder.encode("rname", "UTF-8") + "=" + URLEncoder.encode(rname, "UTF-8") +
                        "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8")+
                        "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")+
                        "&" + URLEncoder.encode("dob", "UTF-8") + "=" + URLEncoder.encode(dob, "UTF-8")+
                        "&" + URLEncoder.encode("gender", "UTF-8") + "=" + URLEncoder.encode(gender, "UTF-8")+
                        "&" + URLEncoder.encode("designation", "UTF-8") + "=" + URLEncoder.encode(designation, "UTF-8")+
                        "&" + URLEncoder.encode("mobile", "UTF-8") + "=" + URLEncoder.encode(mobile, "UTF-8")+
                        "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");                        ;
                bufferedWriter.write(POST_DATA);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
