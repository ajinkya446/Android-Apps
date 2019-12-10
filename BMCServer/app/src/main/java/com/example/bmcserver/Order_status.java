package com.example.bmcserver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.bmcserver.Common.Common;
import com.example.bmcserver.Interface.ItemClickListener;
import com.example.bmcserver.Model.Request;
import com.example.bmcserver.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class Order_status extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request, OrderViewHolder> adapter;
    FirebaseDatabase db;
    DatabaseReference request;

    MaterialSpinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        db= FirebaseDatabase.getInstance();
        request=db.getReference("Requests");

        recyclerView = (RecyclerView)findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        loadOrders();
    }

    private void loadOrders() {
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                request
        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder orderViewHolder, final Request request, int i) {
                orderViewHolder.orderid.setText(adapter.getRef(i).getKey());
                orderViewHolder.orderstatus.setText(Common.ConertCodeToStatus(request.getStatus()));
                orderViewHolder.orderaddress.setText(request.getAddress());
                orderViewHolder.orderphone.setText(request.getPhone());

                orderViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent trackOrder = new Intent(Order_status.this,TrackingOrder.class);
                        Common.CurrentRequest = request;
                        startActivity(trackOrder);
                    }
                });
            }
        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getTitle().equals(Common.UPDATE))
        {
            showUpdateOrder(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));
        }else if(item.getTitle().equals(Common.DELETE))
        {
            deleteOrder(adapter.getRef(item.getOrder()).getKey());
        }
        return super.onContextItemSelected(item);
    }

    private void showUpdateOrder(String key, final Request item) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Order_status.this);
        alertDialog.setTitle("Update Order");
        alertDialog.setMessage("Please Choose Order");

        LayoutInflater layoutInflater = this.getLayoutInflater();
        final View view= layoutInflater.inflate(R.layout.update_order_layout,null);
        spinner = (MaterialSpinner)view.findViewById(R.id.statusSpinner);
        spinner.setItems("Placed","On My Way","Shipped");

        alertDialog.setView(view);

        final String localKey= key;

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                item.setStatus(String.valueOf(spinner.getSelectedIndex()));

                request.child(localKey).setValue(item);
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }

    private void deleteOrder(String key) {
        request.child(key).removeValue();
        Toast.makeText(this, "Order Deleted...!", Toast.LENGTH_SHORT).show();
    }
}
