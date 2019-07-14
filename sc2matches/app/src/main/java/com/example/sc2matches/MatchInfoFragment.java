package com.example.sc2matches;


import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sc2matches.persons.MatchListContent;

import java.io.File;
import java.io.IOException;
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
        int iPlayer1Rating = 2750;
        int iVsPlayer2Race = 60;
        int iPlayer2Rating = 2550;
        int iVsPlayer1Race = 50;
        // ---

        player1Rating.setText("Rating:" + iPlayer1Rating);
        player2Rating.setText("Rating:" + iPlayer2Rating);
        vsPlayer1Race.setText("vs " + match.p1_race + ": " + iVsPlayer1Race);
        vsPlayer2Race.setText("vs " + match.p2_race + ": " + iVsPlayer2Race);


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


}

