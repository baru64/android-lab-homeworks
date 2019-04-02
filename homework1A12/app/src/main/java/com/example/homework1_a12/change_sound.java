package com.example.homework1_a12;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class change_sound extends AppCompatActivity {

    private int selected_sound = 0;
    private int current_sound = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_sound);

        Intent received_intent = getIntent();
        current_sound = received_intent.getIntExtra(MainActivity.SOUND_ID, 0);
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        if (checked) {
            RadioGroup group = (RadioGroup) findViewById(R.id.radioGroup);
            switch (view.getId()){
                case R.id.radioButton: selected_sound = 0;break;
                case R.id.radioButton2: selected_sound = 1;break;
                case R.id.radioButton3: selected_sound = 2;break;
                case R.id.radioButton4: selected_sound = 3;break;
                case R.id.radioButton5: selected_sound = 4;break;
            }
        }
    }

    public void setSoundClick(View v) {
        Intent data = new Intent();
        data.putExtra(MainActivity.SOUND_ID,selected_sound);
        setResult(RESULT_OK, data);
        finish();
    }

    public void cancelClick(View v) {
        Intent data = new Intent();
        data.putExtra(MainActivity.SOUND_ID,current_sound);
        setResult(RESULT_OK, data);
        finish();
    }
}
