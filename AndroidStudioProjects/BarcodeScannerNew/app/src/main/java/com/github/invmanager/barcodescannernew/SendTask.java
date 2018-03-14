package com.github.invmanager.barcodescannernew;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.format.Formatter;

import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.util.Enumeration;

import static android.content.Context.WIFI_SERVICE;


/**
 * Created by Daniel on 13/02/2018.
 */

public class SendTask extends AsyncTask<String, Void, Void> {

    JSONObject o = null;
    JSONResult3 jsonResult3;
    Socket s;
    PrintWriter pw;
    String message;
    Context c;
    Handler h = new Handler();

    SendTask(Context c)
    {
        this.c=c;
    }


    public SendTask(JSONResult3 jsonResult3) {
        this.jsonResult3 = jsonResult3;
    }

    //"192.168.1.8"
    @Override
    protected Void doInBackground(String... data) {

        message = data[0];
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    String ip = MainActivity.ip;
                   // InetAddress address = InetAddress.getByName(getLocalIpAddress());
                    s = new Socket(/*ip.toString()*/"192.168.1.8",7800);//initialises the socket with the IP and port
                    pw = new PrintWriter(s.getOutputStream());//creates a PrintWriter object to store the data sent
                    pw.write(message);//gets the message
                    pw.flush();//and sends it to the stream
                }

                catch (IOException e){
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally{
                    if(pw != null){
                        pw.close();
                    }
                }
             //   pw.close();//closed
            }
        }).start();



        return null;
    }





    private String getLocalIpAddress() throws Exception {
        String resultIpv6 = "";
        String resultIpv4 = "";

        for (Enumeration en = NetworkInterface.getNetworkInterfaces();
             en.hasMoreElements();) {

            NetworkInterface intf = (NetworkInterface) en.nextElement();
            for (Enumeration enumIpAddr = intf.getInetAddresses();
                 enumIpAddr.hasMoreElements();) {

                InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
                if(!inetAddress.isLoopbackAddress()){
                    if (inetAddress instanceof Inet4Address) {
                        resultIpv4 = inetAddress.getHostAddress().toString();
                    } else if (inetAddress instanceof Inet6Address) {
                        resultIpv6 = inetAddress.getHostAddress().toString();
                    }
                }
            }
        }
        MainActivity.description.setText(MainActivity.ip);
        return ((resultIpv4.length() > 0) ? resultIpv4 : resultIpv6);
    }

}
