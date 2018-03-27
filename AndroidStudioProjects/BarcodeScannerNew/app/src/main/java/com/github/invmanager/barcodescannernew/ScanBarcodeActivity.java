package com.github.invmanager.barcodescannernew;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;

import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.camera.CameraSettings;

/**
 * Created by Daniel on 15/02/2018.
 * Single item scanning class
 */

public class ScanBarcodeActivity extends Activity {

    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;
    private View turnflashOn, turnflashOff;
    CameraSettings settings;
    private boolean cameraFlashOn = false;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        barcodeScannerView = initializeContent();
        TorchEventListener torchEventListener = new TorchEventListener(this);
        barcodeScannerView.setTorchListener(torchEventListener);
        barcodeScannerView.setHapticFeedbackEnabled(true);
        turnflashOn = findViewById(R.id.switch_flashlight_on);
        turnflashOff = findViewById(R.id.switch_flashlight_off);

        settings = new CameraSettings();
        settings.setFocusMode(CameraSettings.FocusMode.INFINITY);//set focus mode
        // turn the flash on if set via intent
        Intent scanIntent = getIntent();
        if(scanIntent.hasExtra(appConstants.CAMERA_FLASH_ON)){//get the camera flash on const
            if(scanIntent.getBooleanExtra(appConstants.CAMERA_FLASH_ON,false)){//and check if the flash is off
                barcodeScannerView.setTorchOn();//and set it to on
                updateView();//update view interchanges the flash on and off buttons
            }
        }

        capture = new CaptureManager(this, barcodeScannerView);//instantiate the zxing capture manager intent
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();//starts scanning
    }


    /**
     * Override to use a different layout.
     *
     * @return the DecoratedBarcodeView
     */
    protected DecoratedBarcodeView initializeContent() {
        setContentView(R.layout.capture_flash);
        //setContentView(com.google.zxing.client.android.R.layout.zxing_capture);
        return (DecoratedBarcodeView)findViewById(com.google.zxing.client.android.R.id.zxing_barcode_scanner);
    }

    //handlers for when the user exits the app either by pressing home or closing it
    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        capture.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    public void toggleFlash(View view){
        if(cameraFlashOn){
            barcodeScannerView.setTorchOff();
        }else{
            barcodeScannerView.setTorchOn();
        }
    }

    public void updateView(){
        if(cameraFlashOn){
            turnflashOn.setVisibility(View.GONE);
            turnflashOff.setVisibility(View.VISIBLE);
        }else{
            turnflashOn.setVisibility(View.VISIBLE);
            turnflashOff.setVisibility(View.GONE);
        }
    }

    class TorchEventListener implements DecoratedBarcodeView.TorchListener{
        private ScanBarcodeActivity activity;

        TorchEventListener(ScanBarcodeActivity activity){
            this.activity = activity;
        }

        @Override
        public void onTorchOn() {
            this.activity.cameraFlashOn = true;
            this.activity.updateView();
        }

        @Override
        public void onTorchOff() {
            this.activity.cameraFlashOn = false;
            this.activity.updateView();
        }
    }
}
