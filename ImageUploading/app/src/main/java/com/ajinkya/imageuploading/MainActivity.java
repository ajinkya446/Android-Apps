package com.ajinkya.imageuploading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.ajinkya.imageuploading.Database.DatabaseHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    Button buttonSave;
    ImageView imageViewImage;
    SQLiteDatabase db;
    Cursor cursor;
    String filePath ="";
    Uri uri;
    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper=new DatabaseHelper(this);
        buttonSave=findViewById(R.id.buttonSave);
        imageViewImage=findViewById(R.id.image);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String [] proj={MediaStore.Images.Media.DATA};
//                cursor = managedQuery( uri,
//                        proj, // Which columns to return
//                        null,       // WHERE clause; which rows to return (all rows)
//                        null,       // WHERE clause selection arguments (none)
//                        null); // Order-by clause (ascending by name)
//                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                if (cursor.moveToFirst()) {
//                     filePath = cursor.getString(column_index);
//                }

                boolean result=databaseHelper.insertData(uri.getPath());
                if(result==true)
                    Toast.makeText(MainActivity.this, "True", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "False", Toast.LENGTH_SHORT).show();

            }
        });



        imageViewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {

             uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                 imageViewImage =findViewById(R.id.image);

                imageViewImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
