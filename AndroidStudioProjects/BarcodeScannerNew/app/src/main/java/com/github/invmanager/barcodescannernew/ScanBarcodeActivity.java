package com.github.invmanager.barcodescannernew;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

/**
 * Created by Daniel on 08/02/2018.
 */

public class ScanBarcodeActivity extends Activity {

    SurfaceView cameraPreview;
    private static final int MY_PERMISSION_REQUEST_CAMERA = 2569;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_barcode);
        cameraPreview = (SurfaceView) findViewById(R.id.camera_preview);
        createCameraSource();
    }

    private void createCameraSource(){

        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this).build();
        //building CameraSource and BarcodeDetector objects
        final CameraSource cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1600, 1024)//used to scan smaller barcodes or from a distance
                .build();

        /* add callback  to SurfaceViewHolder to start and stop the cameraSource */
        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {//on create method

                if(ActivityCompat.checkSelfPermission(ScanBarcodeActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){//check if the app has camera access permissions
                    ActivityCompat.requestPermissions(ScanBarcodeActivity.this, new String[]{Manifest.permission.CAMERA},
                            MY_PERMISSION_REQUEST_CAMERA);//if it doesn't it will ask the user to grant the permission
                    return;
                }
                try {
                    cameraSource.start(cameraPreview.getHolder());//start camera
                    Toast toast = Toast.makeText(getApplicationContext(),"Position barcode in front of the camera",Toast.LENGTH_SHORT); toast.setMargin(50,50);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) { //on close method

                cameraSource.stop();
            }
        });
        //set barcode detector's processor to get detected barcodes
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                //store the detected values in an array
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                //after detection sends it to the main activity
                if(barcodes.size()>0){
                    Intent intent = new Intent();
                    intent.putExtra("barcode", barcodes.valueAt(0));//gets only the latest barcode
                    setResult(CommonStatusCodes.SUCCESS, intent);
                    //finish the activity and return to the main one
                    finish();
                }
            }
        });
    }
}
