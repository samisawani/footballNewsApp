package com.weezee.newsappstage2.utilities;

import com.weezee.newsappstage2.pojo.StoryItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class NetworkingUtilities {
    private NetworkingUtilities() {
    }

    public static URL getURLObject(String urlAsString) throws MalformedURLException {
        return new URL(urlAsString);
    }


    public static HttpURLConnection getHttpUrlConnectionObject(URL url) throws IOException {
        return (HttpURLConnection) url.openConnection();
    }

    public static String calculateAndReturnJsonResponse(HttpURLConnection httpURLConnection, BufferedReader bufferedReader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String temp = bufferedReader.readLine();
        while (temp != null) {
            stringBuilder.append(temp);
            temp = bufferedReader.readLine();
        }

        return stringBuilder.toString();
    }


    public static BufferedReader getBufferedReaderObject(HttpURLConnection httpURLConnection) throws IOException {

        return new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
    }

    public static void closeHttpAndBuffered(HttpURLConnection httpURLConnection, BufferedReader bufferedReader) throws IOException {
        bufferedReader.close();
        httpURLConnection.disconnect();
    }


    public static ArrayList<StoryItem> parseResponseAndFillToBeReturnedAnArray(String jsonResponse) throws JSONException, NullPointerException {
        JSONObject root;
        ArrayList<StoryItem> storyItems = new ArrayList<>();
        root = new JSONObject(jsonResponse);
        JSONObject jsonObject = root.getJSONObject("response");
        if (jsonObject != null) {
            if (jsonObject.optString("status", "no response").equals("ok")) {
                JSONArray jsonArray = root.getJSONObject("response").getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {

                    if(jsonArray.optJSONObject(i).optString("sectionName", "No section name").equalsIgnoreCase("football")){
                             StoryItem temp = new StoryItem(
                            (jsonArray.optJSONObject(i).optString("webTitle", "No Title")),
                                    jsonArray.optJSONObject(i).optString("sectionName", "No section name"),
                                    jsonArray.optJSONObject(i).optString("webPublicationDate", "No publication date"),
                                    jsonArray.optJSONObject(i).optJSONObject("fields").optString("byline", "No author"),
                                    jsonArray.optJSONObject(i).optJSONObject("fields").optString("thumbnail", "No thumbnail"),
                                    (jsonArray.optJSONObject(i).optJSONObject("fields").optString("bodyText", "No body text")),
                                    jsonArray.optJSONObject(i).optString("webUrl", "No web url"),
                                    jsonArray.optJSONObject(i).optJSONObject("fields").optString("trailText", "No trailText"));
                                    storyItems.add(temp);
                    }
                }

                return storyItems;
            } else return storyItems;
        }

        return storyItems;


    }
}
