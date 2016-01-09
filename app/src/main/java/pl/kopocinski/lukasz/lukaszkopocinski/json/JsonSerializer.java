package pl.kopocinski.lukasz.lukaszkopocinski.json;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import pl.kopocinski.lukasz.lukaszkopocinski.json.models.JsonServerArray;

/**
 * Created by Łukasz on 2016-01-04.
 */
public class JsonSerializer {
    private static final String CLASS_NAME = JsonSerializer.class.getSimpleName();

    private final Gson gson = new Gson();

    public JsonSerializer() {
    }

    public String serialize(JsonServerArray jsonServerArray) {
        String jsonString;
        try {
            jsonString = gson.toJson(jsonServerArray, JsonServerArray.class);
        } catch (JsonSyntaxException e) {
            Log.e(CLASS_NAME, e.getMessage());
            return null;
        }
        return jsonString;
    }

    public JsonServerArray deserialize(String jsonString) {
        JsonServerArray userEntity;
        try {
            userEntity = gson.fromJson(jsonString, JsonServerArray.class);
        } catch (JsonSyntaxException e) {
            Log.e(CLASS_NAME, e.getMessage());
            return null;
        }
        return userEntity;
    }
}
