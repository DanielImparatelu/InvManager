package com.github.invmanager.barcodescannernew;

import android.os.AsyncTask;
import android.view.Gravity;

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
 * Created by Daniel on 14/03/2018.
 */

public class JSONResult3 extends AsyncTask<Void,Void,Void> {
    String data;
    String parsedData = "";
    String nullPrefix = "null";
    String scanResult = MainActivity.codeContent;
    String title = "";



    @Override
    protected Void doInBackground(Void... params){
      //  5000167081695
        /*
         * upcdatabase.org api code
         * it returns the inforamtion required as JSON objects, so it's quite straightforward
         */
        try{
            URL url = new URL("https://api.upcdatabase.org/product/"+scanResult+"/124C858366989F5CF18364FBE4561210");//create the URL
            //URL url = new URL ("https://api.upcitemdb.com/prod/trial/lookup?upc="+scanResult);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();//create the connection
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));//get returned data
            String line = "";
            while (line!=null){//if there is a result
                line = reader.readLine();//read it
                data = data + line;//and append it to a string
            }
            if(data.startsWith(nullPrefix)){//remove the null prefix usually returned by the api
                data = data.substring(nullPrefix.length(), data.length());
            }

            JSONObject obj = new JSONObject(data);//instantiate a JSON object
            title = obj.get("title").toString();
            parsedData = obj.get("description").toString();//and return the required value

        }
        catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid){//executes after the task has been completed
        super.onPostExecute(aVoid);

        if(parsedData.equals("")){//if theres no result
            if(title.equals("")){
                MainActivity.description.setText("\n\n\nSearching another database, please wait...");//show some text
                JSONResult4 tryAgain = new JSONResult4();
                tryAgain.execute();
            }

        }
        else{//if a product was found
            /*
             * The api returns information about a product in 2 name-value pairs: "title", and "description"
             * Sometimes one of those pairs is empty, while the other contains information
             * The following code checks both strings and if either is empty, it returns the other one
             * If both are empty, the code above calls the execute() method of the second JSON class
             */
            if(parsedData.equals("")){
                MainActivity.description.append(this.title);
                MainActivity.description.setGravity(Gravity.CENTER);
                SendTask sendTask = new SendTask(this);//instantiate the send class
                sendTask.execute(this.title);//send the result to socket
            }
            else if(title.equals("")){
                MainActivity.description.append(this.parsedData);
                MainActivity.description.setGravity(Gravity.CENTER_VERTICAL);
                SendTask sendTask = new SendTask(this);//instantiate the send class
                sendTask.execute(this.parsedData);//send the result to socket
            }

            MainActivity.description.setText(this.parsedData);
        }


    }
}
