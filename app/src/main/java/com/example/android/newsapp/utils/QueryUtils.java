package com.example.android.newsapp.utils;

import android.text.TextUtils;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

class QueryUtils {

    private static final int READ_TIMEOUT = 10000;
    private static final int CONNECT_TIMEOUT = 10000;
    private static final String REQUEST_METHOD = "GET";

    private static final int RESPONSE_CODE = 200;

    private static final String CHARSET_DATA = "UTF-8";
    private static final String LOG_TAG = "QueryUtils";

    private static final String JSON_OBJECT_RESPONSE = "response";
    private static final String JSON_ARRAY_RESULTS = "results";
    private static final String JSON_ARRAY_TAGS = "tags";

    private static final String NEWS_JSON_KEY_TITLE = "webTitle";
    private static final String NEWS_JSON_KEY_AUTHOR = "webTitle";
    private static final String NEWS_JSON_KEY_PUBLICATION_DATE = "webPublicationDate";
    private static final String NEWS_JSON_KEY_SECTION_NAME = "sectionName";
    private static final String NEWS_JSON_KEY_URL = "webUrl";

    static List<News> getNewsData(String urlRequested) {

        URL url = buildUrl(urlRequested);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        return getDataFromJson(jsonResponse);
    }

    private static URL buildUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(READ_TIMEOUT);
            urlConnection.setConnectTimeout(CONNECT_TIMEOUT);
            urlConnection.setRequestMethod(REQUEST_METHOD);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == RESPONSE_CODE) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readInputStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the news JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readInputStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName(CHARSET_DATA));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<News> getDataFromJson(String newsJSON) {

        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }

        List<News> newsList = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(newsJSON);
            JSONObject baseJsonResultResponse = baseJsonResponse.getJSONObject(JSON_OBJECT_RESPONSE);
            JSONArray newsJsonArray = baseJsonResultResponse.getJSONArray(JSON_ARRAY_RESULTS);

            for (int countItem = 0; countItem < newsJsonArray.length(); countItem++) {

                JSONObject newsJsonObject = newsJsonArray.getJSONObject(countItem);
                JSONArray tagsArray = newsJsonObject.getJSONArray(JSON_ARRAY_TAGS);
                JSONObject autorNewsJsonObject = new JSONObject("{\"webTitle\":\"No Author\"}");

                if( tagsArray.length() > 0 ){
                    autorNewsJsonObject = tagsArray.getJSONObject(0);
                }

                newsList.add(new News(
                        newsJsonObject.optString(NEWS_JSON_KEY_TITLE),
                        autorNewsJsonObject.optString(NEWS_JSON_KEY_AUTHOR),
                        formatDate(newsJsonObject.optString(NEWS_JSON_KEY_PUBLICATION_DATE)),
                        newsJsonObject.optString(NEWS_JSON_KEY_SECTION_NAME),
                        newsJsonObject.optString(NEWS_JSON_KEY_URL)
                ));
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the news JSON results", e);
        }

        return newsList;
    }

    private static String formatDate(String publicationDate) {
        String jsonDatePattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        SimpleDateFormat jsonFormatter = new SimpleDateFormat(jsonDatePattern, Locale.US);
        try {
            Date parsedJsonDate = jsonFormatter.parse(publicationDate);
            String finalDatePattern = "MMM d, yyy";
            SimpleDateFormat finalDateFormatter = new SimpleDateFormat(finalDatePattern, Locale.US);
            return finalDateFormatter.format(parsedJsonDate);
        } catch (ParseException e) {
            Log.e(LOG_TAG, "Error parsing JSON date: ", e);
            return "";
        }
    }
}
