package com.example.homework1_a12;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class change_contact extends AppCompatActivity {

    private String selected_contact = "";
    private String current_contact = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_contact);

        Intent received_intent = getIntent();
        current_contact = received_intent.getStringExtra(MainActivity.CONTACT_NAME);
    }

    public void setContactClick(View v) {
        Intent data = new Intent();
        Spinner contact_selector = findViewById(R.id.spinner);
        selected_contact = contact_selector.getSelectedItem().toString();
        data.putExtra(MainActivity.CONTACT_NAME,selected_contact);
        setResult(RESULT_OK, data);
        finish();
    }

    public void cancelClick(View v) {
        Intent data = new Intent();
        data.putExtra(MainActivity.CONTACT_NAME,current_contact);
        setResult(RESULT_OK, data);
        finish();
    }
}
