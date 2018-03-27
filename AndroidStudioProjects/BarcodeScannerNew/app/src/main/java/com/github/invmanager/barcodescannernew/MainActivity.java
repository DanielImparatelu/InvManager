package com.github.invmanager.barcodescannernew;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.Formatter;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * Created by Daniel on 08/02/2018.
 */

public class MainActivity extends Activity {

    public static String codeFormat,codeContent;
    TextView barcodeResult, formatTxt;
    static TextView description;
    MediaPlayer mp;
    static String ip;

    public final int CUSTOMIZED_REQUEST_CODE = 0x0000ffff;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //barcodeResult = (TextView) findViewById(R.id.barcode_result);
        formatTxt = (TextView)findViewById(R.id.scan_format);
        barcodeResult = (TextView)findViewById(R.id.barcode_result);
        description = (TextView)findViewById(R.id.description);
        getIP();
    }

    private String getIP() {
        //https://stackoverflow.com/questions/16730711/get-my-wifi-ip-address-android
        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());//deprecated, but still kind of works so that's good enough
        return ip;
    }

    public void getJson(View v) throws Exception{
        v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);//fix DeadObjectException bug in android 6
        //from https://stackoverflow.com/questions/33128039/android-6-0-marshmallow-weird-error-with-fragment-animation

        JSONResult3 json = new JSONResult3();
        json.execute();

    }

    public void addItems(View v){

        Intent intent = new Intent(this, ScanContinuous.class);
        startActivity(intent);
//        IntentIntegrator integrator = new IntentIntegrator(this);
//        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
//        integrator.setBeepEnabled(false);
//        //integrator.setPrompt(this.getString(R.string.scan_bar_code));
//        integrator.setCaptureActivity(ScanContinuous.class);
//        integrator.setOrientationLocked(false);
//        //integrator.setResultDisplayDuration(0);
//        integrator.setCameraId(0);  // Use a specific camera of the device
//        integrator.setBarcodeImageEnabled(true);
//        // set turn the camera flash on by default
//        // integrator.addExtra(com.github.invmanager.barcodescannernew.appConstants.CAMERA_FLASH_ON,true);
//        integrator.initiateScan();
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
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setBeepEnabled(false);//disable the default beep, because it's annoying

        integrator.setCaptureActivity(ScanBarcodeActivity.class);//set the ScanBarcodeActivity class as the capture activity
        integrator.setOrientationLocked(false);

        integrator.setCameraId(0);  // Use a specific camera of the device, in this case the back camera
        integrator.setBarcodeImageEnabled(true);

        integrator.initiateScan();
    }

    //override onActivityResult to get barcode from the scan activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (requestCode != CUSTOMIZED_REQUEST_CODE && requestCode != IntentIntegrator.REQUEST_CODE) {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, intent);
            return;
        }
        switch (requestCode) {
            case CUSTOMIZED_REQUEST_CODE: {
                Toast.makeText(this, "REQUEST_CODE = " + requestCode, Toast.LENGTH_LONG).show();
                break;
            }
            default:
                break;
        }
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
                JSONResult3 jsonResult = new JSONResult3();
                jsonResult.execute();

                SendTask sendTask = new SendTask(this);//create the object to send the scanned results
                sendTask.execute(codeContent);//execute the method giving it the result of the barcode as parameter to send to server

            }else{
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(getApplicationContext(),"No scan data received!", Toast.LENGTH_SHORT).show();

        }
    }
}
