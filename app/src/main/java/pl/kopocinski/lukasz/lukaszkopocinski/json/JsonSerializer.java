package pl.kopocinski.lukasz.lukaszkopocinski.json;

import com.google.gson.Gson;

import pl.kopocinski.lukasz.lukaszkopocinski.json.models.JsonServerArray;

/**
 * Created by Łukasz on 2016-01-04.
 */
public class JsonSerializer {

    private final Gson gson = new Gson();

    public JsonSerializer() {
    }

    public String serialize(JsonServerArray jsonServerArray) {
        String jsonString = gson.toJson(jsonServerArray, JsonServerArray.class);
        return jsonString;
    }

    public JsonServerArray deserialize(String jsonString) {
        JsonServerArray userEntity = gson.fromJson(jsonString, JsonServerArray.class);
        return userEntity;
    }
}
