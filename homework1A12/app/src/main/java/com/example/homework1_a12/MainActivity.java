package com.example.homework1_a12;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public int currentContact = 0;
    public int currentSoundId = 0;
    public static final String SOUND_ID = "sound id";
    public static final String CONTACT_NAME = "contact id";
    public static final int CONTACT_REQUEST = 1;
    public static final int SOUND_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ImageView contactImage = (ImageView) findViewById(R.id.contactImage);
//        contactImage.setImageDrawable();

        Button contactButton = (Button) findViewById(R.id.contactButton);
        Button soundButton = (Button) findViewById(R.id.soundButton);

        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contactPick = new Intent(getApplicationContext(),change_contact.class);
                contactPick.putExtra(CONTACT_NAME,currentContact);
                startActivityForResult(contactPick, CONTACT_REQUEST);
            }
        });

        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent soundPick = new Intent(getApplicationContext(),change_sound.class);
                soundPick.putExtra(SOUND_ID,currentSoundId);
                startActivityForResult(soundPick, SOUND_REQUEST);
            }
        });
    }
}
