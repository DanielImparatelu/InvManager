package com.github.invmanager.barcodescannernew;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;


/**
 * Created by Daniel on 13/02/2018.
 */

public class SendTask extends AsyncTask<String, Void, Void> {

    Socket s;
    PrintWriter pw;
    String message;
    Context c;
    Handler h = new Handler();

    SendTask(Context c)
    {
        this.c=c;

    }

    @Override
    protected Void doInBackground(String... data) {


        message = data[0];
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    s = new Socket("192.168.1.8", 7800);
                    pw = new PrintWriter(s.getOutputStream());
                    pw.write(message);
                    pw.flush();
                }

                catch (IOException e){
                    e.printStackTrace();
                }
                pw.close();
            }
        }).start();


//        h.post(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(c,"Message sent",Toast.LENGTH_LONG).show();
//            }
//        });



        return null;
    }


}
