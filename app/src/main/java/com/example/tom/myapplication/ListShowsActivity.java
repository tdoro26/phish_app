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
import android.widget.Button;
import android.widget.TextView;

import com.example.tom.myapplication.Phish.in.Objects.Show;
import com.example.tom.myapplication.Phish.in.Objects.Years;

import java.util.ArrayList;


public class ListShowsActivity extends AppCompatActivity {

    private static final String TAG = "ListShowsActivity";
    private String year;

    private LinearLayoutManager myLayoutManager;
    private RecyclerView myRecyclerView;
    private ListShowsActivity.CustomAdapter myAdapter;
    private DividerItemDecoration mDividerItemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_shows);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myRecyclerView = (RecyclerView) findViewById(R.id.shows_rc_view);
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

        Bundle bundle = getIntent().getExtras();
        year = bundle.getString("Year");
        Log.d(TAG, "onCreate: Starting AsyncTask");
        DownloadShows dlShows = new DownloadShows();
        dlShows.execute(year);
        Log.d(TAG, "onCreate: onCreate() Done");
    }

    public class CustomAdapter extends RecyclerView.Adapter<ListShowsActivity.CustomAdapter.CustomViewHolder>  {
        private ArrayList<Show> allShows;

        public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public TextView titleTxView;
            private Show show;

            public CustomViewHolder(View v) {
                super(v);
                titleTxView = (TextView) v.findViewById(R.id.song_title);
                v.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                System.out.println("Show: " + titleTxView.getText());
                Intent i = new Intent(getApplicationContext(), ListTracksActivity.class);
                i.putExtra("Show", show);
                startActivity(i);
            }
        }

        public CustomAdapter(ArrayList<Show> s) {
            this.allShows = s;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public ListShowsActivity.CustomAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                    int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_recycler_layout, parent, false);
            // set the view's size, margins, paddings and layout parameters
            // ...
            ListShowsActivity.CustomAdapter.CustomViewHolder vh = new ListShowsActivity.CustomAdapter.CustomViewHolder(v);
            return vh;
        }

        @Override
        public int getItemCount(){
            return allShows.size();
        }

        @Override
        public void onBindViewHolder(ListShowsActivity.CustomAdapter.CustomViewHolder holder, int position) {
            // Find out the data, based on this view holder's position
            holder.show = this.allShows.get(position);
            holder.titleTxView.setText(holder.show.getDate());
        }

    }


    private class DownloadShows extends AsyncTask<String, Void, ArrayList<Show>> {
        private static final String TAG = "DownloadShows";

        @Override
        protected void onPostExecute(ArrayList<Show> s) {
            // Executed after "doInBackground()" on the main UI thread
            super.onPostExecute(s);
            Log.d(TAG, "onPostExecute: parameter is " + s.toString());

            // create a Customer Adapter and pass in the show
            myAdapter = new CustomAdapter(s);
            // add grey lines between each view in myRecyclerView
            mDividerItemDecoration = new DividerItemDecoration(
                    myRecyclerView.getContext(),
                    myLayoutManager.getOrientation()
            );
            myRecyclerView.addItemDecoration(mDividerItemDecoration);
            myRecyclerView.setAdapter(myAdapter);
        }

        @Override
        protected ArrayList<Show> doInBackground(String... params) {
            Log.d(TAG, "doInBackground: starts with " + params[0]);
            PhishinAPI phAPI = new PhishinAPI();
            ArrayList <Show> allShows = phAPI.getShowsInYear(params[0]);
            final Show btnShow = allShows.get(4);
            return allShows;
        }
    }

}
