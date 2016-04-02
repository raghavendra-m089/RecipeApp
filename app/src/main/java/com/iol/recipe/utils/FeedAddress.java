package com.iol.recipe.utils;

import java.io.Serializable;
import java.util.ArrayList;

public class FeedAddress implements Serializable {
    // Variables required
    private static final long serialVersionUID = 1L;
    private String title;
    private String href;
    private String thumbnail;
    private String ingredients;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
}
