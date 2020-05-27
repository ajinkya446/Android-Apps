package com.ajinkya.majigold.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MajiGold.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("create table billing(b_id integer primary key autoincrement, date text,c_id int," +
                "order_name text,weight text,reparingAmt text,bill_status text," +
                "total_ammount text,remainingAmt text, foreign key(c_id) references customer(c_id))"
        );
        db.execSQL("create table customer(c_id integer primary key autoincrement, cname text," +
                "caddr text,contact text,dob text)"
        );

        db.execSQL("create table Daily_Price(id integer primary key autoincrement, date text," +
                "gold_price text,silver_price text)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS Daily_Price");
        db.execSQL("DROP TABLE IF EXISTS billing");
        db.execSQL("DROP TABLE IF EXISTS customer");
        onCreate(db);
    }

    public boolean insertContact(String date, String gold, String silver) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date", date);
        contentValues.put("gold_price", gold);
        contentValues.put("silver_price", silver);
        long result = db.insert("Daily_Price", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean UpdateData(int id, String status, String amtRemain) {
        SQLiteDatabase dba = this.getReadableDatabase();
        dba.execSQL("UPDATE billing SET remainingAmt =remainingAmt-'" + amtRemain + "', bill_status='" + status + "'WHERE b_id='" + id + "'");
        return true;
    }

    public boolean insertCustomer(String cname, String caddress, String contactCustomer, String dob) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("cname", cname);
        contentValues.put("caddr", caddress);
        contentValues.put("contact", contactCustomer);
        contentValues.put("dob", dob);
        long result = db.insert("customer", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insertBilling(String date, int c_id, String order_name, String weight, String reparingAmt, String bill_status, String total_ammount, String remainingAmt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date", date);
        contentValues.put("c_id", c_id);
        contentValues.put("order_name", order_name);
        contentValues.put("weight", weight);
        contentValues.put("reparingAmt", reparingAmt);
        contentValues.put("bill_status", bill_status);
        contentValues.put("total_ammount", total_ammount);
        contentValues.put("remainingAmt", remainingAmt);
        long result = db.insert("billing", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

}