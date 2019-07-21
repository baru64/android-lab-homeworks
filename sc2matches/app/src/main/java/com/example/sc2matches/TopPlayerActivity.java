package com.example.sc2matches;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.sc2matches.player.topPlayerListContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TopPlayerActivity extends AppCompatActivity implements topPlayerFragment.OnListFragmentInteractionListener {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top30, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.showMatches:
                finish();
                break;
            case R.id.showTop:
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_player);
        ProgressBar progressBar = findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.GONE);
        progressBar.setMax(100);
        new GetPlayersTask(progressBar).execute(30);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onListFragmentInteraction(topPlayerListContent.playerItem item) {

    }

    private class GetPlayersTask extends AsyncTask<Integer, Integer, Boolean> {
        private ProgressBar progressBar;

        GetPlayersTask(ProgressBar progressBar) {
            this.progressBar = progressBar;
//            if (progressBar == null) {
//                this.progressBar = findViewById(R.id.progressBar3);
//            }
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
        }

        @Override
        protected Boolean doInBackground(Integer... cnt) {
            int count = cnt[0];
            String jsonString = "";

            HttpHandler sh = new HttpHandler();
            String url = "http://aligulac.com/api/v1/player/?format=json"+
                    "&current_rating__isnull=false&current_rating__decay__lt=4"
                    +"&order_by=-current_rating__rating&limit=" + count
                    +"&apikey=gmWATUigLKGlr5EfakDZ";
            jsonString = sh.makeServiceCall(url);
            publishProgress(10);
            // read JSON
            try {
                JSONObject response = new JSONObject(jsonString);
                JSONArray players = response.getJSONArray("objects");

                int len = players.length();
                for(int i = 0; i < len; i++) {
                    JSONObject p = players.getJSONObject(i);
                    String name = p.getString("tag");
                    String country = p.getString("country");
                    String race = p.getString("race");

                    JSONObject current_rating = p.getJSONObject("current_rating");
                    int rating = (int) ((current_rating.getDouble("rating")+1)*1000);

                    switch (race) {
                        case "P":
                            race = "Protoss";
                            break;
                        case "Z":
                            race = "Zerg";
                            break;
                        case "T":
                            race = "Terran";
                            break;
                        case "R":
                            race = "Random";
                    }

                    topPlayerListContent.playerItem player = new topPlayerListContent.playerItem(
                            String.valueOf(i+1),
                            name,
                            race,
                            rating,
                            country);
                    topPlayerListContent.addItem(player);
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
            progressBar.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            progressBar.setVisibility(View.GONE);
            ((topPlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentTopPlayer)).notifyDataChange();
        }
    }

}