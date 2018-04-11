package com.github.invmanager.barcodescannernew;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.DefaultDecoderFactory;
import com.journeyapps.barcodescanner.camera.CameraSettings;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;


/**
 * Created by Daniel on 06/03/2018.
 * Multiple item scanning class
 */

public class ScanContinuous extends CaptureActivity  {

    MediaPlayer mp;
    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;
    private View turnflashOn, turnflashOff;
    CameraSettings settings;
    private String lastText;
    private boolean cameraFlashOn = false;
    public SendTask sendTask = new SendTask(this);

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
//            if(result.getText() == null || result.getText().equals(lastText)) {
//                // Prevent duplicate scans
//                return;
//            }
            lastText = result.getText();
            barcodeScannerView.setStatusText(result.getText());

           // MainActivity.description.append(result.getText());
            MainActivity.codeContent = result.getText();
            JSONResult3 result3 = new JSONResult3();
            result3.execute();
           // MainActivity.description.append(result3.parsedData);
            barcodeScannerView.decodeContinuous(callback);

            playSound();
            //TODO
            //make it so it sends the continuous scanned data to the socket
            //sendTask.execute(result.getText());
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };

    private void playSound(){
        mp = MediaPlayer.create(this, R.raw.beep1);//initialise sound file from resources
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mp.release();
            }
        });//after completion change to end state
        mp.start();//play the sound
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        barcodeScannerView = initializeContent();
        ScanContinuous.TorchEventListener torchEventListener = new ScanContinuous.TorchEventListener(this);
        barcodeScannerView.setTorchListener(torchEventListener);
        barcodeScannerView.setHapticFeedbackEnabled(true);//doesn't work, need to use the provided BeepManager class which crashes the app because of an Android 6.0 bug
        turnflashOn = findViewById(R.id.switch_flashlight_on);
        turnflashOff = findViewById(R.id.switch_flashlight_off);

        settings = new CameraSettings();
        settings.setFocusMode(CameraSettings.FocusMode.MACRO);//set focus mode
        // turn the flash on if set via intent
        Intent scanIntent = getIntent();
        if(scanIntent.hasExtra(appConstants.CAMERA_FLASH_ON)){
            if(scanIntent.getBooleanExtra(appConstants.CAMERA_FLASH_ON,false)){
                barcodeScannerView.setTorchOn();
                updateView();
            }
        }
        Collection<BarcodeFormat> formats = Arrays.asList(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39);
        barcodeScannerView.getBarcodeView().setDecoderFactory(new DefaultDecoderFactory());
        barcodeScannerView.decodeContinuous(callback);

        capture = new CaptureManager(this, barcodeScannerView);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();
        triggerScan(barcodeScannerView);//this actually triggers the continuous scanning mode

    }

    /**
     * Override to use a different layout.
     *
     * @return the DecoratedBarcodeView
     */
    protected DecoratedBarcodeView initializeContent() {
        setContentView(R.layout.capture_continuous);
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

    public void pause(View view) {
        barcodeScannerView.pause();
    }

    public void resume(View view) {
        barcodeScannerView.resume();
    }

    public void triggerScan(View view) {
        barcodeScannerView.decodeContinuous(callback);
    }//method that triggers continuous scanning mode

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
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();//ends the scanning process when the back button is pressed
        }
        return barcodeScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    public void toggleFlash(View view){
        if(cameraFlashOn){
            barcodeScannerView.setTorchOff();
        }else{
            barcodeScannerView.setTorchOn();
        }
    }

    public void updateView(){//interchanges the flash on and flash off button icons when they are pressed
        if(cameraFlashOn){
            turnflashOn.setVisibility(View.GONE);
            turnflashOff.setVisibility(View.VISIBLE);
        }else{
            turnflashOn.setVisibility(View.VISIBLE);
            turnflashOff.setVisibility(View.GONE);
        }
    }

    class TorchEventListener implements DecoratedBarcodeView.TorchListener{
        private ScanContinuous activity;

        TorchEventListener(ScanContinuous activity){
            this.activity = activity;
        }

        @Override
        public void onTorchOn() {//turns flashlight on
            this.activity.cameraFlashOn = true;
            this.activity.updateView();
        }

        @Override
        public void onTorchOff() {//turns flashlight off
            this.activity.cameraFlashOn = false;
            this.activity.updateView();
        }
    }
}
