
package com.ajinkya.qrscanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.ImageFormat;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.frame.Frame;
import com.otaliastudios.cameraview.frame.FrameProcessor;

import java.util.List;

public class scanner extends AppCompatActivity {
    CameraView cameraView;
    boolean isDetect=false;
    Button again;

    BarcodeScannerOptions options;
    BarcodeScanner detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            setUpCamera();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
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
                again.setEnabled(isDetect);
            }
        });

       cameraView=(CameraView)findViewById(R.id.camera);
       cameraView.setLifecycleOwner(this);
       cameraView.addFrameProcessor(new FrameProcessor() {
           @Override
           public void process(@NonNull Frame frame) {
               processImage(getInputImageFromFrame(frame));
           }
       });
       options= new BarcodeScannerOptions.Builder()
               .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
               .build();
       detector = BarcodeScanning.getClient(options);
    }

    private void processImage(InputImage image) {
        if(!isDetect)
        {
            detector.process(image)
                    .addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                        @Override
                        public void onSuccess(List<Barcode> barcodes) {
                            processResult(barcodes);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }
    }

    private void processResult(List<Barcode> barcodes) {
        if(barcodes.size() > 0)
        {
            isDetect=true;
            again.setEnabled(isDetect);
            for (Barcode item : barcodes)
            {
                int value=item.getValueType();
                switch (value)
                {
                    case Barcode.TYPE_TEXT:
                    {
                        createDialog(item.getRawValue());
                    }
                    break;
                    case Barcode.TYPE_URL:
                    {
                        Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(item.getRawValue()));
                        startActivity(intent);
                    }
                    break;
                    case Barcode.TYPE_CONTACT_INFO:
                    {
                        Barcode.ContactInfo contactInfo = item.getContactInfo();
                        if (contactInfo != null) {
                            String info = new StringBuilder("Name:")
                                    .append(contactInfo.getName().getFormattedName())
                                    .append("\n")
                                    .append("Address: ")
                                    .append(contactInfo.getAddresses().get(0).getAddressLines()[0])
                                    .append("\n")
                                    .append("Email: ")
                                    .append(contactInfo.getEmails().get(0).getAddress())
                                    .toString();
                            createDialog(info);
                        }
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
        builder.show();
    }

    private InputImage getInputImageFromFrame(Frame frame) {
        byte[] data = frame.getData();
        return InputImage.fromByteArray(
                data,
                frame.getSize().getWidth(),
                frame.getSize().getHeight(),
                frame.getRotationToUser(),
                ImageFormat.NV21
        );
    }
}
