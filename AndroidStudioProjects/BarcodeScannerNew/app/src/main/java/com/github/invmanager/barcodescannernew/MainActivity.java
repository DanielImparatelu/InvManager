package com.github.invmanager.barcodescannernew;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Daniel on 08/02/2018.
 */

public class MainActivity extends Activity {

    TextView barcodeResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        barcodeResult = (TextView) findViewById(R.id.barcode_result);


    }
    //add event to the barcode scanner button

    public void loginScanBarcode(View v){
        Intent intent = new Intent(this, ScanBarcodeActivity.class);
        startActivityForResult(intent, 0);
    }

    //override onActivityResult to get barcode from the scan activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0){
            if(resultCode == CommonStatusCodes.SUCCESS){
                if(data != null){//check for barcode and display the value
                    Barcode barcode = data.getParcelableExtra("barcode");
                    barcodeResult.setText("Barcode value: "+barcode.displayValue);
                   // sendLoginResult(null);

                    SendTask sendTask = new SendTask(this);
                    sendTask.execute(barcode.displayValue);//gets the scanned barcode and sends its value through TCP/IP over to the server

                }
                else{
                    barcodeResult.setText("No barcode found");
                }
            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}
