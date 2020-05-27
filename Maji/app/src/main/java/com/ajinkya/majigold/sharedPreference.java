package com.ajinkya.majigold;

import android.content.Context;
import android.content.SharedPreferences;

public class sharedPreference {
    private SharedPreferences sharedPreferences;
    private Context context;

    public sharedPreference(Context context) {
        sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.login_prefernce), Context.MODE_PRIVATE);
        this.context = context;
    }

    public void writeLoginStatus(boolean status) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(context.getResources().getString(R.string.login_status), status);
        edit.commit();
    }

    public boolean readLoginStatus() {
        boolean statusLog = false;
        statusLog = sharedPreferences.getBoolean(context.getResources().getString(R.string.login_status), false);
        return statusLog;
    }
}
