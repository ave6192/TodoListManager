package com.example.ex2;

/**
 * Created by Aviv on 27/04/2017.
 */

public class Item {

    private String id;
    private String text;

    public Item(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
