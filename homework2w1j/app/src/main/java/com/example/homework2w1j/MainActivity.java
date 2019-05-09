package com.example.homework2w1j;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.homework2w1j.persons.PersonListContent;

import static com.example.homework2w1j.AddPersonActivity.BUTTON_REQUEST;

public class MainActivity extends AppCompatActivity
        implements
        PersonFragment.OnListFragmentInteractionListener,
        DeleteDialog.OnDeleteDialogInteractionListener{

    public static final String taskExtra = "taskExtra";
    private int currentItemPosition = -1;
    private PersonListContent.Person currentPerson;
    private final String CURRENT_PERSON_KEY = "CurrentTask";
    public static final String takePhoto = "takePhoto";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            currentPerson = savedInstanceState.getParcelable(CURRENT_PERSON_KEY);
        }

        FloatingActionButton addPersonButton = findViewById(R.id.addPersonButton);
        FloatingActionButton addPersonCamButton = findViewById(R.id.addPersonCamButton);

        addPersonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddPersonActivity();
            }
        });

        addPersonCamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddPersonCamActivity();
            }
        });
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
            if(currentPerson != null)
                displayTaskInFragment(currentPerson);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (currentPerson != null)
            outState.putParcelable(CURRENT_PERSON_KEY, currentPerson);
        super.onSaveInstanceState(outState);
    }

        private void startSecondActivity(PersonListContent.Person person, int position){
        Intent intent = new Intent(this, PersonInfoActivity.class);
        intent.putExtra(taskExtra, person);
        startActivity(intent);
    }

//    public void addClick(View view) {
//        EditText taskTitleEditText = findViewById(R.id.taskTitle);
//        EditText taskDescriptionEditText = findViewById(R.id.taskDescription);
//        Spinner drawableSpinner = findViewById(R.id.drawableSpinner);
//        String taskTitle = taskTitleEditText.getText().toString();
//        String taskDescription = taskDescriptionEditText.getText().toString();
//        String selectedImage = drawableSpinner.getSelectedItem().toString();
//
//        if(taskTitle.isEmpty() && taskDescription.isEmpty()){
//            PersonListContent.addItem(new PersonListContent.Person("Person."+ PersonListContent.ITEMS.size()+1,
//                    getString(R.string.default_name),
//                    getString(R.string.default_description),
//                    selectedImage));
//        }else{
//            if(taskTitle.isEmpty())
//                taskTitle = getString(R.string.default_name);
//            if(taskDescription.isEmpty())
//                taskDescription = getString(R.string.default_description);
//            PersonListContent.addItem(new PersonListContent.Person("Person."+ PersonListContent.ITEMS.size()+1,
//                    taskTitle,
//                    taskDescription,
//                    selectedImage));
//        }
//
//        ((PersonFragment) getSupportFragmentManager().findFragmentById(R.id.taskFragment)).notifyDataChange();
//
//        taskTitleEditText.setText("");
//        taskDescriptionEditText.setText("");
//
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(view.getWindowToken(),0);
//    }
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    if(requestCode == 1){
        if(resultCode == RESULT_OK) {

            String name = data.getStringExtra("name");
            String personDescription = data.getStringExtra("description");
            String birthday = data.getStringExtra("birthday");
            String personPicPath = data.getStringExtra("pic_path");
            String selectedImage = personPicPath;
            PersonListContent.Person person;
            if(name.isEmpty() && personDescription.isEmpty() && birthday.isEmpty()) {
                person = new PersonListContent.Person("Person."+ PersonListContent.ITEMS.size()+1,
                        getString(R.string.default_name),
                        getString(R.string.default_description),
                        getString(R.string.default_birthday),
                        selectedImage);
                PersonListContent.addItem(person);
            }else {
                if(name.isEmpty())
                    name = getString(R.string.default_name);
                if(personDescription.isEmpty())
                    personDescription = getString(R.string.default_description);
                if(birthday.isEmpty())
                    birthday = getString(R.string.default_birthday);
                person = new PersonListContent.Person("Movie."+ PersonListContent.ITEMS.size()+1,
                        name,
                        personDescription,
                        birthday,
                        selectedImage);
                PersonListContent.addItem(person);
            }
            ((PersonFragment) getSupportFragmentManager().findFragmentById(R.id.personFragment)).notifyDataChange();

        }
    }

}


    private void displayTaskInFragment(PersonListContent.Person person){
        PersonInfoFragment personInfoFragment = ((PersonInfoFragment) getSupportFragmentManager().findFragmentById(R.id.displayFragment));
        if(person != null){
            personInfoFragment.displayPerson(person);
        }
    }

    private void showDeleteDialog(){
        DeleteDialog.newInstance().show(getSupportFragmentManager(),getString(R.string.delete_dialog_tag));
    }

    @Override
    public void onListFragmentClickInteraction(PersonListContent.Person person, int position) {
        currentPerson = person;
        Toast.makeText(this,getString(R.string.item_selected_msg)+position,Toast.LENGTH_SHORT).show();
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            displayTaskInFragment(person);
        }else {
            startSecondActivity(person, position);
        }
    }

    @Override
    public void onListFragmentDeleteInteraction(int position) {
        Toast.makeText(this,getString(R.string.long_click_msg)+position,Toast.LENGTH_SHORT).show();
        showDeleteDialog();
        currentItemPosition = position;
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        if(currentItemPosition != -1 && currentItemPosition < PersonListContent.ITEMS.size()){
            PersonListContent.removeItem(currentItemPosition);
            ((PersonFragment) getSupportFragmentManager().findFragmentById(R.id.personFragment)).notifyDataChange();
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        View v = findViewById(R.id.addButton);
        if(v != null){
            Snackbar.make(v,getString(R.string.delete_cancel_msg),Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.retry_msg), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showDeleteDialog();
                        }
                    });
        }

    }
}
