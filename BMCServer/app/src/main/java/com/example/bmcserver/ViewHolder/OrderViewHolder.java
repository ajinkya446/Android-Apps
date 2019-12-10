package com.example.bmcserver.ViewHolder;

import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bmcserver.Common.Common;
import com.example.bmcserver.Interface.ItemClickListener;
import com.example.bmcserver.R;


public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {


    public TextView orderid,orderstatus,orderphone,orderaddress;

    private ItemClickListener itemClickListener;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);

        orderid= (TextView)itemView.findViewById(R.id.OrderId);
        orderstatus= (TextView)itemView.findViewById(R.id.order_status);
        orderphone= (TextView)itemView.findViewById(R.id.order_phone);
        orderaddress= (TextView)itemView.findViewById(R.id.order_address);

        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.setHeaderTitle("Select Action");

        contextMenu.add(0,0,getAdapterPosition(), Common.UPDATE);
        contextMenu.add(0,1,getAdapterPosition(), Common.DELETE);

    }
}
