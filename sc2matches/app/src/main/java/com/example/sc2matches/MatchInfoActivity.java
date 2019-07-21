package com.example.sc2matches;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MatchInfoActivity extends AppCompatActivity {
    public static String DATA_CHANGED_KEY = "dataSetChanged";
//    private boolean imgChanged;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_info);
//        imgChanged = false;
    }
//    public void setImgChanged(boolean val){
//        imgChanged = val;
//    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}

