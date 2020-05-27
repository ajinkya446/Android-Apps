package com.ajinkya.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button b1;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1=(Button)findViewById(R.id.Send);
        editText=(EditText)findViewById(R.id.phone);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number=editText.getText().toString();
                if(number.isEmpty() || number.length()<0)
                {
                    editText.setError("Invalied Number");
                    editText.requestFocus();
                    return;
                }

                String phonenumber="+91"+number;
                Intent intent= new Intent(MainActivity.this,Varify.class);
                intent.putExtra("phone",phonenumber);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            Intent intent=new Intent(this,MainActivity.class);
            intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
        }
    }
}
