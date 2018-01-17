package com.example.tom.myapplication.Phish.in.Objects;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by tom on 11/16/17.
 */

public class Show implements Serializable {  // TODO look to see if worth implementing Parsable instead of Serializable

    private String[] data;

    private int id;
    private String date;
    private int duration; // see if theres a Java time interval data type
    private Boolean incomplete;
    private Boolean missing;
    private Boolean sbd;
    private Boolean remastered;
    private int tour_id;
    private int venue_id;
    private int likes_count;

    private String taper_notes;
    // "updated_at":"2017-01-16T04:19:18Z"
    private String updated_at;
    private String venue_name;
    private String location;

    private ArrayList<Track> setList;

    /**
    private String[] tags;
    private Venue venue;     //  TODO: make Venue class

    private String[] sets;
    private String[] tracks;


    private PhishNetSetlist setlist;  // TODO make Setlist class

    private String fullLocation;
    // private cacheKey;             //  TODO maybe...
    **/


    //private Show() {}

    public Show() {
    }

    public void setData(String[] data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Boolean getIncomplete() {
        return incomplete;
    }

    public void setIncomplete(Boolean incomplete) {
        this.incomplete = incomplete;
    }

    public Boolean getMissing() {
        return missing;
    }

    public void setMissing(Boolean missing) {
        this.missing = missing;
    }

    public Boolean getSbd() {
        return sbd;
    }

    public void setSbd(Boolean sbd) {
        this.sbd = sbd;
    }

    public Boolean getRemastered() {
        return remastered;
    }

    public void setRemastered(Boolean remastered) {
        this.remastered = remastered;
    }

    public int getTour_id() {
        return tour_id;
    }

    public void setTour_id(int tour_id) {
        this.tour_id = tour_id;
    }

    public int getVenue_id() {
        return venue_id;
    }

    public void setVenue_id(int venue_id) {
        this.venue_id = venue_id;
    }

    public int getLikes_count() {
        return likes_count;
    }

    public void setLikes_count(int likes_count) {
        this.likes_count = likes_count;
    }

    public String getTaper_notes() {
        return taper_notes;
    }

    public void setTaper_notes(String taper_notes) {
        this.taper_notes = taper_notes;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getVenue_name() {
        return venue_name;
    }

    public void setVenue_name(String venue_name) {
        this.venue_name = venue_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String[] getData() {
        return this.data;
    }

    public void setSetList(ArrayList<Track> setList) {
        this.setList = setList;
    }

    public ArrayList<Track> getSetList() {
        return setList;
    }

    @Override
    public String toString() {
        String s = String.format("ID: %s,\tDate: %s,\tVenue: %s,\tLocation: %s", id, date, venue_name, location);
        return s;
    }
}
