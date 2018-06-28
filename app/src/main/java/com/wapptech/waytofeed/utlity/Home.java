package com.wapptech.waytofeed.utlity;

public class Home {
    private String name;
    private int numOfSongs;
    public int id, user_id, like_count, view_count, comment_count, category_id;
    public String title, slug, discription, short_discription, tags, lan_type, thumbnail_image;

    public Home() {
    }

    public Home(String name, int numOfSongs, String thumbnail) {
        this.name = name;
        this.numOfSongs = numOfSongs;
        this.thumbnail_image = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumOfSongs() {
        return numOfSongs;
    }

    public void setNumOfSongs(int numOfSongs) {
        this.numOfSongs = numOfSongs;
    }

    public String getThumbnail() {
        return thumbnail_image;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail_image = thumbnail;
    }
}
