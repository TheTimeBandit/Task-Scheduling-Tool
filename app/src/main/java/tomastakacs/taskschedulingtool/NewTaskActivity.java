package tomastakacs.taskschedulingtool;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NewTaskActivity extends AppCompatActivity{

    private Task newTask = null;

    private CustomDatePickerFragment datePickerFragment = null;
    private CustomTimePickerFragment timePickerFragment = null;

    // gettery
    CustomDatePickerFragment getDatePickerFragment() {
        return datePickerFragment;
    }

    CustomTimePickerFragment getTimePickerFragment() {
        return timePickerFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_edit);

        createPickers();
        newTask = new Task();
        addListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_newtask_activity_task_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_addtask) {
            if (datePickerFragment!=null && timePickerFragment!=null) {
                String inputDeadlineDate = datePickerFragment.getPickedDate();
                String inputDeadlineTime = timePickerFragment.getPickedTime();
                newTask.setDeadlineDate(inputDeadlineDate);
                newTask.setDeadlineTime(inputDeadlineTime);
            }

            ArrayList<Task> tasksList = TSTApplication.getInstance().getTaskArrayList();
            tasksList.add(newTask);
            TSTApplication.getInstance().getTasksListViewAdapter().notifyDataSetChanged();
            InternalStorageHandler.getInstance().saveTasksList(this, tasksList);

            onBackPressed();
            Toast.makeText(getApplicationContext(), "Task added", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void addListeners(){
        // find Views
        EditText editTextTitle = findViewById(R.id.editTextTitle);
        EditText editTextNotes = findViewById(R.id.editTextNotes);
        Button buttonSetDeadlineDate = findViewById(R.id.buttonDeadlineDate);
        Button buttonSetDeadlineTime = findViewById(R.id.buttonDeadlineTime);
        ImageView imageViewDeadlineClear = findViewById(R.id.imageViewDeadlineClear);

        // create Listeners
        View.OnClickListener onClickListener = createOnClickListener();
        TextWatcher textWatcherTitle = createTextWatcherTitle();
        TextWatcher textWatcherNotes = createTextWatcherNotes();

        // sets Listeners to Views
        editTextTitle.addTextChangedListener(textWatcherTitle);
        editTextNotes.addTextChangedListener(textWatcherNotes);
        buttonSetDeadlineDate.setOnClickListener(onClickListener);
        buttonSetDeadlineTime.setOnClickListener(onClickListener);
        imageViewDeadlineClear.setOnClickListener(onClickListener);
    }

    private void createPickers(){
        Button buttonSetDeadlineDate = findViewById(R.id.buttonDeadlineDate);
        Button buttonSetDeadlineTime = findViewById(R.id.buttonDeadlineTime);
        timePickerFragment = new CustomTimePickerFragment();
        timePickerFragment.setTimeTextViewTarget(buttonSetDeadlineTime);
        timePickerFragment.setTimeFormat(getResources().getString(R.string.time_format_fill));

        datePickerFragment = new CustomDatePickerFragment();
        datePickerFragment.setDateFormat(getResources().getString(R.string.date_format_fill));
        datePickerFragment.setDateTextViewTarget((buttonSetDeadlineDate));
        datePickerFragment.setTimePicker(timePickerFragment);
    }

    private TextWatcher createTextWatcherTitle() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                newTask.setTitle(s.toString());
            }
        };
    }

    private TextWatcher createTextWatcherNotes() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                newTask.setNotes(s.toString());
            }
        };
    }

    private OnClickListener createOnClickListener() {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                switch (v.getId()){
                    case R.id.buttonDeadlineDate:
                        showDatePicker();
                        break;
                    case R.id.buttonDeadlineTime:
                        showTimePicker();
                        break;
                    case R.id.imageViewDeadlineClear:
                        datePickerFragment.clear();
                        timePickerFragment.clear();
                        Toast.makeText(getApplicationContext(), "Deadline removed", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }
            }
        };
    }


    //\\ ------------------------ PICKERS

    public void showDatePicker() {
        datePickerFragment.show(getSupportFragmentManager(), "date picker");
    }

    public void showTimePicker() {
        timePickerFragment.show(getSupportFragmentManager(), "time picker");
    }

    // ---------------- PICKERS \\//

    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }

}
