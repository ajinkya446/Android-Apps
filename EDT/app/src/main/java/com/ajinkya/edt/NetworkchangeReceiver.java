package com.ajinkya.edt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class NetworkchangeReceiver extends BroadcastReceiver {
    public static final String NETWORK_AVAILABLE_ACTION = "com.ajinkya.NetworkAvailable";
    public static final String ISNETWORKAVAILABLE="isNetworkAvailable";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent networkstateIntent=new Intent(NETWORK_AVAILABLE_ACTION);
        networkstateIntent.putExtra(ISNETWORKAVAILABLE,isConnectedToInternet(context));
        LocalBroadcastManager.getInstance(context).sendBroadcast(networkstateIntent);

    }

    private boolean isConnectedToInternet(Context context) {
        try {
            if(context!=null){
                ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
                return  networkInfo!=null&& networkInfo.isConnected();
            }
            return false;
        }catch (Exception e){
            Log.e(NetworkchangeReceiver.class.getName(),e.getMessage());
            return false;
        }
    }
}
