package pl.kopocinski.lukasz.lukaszkopocinski.gson;

import com.google.gson.Gson;

import pl.kopocinski.lukasz.lukaszkopocinski.JsonServerList;
import pl.kopocinski.lukasz.lukaszkopocinski.recycler.models.MainListRow;

/**
 * Created by Łukasz on 2016-01-04.
 */
public class JsonSerializer {

    private final Gson gson = new Gson();

    public JsonSerializer() {}

    public String serialize(JsonServerList userEntity) {
        String jsonString = gson.toJson(userEntity, JsonServerList.class);
        return jsonString;
    }

    public JsonServerList deserialize(String jsonString) {
        JsonServerList userEntity = gson.fromJson(jsonString, JsonServerList.class);
        return userEntity;
    }
}
