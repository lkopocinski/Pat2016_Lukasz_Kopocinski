package pl.kopocinski.lukasz.lukaszkopocinski.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class JsonServerArray {

    @SerializedName("array")
    @Expose
    private List<JsonRowData> array = new ArrayList<JsonRowData>();

    /**
     * @return The array
     */
    public List<JsonRowData> getArray() {
        return array;
    }

    /**
     * @param array The array
     */
    public void setArray(List<JsonRowData> array) {
        this.array = array;
    }

}