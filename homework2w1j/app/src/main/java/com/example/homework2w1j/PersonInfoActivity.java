package com.example.homework2w1j;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class PersonInfoActivity extends AppCompatActivity {
    public static String DATA_CHANGED_KEY = "dataSetChanged";
    private boolean imgChanged;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        imgChanged = false;
    }
    public void setImgChanged(boolean val){
        imgChanged = val;
    }
    @Override
    public void onBackPressed(){
        setResult(RESULT_OK,new Intent().putExtra(DATA_CHANGED_KEY,imgChanged));
        super.onBackPressed();
    }
}

