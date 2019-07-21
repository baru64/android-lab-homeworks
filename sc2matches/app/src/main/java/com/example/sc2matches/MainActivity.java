package com.example.sc2matches;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ActionMenuItem;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.sc2matches.matches.MatchListContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity
        implements
        MatchFragment.OnListFragmentInteractionListener{

    public static final String taskExtra = "taskExtra";
    private int currentItemPosition = -1;
    private MatchListContent.Match currentMatch;
    private final String CURRENT_PERSON_KEY = "CurrentTask";
    private ProgressBar progressBar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.showMatches:
                break;
            case R.id.showTop:
                showTopPlayers();
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            currentMatch = savedInstanceState.getParcelable(CURRENT_PERSON_KEY);
        }
//
//        Toolbar myToolbar = findViewById(R.id.my_toolbar);
//        setSupportActionBar(myToolbar);


        FloatingActionButton refreshButton = findViewById(R.id.refreshButton);
        FloatingActionButton getMoreButton = findViewById(R.id.getMoreButton);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        progressBar.setMax(100);

        // refresh matches
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Main", "refresh button clicked");
                refreshList();
            }
        });

        // get more matches
        getMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Main", "get more button clicked");
                new GetMatchesTask(progressBar).execute(8);
//                ((MatchFragment) getSupportFragmentManager().findFragmentById(R.id.matchFragment)).notifyDataChange();
            }
        });

        new GetMatchesTask(progressBar).execute(16);

    }


    public void refreshList() {
        MatchListContent.clearList();
        new GetMatchesTask(progressBar).execute(16);
//        ((MatchFragment) getSupportFragmentManager().findFragmentById(R.id.matchFragment)).notifyDataChange();
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            if(currentMatch != null)
                displayMatchInFragment(currentMatch);
        }
    }

    @Override
    protected void onDestroy() {
//        savePersonsToJson();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (currentMatch != null)
            outState.putParcelable(CURRENT_PERSON_KEY, currentMatch);
        super.onSaveInstanceState(outState);
    }

    private void startSecondActivity(MatchListContent.Match match, int position){
        Intent intent = new Intent(this, MatchInfoActivity.class);
        intent.putExtra(taskExtra, match);
        startActivity(intent);
    }

    private void showTopPlayers() {
        Intent intent = new Intent(this, TopPlayerActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

}


    private void displayMatchInFragment(MatchListContent.Match match){
        MatchInfoFragment matchInfoFragment = ((MatchInfoFragment) getSupportFragmentManager().findFragmentById(R.id.displayFragment));
        if(match != null){
            matchInfoFragment.displayMatch(match);
        }
    }


    @Override
    public void onListFragmentClickInteraction(MatchListContent.Match match, int position) {
        currentMatch = match;
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            displayMatchInFragment(match);
        }else {
            startSecondActivity(match, position);
        }
    }

    @Override
    public void onListFragmentDeleteInteraction(int position) {
        currentItemPosition = position;
    }


    private class GetMatchesTask extends AsyncTask<Integer, Integer, Boolean> {
        private ProgressBar progressBar;

        GetMatchesTask(ProgressBar progressBar) {
            this.progressBar = progressBar;
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
        }

        @Override
        protected Boolean doInBackground(Integer... cnt) {
            int count = cnt[0];
            int offset = MatchListContent.ITEMS.size();

            // get matches
            String jsonString = "";
//            try {
//                URL url = new URL(
//                "http://aligulac.com/api/v1/match/?format=json&order_by=-date&offline=true"
//                        + "&limit=" + count + "&offset=" + offset
//                        + "&apikey=gmWATUigLKGlr5EfakDZ"
//                );
//                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//
//                try {
//                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//                    BufferedReader r = new BufferedReader(new InputStreamReader(in));
//                    StringBuilder total = new StringBuilder();
//                    for (String line; (line = r.readLine()) != null; ) {
//                        total.append(line).append('\n');
//                    }
//                    jsonString = total.toString();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    urlConnection.disconnect();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            HttpHandler sh = new HttpHandler();
            String url = "http://aligulac.com/api/v1/match/?format=json&order_by=-date&offline=true"
                        + "&limit=" + count + "&offset=" + offset
                        + "&apikey=gmWATUigLKGlr5EfakDZ";
            jsonString = sh.makeServiceCall(url);
            publishProgress(10);
            // read JSON
            try {
                JSONObject response = new JSONObject(jsonString);
                JSONArray matches = response.getJSONArray("objects");

                int len = matches.length();
                for(int i = 0; i < len; i++) {
                    JSONObject m = matches.getJSONObject(i);
                    Integer id = m.getInt("id");
                    String score = m.getInt("sca") + " : " + m.getInt("scb");
                    String date = m.getString("date");

                    JSONObject p1 = m.getJSONObject("pla");
                    JSONObject p2 = m.getJSONObject("plb");
                    JSONObject eventObj = m.getJSONObject("eventobj");

                    String event = eventObj.getString("fullname");
                    String p1_name = p1.getString("tag");
                    Integer p1_id = p1.getInt("id");
                    String p1_race = p1.getString("race");
                    String p2_name = p2.getString("tag");
                    Integer p2_id = p2.getInt("id");
                    String p2_race = p2.getString("race");

                    switch (p1_race) {
                        case "P":
                            p1_race = "Protoss";
                            break;
                        case "Z":
                            p1_race = "Zerg";
                            break;
                        case "T":
                            p1_race = "Terran";
                            break;
                        case "R":
                            p1_race = "Random";
                    }

                    switch (p2_race) {
                        case "P":
                            p2_race = "Protoss";
                            break;
                        case "Z":
                            p2_race = "Zerg";
                            break;
                        case "T":
                            p2_race = "Terran";
                            break;
                        case "R":
                            p2_race = "Random";
                    }

                    MatchListContent.Match match = new MatchListContent.Match(
                            "" + MatchListContent.ITEMS.size()+1, event, date,
                            p1_name, p1_race, p1_id,
                            p2_name, p2_race, p2_id,
                            score);
                    MatchListContent.addItem(match);
                    publishProgress((int) ((i / (float) len) * 90));
                    if (isCancelled()) break;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            ProgressBar progressBar = findViewById(R.id.progressBar);
            progressBar.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            progressBar.setVisibility(View.GONE);
            ((MatchFragment) getSupportFragmentManager().findFragmentById(R.id.matchFragment)).notifyDataChange();
        }
    }

}
