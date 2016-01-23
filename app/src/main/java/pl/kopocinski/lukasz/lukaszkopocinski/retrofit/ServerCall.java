package pl.kopocinski.lukasz.lukaszkopocinski.retrofit;

import pl.kopocinski.lukasz.lukaszkopocinski.models.JsonServerArray;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Łukasz on 2016-01-21.
 */
public interface ServerCall {

    @GET("/page_{pageNumber}.json")
    Call<JsonServerArray> getServerArray(@Path("pageNumber") int pageNumber);

}
