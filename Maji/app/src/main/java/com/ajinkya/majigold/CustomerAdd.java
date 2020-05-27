package com.ajinkya.majigold;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ajinkya.majigold.DatabaseHelper.DbHelper;

public class CustomerAdd extends AppCompatActivity {
    private AppCompatEditText cname, caddr, contact, dob;
    private AppCompatButton submit, cancel;
    DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_add);

        initiate();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = db.insertCustomer(cname.getText().toString(), caddr.getText().toString(),
                        contact.getText().toString(), dob.getText().toString());
                if (isInserted = true) {
                    Toast.makeText(CustomerAdd.this, "Customer Details Added Successfully", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(CustomerAdd.this, "Customer details isn't Accepted", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerAdd.this, Home.class));
            }
        });
    }

    private void initiate() {
        cname = findViewById(R.id.cust_name);
        caddr = findViewById(R.id.cust_address);
        contact = findViewById(R.id.cust_contact);
        dob = findViewById(R.id.cust_dob);
        submit = findViewById(R.id.submit_customer);
        cancel = findViewById(R.id.cancelCustomer);
        db = new DbHelper(this);
    }
}
