package tomastakacs.taskschedulingtool;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


public class DetailedTaskActivity extends AppCompatActivity {

    private Task detailedTask;

    private CustomDatePickerFragment datePickerFragment = null;
    private CustomTimePickerFragment timePickerFragment = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_edit);

        // getting Extras from clicked ListItem
        Bundle extras = getIntent().getExtras();
        ArrayAdapter parent = (ArrayAdapter) extras.get("parent");
        int position = extras.getInt("position");
        //detailedTask = (Task) extras.getSerializable("task");
        detailedTask = TSTApplication.getInstance().getTaskArrayList().get(position);

        createPickers();
        fillViewsFromTask();
        addListeners();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detailed_activity_task_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (detailedTask.getDeadlineDate() == null && detailedTask.getDeadlineTime()==null){
            if (datePickerFragment !=null && timePickerFragment != null) {
                String inputDeadlineDate = datePickerFragment.getPickedDate();
                String inputDeadlineTime = timePickerFragment.getPickedTime();
                detailedTask.setDeadlineDate(inputDeadlineDate);
                detailedTask.setDeadlineTime(inputDeadlineTime);
            }
        }
        if (datePickerFragment!=null && timePickerFragment!= null) {
            String inputDeadlineDate = datePickerFragment.getPickedDate();
            String inputDeadlineTime = timePickerFragment.getPickedTime();
            detailedTask.setDeadlineDate(inputDeadlineDate);
            detailedTask.setDeadlineTime(inputDeadlineTime);
        }

        int id = item.getItemId();
        if (id == R.id.action_completerestoretask){
            //detailedTask.setCompleted(!detailedTask.isCompleted());
            if (detailedTask.isCompleted()){
                detailedTask.setCompleted(false);
                Toast.makeText(this,"Task restored",Toast.LENGTH_LONG).show();
            }
            else {
                detailedTask.setCompleted(true);
                Toast.makeText(this,"Task completed",Toast.LENGTH_LONG).show();
            }
            boolean isUpdated = InternalStorageHandler.getInstance().saveTasksList(this,TSTApplication.getInstance().getTaskArrayList());
            TSTApplication.getInstance().getTasksListViewAdapter().notifyDataSetChanged();
            onBackPressed();
            return isUpdated;
        }
        else if (id == R.id.action_deletetask) {
            TSTApplication.getInstance().getTaskArrayList().remove(detailedTask);
            boolean isDeleted = InternalStorageHandler.getInstance().saveTasksList(this,TSTApplication.getInstance().getTaskArrayList());
            TSTApplication.getInstance().getTasksListViewAdapter().notifyDataSetChanged();
            Toast.makeText(this,"Task deleted",Toast.LENGTH_LONG).show();
            onBackPressed();
            return isDeleted;
        }
        else if (id == R.id.action_savechanges) {
            boolean isChanged = InternalStorageHandler.getInstance().saveTasksList(this,TSTApplication.getInstance().getTaskArrayList());
            TSTApplication.getInstance().getTasksListViewAdapter().notifyDataSetChanged();
            Toast.makeText(this,"Changes saved",Toast.LENGTH_LONG).show();
            return isChanged;
        }
        return super.onOptionsItemSelected(item);
    }


    private void fillViewsFromTask() {
        // find Views
        TextView textStatus = findViewById(R.id.textViewStatus);
        EditText editTextTitle = findViewById(R.id.editTextTitle);
        EditText editTextNotes = findViewById(R.id.editTextNotes);
        Button buttonDeadline = findViewById(R.id.buttonDeadlineDate);
        Button buttonDeadlineTime = findViewById(R.id.buttonDeadlineTime);

        // status
        textStatus.setVisibility(View.VISIBLE);
        if (detailedTask.isCompleted()) {
            textStatus.setText("Completed");
        }
        else {
            textStatus.setText("This task is active");
        }
        // title
        editTextTitle.setText(detailedTask.getTitle());

        // notes
        editTextNotes.setText(detailedTask.getNotes());

        if (detailedTask.getDeadlineDate()!=null && detailedTask.getDeadlineTime()!=null) {
            datePickerFragment.setPickedDate(detailedTask.getDeadlineDate());
            timePickerFragment.setPickedTime(detailedTask.getDeadlineTime());
            buttonDeadline.setText(detailedTask.getDeadlineDate());
            buttonDeadlineTime.setText(detailedTask.getDeadlineTime());
            buttonDeadlineTime.setVisibility(View.VISIBLE);
        }

    }


    private TextWatcher createTextWatcherTitle() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                detailedTask.setTitle(s.toString());
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
                detailedTask.setNotes(s.toString());
            }
        };
    }

    private View.OnClickListener createOnClickListener() {
        return new View.OnClickListener() {
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
                        Toast.makeText(getApplicationContext(), "Deadline removed", Toast.LENGTH_SHORT).show();
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
