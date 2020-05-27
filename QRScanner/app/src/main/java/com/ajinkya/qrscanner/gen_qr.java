package com.ajinkya.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class gen_qr extends AppCompatActivity {
    EditText t1;
    Button QR_gen,back;
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gen_qr);

        t1 = (EditText) findViewById(R.id.qr);
        QR_gen = (Button) findViewById(R.id.gen);
        back = (Button) findViewById(R.id.back);
        iv = (ImageView) findViewById(R.id.iv);

        QR_gen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = t1.getText().toString();
                if (t1.getText().toString() != null) {
                    try {
                        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                        BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 246, 232);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        iv.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Toast.makeText(gen_qr.this, "Empty Text", Toast.LENGTH_SHORT).show();
                }
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv.setImageBitmap(null);
                t1.setText("");
            }
        });

    }
}
