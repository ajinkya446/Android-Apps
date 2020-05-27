package com.ajinkya.missingir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.ajinkya.missingir.Model.addDetails;

import static com.ajinkya.missingir.Credential.stringsDesignation;
import static com.ajinkya.missingir.Credential.stringsGender;

public class register extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private AppCompatButton appCompatButtonCreate, appCompatButtonCancel;
    private AppCompatEditText rname, username1, pass1, mobile, dob, email;
    private AppCompatSpinner appCompatSpinnerGender, appCompatSpinnerDeignation;
    private ArrayAdapter<String> aa;
    Context context;
    String item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initiate();

        appCompatSpinnerGender.setOnItemSelectedListener(register.this);
        aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stringsGender);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        appCompatSpinnerGender.setAdapter(aa);

        appCompatSpinnerDeignation.setOnItemSelectedListener(register.this);
        aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stringsDesignation);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        appCompatSpinnerDeignation.setAdapter(aa);

        appCompatButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(register.this, MainActivity.class));
            }
        });


        appCompatButtonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDetails add = new addDetails(register.this);
                add.execute("insert", rname.getText().toString(), username1.getText().toString(),
                        email.getText().toString(), dob.getText().toString(),
                        appCompatSpinnerGender.getSelectedItem().toString(),
                        appCompatSpinnerDeignation.getSelectedItem().toString(),
                        mobile.getText().toString(), pass1.getText().toString());

                clearField();

            }
        });

    }

    private void clearField() {
        rname.setText("");
        username1.setText("");
        email.setText("");
        dob.setText("");
        mobile.setText("");
        pass1.setText("");
    }

    private void initiate() {
        appCompatButtonCreate = findViewById(R.id.signup_user);
        rname = findViewById(R.id.rname);
        username1 = findViewById(R.id.userName);
        context = getApplicationContext();
        email = findViewById(R.id.email);
        dob = findViewById(R.id.dob);
        mobile = findViewById(R.id.phone);
        pass1 = findViewById(R.id.pass1);
        appCompatButtonCancel = findViewById(R.id.back);
        appCompatSpinnerGender = findViewById(R.id.gender);
        appCompatSpinnerDeignation = findViewById(R.id.designation);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = parent.getItemAtPosition(position).toString();
        // Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
