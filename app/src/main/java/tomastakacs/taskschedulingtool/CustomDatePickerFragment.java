package tomastakacs.taskschedulingtool;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;


public class CustomDatePickerFragment extends DialogFragment {

    private String pickedDate = null;

    private TextView dateTextViewTarget = null;
    private CustomTimePickerFragment customTimePickerFragment;

    private String dateFormat;

    public void setDateTextViewTarget(TextView target){
        dateTextViewTarget = target;
    }

    public void setDateFormat(String format){
        this.dateFormat = format;
    }

    public void setTimePicker(CustomTimePickerFragment timePicker){
        customTimePickerFragment = timePicker;

    }

    public void clear() {
        this.pickedDate = null;
        dateTextViewTarget.setText(null);
    }

    public void setPickedDate(String date){ pickedDate = date;}
    public String getPickedDate(){
        return pickedDate;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // get Today
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        OnDateSetListener onDateSetListener = createDateSetListener();  // SET
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), onDateSetListener, year, month, day);
        return datePickerDialog;
    }


    private OnDateSetListener createDateSetListener() {
        return new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int month, int day) {
                String date = String.format(dateFormat, view.getDayOfMonth(),view.getMonth()+1,view.getYear());
                pickedDate = date;
                dateTextViewTarget.setText(pickedDate);

                if ( customTimePickerFragment != null && customTimePickerFragment.getPickedTime() == null) {
                    customTimePickerFragment.setDefaultTime();
                }
            }
        };
    }



}
