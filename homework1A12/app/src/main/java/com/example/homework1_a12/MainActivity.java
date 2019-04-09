package com.example.homework1_a12;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public String currentContact = "John Smith";
    public int currentSoundId = 0;
    public static final String SOUND_ID = "sound id";
    public static final String CONTACT_NAME = "contact id";
    public static final int CONTACT_REQUEST = 1;
    public static final int SOUND_REQUEST = 2;

    private static int[] contactImages = {  R.drawable.pic1,
                                            R.drawable.pic2,
                                            R.drawable.pic3,
                                            R.drawable.pic4,
                                            R.drawable.pic5 };

    private MediaPlayer buttonPlayer;
    static public Uri[] sounds;
    boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sounds = new Uri[5];
        sounds[0]  =  Uri.parse("android.resource://"  +  getPackageName()  +  "/"  + R.raw.mario);
        sounds[1]  =  Uri.parse("android.resource://"  +  getPackageName()  +  "/"  + R.raw.ring01);
        sounds[2]  =  Uri.parse("android.resource://"  + getPackageName()  +  "/"  + R.raw.ring02);
        sounds[3]  =  Uri.parse("android.resource://"  +  getPackageName()  +  "/"  + R.raw.ring03);
        sounds[4]  =  Uri.parse("android.resource://"  +  getPackageName()  +  "/"  + R.raw.ring04);

        buttonPlayer = new MediaPlayer();
        buttonPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        Button contactButton = findViewById(R.id.contactButton);
        Button soundButton = findViewById(R.id.soundButton);
        FloatingActionButton playButton = findViewById(R.id.playButton);
        ImageView contactImage = findViewById(R.id.contactImage);
        contactImage.setImageResource(contactImages[(int)(4 * Math.random())]);

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

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isPlaying) {
                    isPlaying = true;
                    buttonPlayer.reset();
                    try {
                        buttonPlayer.setDataSource(getApplicationContext(), sounds[currentSoundId]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    buttonPlayer.prepareAsync();
                } else {
                    buttonPlayer.reset();
                    isPlaying = false;
                }
            }
        });

        buttonPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                isPlaying = false;
            }
        });

        buttonPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        buttonPlayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onActivityResult(int  requestCode,  int  resultCode,  Intent data) {
        if(resultCode == RESULT_OK) {
            if(requestCode == CONTACT_REQUEST) {
                currentContact = data.getStringExtra(CONTACT_NAME);
                TextView contactText = findViewById(R.id.textView);
                contactText.setText(currentContact);
            } else if(requestCode == SOUND_REQUEST) {
                currentSoundId = data.getIntExtra(SOUND_ID, 0);
            }
        }
        ImageView contactImage = (ImageView) findViewById(R.id.contactImage);
        contactImage.setImageResource(contactImages[(int)(4 * Math.random())]);
    }
}