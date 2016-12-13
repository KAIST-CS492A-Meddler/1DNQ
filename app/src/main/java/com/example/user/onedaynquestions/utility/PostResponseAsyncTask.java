package com.example.user.onedaynquestions.utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.user.onedaynquestions.model.AsyncResponse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by selab-ymbaek on 12/6/2016.
 */

public class PostResponseAsyncTask extends AsyncTask<String, Void, String> {

//    private ProgressDialog progressDialog;

    private AsyncResponse delegate;
    private Context context;
    private HashMap<String, String> postData =
            new HashMap<String, String>();
    private String loadingMessage = "Loading...";


    public PostResponseAsyncTask(AsyncResponse delegate) {
        this.delegate = delegate;
        this.context = (Context)delegate;
    }


    public PostResponseAsyncTask(AsyncResponse delegate,
                                 HashMap<String, String> postData){

        this.delegate = delegate;
        this.context = (Context)delegate;
        this.postData = postData;
    }

    public PostResponseAsyncTask(AsyncResponse delegate, String loadingMessage){
        this.delegate = delegate;
        this.context = (Context)delegate;
        this.loadingMessage = loadingMessage;
    }

    public PostResponseAsyncTask(AsyncResponse delegate,
                                 HashMap<String, String> postData, String loadingMessage){

        this.delegate = delegate;
        this.context = (Context)delegate;
        this.postData = postData;
        this.loadingMessage = loadingMessage;
    }
    //End Constructor


    @Override
    protected void onPreExecute() {
//        progressDialog = new ProgressDialog(context);
//        progressDialog.setMessage(loadingMessage);
//        progressDialog.show();

        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... urls) {
        String result = "";

        for(int i = 0; i <= 0; i++) {
            result = invokePost(urls[i], postData);
        }

        return result;
//        return null;
    }


    public String invokePost(String requestURL, HashMap<String,
            String> postDataParams) {
        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                response="";

                Log.i("PostResponseAsyncTask", responseCode + "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }//invokePost

    private String getPostDataString(HashMap<String, String> params)
            throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));



//            if (entry.getValue().getClass().equals(String.class)) {
//                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
//            } else {
//                result.append(entry.getValue().toString());
//            }

//            else if (entry.getValue().getClass().equals(Integer.class)) {
//                result.append("" + entry.getValue());
//            } else if (entry.getValue().getClass().equals(Float.class)) {
//                result.append("" + entry.getValue());
//            }

        }

        return result.toString();
    }//getPostDataString

    @Override
    protected void onPostExecute(String result) {
//        if (progressDialog.isShowing()) {
//            progressDialog.dismiss();
//        }

        result = result.trim();

        delegate.processFinish(result);
//        super.onPostExecute(s);
    }

    //Getter & Setter

//    public ProgressDialog getProgressDialog() {
//        return progressDialog;
//    }
//
//    public void setProgressDialog(ProgressDialog progressDialog) {
//        this.progressDialog = progressDialog;
//    }

    public AsyncResponse getDelegate() {
        return delegate;
    }

    public void setDelegate(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public HashMap<String, String> getPostData() {
        return postData;
    }

    public void setPostData(HashMap<String, String> postData) {
        this.postData = postData;
    }

    public String getLoadingMessage() {
        return loadingMessage;
    }

    public void setLoadingMessage(String loadingMessage) {
        this.loadingMessage = loadingMessage;
    }
}
