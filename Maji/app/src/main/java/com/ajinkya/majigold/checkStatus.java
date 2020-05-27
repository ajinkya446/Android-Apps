package com.ajinkya.majigold;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.ajinkya.majigold.Adapter.ListAdapter;
import com.ajinkya.majigold.Adapter.statusAdapter;
import com.ajinkya.majigold.DatabaseHelper.DbHelper;
import com.ajinkya.majigold.Model.Billing;

import java.util.ArrayList;
import java.util.List;

public class checkStatus extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DbHelper dbHelper;
    private AppCompatEditText editTextCustomerName;
    private AppCompatButton buttonSearch;
    RecyclerView recyclerViewList;
    AppCompatSpinner spinStatus;
    SwipeRefreshLayout mSwipeRefreshLayout;
    List<Billing> listStatus;
    String[] stringsOrder = {"Select", "Pending", "Paid"};
    private ArrayAdapter<String> aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_status);

        spinStatus = findViewById(R.id.spinnerStatus);

        spinStatus.setOnItemSelectedListener(checkStatus.this);
        aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stringsOrder);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinStatus.setAdapter(aa);

        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshStatus);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listStatus.clear();
                fetchData();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        dbHelper = new DbHelper(this);
        buttonSearch = findViewById(R.id.searchStatus);
        editTextCustomerName = findViewById(R.id.checkCustomerName);
        recyclerViewList = findViewById(R.id.recyclerViewStatus);

        recyclerViewList.setHasFixedSize(true);
        recyclerViewList.setLayoutManager(new LinearLayoutManager(this));
        listStatus = new ArrayList<>();

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextCustomerName.getText().toString().isEmpty()) {
                    editTextCustomerName.setError("Please Enter Customer Name first");
                } else {
                    listStatus.clear();
                    fetchData();
                }
            }
        });

    }

    private void fetchData() {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String name = editTextCustomerName.getText().toString();
        if (database != null) {
            Cursor cursor = database.rawQuery("SELECT DISTINCT billing.b_id,billing.c_id," +
                    "billing.date,billing.order_name,billing.bill_status,billing.remainingAmt," +
                    "billing.reparingAmt,billing.total_ammount,billing.weight,customer.cname," +
                    "customer.caddr,customer.contact FROM billing,customer " +
                    "where billing.c_id=customer.c_id AND billing.bill_status='" + spinStatus.getSelectedItem().toString() + "'" +
                    " AND customer.cname LIKE'%" + name + "%'", null);
            StringBuffer buffer = new StringBuffer();
            if (cursor.getCount() == 0) {
                Toast.makeText(checkStatus.this, "No Customer Details Available", Toast.LENGTH_SHORT).show();
            } else {
                while (cursor.moveToNext()) {
                    buffer.append("cname" + cursor.getString(10));
                    buffer.append("date" + cursor.getString(2));
                    buffer.append("bill_status" + cursor.getString(4));
                    buffer.append("remainingAmt" + cursor.getString(6));
                    listStatus.add(new Billing(cursor.getInt(0), cursor.getString(1),
                            cursor.getString(2), cursor.getString(3),
                            cursor.getString(4), cursor.getString(5),
                            cursor.getString(6), cursor.getString(7),
                            cursor.getString(8), cursor.getString(9),
                            cursor.getString(10), cursor.getString(11)
                    ));
                }
            }
        }
        statusAdapter adapter = new statusAdapter(checkStatus.this, listStatus);
        recyclerViewList.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(checkStatus.this, Home.class));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
