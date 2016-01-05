package pl.kopocinski.lukasz.lukaszkopocinski.recycler.models;

/**
 * Created by Łukasz on 2016-01-03.
 */
public class MainListRow {
    private int imageUrl;
    private String title;
    private String description;

    public MainListRow(int imageUrl, String title, String description) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.description = description;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
