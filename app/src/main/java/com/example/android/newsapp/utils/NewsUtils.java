package com.example.android.newsapp.utils;

import android.util.Log;

import com.example.android.newsapp.model.News;

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
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class NewsUtils {

    private static final int READ_TIMEOUT = 10000;
    private static final int CONNECT_TIMEOUT = 10000;
    private static final String REQUEST_METHO = "GET";

    private static final int RESPONSE_CODE = 200;

    private static final String CHARSET_DATA = "UTF-8";
    private static final String LOG_TAG = "NewsUtils";

    private static final String JSON_OBJECT_RESPONSE = "response";
    private static final String JSON_ARRAY_RESULTS = "results";

    public static String getNewsData(String url) throws IOException {

        String newsJsonData = null;
        URL urlRequest = null;
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        if(url == null ){ return null; }

        try {
            urlRequest = new URL(url);
        } catch (MalformedURLException malformedURLException) {
            Log.e(LOG_TAG, "Failed in get URL: " + malformedURLException.getMessage() );
        }

        try {

            urlConnection = (HttpURLConnection) urlRequest.openConnection();
            urlConnection.setReadTimeout(READ_TIMEOUT);
            urlConnection.setConnectTimeout(CONNECT_TIMEOUT);
            urlConnection.setRequestMethod(REQUEST_METHO);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == RESPONSE_CODE){
                inputStream = urlConnection.getInputStream();
                newsJsonData = readingInputStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Request Fail, code: " + urlConnection.getResponseCode());
            }
        } catch (IOException ioexception){
            Log.e(LOG_TAG, "Something went wrong: " + ioexception);
        } finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }

            if (inputStream != null) {
                inputStream.close();
            }
        }

        return newsJsonData;
    }

    private static String readingInputStream(InputStream inputStream) throws IOException {
        StringBuilder newsResponseData = new StringBuilder();

        if(inputStream != null){

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName(CHARSET_DATA));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String readLine = bufferedReader.readLine();

            while (readLine != null){
                newsResponseData.append(readLine);
                readLine = bufferedReader.readLine();
            }
        }

        return newsResponseData.toString();
    }

    public static List<News> newsFormatingData(String newsJson) {

        List<News> newsList = new ArrayList<>();

        if (newsJson == null){
            return null;
        }

        try {
            JSONObject baseJsonResponse = new JSONObject(newsJson);
            JSONObject baseJsonResultResponse = baseJsonResponse.getJSONObject(JSON_OBJECT_RESPONSE);
            JSONArray newsJsonArray = baseJsonResultResponse.getJSONArray(JSON_ARRAY_RESULTS);

            for (int countItem = 0; countItem < newsJsonArray.length(); countItem++) {

                JSONObject newsJsonObject = newsJsonArray.getJSONObject(countItem);

                newsList.add(new News(
                        newsJsonObject.optString("webTitle"),
                        null,
                        newsJsonObject.optString("webPublicationDate"),
                        newsJsonObject.optString("webUrl")
                ));
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage());
        }

        return newsList;
    }
}
