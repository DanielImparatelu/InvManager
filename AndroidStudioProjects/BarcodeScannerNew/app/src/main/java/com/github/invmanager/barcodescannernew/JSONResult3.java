package com.github.invmanager.barcodescannernew;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;

import javax.xml.datatype.Duration;

/**
 * Created by Daniel on 14/03/2018.
 */

public class JSONResult3 extends AsyncTask<Void,Void,Void> {
    String data;
    String parsedData = "";
    String nullPrefix = "null";
    String scanResult = MainActivity.codeContent;
    @Override
    protected Void doInBackground(Void... params){
      //  5000167081695
        try{
            URL url = new URL("https://api.upcdatabase.org/product/"+scanResult+"/124C858366989F5CF18364FBE4561210");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line!=null){
                line = reader.readLine();
                data = data + line;
            }
            if(data.startsWith(nullPrefix)){
                data = data.substring(nullPrefix.length(), data.length());
            }
            JSONObject obj = new JSONObject(data);
            parsedData = obj.get("description").toString();
          //  SendTask sendTask = new SendTask(this);
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            MainActivity.description.setText("Product not found");
            e.printStackTrace();
        } catch (JSONException e) {
            MainActivity.description.setText("JSON problem");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);
        SendTask sendTask = new SendTask(this);
        sendTask.execute(this.parsedData);

        MainActivity.description.setText(this.parsedData);
    }
}
