package com.nathan.expensemanager;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class HttpUtils {

    private static final String BASE_URL = "http://cwservice1786.herokuapp.com/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void post(String url,RequestParams entity, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), entity, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
