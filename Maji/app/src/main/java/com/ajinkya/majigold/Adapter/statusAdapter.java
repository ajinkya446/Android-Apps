/**/
package com.ajinkya.majigold.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.RecyclerView;

import com.ajinkya.majigold.DatabaseHelper.DbHelper;
import com.ajinkya.majigold.Model.Billing;
import com.ajinkya.majigold.R;
import com.ajinkya.majigold.UpdateBills;

import java.util.List;

public class statusAdapter extends RecyclerView.Adapter<statusAdapter.ListViewHolder> {

    private Context mCtx;
    private List<Billing> billingList;
    DbHelper db;
    String st = "";

    public statusAdapter(Context mCtx, List<Billing> billingList) {
        this.mCtx = mCtx;
        this.billingList = billingList;
    }

    @NonNull
    @Override
    public statusAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.billing_data, null);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull statusAdapter.ListViewHolder holder, int position) {
        final Billing missing = billingList.get(position);

        holder.name.setText(missing.getCname());
        holder.date.setText(missing.getDate());
        holder.status.setText(missing.getBill_status());
        holder.ordername.setText(missing.getOrder_name());
    }

    @Override
    public int getItemCount() {
        return billingList.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView name, date, status, ordername;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.billDate);
            status = itemView.findViewById(R.id.status);
            ordername = itemView.findViewById(R.id.oname);
        }
    }
}
