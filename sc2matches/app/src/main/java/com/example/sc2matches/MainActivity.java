package com.example.sc2matches;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.sc2matches.persons.MatchListContent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import static com.example.sc2matches.AddPersonActivity.BUTTON_REQUEST;

public class MainActivity extends AppCompatActivity
        implements
        MatchFragment.OnListFragmentInteractionListener,
        DeleteDialog.OnDeleteDialogInteractionListener{

    public static final String taskExtra = "taskExtra";
    private int currentItemPosition = -1;
    private MatchListContent.Match currentMatch;
    private final String CURRENT_PERSON_KEY = "CurrentTask";
    public static final String takePhoto = "takePhoto";

    private final String CHECKED_ID = "checkedId";
    private final String PERSONS_JSON_FILE = "persons.json";
    private final String NUM_PERSONS = "NumOfPersons";
    private final String PERSON = "person_";
    private final String BIRTHDAY = "birthday_";
    private final String DETAIL = "desc_";
    private final String PIC = "pic_";
    private final String ID = "id_";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            currentMatch = savedInstanceState.getParcelable(CURRENT_PERSON_KEY);
        }

        FloatingActionButton addPersonButton = findViewById(R.id.addPersonButton);
//        FloatingActionButton addPersonCamButton = findViewById(R.id.addPersonCamButton);

        // TODO add -> refresh
        addPersonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddPersonActivity();
            }
        });

        // TODO wywalić to
//        addPersonCamButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openAddPersonCamActivity();
//            }
//        });

//        restorePersonsFromJson();
    }

    private void openAddPersonActivity() {
        Intent intent = new Intent(this, AddPersonActivity.class);
        intent.putExtra(takePhoto, false);
        startActivityForResult(intent, BUTTON_REQUEST);
    }

    private void openAddPersonCamActivity() {
        Intent intent = new Intent(this, AddPersonActivity.class);
        intent.putExtra(takePhoto, true);
        startActivityForResult(intent, BUTTON_REQUEST);
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            if(currentMatch != null)
                displayTaskInFragment(currentMatch);
        }
    }

    @Override
    protected void onDestroy() {
//        savePersonsToJson();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (currentMatch != null)
            outState.putParcelable(CURRENT_PERSON_KEY, currentMatch);
        super.onSaveInstanceState(outState);
    }

        // TODO refresh
        private void startSecondActivity(MatchListContent.Match match, int position){
        Intent intent = new Intent(this, MatchInfoActivity.class);
        intent.putExtra(taskExtra, match);
        startActivity(intent);
    }

    private void savePersonsToJson() {
//        Gson gson = new Gson();
//        String listJson = gson.toJson(MatchListContent.ITEMS);
//        FileOutputStream outputStream;
//        try {
//            outputStream = openFileOutput(PERSONS_JSON_FILE, MODE_PRIVATE);
//            FileWriter writer = new FileWriter(outputStream.getFD());
//            writer.write(listJson);
//            writer.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private void restorePersonsFromJson() {
//        FileInputStream inputStream;
//        int DEFAULT_BUFFER_SIZE = 10000;
//        Gson gson = new Gson();
//        String readJson;
//
//        try {
//            inputStream = openFileInput(PERSONS_JSON_FILE);
//            FileReader reader = new FileReader(inputStream.getFD());
//            char[] buf = new char[DEFAULT_BUFFER_SIZE];
//            int n;
//            StringBuilder builder = new StringBuilder();
//            while ((n = reader.read(buf)) >= 0) {
//                String tmp = String.valueOf(buf);
//                String substring = (n<DEFAULT_BUFFER_SIZE ? tmp.substring(0, n) : tmp);
//                builder.append(substring);
//            }
//            reader.close();
//            readJson = builder.toString();
//            Type collectionType = new TypeToken<List<MatchListContent.Match>>() {
//            }.getType();
//            List<MatchListContent.Match> o = gson.fromJson(readJson, collectionType);
//            if (o != null) {
//                MatchListContent.clearList();
//                for(MatchListContent.Match match : o) {
//                    MatchListContent.addItem(match);
//                }
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

//    if(requestCode == 1){
//        if(resultCode == RESULT_OK) {
//
//            String name = data.getStringExtra("event");
//            String personDescription = data.getStringExtra("description");
//            String birthday = data.getStringExtra("date");
//            String personPicPath = data.getStringExtra("pic_path");
//            String selectedImage = personPicPath;
//            MatchListContent.Match match;
//            if(name.isEmpty() && personDescription.isEmpty() && birthday.isEmpty()) {
//                match = new MatchListContent.Match("Match."+ MatchListContent.ITEMS.size()+1,
//                        getString(R.string.default_name),
//                        getString(R.string.default_description),
//                        getString(R.string.default_birthday),
//                        selectedImage);
//                MatchListContent.addItem(match);
//            }else {
//                if(name.isEmpty())
//                    name = getString(R.string.default_name);
//                if(personDescription.isEmpty())
//                    personDescription = getString(R.string.default_description);
//                if(birthday.isEmpty())
//                    birthday = getString(R.string.default_birthday);
//                match = new MatchListContent.Match("Movie."+ MatchListContent.ITEMS.size()+1,
//                        name,
//                        personDescription,
//                        birthday,
//                        selectedImage);
//                MatchListContent.addItem(match);
//            }
//            ((MatchFragment) getSupportFragmentManager().findFragmentById(R.id.personFragment)).notifyDataChange();
//
//        }
//    }

}


    private void displayTaskInFragment(MatchListContent.Match match){
        MatchInfoFragment matchInfoFragment = ((MatchInfoFragment) getSupportFragmentManager().findFragmentById(R.id.displayFragment));
        if(match != null){
            matchInfoFragment.displayMatch(match);
        }
    }

    private void showDeleteDialog(){
//        DeleteDialog.newInstance().show(getSupportFragmentManager(),getString(R.string.delete_dialog_tag));
    }

    @Override
    public void onListFragmentClickInteraction(MatchListContent.Match match, int position) {
        currentMatch = match;
        //Toast.makeText(this,getString(R.string.item_selected_msg)+position,Toast.LENGTH_SHORT).show();
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            displayTaskInFragment(match);
        }else {
            startSecondActivity(match, position);
        }
    }

    @Override
    public void onListFragmentDeleteInteraction(int position) {
//        Toast.makeText(this,getString(R.string.long_click_msg)+position,Toast.LENGTH_SHORT).show();
        showDeleteDialog();
        currentItemPosition = position;
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        if(currentItemPosition != -1 && currentItemPosition < MatchListContent.ITEMS.size()){
            MatchListContent.removeItem(currentItemPosition);
            ((MatchFragment) getSupportFragmentManager().findFragmentById(R.id.personFragment)).notifyDataChange();
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        View v = findViewById(R.id.addButton);
//        if(v != null){
//            Snackbar.make(v,getString(R.string.delete_cancel_msg),Snackbar.LENGTH_LONG)
//                    .setAction(getString(R.string.retry_msg), new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            showDeleteDialog();
//                        }
//                    });
//        }

    }
}
