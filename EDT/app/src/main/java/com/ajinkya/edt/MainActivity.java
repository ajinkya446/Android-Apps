package com.ajinkya.edt;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import static com.ajinkya.edt.NetworkchangeReceiver.ISNETWORKAVAILABLE;

public class MainActivity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 7117;
    List<AuthUI.IdpConfig> provide;
    AlertDialog.Builder dialog;
    ImageView img;
    TextView txtName;
    Button btnSignOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSignOut=findViewById(R.id.signout);
        img=findViewById(R.id.imgUser);
        txtName=findViewById(R.id.name);
         dialog= new AlertDialog.Builder(this);
        IntentFilter intentFilter = new IntentFilter(NetworkchangeReceiver.NETWORK_AVAILABLE_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean isNetAvailable = intent.getBooleanExtra(ISNETWORKAVAILABLE, false);
                String NetStatus = isNetAvailable ? "Connected" : "Dis-Connected";
                Snackbar.make(findViewById(R.id.activity_main), "Network Status" + NetStatus, Snackbar.LENGTH_LONG).show();

            }
        }, intentFilter);

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance()
                        .signOut(MainActivity.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                btnSignOut.setEnabled(false);
                                Intent i= new Intent(MainActivity.this,Login.class);
                                startActivity(i);

                                showSignInOptions();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



        provide = Arrays.asList(
//                new AuthUI.IdpConfig.EmailBuilder().build(),    //Email Authentication
//                new AuthUI.IdpConfig.PhoneBuilder().build(),    //Phone Authentication
                new AuthUI.IdpConfig.FacebookBuilder().build(), //Fb Authentication
                new AuthUI.IdpConfig.GoogleBuilder().build()    //google Authentication
        );
        showSignInOptions();
    }

    @SuppressLint("ResourceType")
    public void showSignInOptions() {

        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(provide)
                        .setTheme(R.style.myTheme)
                        .build(), MY_REQUEST_CODE
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                //show email after login
                Toast.makeText(this, "" + user.getEmail(), Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                intent.putExtra("message", );
               // startActivity(intent);

                btnSignOut.setEnabled(true);
                img.setEnabled(true);
                txtName.setText(user.getEmail());
            } else {
                Toast.makeText(this, "" + response.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
