package com.ajinkya.majigold;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ajinkya.majigold.DatabaseHelper.DbHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class billsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    SwipeRefreshLayout mSwipeRefreshLayout;
    private ArrayAdapter<String> aa;
    private EditText editTextRepairingPrice, editTextTotalAmt, editTextCName, editTextCAddress,
            editTextContact, editTextWeight, editTextOrderAmt;
    String billStatus = "";
    String total = "";
    String reminingAmt = "";
    DbHelper db;
    AppCompatButton appCompatButtonSubmit, appCompatButtonCancel;
    AppCompatSpinner appCompatSpinnerOrder;
    ImageView img;
    String[] stringsOrder = {"Select", "Ring", "Necklace"};
    String[] stringsAmmount = {"Select", "Paid", "Remaining"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills);
        initialise();

        checkEmpty();

        editTextTotalAmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double d = Double.parseDouble(editTextRepairingPrice.getText().toString())
                        * Double.parseDouble(editTextWeight.getText().toString());
                total = Double.toString(Math.round(d));
                editTextTotalAmt.setText(total);
            }
        });

        //spinner order
        appCompatSpinnerOrder.setOnItemSelectedListener(this);
        aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stringsOrder);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        appCompatSpinnerOrder.setAdapter(aa);

        appCompatButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(billsActivity.this,Home.class));
            }
        });

        appCompatButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkEmpty();

                //Current Date.
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                String currentDateandTime = sdf.format(new Date());

                double remaining = Double.parseDouble(total) - Math.round(Double.parseDouble(editTextOrderAmt.getText().toString()));
                int remain = (int) remaining;
                reminingAmt = Double.toString(Math.round(remaining));
                if (remain == 0) {
                    billStatus = "Paid";
                } else {
                    billStatus = "Pending";
                }
                checkEmpty();

                if (appCompatSpinnerOrder.getSelectedItem().toString().equals("Select")) {
                    Toast.makeText(billsActivity.this, "Please Select Other Option first", Toast.LENGTH_SHORT).show();
                }
                int s=Integer.parseInt(editTextCName.getText().toString());
                boolean isInserted = db.insertBilling(currentDateandTime,s ,
                        appCompatSpinnerOrder.getSelectedItem().toString(), editTextWeight.getText().toString(),
                        editTextRepairingPrice.getText().toString(), billStatus, total, reminingAmt);
                if (isInserted = true) {
                    Toast.makeText(billsActivity.this, "New Bill Added Successfully", Toast.LENGTH_SHORT).show();
                    clearText();

                } else {
                    Toast.makeText(billsActivity.this, "Bill isn't Added", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Navigation item
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_Missing);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //SwipeRefreshLayout
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshMissing);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startActivity(new Intent(billsActivity.this, billsActivity.class));
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });


    }

    private void clearText() {
        editTextCName.setText("");
        appCompatSpinnerOrder.setSelection(0);
        editTextWeight.setText("");
        editTextRepairingPrice.setText("");
        editTextOrderAmt.setText("");
        editTextTotalAmt.setText("");
    }

    private void checkEmpty() {

        if (editTextCName.getText().toString().isEmpty()) {
            editTextCName.setError("Please Enter Id");
        }
        if (editTextOrderAmt.getText().toString().isEmpty()) {
            editTextOrderAmt.setError("Please Enter Order Ammount");
        }
        if (editTextWeight.getText().toString().isEmpty()) {
            editTextWeight.setError("Please Enter Weight of order");
        }
        if (editTextRepairingPrice.getText().toString().isEmpty()) {
            editTextRepairingPrice.setError("Please Enter Reparing Ammount");
        }
    }

    private void initialise() {
        appCompatSpinnerOrder = findViewById(R.id.spinnerOrder);
        editTextRepairingPrice = findViewById(R.id.repairePrice);
        editTextWeight = findViewById(R.id.orderWeight);
        editTextOrderAmt = findViewById(R.id.orderAmt);
        editTextTotalAmt = findViewById(R.id.totalAmt);
        editTextCName = findViewById(R.id.c_id);
        img = findViewById(R.id.imgOrder);
        appCompatButtonSubmit = findViewById(R.id.submit);
        appCompatButtonCancel = findViewById(R.id.cancelReport);


        db = new DbHelper(this);
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(billsActivity.this, Home.class));
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.Complaint_delete:
                    Intent intent2 = new Intent(billsActivity.this, UpdateBills.class);
                    startActivity(intent2);
                    return true;
                case R.id.complaint_add:
                    Intent intent = new Intent(billsActivity.this, checkStatus.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (appCompatSpinnerOrder.getSelectedItem().toString().equals("Ring")) {
            img.setImageResource(R.drawable.ic_wedding_ring);
        } else if (appCompatSpinnerOrder.getSelectedItem().toString().equals("Necklace")) {
            img.setImageResource(R.drawable.ic_jewelry);
        } else {

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
