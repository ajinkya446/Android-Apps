package com.ajinkya.missingir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ajinkya.missingir.Model.sharedPreference;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;

public class Home extends AppCompatActivity {
    private ActionBar toolbar;
    private sharedPreference preference;
    private ImageView imgSignOut;
    private TextView username;
    private CardView cardViewAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = getSupportActionBar();

        imgSignOut = findViewById(R.id.signOut);
        username = findViewById(R.id.name);
        cardViewAdd = findViewById(R.id.addNew);
        Intent intent = getIntent();
        String uname = intent.getStringExtra("Username");
        //username.setText("Missing Registery.");

        cardViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, AddData.class));
            }
        });


        preference = new sharedPreference(getApplicationContext());

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        imgSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preference.writeLoginStatus(false);
                startActivity(new Intent(Home.this, MainActivity.class));
                finish();
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;

            switch (item.getItemId()) {
                case R.id.navigation_shop:
                    toolbar.setTitle("Update Data");
                    return true;
                case R.id.navigation_search:
                    toolbar.setTitle("Search");
                    return true;
                case R.id.navigation_complaint:
                    Intent intent=new Intent(Home.this,record.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }
    };
}
