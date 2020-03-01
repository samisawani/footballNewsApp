package com.weezee.newsappstage2.utilities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import com.weezee.newsappstage2.pojo.StoryItem;
import org.json.JSONException;
import static com.weezee.newsappstage2.utilities.NetworkingUtilities.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class StoriesLoader extends AsyncTaskLoader<ArrayList<StoryItem>> {
    private static final String TAG = "StoriesLoader";
    private static final int ONE_SECOND=1000;
    private final String URL;

    public StoriesLoader(@NonNull Context context, String URL) {
        super(context);
        this.URL=URL;
    }

    @Nullable
    @Override
    public ArrayList<StoryItem> loadInBackground() {
        ArrayList<StoryItem> storyItems=new ArrayList<>();
        try{
            URL urlObject=getURLObject(this.URL);
            HttpURLConnection httpURLConnection=getHttpUrlConnectionObject(urlObject);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(ONE_SECOND);
            httpURLConnection.setConnectTimeout(ONE_SECOND);
            httpURLConnection.connect();
            BufferedReader bufferedReader=getBufferedReaderObject(httpURLConnection);
            String jSonResponse=calculateAndReturnJsonResponse(httpURLConnection,bufferedReader );
            storyItems=parseResponseAndFillToBeReturnedAnArray(jSonResponse);
            closeHttpAndBuffered(httpURLConnection,bufferedReader );
            return storyItems;

        }
        catch (MalformedURLException e){
            Log.e(TAG, "loadInBackground: "+"error in the URL!" );
        }
        catch (JSONException a){
            Log.e(TAG, "loadInBackground: "+"error in the JSON Parsing!" );
        }
        catch (IOException b){
            Log.e(TAG, "loadInBackground: "+"IO error!" );

        }
        return storyItems;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();    }
}
