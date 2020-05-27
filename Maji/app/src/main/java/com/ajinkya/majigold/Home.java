package com.ajinkya.majigold;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ajinkya.majigold.DatabaseHelper.DbHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Home extends AppCompatActivity {
    private sharedPreference preference;
    private CardView cardViewBill, cardCustomer;
    DbHelper db;
    FloatingActionButton Signout;
    EditText editTextGold, editTextSilver;
    AppCompatButton appCompatButtonCancel, appCompatButtonUpdatePrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initialize();

        cardCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, CustomerAdd.class));
            }
        });

        cardViewBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, billsActivity.class));
            }
        });

        final Dialog mydialog = new Dialog(this);
        mydialog.setContentView(R.layout.custom_popup);
        appCompatButtonCancel = mydialog.findViewById(R.id.cancelPrice);
        appCompatButtonUpdatePrice = mydialog.findViewById(R.id.updatePrice);
        editTextSilver = mydialog.findViewById(R.id.silver);
        editTextGold = mydialog.findViewById(R.id.gold);

        appCompatButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog.dismiss();
            }
        });

        appCompatButtonUpdatePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                String currentDateandTime = sdf.format(new Date());
                boolean isInserted = db.insertContact(currentDateandTime, editTextGold.getText().toString(),
                        editTextSilver.getText().toString());
                if (isInserted = true) {
                    Toast.makeText(Home.this, "Today's Price Added Successfully", Toast.LENGTH_SHORT).show();
                    editTextGold.setText("");
                    editTextSilver.setText("");
                    mydialog.dismiss();
                } else {
                    Toast.makeText(Home.this, "Price isn't Added", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mydialog.show();

        Signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preference.writeLoginStatus(false);
                startActivity(new Intent(Home.this, MainActivity.class));
                finish();
            }
        });
    }

    private void initialize() {
        preference = new sharedPreference(getApplicationContext());
        Signout = findViewById(R.id.signout);
        cardViewBill = findViewById(R.id.billCard);
        cardCustomer = findViewById(R.id.customerAdd);
        db = new DbHelper(this);

    }

}
