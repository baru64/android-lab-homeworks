package com.example.sc2matches;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.sc2matches.persons.MatchListContent;

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

import static com.example.sc2matches.AddPersonActivity.BUTTON_REQUEST;

public class MainActivity extends AppCompatActivity
        implements
        MatchFragment.OnListFragmentInteractionListener,
        DeleteDialog.OnDeleteDialogInteractionListener{

    public static final String taskExtra = "taskExtra";
    private int currentItemPosition = -1;
    private MatchListContent.Match currentMatch;
    private final String CURRENT_PERSON_KEY = "CurrentTask";
    public static final String takePhoto = "takePhoto";

    private final String CHECKED_ID = "checkedId";
    private final String PERSONS_JSON_FILE = "persons.json";
    private final String NUM_PERSONS = "NumOfPersons";
    private final String PERSON = "person_";
    private final String BIRTHDAY = "birthday_";
    private final String DETAIL = "desc_";
    private final String PIC = "pic_";
    private final String ID = "id_";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            currentMatch = savedInstanceState.getParcelable(CURRENT_PERSON_KEY);
        }

        FloatingActionButton refreshButton = findViewById(R.id.refreshButton);
        FloatingActionButton getMoreButton = findViewById(R.id.getMoreButton);

        // refresh matches
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshList();
            }
        });

        // get more matches
        getMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetMatchesTask().execute(8);
                ((MatchFragment) getSupportFragmentManager().findFragmentById(R.id.matchFragment)).notifyDataChange();
            }
        });

//        restorePersonsFromJson();
        refreshList();
    }

//    private void openAddPersonActivity() {
//        Intent intent = new Intent(this, AddPersonActivity.class);
//        intent.putExtra(takePhoto, false);
//        startActivityForResult(intent, BUTTON_REQUEST);
//    }

    private void refreshList() {
        MatchListContent.clearList();
        new GetMatchesTask().execute(16);
        ((MatchFragment) getSupportFragmentManager().findFragmentById(R.id.matchFragment)).notifyDataChange();
    }

    private void openAddPersonCamActivity() {
        Intent intent = new Intent(this, AddPersonActivity.class);
        intent.putExtra(takePhoto, true);
        startActivityForResult(intent, BUTTON_REQUEST);
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            if(currentMatch != null)
                displayTaskInFragment(currentMatch);
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

        // TODO refresh
        private void startSecondActivity(MatchListContent.Match match, int position){
        Intent intent = new Intent(this, MatchInfoActivity.class);
        intent.putExtra(taskExtra, match);
        startActivity(intent);
    }

    private void savePersonsToJson() {
//        Gson gson = new Gson();
//        String listJson = gson.toJson(MatchListContent.ITEMS);
//        FileOutputStream outputStream;
//        try {
//            outputStream = openFileOutput(PERSONS_JSON_FILE, MODE_PRIVATE);
//            FileWriter writer = new FileWriter(outputStream.getFD());
//            writer.write(listJson);
//            writer.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private void restorePersonsFromJson() {
//        FileInputStream inputStream;
//        int DEFAULT_BUFFER_SIZE = 10000;
//        Gson gson = new Gson();
//        String readJson;
//
//        try {
//            inputStream = openFileInput(PERSONS_JSON_FILE);
//            FileReader reader = new FileReader(inputStream.getFD());
//            char[] buf = new char[DEFAULT_BUFFER_SIZE];
//            int n;
//            StringBuilder builder = new StringBuilder();
//            while ((n = reader.read(buf)) >= 0) {
//                String tmp = String.valueOf(buf);
//                String substring = (n<DEFAULT_BUFFER_SIZE ? tmp.substring(0, n) : tmp);
//                builder.append(substring);
//            }
//            reader.close();
//            readJson = builder.toString();
//            Type collectionType = new TypeToken<List<MatchListContent.Match>>() {
//            }.getType();
//            List<MatchListContent.Match> o = gson.fromJson(readJson, collectionType);
//            if (o != null) {
//                MatchListContent.clearList();
//                for(MatchListContent.Match match : o) {
//                    MatchListContent.addItem(match);
//                }
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

//    if(requestCode == 1){
//        if(resultCode == RESULT_OK) {
//
//            String name = data.getStringExtra("event");
//            String personDescription = data.getStringExtra("description");
//            String birthday = data.getStringExtra("date");
//            String personPicPath = data.getStringExtra("pic_path");
//            String selectedImage = personPicPath;
//            MatchListContent.Match match;
//            if(name.isEmpty() && personDescription.isEmpty() && birthday.isEmpty()) {
//                match = new MatchListContent.Match("Match."+ MatchListContent.ITEMS.size()+1,
//                        getString(R.string.default_name),
//                        getString(R.string.default_description),
//                        getString(R.string.default_birthday),
//                        selectedImage);
//                MatchListContent.addItem(match);
//            }else {
//                if(name.isEmpty())
//                    name = getString(R.string.default_name);
//                if(personDescription.isEmpty())
//                    personDescription = getString(R.string.default_description);
//                if(birthday.isEmpty())
//                    birthday = getString(R.string.default_birthday);
//                match = new MatchListContent.Match("Movie."+ MatchListContent.ITEMS.size()+1,
//                        name,
//                        personDescription,
//                        birthday,
//                        selectedImage);
//                MatchListContent.addItem(match);
//            }
//            ((MatchFragment) getSupportFragmentManager().findFragmentById(R.id.personFragment)).notifyDataChange();
//
//        }
//    }

}


    private void displayTaskInFragment(MatchListContent.Match match){
        MatchInfoFragment matchInfoFragment = ((MatchInfoFragment) getSupportFragmentManager().findFragmentById(R.id.displayFragment));
        if(match != null){
            matchInfoFragment.displayMatch(match);
        }
    }

    private void showDeleteDialog(){
//        DeleteDialog.newInstance().show(getSupportFragmentManager(),getString(R.string.delete_dialog_tag));
    }

    @Override
    public void onListFragmentClickInteraction(MatchListContent.Match match, int position) {
        currentMatch = match;
        //Toast.makeText(this,getString(R.string.item_selected_msg)+position,Toast.LENGTH_SHORT).show();
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            displayTaskInFragment(match);
        }else {
            startSecondActivity(match, position);
        }
    }

    @Override
    public void onListFragmentDeleteInteraction(int position) {
//        Toast.makeText(this,getString(R.string.long_click_msg)+position,Toast.LENGTH_SHORT).show();
        showDeleteDialog();
        currentItemPosition = position;
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        if(currentItemPosition != -1 && currentItemPosition < MatchListContent.ITEMS.size()){
            MatchListContent.removeItem(currentItemPosition);
            ((MatchFragment) getSupportFragmentManager().findFragmentById(R.id.matchFragment)).notifyDataChange();
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        View v = findViewById(R.id.addButton);


    }

    private class GetMatchesTask extends AsyncTask<Integer, Integer, Boolean> {
        protected Boolean doInBackground(Integer... cnt) {
            int count = cnt[0];
            int offset = MatchListContent.ITEMS.size();
            // get matches
            String jsonString = "";
            try {
                URL url = new URL(
                "http://aligulac.com/api/v1/match/?format=json&order_by=-date&offline=true"
                        + "&limit=" + count + "&offset=" + offset
                        + "&apikey=gmWATUigLKGlr5EfakDZ"
                );
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader r = new BufferedReader(new InputStreamReader(in));
                    StringBuilder total = new StringBuilder();
                    for (String line; (line = r.readLine()) != null; ) {
                        total.append(line).append('\n');
                    }
                    jsonString = total.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            // read JSON
            try {
                JSONObject response = new JSONObject(jsonString);
                JSONArray matches = response.getJSONArray("objects");
                // TODO DOKOŃCZ to + jeśli już mamy pobrane rzeczy to offset +
                // TODO do tego refresh ktory pod drugim przyciskiem wywala wszystko i na nowo 16 wciaga
                // daj to do oncreate i na przyciski getmore i refresh
                // + asynctask do brania statystyk gracza
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
                    }

                    MatchListContent.Match match = new MatchListContent.Match(
                            "" + MatchListContent.ITEMS.size()+1, event, date,
                            p1_name, p1_race, p1_id,
                            p2_name, p2_race, p2_id,
                            score);
                    MatchListContent.addItem(match);
                    publishProgress((int) ((i / (float) len) * 100));
                    if (isCancelled()) break;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        }

        protected void onProgressUpdate(Integer... progress) {
//            setProgressPercent(progress[0]);
        }

        protected void onPostExecute(Boolean result) {
//            showDialog("Downloaded " + result + " bytes");
        }
    }

}
