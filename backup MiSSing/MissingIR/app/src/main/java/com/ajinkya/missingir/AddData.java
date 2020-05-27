package com.ajinkya.missingir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.ajinkya.missingir.Model.addDetails;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

public class AddData extends AppCompatActivity {
    private AppCompatImageView photo;
    AppCompatSpinner gender;
    AppCompatButton submit,cancel;
    AppCompatEditText name,address,email,contactPeople,parentName,parentContact,dob;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        initialise();
    }

    private void initialise() {
        photo=findViewById(R.id.imagePeople);
        name=findViewById(R.id.namePeople);
        gender=findViewById(R.id.genderPeople);
        submit=findViewById(R.id.submit);
        cancel=findViewById(R.id.cancelData);
        address=findViewById(R.id.AddressPeople);
        email=findViewById(R.id.EmailPeople);
        parentContact=findViewById(R.id.parentMobile);
        parentName=findViewById(R.id.parentName);
        contactPeople=findViewById(R.id.mobilePeople);
        dob=findViewById(R.id.birthDate);


    }
}
