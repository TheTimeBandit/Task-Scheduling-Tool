package tomastakacs.taskschedulingtool;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class TSTApplication {

    private static final TSTApplication INSTANCE = new TSTApplication();
    public static TSTApplication getInstance() {return INSTANCE;}


    private ArrayList<Task> taskArrayList = null;
    private ArrayAdapter tasksListViewAdapter = null;

    public void setTaskArrayList(ArrayList<Task> tasksList){
        taskArrayList = tasksList;
    }
    public ArrayList<Task> getTaskArrayList(){return taskArrayList;}

    public void setTasksListViewAdapter(ArrayAdapter tasksListViewAdapter) {this.tasksListViewAdapter = tasksListViewAdapter;}
    public ArrayAdapter getTasksListViewAdapter(){return tasksListViewAdapter;}

    public void createEmptyTaskArrayList(){
        taskArrayList = new ArrayList<Task>();
    }
}
