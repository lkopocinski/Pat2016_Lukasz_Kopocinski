package pl.kopocinski.lukasz.lukaszkopocinski;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import pl.kopocinski.lukasz.lukaszkopocinski.gson.JsonArrayServer;

public class JsonServerList {

    @SerializedName("array")
    @Expose
    private List<JsonArrayServer> array = new ArrayList<JsonArrayServer>();

    /**
     *
     * @return
     * The array
     */
    public List<JsonArrayServer> getArray() {
        return array;
    }

    /**
     *
     * @param array
     * The array
     */
    public void setArray(List<JsonArrayServer> array) {
        this.array = array;
    }

}