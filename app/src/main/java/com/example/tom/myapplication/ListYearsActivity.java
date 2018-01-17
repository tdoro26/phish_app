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

public class ListYearsActivity extends AppCompatActivity {
    private Button btnSelectShow;
    private static final String TAG = "ListYearsActivity";
    private static final String year = "1998";

    private LinearLayoutManager myLayoutManager;
    private RecyclerView myRecyclerView;
    private ListYearsActivity.CustomAdapter myAdapter;
    private DividerItemDecoration mDividerItemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_years);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myRecyclerView = (RecyclerView) findViewById(R.id.years_rc_view);
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

        Log.d(TAG, "onCreate: Starting AsyncTask");
        ListYearsActivity.DownloadShows dlShows = new ListYearsActivity.DownloadShows();
        dlShows.execute(year);
        Log.d(TAG, "onCreate: onCreate() Done");
    }

    public class CustomAdapter extends RecyclerView.Adapter<ListYearsActivity.CustomAdapter.CustomViewHolder>  {
        private Years allYears;

        public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public TextView yearTxView;
            private String year;

            public CustomViewHolder(View v) {
                super(v);
                yearTxView = (TextView) v.findViewById(R.id.song_title);
                v.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                System.out.println("Year: " + yearTxView.getText());
                Intent i = new Intent(getApplicationContext(), ListShowsActivity.class);
                i.putExtra("Year", year);
                startActivity(i);
            }
        }

        public CustomAdapter(Years y) {
            this.allYears = y;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public ListYearsActivity.CustomAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                   int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_recycler_layout, parent, false);
            // set the view's size, margins, paddings and layout parameters
            // ...
            ListYearsActivity.CustomAdapter.CustomViewHolder vh = new ListYearsActivity.CustomAdapter.CustomViewHolder(v);
            return vh;
        }

        @Override
        public int getItemCount(){
            return allYears.getData().length;
        }

        @Override
        public void onBindViewHolder(ListYearsActivity.CustomAdapter.CustomViewHolder holder, int position) {
            // Find out the data, based on this view holder's position
            holder.year = this.allYears.getData()[position];
            holder.yearTxView.setText(holder.year);
        }

    }


    private class DownloadShows extends AsyncTask<String, Void, Years> {
        private static final String TAG = "DownloadShows";

        @Override
        protected void onPostExecute(Years y) {
            // Executed after "doInBackground()" on the main UI thread
            super.onPostExecute(y);
            Log.d(TAG, "onPostExecute: parameter is " + y.toString());

            // create a Customer Adapter and pass in the show
            myAdapter = new ListYearsActivity.CustomAdapter(y);
            // add grey lines between each view in myRecyclerView
            mDividerItemDecoration = new DividerItemDecoration(
                    myRecyclerView.getContext(),
                    myLayoutManager.getOrientation()
            );
            myRecyclerView.addItemDecoration(mDividerItemDecoration);
            myRecyclerView.setAdapter(myAdapter);
        }

        @Override
        protected Years doInBackground(String... params) {
            Log.d(TAG, "doInBackground: starts with " + params[0]);
            PhishinAPI phAPI = new PhishinAPI();
            Years allYears = phAPI.getAllYears();
            //ArrayList <Show> allShows = phAPI.getShowsInYear(params[0]);
            //final Show btnShow = allShows.get(4);
            return allYears;
        }
    }

}
