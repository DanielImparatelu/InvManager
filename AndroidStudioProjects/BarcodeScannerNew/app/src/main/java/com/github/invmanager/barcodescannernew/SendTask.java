package com.github.invmanager.barcodescannernew;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
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
                    s = new Socket("192.168.1.8", 7800);//initialises the socket with the IP and port
                    pw = new PrintWriter(s.getOutputStream());//creates a PrintWriter object to store the data sent
                    pw.write(message);//gets the message
                    pw.flush();//and sends it to the stream
                }

                catch (IOException e){
                    e.printStackTrace();
                }
                pw.close();//closed
            }
        }).start();



        return null;
    }


}
