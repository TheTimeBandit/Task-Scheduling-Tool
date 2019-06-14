package tomastakacs.taskschedulingtool;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;



public class CustomTimePickerFragment extends DialogFragment {

    final private int DEFAULT_TIME_HOURS = 8;
    final private int DEFAULT_TIME_MINUTES = 0;

    private Activity parentAct;
    private String pickedTime = null;
    private TextView timeTextViewTarget = null;

    private String timeFormat;


    public void setTimeTextViewTarget(TextView target) {
        this.timeTextViewTarget = target;
    }

    public void setTimeFormat(String format){
        this.timeFormat = format;
    }


    public void setDefaultTime() {
        pickedTime = formatTime(DEFAULT_TIME_HOURS,DEFAULT_TIME_MINUTES);
        timeTextViewTarget.setText(pickedTime);
        timeTextViewTarget.setVisibility(View.VISIBLE);
    }

    public void clear() {
        pickedTime = null;
        timeTextViewTarget.setText(null);
        timeTextViewTarget.setVisibility(View.INVISIBLE);
    }

    public void setPickedTime(String time){ pickedTime = time;}
    public String getPickedTime(){
        return this.pickedTime;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        int hourOfDay = DEFAULT_TIME_HOURS;
        int minute = DEFAULT_TIME_MINUTES;

        OnTimeSetListener onTimeSetListener = createOnTimeSetListener();
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), onTimeSetListener, hourOfDay, minute, true);
        return timePickerDialog;
    }


    private String formatTime(int hours, int minutes){
        String strHours = String.format("%2d", hours).replace(' ', '0');
        String strMinute = String.format("%2d", minutes).replace(' ', '0');

        return String.format(timeFormat, strHours, strMinute);
    }

    private OnTimeSetListener createOnTimeSetListener() {
        return new OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker picker, int hourOfDay, int minute) {
                int hours = picker.getHour();
                int minutes = picker.getMinute();

                pickedTime = formatTime(hours, minutes);
                timeTextViewTarget.setText(pickedTime);
            }
        };
    }


}
