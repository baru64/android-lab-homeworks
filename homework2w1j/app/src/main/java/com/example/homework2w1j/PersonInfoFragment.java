package com.example.homework2w1j;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.homework2w1j.persons.PersonListContent;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class PersonInfoFragment extends Fragment implements View.OnClickListener {

    public static final int REQUEST_IMAGE_CAPTURE = 1; // request code for image capture
    private String mCurrentPhotoPath; // String used to save the path of the picture
    private PersonListContent.Person mDisplayedPerson;

    private File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = mDisplayedPerson.title + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public PersonInfoFragment() {
        // Required empty public constructor
    }

    public void displayTask(PersonListContent.Person person){
        FragmentActivity activity = getActivity();

        (activity.findViewById(R.id.displayFragment)).setVisibility(View.VISIBLE);

        TextView taskInfoTitle = activity.findViewById(R.id.taskInfoTitle);
        TextView taskInfoDescription = activity.findViewById(R.id.taskInfoDescription);
        final ImageView taskInfoImage = activity.findViewById(R.id.taskInfoImage);

        taskInfoTitle.setText(person.title);
        taskInfoDescription.setText(person.details);
        if(person.picPath != null && !person.picPath.isEmpty()){
            if(person.picPath.contains("drawable")){
                Drawable taskDrawable;
                switch(person.picPath){
                    case "drawable 1":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.circle_drawable_green);
                        break;
                    case "drawable 2":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.circle_drawable_orange);
                        break;
                    case "drawable 3":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.circle_drawable_red);
                        break;
                    default:
                        taskDrawable = activity.getResources().getDrawable(R.drawable.circle_drawable_green);
                }
                taskInfoImage.setImageDrawable(taskDrawable);
        }else {
                Handler handler = new Handler();
                taskInfoImage.setVisibility(View.INVISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        taskInfoImage.setVisibility(View.VISIBLE);
                        Bitmap cameraImage = PicUtils.decodePic(mDisplayedPerson.picPath,
                                taskInfoImage.getWidth(),
                                taskInfoImage.getHeight());
                        taskInfoImage.setImageBitmap(cameraImage);
                    }
                }, 200);
            }
        }else{
            taskInfoImage.setImageDrawable(activity.getResources().getDrawable(R.drawable.circle_drawable_green));
        }
        mDisplayedPerson = person;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            FragmentActivity holdingActivity = getActivity();
            if(holdingActivity != null){
                ImageView taskImage = holdingActivity.findViewById(R.id.taskInfoImage);
                Bitmap cameraImage = PicUtils.decodePic(mCurrentPhotoPath,
                        taskImage.getWidth(),
                        taskImage.getHeight());

                taskImage.setImageBitmap(cameraImage);
                mDisplayedPerson.setPicPath(mCurrentPhotoPath);

                PersonListContent.Person person = PersonListContent.ITEM_MAP.get(mDisplayedPerson.id);
                if(person !=null){
                    person.setPicPath(mCurrentPhotoPath);
                }
                if(holdingActivity instanceof MainActivity){
                    ((PersonFragment)holdingActivity.getSupportFragmentManager().findFragmentById(R.id.taskFragment)).notifyDataChange();

                }else if(holdingActivity instanceof PersonInfoActivity){
                    ((PersonInfoActivity)holdingActivity).setImgChanged(true);
                }
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        FragmentActivity activity = getActivity();

        activity.findViewById(R.id.displayFragment).setVisibility(View.INVISIBLE);
        activity.findViewById(R.id.taskInfoImage).setOnClickListener(this);

        Intent intent = getActivity().getIntent();
        if(intent != null){
            PersonListContent.Person receivedPerson = intent.getParcelableExtra(MainActivity.taskExtra);
            if(receivedPerson != null){
                displayTask(receivedPerson);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_person_info, container, false);
    }

    @Override
    public void onClick(View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null){
            File photoFile = null;
            try{
                photoFile = createImageFile();
            }catch (IOException ex){

            }
            if(photoFile != null){
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        getString(R.string.myFileprovider),
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);
            }
        }
    }

}

