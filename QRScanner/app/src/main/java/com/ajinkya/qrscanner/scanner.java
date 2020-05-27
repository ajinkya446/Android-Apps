
package com.ajinkya.qrscanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.frame.Frame;
import com.otaliastudios.cameraview.frame.FrameProcessor;

import java.util.List;

public class scanner extends AppCompatActivity {
    CameraView cameraView;
    boolean isDetect=false;
    Button again;

    FirebaseVisionBarcodeDetectorOptions options;
    FirebaseVisionBarcodeDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        Dexter.withActivity(this)
                .withPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO})
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        setUpCamera();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                    }
                }).check();
    }

    private void setUpCamera()
    {
        again=(Button)findViewById(R.id.Scan_qr);
        again.setEnabled(isDetect);
        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDetect=!isDetect;
            }
        });

       cameraView=(CameraView)findViewById(R.id.camera);
       cameraView.setLifecycleOwner(this);
       cameraView.addFrameProcessor(new FrameProcessor() {
           @Override
           public void process(@NonNull Frame frame) {
               processImage(getVisionImageFromFrame(frame));
           }
       });
       options= new FirebaseVisionBarcodeDetectorOptions.Builder()
               .setBarcodeFormats(FirebaseVisionBarcode.FORMAT_QR_CODE)
               .build();
       detector=FirebaseVision.getInstance().getVisionBarcodeDetector(options);
    }

    private void processImage(FirebaseVisionImage image) {
        if(!isDetect)
        {
            detector.detectInImage(image)
                    .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionBarcode>>() {
                        @Override
                        public void onSuccess(List<FirebaseVisionBarcode> firebaseVisionBarcodes) {
                            processResult(firebaseVisionBarcodes);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }
    }

    private void processResult(List<FirebaseVisionBarcode> firebaseVisionBarcodes) {
        if(firebaseVisionBarcodes.size() > 0)
        {
            isDetect=true;
            again.setEnabled(isDetect);
            for (FirebaseVisionBarcode item:firebaseVisionBarcodes)
            {
                int value=item.getValueType();
                switch (value)
                {
                    case FirebaseVisionBarcode.TYPE_TEXT:
                    {
                        createDialog(item.getRawValue());
                    }
                    break;
                    case FirebaseVisionBarcode.TYPE_URL:
                    {
                        Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(item.getRawValue()));
                        startActivity(intent);
                    }
                    break;
                    case FirebaseVisionBarcode.TYPE_CONTACT_INFO:
                    {
                        String info= new StringBuilder("Name:")
                                .append(item.getContactInfo().getName().getFormattedName())
                                .append("\n")
                                .append("Address: ")
                                .append(item.getContactInfo().getAddresses().get(0).getAddressLines()[0])
                                .append("\n")
                                .append("Email: ")
                                .append(item.getContactInfo().getEmails().get(0).getAddress())
                                .toString();
                        createDialog(info);
                    }
                    break;
                    default:
                        break;
                }
            }
        }
    }

    private void createDialog(String rawValue) {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setMessage(rawValue)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog dialog= builder.create();
        builder.show();
    }

    private FirebaseVisionImage getVisionImageFromFrame(Frame frame) {
        byte[] data=frame.getData();
        FirebaseVisionImageMetadata metadata= new FirebaseVisionImageMetadata.Builder().
                setFormat(FirebaseVisionImageMetadata.IMAGE_FORMAT_NV21)
                .setHeight(frame.getSize().getHeight())
                .setWidth(frame.getSize().getWidth())
                .build();
               // .setRotation(frame.getRotation())
        return FirebaseVisionImage.fromByteArray(data,metadata);
    }
}
