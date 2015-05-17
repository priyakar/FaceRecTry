package com.facerecog.shahmalav.facerecognitionapp;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Malav on 5/16/2015.
 */
public class HttpPostAsync extends AsyncTask<String, Void, String> {

    //Declaration of the interface
    public HttpAsyncResponse responseInterface = null;


    /**
     * Push the image base64 string to the server as a post request
     * receive a response in JSON format from the http post.
     * Once the post request is complete send the result JSON to MainActivity
     */
    @Override
    protected String doInBackground(String... params) {

        BufferedReader inBuffer = null;
        // url for the post api
        String url = "http://54.152.252.216/api/find-face.php";
      //  String url = "http://52.11.86.64/api/find-face.php";
        String result = "fail";
        try {
            //create HTTP Post connection
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost request = new HttpPost(url);

            //Prepare parameters/Payload for Post request
            List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("image", params[0]));

            //Encode parameters
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
                    postParameters);

            //
            request.setEntity(formEntity);
            //Execute HTTP Post request and receive JSON response
            HttpResponse responseText = httpClient.execute(request);

            //Convert HTTPResponse to UTF-8 formatted String
            String json = EntityUtils.toString(responseText.getEntity(), "UTF-8");
            result=json;

            Log.e("TheJsonResult", json);

        } catch(Exception e) {
            // Get message of the exception
            result = e.getMessage();
        } finally {
            if (inBuffer != null) {
                try {
                    inBuffer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return  result;
    }

    /**
     * Called when the post request is executed. Grabs the response and pass it as page.
     * @param result
     */
    protected void onPostExecute(String result)
    {
        Log.e("CheckThisOUt : ", result);
        responseInterface.processFinish(result);
    }
}
