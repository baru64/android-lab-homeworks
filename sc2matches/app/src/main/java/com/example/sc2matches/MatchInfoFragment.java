package com.example.sc2matches;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sc2matches.persons.MatchListContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class MatchInfoFragment extends Fragment {

    public static final int REQUEST_IMAGE_CAPTURE = 1; // request code for image capture
    private String mCurrentPhotoPath; // String used to save the path of the picture
    private MatchListContent.Match mDisplayedMatch;

    private File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = mDisplayedMatch.event + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public MatchInfoFragment() {
        // Required empty public constructor
    }

    // TODO display statystyki meczu
    public void displayMatch(MatchListContent.Match match){
        FragmentActivity activity = getActivity();

        (activity.findViewById(R.id.displayFragment)).setVisibility(View.VISIBLE);

        TextView player1Name = activity.findViewById(R.id.player1Name);
        ImageView player1Race = activity.findViewById(R.id.player1Race);
        TextView player1Rating = activity.findViewById(R.id.player1Rating);
        TextView vsPlayer2Race = activity.findViewById(R.id.vsPlayer2Race);

        TextView player2Name = activity.findViewById(R.id.player2Name);
        ImageView player2Race = activity.findViewById(R.id.player2Race);
        TextView player2Rating = activity.findViewById(R.id.player2Rating);
        TextView vsPlayer1Race = activity.findViewById(R.id.vsPlayer1Race);

        TextView eventName = activity.findViewById(R.id.infoEvent);
        TextView score = activity.findViewById(R.id.infoScore);
        TextView date = activity.findViewById(R.id.infoDate);

        eventName.setText(match.event);
        score.setText(match.score);
        date.setText(match.date);

        player1Name.setText(match.p1_name);
        player2Name.setText(match.p2_name);

        // get aligulac data

        int iPlayer1Rating = 0;
        int iPlayer2Rating = 0;
        new GetStatsTask().execute(match.p1_id, match.p2_id);

        // ---

        player1Rating.setText("Rating:" + iPlayer1Rating);
        player2Rating.setText("Rating:" + iPlayer2Rating);
        vsPlayer1Race.setText("vs " + match.p1_race + ":\n");
        vsPlayer2Race.setText("vs " + match.p2_race + ":\n" );


        switch(match.p1_race) {
            case "Zerg":
                player1Race.setImageResource(R.drawable.zicon_small);
                break;
            case "Protoss":
                player1Race.setImageResource(R.drawable.ticon_small);
                break;
            case "Terran":
                player1Race.setImageResource(R.drawable.picon_small);
                break;
        }

        switch(match.p2_race) {
            case "Zerg":
                player2Race.setImageResource(R.drawable.zicon_small);
                break;
            case "Protoss":
                player2Race.setImageResource(R.drawable.ticon_small);
                break;
            case "Terran":
                player2Race.setImageResource(R.drawable.picon_small);
                break;
        }

        mDisplayedMatch = match;
    }

    private void setStats() {
        // TODO
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
//        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
//            FragmentActivity holdingActivity = getActivity();
//            if(holdingActivity != null){
//                ImageView taskImage = holdingActivity.findViewById(R.id.player1Race);
//                Bitmap cameraImage = PicUtils.decodePic(mCurrentPhotoPath,
//                        taskImage.getWidth(),
//                        taskImage.getHeight());
//
//                taskImage.setImageBitmap(cameraImage);
//                mDisplayedMatch.setPicPath(mCurrentPhotoPath);
//
//                MatchListContent.Match match = MatchListContent.ITEM_MAP.get(mDisplayedMatch.id);
//                if(match !=null){
//                    match.setPicPath(mCurrentPhotoPath);
//                }
//                if(holdingActivity instanceof MainActivity){
//                    ((MatchFragment)holdingActivity.getSupportFragmentManager().findFragmentById(R.id.personFragment)).notifyDataChange();
//
//                }else if(holdingActivity instanceof MatchInfoActivity){
//                    ((MatchInfoActivity)holdingActivity).setImgChanged(true);
//                }
//            }
//        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        FragmentActivity activity = getActivity();

        activity.findViewById(R.id.displayFragment).setVisibility(View.INVISIBLE);

        Intent intent = getActivity().getIntent();
        // TODO jak nie ma intentu, my mamy intent tutaj gdy chcemy refresh uzywac, w innym wypadku statystyki
        if(intent != null){
            MatchListContent.Match receivedMatch = intent.getParcelableExtra(MainActivity.taskExtra);
            if(receivedMatch != null){
                displayMatch(receivedMatch);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_match_info, container, false);
    }

    private class GetStatsTask extends AsyncTask<Integer, Integer, MatchDetails> {
        protected MatchDetails doInBackground(Integer... ids) {

            HttpHandler sh = new HttpHandler();
            String url1 = "http://aligulac.com/api/v1/player/"
                    + ids[0]
                    +"/?format=json&apikey=gmWATUigLKGlr5EfakDZ";
            String url2 = "http://aligulac.com/api/v1/player/"
                    + ids[1]
                    +"/?format=json&apikey=gmWATUigLKGlr5EfakDZ";
            String jsonStringP1 = sh.makeServiceCall(url1);
            Log.e("Match Stats", "Response from url: " + jsonStringP1);
            String jsonStringP2 = sh.makeServiceCall(url2);
            Log.e("Match Stats", "Response from url: " + jsonStringP2);

            // read JSON
            double player1Rating = 0.0;
            double player2Rating = 0.0;
            double vsPlayer1Race = 0.0;
            double vsPlayer2Race = 0.0;
            String player1Race;
            String player2Race;
            String player1Country = "";
            String player2Country = "";
            try {
                JSONObject responsePlayer1 = new JSONObject(jsonStringP1);
                JSONObject responsePlayer2 = new JSONObject(jsonStringP2);

                player1Race = responsePlayer1.getString("race");
                player2Race = responsePlayer2.getString("race");
                player1Country = responsePlayer1.getString("country");
                player2Country = responsePlayer2.getString("country");


                JSONObject player1Form = responsePlayer1.getJSONObject("form");
                JSONObject player2Form = responsePlayer2.getJSONObject("form");


                JSONArray player1MatchesArray= player1Form.getJSONArray(player2Race);
                int player1WonMatches = player1MatchesArray.getInt(0);
                int player1LostMatches = player1MatchesArray.getInt(1);
                vsPlayer2Race = Double.valueOf(player1WonMatches) /
                                Double.valueOf(player1WonMatches + player1LostMatches);

                JSONArray player2MatchesArray= player2Form.getJSONArray(player1Race);
                int player2WonMatches = player2MatchesArray.getInt(0);
                int player2LostMatches = player2MatchesArray.getInt(1);
                vsPlayer1Race = Double.valueOf(player2WonMatches) /
                                Double.valueOf(player2WonMatches + player2LostMatches);

                player1Rating = (responsePlayer1.getJSONObject("current_rating")
                        .getDouble("rating")+1) * 1000.0;
                player2Rating = (responsePlayer2.getJSONObject("current_rating")
                        .getDouble("rating")+1) * 1000.0;

            } catch (JSONException e) {
                e.printStackTrace();
            }
            MatchDetails matchDetails = new MatchDetails();
            matchDetails.player1Rating = player1Rating;
            matchDetails.player2Rating = player2Rating;
            matchDetails.vsPlayer1Race = vsPlayer1Race;
            matchDetails.vsPlayer2Race = vsPlayer2Race;
            matchDetails.player1Country = player1Country;
            matchDetails.player2Country = player2Country;
            return matchDetails;
        }

        protected void onProgressUpdate(Integer... progress) {
//            setProgressPercent(progress[0]);
        }

        protected void onPostExecute(MatchDetails matchDetails) {
            FragmentActivity activity = getActivity();
            TextView player1Rating = activity.findViewById(R.id.player1Rating);
            TextView vsPlayer2Race = activity.findViewById(R.id.vsPlayer2Race);
            TextView player2Rating = activity.findViewById(R.id.player2Rating);
            TextView vsPlayer1Race = activity.findViewById(R.id.vsPlayer1Race);
            TextView player1Country = activity.findViewById(R.id.player1Country);
            TextView player2Country = activity.findViewById(R.id.player2Country);

            player1Rating.setText("Rating:\n" + matchDetails.player1Rating.intValue());
            player2Rating.setText("Rating:\n" + matchDetails.player2Rating.intValue());
            vsPlayer1Race.append((int)(matchDetails.vsPlayer1Race*100)+"%");
            vsPlayer2Race.append((int)(matchDetails.vsPlayer2Race*100)+"%");
            player1Country.setText("Country:\n" + matchDetails.player1Country);
            player2Country.setText("Country:\n" + matchDetails.player2Country);
        }
    }

    private class MatchDetails {
        public Double player1Rating;
        public Double player2Rating;
        public Double vsPlayer1Race;
        public Double vsPlayer2Race;
        public String player1Country;
        public String player2Country;
        MatchDetails(Double p1rating,Double p2rating,Double vsP1race,Double vsP2race,
                     String p1country,String p2country) {
            player1Rating = p1rating;
            player2Rating = p2rating;
            vsPlayer1Race = vsP1race;
            vsPlayer2Race = vsP2race;
            player1Country = p1country;
            player2Country = p2country;
        }
        MatchDetails() {

        }
    }
}

