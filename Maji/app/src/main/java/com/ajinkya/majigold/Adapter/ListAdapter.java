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

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private Context mCtx;
    private List<Billing> billingList;
    DbHelper db;
    String st = "";

    public ListAdapter(Context mCtx, List<Billing> billingList) {
        this.mCtx = mCtx;
        this.billingList = billingList;
    }

    @NonNull
    @Override
    public ListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.billing_data, null);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ListViewHolder holder, int position) {
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
        AppCompatButton updateBills, cancelBills;
        Button b1;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.billDate);
            status = itemView.findViewById(R.id.status);
            ordername = itemView.findViewById(R.id.oname);
            db = new DbHelper(mCtx);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final TextView cid, c_name, c_addr, phone, bill_date, billType, billWeight, totalBill, amtRepair, amtRemain, status;
                    Dialog mydialog = new Dialog(mCtx);
                    mydialog.setContentView(R.layout.billing_details);
                    c_name = mydialog.findViewById(R.id.cust_billName);
                    cid = mydialog.findViewById(R.id.cust_billId);
                    c_addr = mydialog.findViewById(R.id.cust_billCustAddr);
                    phone = mydialog.findViewById(R.id.cust_billPhone);
                    bill_date = mydialog.findViewById(R.id.cust_billDate);
                    billType = mydialog.findViewById(R.id.cust_billType);
                    billWeight = mydialog.findViewById(R.id.cust_billWeight);
                    totalBill = mydialog.findViewById(R.id.cust_billTotal);
                    amtRepair = mydialog.findViewById(R.id.cust_billRepairAmt);
                    amtRemain = mydialog.findViewById(R.id.cust_billRemainAmt);
                    status = mydialog.findViewById(R.id.cust_billStatus);
                    updateBills = mydialog.findViewById(R.id.updateBillCustomer);
                    cancelBills = mydialog.findViewById(R.id.updateBillCancel);


                    updateBills.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Dialog dialog = new Dialog(mCtx);
                            dialog.setContentView(R.layout.update_amt_bill);
                            b1 = dialog.findViewById(R.id.updateAmount);
                            final AppCompatEditText amt = dialog.findViewById(R.id.amt_remain_update);

                            b1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    double amts = Double.parseDouble(amtRemain.getText().toString()) - Double.parseDouble(amt.getText().toString());

                                    if (amts == 0) {
                                        st = "Paid";
                                    } else if (amts > 0) {
                                        st = "Remaining";
                                    }
                                    boolean isUpdate = db.UpdateData(billingList.get(getAdapterPosition()).getB_id(),
                                            st, amt.getText().toString());
                                    if (isUpdate == true) {
                                        Toast.makeText(mCtx, "Updated Successfully", Toast.LENGTH_SHORT).show();
                                        amt.setText("");
                                        mCtx.startActivity(new Intent(mCtx,UpdateBills.class));
                                    } else {
                                        Toast.makeText(mCtx, "Can't Updated", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            dialog.show();
                        }
                    });

                    cid.setText(billingList.get(getAdapterPosition()).getC_id());
                    c_name.setText(billingList.get(getAdapterPosition()).getCname());
                    c_addr.setText(billingList.get(getAdapterPosition()).getCaddr());
                    phone.setText(billingList.get(getAdapterPosition()).getContact());
                    bill_date.setText(billingList.get(getAdapterPosition()).getDate());
                    billType.setText(billingList.get(getAdapterPosition()).getOrder_name());
                    billWeight.setText(billingList.get(getAdapterPosition()).getWeight());
                    totalBill.setText(billingList.get(getAdapterPosition()).getTotal_ammount());
                    amtRepair.setText(billingList.get(getAdapterPosition()).getRepairingAmt());
                    amtRemain.setText(billingList.get(getAdapterPosition()).getRemainingAmt());
                    status.setText(billingList.get(getAdapterPosition()).getBill_status());


                    mydialog.show();
                }
            });

        }
    }
}
