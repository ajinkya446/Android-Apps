package com.ajinkya.addboat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    AppCompatEditText e1, e2, e3, e4, e5, e6, e7, e8, e9, e10;
    private String a, b, c, d, e, f, g, h, i, j;
    AppCompatButton save;
    AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        e1 = findViewById(R.id.oname);
        e2 = findViewById(R.id.lno);
        e3 = findViewById(R.id.type);
        e4 = findViewById(R.id.bno);
        e5 = findViewById(R.id.oaddr);
        e6 = findViewById(R.id.mno);
        e7 = findViewById(R.id.insuranceNo);
        e8 = findViewById(R.id.bname);
        e9 = findViewById(R.id.society);
        e10 = findViewById(R.id.station);
        save = findViewById(R.id.save);

        a = e1.getText().toString();
        b = e2.getText().toString();
        c = e3.getText().toString();
        d = e4.getText().toString();
        e = e5.getText().toString();
        f = e6.getText().toString();
        g = e7.getText().toString();
        h = e8.getText().toString();
        i = e9.getText().toString();
        j = e10.getText().toString();
        //alertDialog= new AlertDialog.Builder(this);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<DefaultResponse> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .addBoat(a, e, f, b, c, d, i, h, g, j);

                call.enqueue(new Callback<DefaultResponse>() {
                    @Override
                    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {

                        if (response.body().toString().equals(false)) {
                           /* alertDialog = new AlertDialog.Builder(getApplicationContext());
                            alertDialog.setMessage("Registration Failed...")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    });*/
                           String s=response.body().toString();
                            Toast.makeText(MainActivity.this, "Failed"+s, Toast.LENGTH_SHORT).show();


                        } else  if (response.body().toString().equals(true)){
                            Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            /*alertDialog = new AlertDialog.Builder(getApplicationContext());
                            alertDialog.setMessage("Registration Success...")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    });
                            alertDialog.create();
                            alertDialog.setTitle("Boat Registration");
//                            alertDialog.show();*/
                            String s=response.body().toString();
                            Toast.makeText(MainActivity.this, "Success"+s, Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(MainActivity.this, ""+response.body().toString(), Toast.LENGTH_SHORT).show();
                            Log.d("Error","Error Occured");
                        }
                    }


                    @Override
                    public void onFailure(Call<DefaultResponse> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}
