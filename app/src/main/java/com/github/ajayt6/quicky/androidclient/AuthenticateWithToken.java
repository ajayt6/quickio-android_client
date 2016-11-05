package com.github.ajayt6.quicky.androidclient;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import static android.content.ContentValues.TAG;

/**
 * Created by ajayt on 11/5/2016.
 */

public class AuthenticateWithToken extends AsyncTask<String, Void, Integer> {

    private Exception exception;

    protected Integer doInBackground(String... idToken) {

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://ec2-52-42-76-33.us-west-2.compute.amazonaws.com:3000/androidSigninVerificationPOST");

        Integer ret = 0;

        try {


            List nameValuePairs = new ArrayList(1);
            nameValuePairs.add(new BasicNameValuePair("idToken", idToken[0]));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            final String responseBody = EntityUtils.toString(response.getEntity());
            Log.i(TAG, "Signed in as: " + responseBody);
            ret = 1;
        } catch (ClientProtocolException e) {
            Log.e(TAG, "Error sending ID token to backend.", e);
        } catch (IOException e) {
            Log.e(TAG, "Error sending ID token to backend.", e);
        } catch (Exception e) {
            Log.e(TAG, "The exception is", e);
        }


        /*
        try{
            URL url = new URL("http://ec2-52-42-76-33.us-west-2.compute.amazonaws.com:3000/androidSigninVerificationPOST");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("idToken", idToken[0]);
                    //.appendQueryParameter("secondParam", paramValue2)
                    //.appendQueryParameter("thirdParam", paramValue3);
            String query = builder.build().getEncodedQuery();

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

            conn.connect();
            ret = 1;
        }
        catch (Exception e) {
            Log.e(TAG, "The exception is", e);
        }
        */


        return ret;
    }


    protected void onPostExecute(Long feed) {
        // TODO: check this.exception
        // TODO: do something with the feed
    }
}
