package com.example.homework2w1j;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class AddPersonActivity extends AppCompatActivity {
    public static final int BUTTON_REQUEST = 1;
    public static Boolean take_photo;
    private String currentPhotoPath = "";
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
        String[] image_array = getResources().getStringArray(R.array.images);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                take_photo = null;
            } else {
                take_photo = extras.getBoolean("takePhoto");
            }
        } else {
            take_photo = (Boolean) savedInstanceState.getSerializable("takePhoto");
        }
        if(take_photo){
            makePhoto();
        }else{
            currentPhotoPath = image_array[new Random().nextInt(image_array.length)];
        }

        Button but_ADD = findViewById(R.id.addButton);
        but_ADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText addNameEditTxt = findViewById(R.id.addName);
                EditText addDescriptionEditTxt = findViewById(R.id.addPersonDescription);
                EditText addBirthdayEditTxt = findViewById(R.id.addPersonBirthday);
                String addName = addNameEditTxt.getText().toString();
                String addDescription = addDescriptionEditTxt.getText().toString();
                String addBirthday = addBirthdayEditTxt.getText().toString();

                sendToMainActivity(addName, addDescription, addBirthday);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(),0);
            }
        });
    }

    public void makePhoto(){
        String path = "";
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        getString(R.string.myFileprovider),
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }

    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    public void openMainActivity() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    public void sendToMainActivity(String name, String description, String birthday) {
        Intent intent = new Intent();
        intent.putExtra("name", name);
        intent.putExtra("description", description);
        intent.putExtra("birthday", birthday);
        intent.putExtra("pic_path", currentPhotoPath);
        setResult(RESULT_OK, intent);
        finish();
    }
}
