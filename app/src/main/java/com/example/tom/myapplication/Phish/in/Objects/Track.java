package com.example.tom.myapplication.Phish.in.Objects;

/**
 * Created by tom on 11/19/17.
 */

public class Track {
    /*
    "id":17923,
    "title":"1999",
    "position":1,
    "duration":565316,
    "set":"1",
    "set_name":"Set 1",
    "likes_count":7,
    "slug":"1999",
    "mp3":"https://phish.in/audio/000/017/923/17923.mp3",
    "song_ids":[1],
    "updated_at":"2013-03-30T04:53:21Z"
    */

    private int id;
    private String title;
    private int position;
    private int duration;
    private String set;
    private String setName;
    private int likesCount;
    private String slug;
    private String mp3URL;
    // private int[] songIDs;
    private String updatedAt;

    public Track() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getMp3URL() {
        return mp3URL;
    }

    public void setMp3URL(String mp3URL) {
        this.mp3URL = mp3URL;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
