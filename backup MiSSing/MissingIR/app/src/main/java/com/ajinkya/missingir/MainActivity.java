package com.ajinkya.missingir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ajinkya.missingir.Model.sharedPreference;

public class MainActivity extends AppCompatActivity {
    AppCompatEditText appCompatEditTextUser, appCompatEditTextPassword;
    AppCompatButton appCompatButtonLogin;
    private TextView register;
    private sharedPreference shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shared=new sharedPreference(getApplicationContext());
        register=findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,register.class));
            }
        });

        appCompatButtonLogin = findViewById(R.id.login);
        appCompatEditTextPassword = findViewById(R.id.pass);
        appCompatEditTextUser = findViewById(R.id.user);
//        Intent intent=getIntent();
//        intent.putExtra("Username",appCompatEditTextUser.getText().toString());
//

        //already logged in
        if (shared.readLoginStatus())
        {
            startActivity(new Intent(this,Home.class));
            finish();
        }


        appCompatButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = appCompatEditTextUser.getText().toString();
                String password = appCompatEditTextPassword.getText().toString();
                BackgroundWorker backgroundWorker = new BackgroundWorker(MainActivity.this);
                backgroundWorker.execute("login", name, password);

                shared.writeLoginStatus(true);


            }
        });
    }

}
