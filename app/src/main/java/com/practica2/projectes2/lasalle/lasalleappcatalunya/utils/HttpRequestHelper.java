package com.practica2.projectes2.lasalle.lasalleappcatalunya.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequestHelper {

    private static HttpRequestHelper instance = null;
    private final int CONNECTION_TIMEOUT = 20000;
    private final int READ_TIMEOUT = 20000;

    private HttpRequestHelper(){}

    public static HttpRequestHelper getInstance() {
        if (instance == null) {
            instance = new HttpRequestHelper();
        }

        return instance;
    }

    public JSONObject doHttpRequest(String url, String method) {
        HttpURLConnection connection = null;
        JSONObject jsonObject = new JSONObject();

        try {
            URL u = new URL(url);
            connection = (HttpURLConnection) u.openConnection();
            connection.setRequestMethod(method);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setRequestProperty("Content-length", "0");
            connection.setUseCaches(false);
            connection.setAllowUserInteraction(false);
            connection.connect();
            int status = connection.getResponseCode();

            switch (status) {
                case HttpURLConnection.HTTP_OK:
                case HttpURLConnection.HTTP_CREATED:
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                    br.close();

                    jsonObject = new JSONObject(sb.toString());
            }

        } catch (Exception ex) {
            //Log.e(getClass().getName(), "Exception", ex);
        } finally {
            if (connection != null) {
                try {
                    connection.disconnect();
                } catch (Exception ex) {
                    Log.e(getClass().getName(), "Exception", ex);
                }
            }
        }

        return jsonObject;
    }


    public Bitmap doHttpRequestForBitmap (String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        HttpURLConnection connection  = (HttpURLConnection) url.openConnection();

        InputStream is = connection.getInputStream();
        Bitmap img = BitmapFactory.decodeStream(is);

        return img;
    }

}

