package com.ajinkya.majigold;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ajinkya.majigold.Adapter.ListAdapter;
import com.ajinkya.majigold.DatabaseHelper.DbHelper;
import com.ajinkya.majigold.Model.Billing;

import java.util.ArrayList;
import java.util.List;

public class UpdateBills extends AppCompatActivity {
    DbHelper dbHelper;
    private AppCompatEditText editTextCustomerName;
    private AppCompatButton buttonSearch;
    RecyclerView recyclerViewList;
    SwipeRefreshLayout mSwipeRefreshLayout;
    List<Billing> listBillng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_bills);

        mSwipeRefreshLayout =findViewById(R.id.swipeRefreshMissing);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listBillng.clear();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        dbHelper = new DbHelper(this);
        buttonSearch = findViewById(R.id.searchBill);
        editTextCustomerName = findViewById(R.id.updateCustomerName);
        recyclerViewList = findViewById(R.id.recyclerViewUpdate);

        recyclerViewList.setHasFixedSize(true);
        recyclerViewList.setLayoutManager(new LinearLayoutManager(this));
        listBillng = new ArrayList<>();

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextCustomerName.getText().toString().isEmpty()) {
                    editTextCustomerName.setError("Please Enter Customer Name first");
                }
                else {
                    listBillng.clear();
                    SQLiteDatabase database = dbHelper.getReadableDatabase();
                    String name = editTextCustomerName.getText().toString();
                    if (database != null) {
                        Cursor cursor = database.rawQuery("SELECT DISTINCT billing.b_id,billing.c_id," +
                                "billing.date,billing.order_name,billing.bill_status,billing.remainingAmt," +
                                "billing.reparingAmt,billing.total_ammount,billing.weight,customer.cname," +
                                "customer.caddr,customer.contact FROM billing,customer " +
                                "where billing.c_id=customer.c_id AND billing.bill_status='Pending' AND customer.cname LIKE'%" + name + "%'", null);
                        StringBuffer buffer = new StringBuffer();
                        if (cursor.getCount() == 0) {
                            Toast.makeText(UpdateBills.this, "No Customer Details Available", Toast.LENGTH_SHORT).show();
                        } else {
                            while (cursor.moveToNext()) {
                                buffer.append("cname" + cursor.getString(10));
                                buffer.append("date" + cursor.getString(2));
                                buffer.append("bill_status" + cursor.getString(4));
                                buffer.append("remainingAmt" + cursor.getString(6));
                                listBillng.add(new Billing(cursor.getInt(0), cursor.getString(1),
                                        cursor.getString(2), cursor.getString(3),
                                        cursor.getString(4), cursor.getString(5),
                                        cursor.getString(6), cursor.getString(7),
                                        cursor.getString(8), cursor.getString(9),
                                        cursor.getString(10), cursor.getString(11)
                                ));
                            }
                        }
                    }
                    ListAdapter adapter = new ListAdapter(UpdateBills.this, listBillng);
                    recyclerViewList.setAdapter(adapter);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(UpdateBills.this, Home.class));
    }
}
