package com.example.tom.myapplication;

import android.util.JsonReader;

import com.example.tom.myapplication.Phish.in.Objects.*;
import com.google.gson.Gson;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Created by tom on 11/15/17.
 */

public class PhishinAPI {

    private String url = "http://phish.in/api/v1/";
    //private String shows = "shows.json";
    private HttpURLConnection con;
    String charset = "UTF-8";  // Or in Java 7 and later, use the constant: java.nio.charset.StandardCharsets.UTF_8.name()
    String param1 = "3";
    String param2 = "1";
    String param3 = "date";
    String param4 = "desc";
    HashMap<String, String> params = new HashMap<String, String>();

    public PhishinAPI() {
        params.put("sort_attr", ""); // Which attribute to sort on (Ex: "date", "name")
        params.put("sort_dir", ""); // Which direction to sort in (asc|desc)
        params.put("per_page", ""); // How many results to list per page (default: 20)
        params.put("page", ""); //  Which page of results to display (default: 1)

    }
    //  GET http://phish.in/api/v1/shows.json?per_page=3&page=1&sort_attr=date&sort_dir=desc



    public InputStream getRequest(String query, String request) {
        try {

            URLConnection connection = new URL(url + request + "?" + query).openConnection();
            //connection.setRequestProperty("Accept-Charset", charset);
            /* optional request headers */
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json");
            /* optional request headers */

            InputStream response = connection.getInputStream();
            return response;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getRequestToString(String query, String request) {
        InputStream response = getRequest(query, request);

        Scanner scanner = new Scanner(response);
        String responseBody = scanner.useDelimiter("\\A").next();
        System.out.println(responseBody);
        return responseBody;
    }

    public Years getAllYears() {
        String req = "years.json";
        String jsonResponse;

        try {
            String query = String.format("per_page=%s&page=%s&sort_attr=%s&sort_dir=%s",
                    URLEncoder.encode("45", charset),
                    URLEncoder.encode("1", charset),
                    URLEncoder.encode("date", charset),
                    URLEncoder.encode("desc", charset));

            jsonResponse = getRequestToString("sort_dir=asc", req);

            Gson gson = new Gson();
            Years allYears = gson.fromJson(jsonResponse, Years.class);
            return allYears;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Show createShowInstance(JsonReader reader) {
        Show show = new Show();
        try {
            reader.beginObject();
            while (reader.hasNext()) {
                try {
                    String name = reader.nextName();
                    if (name.equals("id")) {
                        show.setId(reader.nextInt());
                    } else if (name.equals("date")) {
                        show.setDate(reader.nextString());
                    } else if (name.equals("duration")) {
                        show.setDuration(reader.nextInt());
                    } else if (name.equals("incomplete")) {
                        show.setIncomplete(reader.nextBoolean());
                    } else if (name.equals("missing")) {
                        show.setMissing(reader.nextBoolean());
                    } else if (name.equals("sbd")) {
                        show.setSbd(reader.nextBoolean());
                    } else if (name.equals("remastered")) {
                        show.setRemastered(reader.nextBoolean());
                    } else if (name.equals("tour_id")) {
                        show.setTour_id(reader.nextInt());
                    } else if (name.equals("venue_id")) {
                        show.setVenue_id(reader.nextInt());
                    } else if (name.equals("likes_count")) {
                        show.setLikes_count(reader.nextInt());
                    } else if (name.equals("taper_notes")) {
                        show.setTaper_notes(reader.nextString());
                    } else if (name.equals("updated_at")) {
                        show.setUpdated_at(reader.nextString());
                    } else if (name.equals("venue_name")) {
                        show.setVenue_name(reader.nextString());
                    } else if (name.equals("location")) {
                        show.setLocation(reader.nextString());
                    } else {
                        reader.skipValue();
                    }
                } catch (IllegalStateException e) {
                    try {
                        reader.skipValue();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            reader.endObject();
            //System.out.println(show.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return show;
    }

    public ArrayList<Track> addTracksToShow(InputStream response) {
        Show show = new Show();
        ArrayList<Track> trackList = new ArrayList<Track>();

        try {
            JsonReader reader = new JsonReader(new InputStreamReader(response, "UTF-8"));
            reader.beginObject();
            while (reader.hasNext()) {
                String obj_name = reader.nextName();
                if (obj_name.equals("data")) {
                    reader.beginObject();
                    while (reader.hasNext()) {
                        String showAttribute = reader.nextName();
                        if (showAttribute.equals("tracks")) {
                            reader.beginArray();
                            while (reader.hasNext()) {
                                trackList.add(createTrackInstance(reader));
                            }
                        }
                        else {
                            reader.skipValue();
                        }
                    }
                } else {
                    reader.skipValue();
                }
            }
            reader.close();
            return trackList;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Track createTrackInstance(JsonReader reader) {
        Track track = new Track();

        try {
            reader.beginObject();
            while (reader.hasNext()) {
                try {
                    String name = reader.nextName();
                    if (name.equals("id")) {
                        track.setId(reader.nextInt());
                    } else if (name.equals("title")) {
                        track.setTitle(reader.nextString());
                    } else if (name.equals("position")) {
                        track.setPosition(reader.nextInt());
                    } else if (name.equals("duration")) {
                        track.setDuration(reader.nextInt());
                    } else if (name.equals("set")) {
                        track.setSet(reader.nextString());
                    } else if (name.equals("set_name")) {
                        track.setSetName(reader.nextString());
                    } else if (name.equals("likes_count")) {
                        track.setLikesCount(reader.nextInt());
                    } else if (name.equals("slug")) {
                        track.setSlug(reader.nextString());
                    } else if (name.equals("mp3")) {
                        track.setMp3URL(reader.nextString());
                    } else if (name.equals("updated_at")) {
                        track.setUpdatedAt(reader.nextString());
                    } else {
                        reader.skipValue();
                    }
                } catch (IllegalStateException e) {
                    try {
                        reader.skipValue();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            reader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return track;
    }

    public ArrayList <Show> createMapOfAllShows(InputStream response) {
        /**
         * Takes in InputStream from getAllShowsInYear() and creates a HashMap of all the shows
         * **/
         ArrayList <Show> allShows = new ArrayList<Show>();
        //HashMap <Integer, Show> allShows = new HashMap<Integer, Show>();  // TODO was suggested to use SparseArray for better performance...

        try {
            JsonReader reader = new JsonReader(new InputStreamReader(response, "UTF-8"));
            reader.beginObject();
            while (reader.hasNext()) {
                String obj_name = reader.nextName();
                if (obj_name.equals("data")) {
                    reader.beginArray();
                    while (reader.hasNext()) {
                        Show temp = createShowInstance(reader);
                        //allShows.put(temp.getId(), temp);
                        allShows.add(temp);
                    }
                } else {
                    reader.skipValue();
                }
            }

            reader.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return allShows;
    }

    public ArrayList<Show> getShowsInYear(String year) {
        try {
            String req = String.format("years/%s.json", URLEncoder.encode(year, charset));
            String query = String.format("per_page=%s&page=%s&sort_attr=%s&sort_dir=%s",
                    URLEncoder.encode(param1, charset),
                    URLEncoder.encode(param2, charset),
                    URLEncoder.encode(param3, charset),
                    URLEncoder.encode(param4, charset));
            /**
            String response = getRequestToString("", req);

            Gson gson = new Gson();
            Years allYears = gson.fromJson(response, Years.class);
            return allYears;
            **/
            //InputStream response = getRequest("", req);
            System.out.println(getRequestToString("", req));

            return(createMapOfAllShows(getRequest("", req)));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public InputStream getShowByID(int id) {

        try {
            String req = String.format("shows/%s.json", URLEncoder.encode(String.valueOf(id), charset));
            String query = String.format("per_page=%s&page=%s&sort_attr=%s&sort_dir=%s",
                    URLEncoder.encode(param1, charset),
                    URLEncoder.encode(param2, charset),
                    URLEncoder.encode(param3, charset),
                    URLEncoder.encode(param4, charset));

            return getRequest("", req);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getAllShows() {
        String req = "shows.json";
        try {
            String query = String.format("per_page=%s&page=%s&sort_attr=%s&sort_dir=%s",
                    URLEncoder.encode(param1, charset),
                    URLEncoder.encode(param2, charset),
                    URLEncoder.encode(param3, charset),
                    URLEncoder.encode(param4, charset));


            getRequestToString(query, req);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
