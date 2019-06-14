package tomastakacs.taskschedulingtool;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {

    private Context context;
    private List<Task> tasksList = new ArrayList<Task>();

    public TaskAdapter(@NonNull Context context, int resource, @NonNull List<Task> objects) {
        super(context, resource, objects);
        this.context = context;
        this.tasksList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null){
            listItem = LayoutInflater.from(this.context).inflate(R.layout.list_item_task, parent, false);
        }
        Task currentTask = this.tasksList.get(position);

        TextView titleView = listItem.findViewById(R.id.textViewTitle);
        titleView.setText(currentTask.getTitle());

        TextView notesView = listItem.findViewById(R.id.textViewNotes);
        notesView.setText(currentTask.getNotes());

        TextView deadlineDateView = listItem.findViewById(R.id.textViewDeadlineDate);
        String deadlineDate = currentTask.getDeadlineDate();
        deadlineDateView.setText(deadlineDate);

        TextView deadlineTimeView = listItem.findViewById(R.id.textViewDeadlineTime);
        String deadlineTime = currentTask.getDeadlineTime();
        deadlineTimeView.setText(deadlineTime);

        // Colored header of Item
        TextView deadlineAlertView = listItem.findViewById(R.id.textDeadlineAlert);
        deadlineAlertView.setVisibility(View.GONE); // Just in case ...


        if (deadlineDate == null || deadlineTime == null){
            LinearLayout deadlineLayout = listItem.findViewById(R.id.deadlineLayout);
            deadlineLayout.setVisibility(View.GONE);
        }
        if (currentTask.isCompleted()){
            deadlineAlertView.setVisibility(View.VISIBLE);
            deadlineAlertView.setText("Completed");
            deadlineAlertView.setBackgroundResource(R.drawable.list_item_bg_text_deadlinealert_completed);
        }
        else if (deadlineDate!=null && deadlineTime!=null){
            LinearLayout deadlineLayout = listItem.findViewById(R.id.deadlineLayout);
            deadlineLayout.setVisibility(View.VISIBLE);
            // Set DUE ALERT header if NOT COMPLETED
            SimpleDateFormat dateFormat = new SimpleDateFormat(context.getString(R.string.date_format));
            try {
                Date dDate = dateFormat.parse(deadlineDate);
                Calendar deadline = Calendar.getInstance(); // deadline
                deadline.setTime(dDate);

                Calendar today = Calendar.getInstance(); // today
                today.setTime(dateFormat.parse(dateFormat.format(new Date())));

                Calendar tomorrow = Calendar.getInstance();
                tomorrow.setTime(today.getTime());
                tomorrow.add(Calendar.DAY_OF_YEAR, 1);

                if (deadline.equals(tomorrow)) {
                    deadlineAlertView.setVisibility(View.VISIBLE);
                    deadlineAlertView.setText("Deadline Tomorrow");
                    deadlineAlertView.setBackgroundResource(R.drawable.list_item_bg_text_deadlinealert_tomorrow);
//                    Log.d("[DEBUG] +1", "tomorow");
                }
                else if (deadline.equals(today)) {
                    deadlineAlertView.setVisibility(View.VISIBLE);
                    deadlineAlertView.setText("Deadline Today");
                    deadlineAlertView.setBackgroundResource(R.drawable.list_item_bg_text_deadlinealert_today);
//                    Log.d("[DEBUG] ==", "today");
                }
                else if (deadline.before(today)){
                    deadlineAlertView.setVisibility(View.VISIBLE);
                    deadlineAlertView.setText("After Deadline");
                    deadlineAlertView.setBackgroundResource(R.drawable.list_item_bg_text_deadlinealert_after);
//                    Log.d("[DEBUG] passed", "after deadl");
                }
                else {
                    deadlineAlertView.setVisibility(View.GONE);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        return listItem;
    }
}
