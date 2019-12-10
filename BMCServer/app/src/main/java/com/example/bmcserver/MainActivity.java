package com.example.bmcserver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bmcserver.Common.Common;
import com.example.bmcserver.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText edtpassword, edtname;
    Button l;
     FirebaseDatabase db;
     DatabaseReference signUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Server Login");
        edtpassword=(EditText)findViewById(R.id.passw1);
        edtname=(EditText)findViewById(R.id.cat_name);
        l=(Button)findViewById(R.id.login);

        db= FirebaseDatabase.getInstance();
        signUser=db.getReference("User");

        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignInUser(edtname.getText().toString(),edtpassword.getText().toString());
            }
        });
    }

    private void SignInUser(String name, String password) {
        final ProgressDialog m = new ProgressDialog(MainActivity.this);
        m.setMessage("Please Wait...");
        m.show();
        signUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(edtname.getText().toString()).exists()) {
                    final ProgressDialog m = new ProgressDialog(MainActivity.this);
                    m.dismiss();
                    User user = dataSnapshot.child(edtname.getText().toString()).getValue(User.class);
                    if(Boolean.parseBoolean(user.getIsStaff()))
                    {
                        if (user.getPassword().equals(edtpassword.getText().toString()))
                        {
                            Intent intent = new Intent(MainActivity.this, Home.class);
                           // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            Common.CurrentUser=user;
                            startActivity(intent);
                            finish();


                        }else
                            Toast.makeText(MainActivity.this,"Wrong Password",Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(MainActivity.this, "Please Login With Staff Account..", Toast.LENGTH_SHORT).show();
                    }
                }else
                {
                    Toast.makeText(MainActivity.this, "User Not Exists in Db...!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
