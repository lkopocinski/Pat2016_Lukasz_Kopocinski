package pl.kopocinski.lukasz.lukaszkopocinski.http;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpAsync {
    private static final String CLASS_NAME = HttpAsync.class.getSimpleName();

    private static final Handler handler = new Handler(Looper.getMainLooper());
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public void download(String url, onHttpResponse onHttpRequest) {
        executorService.execute(new HttpRunnable(url, onHttpRequest));
    }

    private class HttpRunnable implements Runnable {
        private String url;
        private onHttpResponse onHttpRequest;
        private OkHttpClient httpClient = new OkHttpClient();

        public HttpRunnable(final String url, final onHttpResponse onHttpRequest) {
            this.url = url;
            this.onHttpRequest = onHttpRequest;
        }

        @Override
        public void run() {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response;
            try {
                response = httpClient.newCall(request).execute();
                if (response.code() == HttpURLConnection.HTTP_OK || response.code() == HttpURLConnection.HTTP_CREATED) {
                    onSuccessResponse(response.body().string());
                } else {
                    onError("Http error code " + response.code());
                }
            } catch (IOException e) {
                onError(e.getMessage());
            }
        }

        private void onSuccessResponse(final String response) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onHttpRequest.onHttpResponseSuccess(response);
                }
            });
        }

        private void onError(final String message) {
            Log.e(CLASS_NAME, message);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onHttpRequest.onHttpResponseError(message);
                }
            });
        }
    }
}
