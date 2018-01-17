package com.example.tom.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.tom.myapplication.Phish.in.Objects.Show;
import com.example.tom.myapplication.Phish.in.Objects.Track;

import java.util.ArrayList;


public class ListTracksActivity extends AppCompatActivity {

    LinearLayoutManager myLayoutManager;
    RecyclerView myRecyclerView;
    CustomAdapter myAdapter;
    private DividerItemDecoration mDividerItemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tracks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myRecyclerView = (RecyclerView) findViewById(R.id.recyc_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        myRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        myLayoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(myLayoutManager);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Show tempShow = (Show) getIntent().getSerializableExtra("Show");
        System.out.println(tempShow.toString());
        DownloadTracks dlTracks = new DownloadTracks();
        dlTracks.execute(tempShow);
    }



    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder>  {

        private Show show;
        private ArrayList<Track> setList;

        public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public TextView titleTxView;
            public TextView durationTxView;
            private String url;

            public CustomViewHolder(View v) {
                super(v);
                titleTxView = (TextView) v.findViewById(R.id.song_title);
                durationTxView = (TextView) v.findViewById(R.id.song_duration);
                //titleTxView.setOnClickListener(this);
                v.setOnClickListener(this);

            }

            @Override
            public void onClick(View v) {
                System.out.println("Song: " + titleTxView.getText() + " :  " + url);
                Intent i = new Intent(getApplicationContext(), StreamAudio.class);
                i.putExtra("URL", url);
                startActivity(i);
            }
        }

        public CustomAdapter(Show s) {
            this.show = s;
            this.setList = s.getSetList();
        }

        // Create new views (invoked by the layout manager)
        @Override
        public CustomAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_recycler_layout, parent, false);
            // set the view's size, margins, paddings and layout parameters
            // ...
            CustomViewHolder vh = new CustomViewHolder(v);
            return vh;
        }

        @Override
        public int getItemCount(){
            return show.getSetList().size();
        }

        @Override
        public void onBindViewHolder(CustomViewHolder holder, int position) {
            // Find out the data, based on this view holder's position
            holder.titleTxView.setText(this.setList.get(position).getTitle());
            holder.url = this.setList.get(position).getMp3URL();
            //holder.durationTxView.setText(String.valueOf(this.setList.get(position).getDuration()));
        }

    }


    private class DownloadTracks extends AsyncTask<Show, Void, Show> {
        private static final String TAG = "DownloadTracks";

        @Override
        protected void onPostExecute(Show s) {
            // Executed after "doInBackground()" on the main UI thread
            super.onPostExecute(s);
            Log.d(TAG, "onPostExecute: parameter is " + s.toString());
            int count = 0;
            Show show = s;

            // create a Customer Adapter and pass in the show
            myAdapter = new CustomAdapter(show);
            // add grey lines between each view in myRecyclerView
            mDividerItemDecoration = new DividerItemDecoration(
                    myRecyclerView.getContext(),
                    myLayoutManager.getOrientation()
            );
            myRecyclerView.addItemDecoration(mDividerItemDecoration);
            myRecyclerView.setAdapter(myAdapter);
            // myAdapter = new CustomAdapter(myDataSet);
            for (Track t: show.getSetList()) {
                System.out.println(t.getTitle() + t.getMp3URL());
            }
        }

        @Override
        protected Show doInBackground(Show... params) {
            Log.d(TAG, "doInBackground: starts with " + params[0]);
            Show inputShow = params[0];
            PhishinAPI phAPI = new PhishinAPI();
            inputShow.setSetList(phAPI.addTracksToShow(phAPI.getShowByID(inputShow.getId())));
            return inputShow;
        }
    }

}


