package com.practica2.projectes2.lasalle.lasalleappcatalunya.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

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

    public JSONObject doHttpRequest(String url, String method, @Nullable String name, @Nullable String address,
                                    @Nullable String province, @Nullable String[] type, @Nullable String description) {
        HttpURLConnection connection = null;
        JSONObject jsonObject = new JSONObject();

        if (method.equals("GET")) {
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
                Log.e(getClass().getName(), "Exception", ex);
            } finally {
                if (connection != null) {
                    try {
                        connection.disconnect();
                    } catch (Exception ex) {
                        Log.e(getClass().getName(), "Exception", ex);
                    }
                }
            }
        }

        if (method.equals("POST")){
            String auxType = type[0].concat(type[1]).concat(type[2]).concat(type[3]).concat(type[4]).concat(type[5]);
            try {
                URL u = new URL(url+"jjj");

                JSONObject postDataParams = new JSONObject();

                postDataParams.put("method", "addSchool");
                postDataParams.put("name", name);
                postDataParams.put("address", address);
                postDataParams.put("province", province);
                postDataParams.put("type", auxType);
                postDataParams.put("description", description);

                connection = (HttpURLConnection) u.openConnection();
                connection.setConnectTimeout(CONNECTION_TIMEOUT);
                connection.setReadTimeout(READ_TIMEOUT);

                connection.setRequestMethod("POST");
                connection.setRequestProperty("method", "addSchool");
                connection.setRequestProperty("name", name);
                connection.setRequestProperty("address", province);
                connection.setRequestProperty("province", province);
                connection.setRequestProperty("type", auxType);
                connection.setRequestProperty("description", description);

                connection.setDoOutput(true);

                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int status = connection.getResponseCode();

                //?
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
                Log.e(getClass().getName(), "Exception", ex);
            } finally {
                if (connection != null) {
                    try {
                        connection.disconnect();
                    } catch (Exception ex) {
                        Log.e(getClass().getName(), "Exception", ex);
                    }
                }
            }
        }

        return jsonObject;
    }



    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }

}

