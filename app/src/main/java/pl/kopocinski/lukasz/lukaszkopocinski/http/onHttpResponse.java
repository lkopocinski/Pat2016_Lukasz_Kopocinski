package pl.kopocinski.lukasz.lukaszkopocinski.http;

/**
 * Created by Łukasz on 2016-01-05.
 */
public interface onHttpResponse {
    public void onHttpResponseSuccess(String response);
    public void onHttpResponseError(String errorMessage);

}
