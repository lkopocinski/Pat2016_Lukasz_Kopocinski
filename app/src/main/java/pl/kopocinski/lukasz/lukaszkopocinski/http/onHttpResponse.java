package pl.kopocinski.lukasz.lukaszkopocinski.http;

public interface onHttpResponse {
    public void onHttpResponseSuccess(String response);
    public void onHttpResponseError(String errorMessage);

}
