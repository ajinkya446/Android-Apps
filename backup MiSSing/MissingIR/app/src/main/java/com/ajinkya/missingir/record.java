package com.ajinkya.missingir;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class record extends AppCompatActivity {
    private AppCompatButton display;
    AlertDialog alertDialog;
    private RequestQueue mQueue;
    TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        display=findViewById(R.id.display);
        mQueue= Volley.newRequestQueue(this);
        data=findViewById(R.id.data);
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchJSON();
            }
        });

    }

    private void fetchJSON() {
        String JSON_URL="http://3.132.214.190/missing/api/all.php";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, JSON_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray=response.getJSONArray("city");
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject city=jsonArray.getJSONObject(i);
                                String station_name=city.getString("station_name");
                                String personName=city.getString("personName");

                                LayoutInflater inflater = getLayoutInflater();
                                View alertLayout = inflater.inflate(R.layout.display_record, null);
                                final TextView cityName = alertLayout.findViewById(R.id.cityName);
                                final TextView person_Name = alertLayout.findViewById(R.id.personName);

                                alertDialog = new AlertDialog.Builder(record.this).create();
                                alertDialog.setTitle("Display Record");
                                alertDialog.setView(alertLayout);
                                cityName.setText(station_name);
                                person_Name.setText(personName);

                               // alertDialog.setMessage(s);
                                alertDialog.show();
                               data.append(station_name+" "+personName);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(record.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
        mQueue.add(jsonObjectRequest);
    }
}
