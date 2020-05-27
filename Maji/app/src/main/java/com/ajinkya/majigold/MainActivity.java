package com.ajinkya.majigold;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private Button buttonLogin;
    private EditText editTextUserName, editTextPassword;
    private sharedPreference shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initiate();
        shared = new sharedPreference(getApplicationContext());

        if (shared.readLoginStatus()) {
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            finish();
        }
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextUserName.getText().toString().equals("Sudip") && editTextPassword.getText().toString().equals("GoldenSudip")) {
                    shared.writeLoginStatus(true);
                    startActivity(new Intent(MainActivity.this, Home.class));
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Incorrect Username & Password");
                    builder.setTitle("Login Status..");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            editTextUserName.setText("");
                            editTextPassword.setText("");
                        }
                    });
                    builder.show();
                }
            }
        });


    }

    private void initiate() {
        buttonLogin = findViewById(R.id.Login);
        editTextUserName = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.password);
    }
}
