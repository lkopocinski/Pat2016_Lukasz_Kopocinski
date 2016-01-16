package pl.kopocinski.lukasz.lukaszkopocinski.http;

public interface onHttpResponse {
    void onHttpResponseSuccess(String response);
    void onHttpResponseError(String errorMessage);
}
