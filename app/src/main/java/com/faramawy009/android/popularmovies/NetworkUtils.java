package com.faramawy009.android.popularmovies;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by elfar009 on 6/10/18.
 */

public class NetworkUtils {
    public static String getUrlResponse(URL url) throws Exception {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            return streamToString(in);
        } finally {
            urlConnection.disconnect();
        }
    }

    private static String streamToString(InputStream in) {
        Scanner s = new Scanner(in).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
