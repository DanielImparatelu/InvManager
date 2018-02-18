package com.github.invmanager.barcodescannernew;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * Created by Daniel on 08/02/2018.
 */

public class MainActivity extends Activity {

    private String codeFormat,codeContent;
    TextView barcodeResult, formatTxt;
    MediaPlayer mp;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //barcodeResult = (TextView) findViewById(R.id.barcode_result);
        formatTxt = (TextView)findViewById(R.id.scan_format);
        barcodeResult = (TextView)findViewById(R.id.barcode_result);
    }

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
    //add event to the barcode scanner button

    public void loginScanBarcode(View v){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setBeepEnabled(false);
        //integrator.setPrompt(this.getString(R.string.scan_bar_code));
        integrator.setCaptureActivity(ScanBarcodeActivity.class);
        integrator.setOrientationLocked(false);
        //integrator.setResultDisplayDuration(0);
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBarcodeImageEnabled(true);
        // set turn the camera flash on by default
        // integrator.addExtra(com.github.invmanager.barcodescannernew.appConstants.CAMERA_FLASH_ON,true);
        integrator.initiateScan();
    }

    //override onActivityResult to get barcode from the scan activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null) {
            if(scanningResult.getContents() != null){
                //we have a result
                codeContent = scanningResult.getContents();
                codeFormat = scanningResult.getFormatName();
                playSound();//once we have the result play the beep

                // display it on screen
                formatTxt.setText("FORMAT: " + codeFormat);
                barcodeResult.setText("CONTENT: " + codeContent);

                SendTask sendTask = new SendTask(this);//create the object to send the scanned results
                sendTask.execute(codeContent);//execute the method giving it the result of the barcode as parameter to send to server

            }else{
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast toast = Toast.makeText(getApplicationContext(),"No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
