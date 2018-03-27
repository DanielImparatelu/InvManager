package com.github.invmanager.barcodescannernew;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Daniel on 24/03/2018.
 */

public class JSONResult4 extends AsyncTask<Void, Void, Void>{

    /*
     * upcitemdb.com product api code
     * different from the previous api code as it returns the data in the form of a JSONArray
     * which needs to be parsed differently
     */
    String data;
    String parsedData = "";
    String nullPrefix = "null";//if the api returns a null value we get rid of that
    String scanResult = MainActivity.codeContent;
    String item = "";
    String shitPrefix = "code"+":"+"OK"+","+"total"+":1,"+"offset"+":0,"+"items"+":";//unnecessary prefix specific to this api


    @Override
    protected Void doInBackground(Void... voids) {

        try {
            URL url = new URL ("https://api.upcitemdb.com/prod/trial/lookup?upc="+scanResult);//link to the api

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();//create connection
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));//get returned data
            String line = "";

            while (line!=null){//if there is a value returned
                line = reader.readLine();//read it
                data = data + line;//and append it to our string
            }
            if(data.startsWith(shitPrefix)){//get rid of the unneeded prefix
                data = data.substring(shitPrefix.length(), data.length());
            }
            if(data.startsWith(nullPrefix)){//get rid of a null prefix if there is one
                data = data.substring(nullPrefix.length(), data.length());
            }

            JSONObject obj = new JSONObject(data);//create a new JSON object
            JSONArray arr = obj.getJSONArray("items");//get the JSON array named "items"
            for(int i=0;i<arr.length();i++)//loop through the array
            {
                JSONObject json = arr.getJSONObject(i);//and return the needed value(s)
                item = json.optString("title");//append it to a string

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);

        if(item.equals("")){//if theres no result
            MainActivity.description.append("\n\n\nProduct not found");//show some text
        }
        else{//if a product was found

            MainActivity.description.append("\n\n\n"+this.item);
            SendTask sendTask = new SendTask(this);
            sendTask.execute(this.item);//send the item title to the main Java program
        }
    }
}
