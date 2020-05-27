package com.ajinkya.account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Varify extends AppCompatActivity {

    private String varifyId;
    private FirebaseAuth mAuth;
    private Button b2;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_varify);
        b2=(Button)findViewById(R.id.varify);

        mAuth=FirebaseAuth.getInstance();
        editText=(EditText)findViewById(R.id.v);

        String phone=getIntent().getStringExtra("phone");
        sendVarifyCode(phone);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String code=editText.getText().toString();
                if(code.isEmpty() || code.length()<0)
                {
                    editText.setError("Enter Code");
                    //editText.requestFocus();
                    return;
                }else
                {
                    Toast.makeText(Varify.this, "OTP Varified Successfully...", Toast.LENGTH_SHORT).show();
                }

                VarifyCode(code);
            }
        });

    }
    private void VarifyCode(String varify) {
        PhoneAuthCredential phoneAuthCredential= PhoneAuthProvider.getCredential(varifyId,varify);
        signInWithCredential(phoneAuthCredential);
    }

    private void signInWithCredential(PhoneAuthCredential phoneAuthCredential) {
        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(Varify.this, "varification Complete", Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(Varify.this, "Error Occured", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendVarifyCode(String phone) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phone,60, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD,mCallBack);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack=
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                    String code=phoneAuthCredential.getSmsCode();
                    if(code!=null){
                        VarifyCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {

                }
            };

}
