package tomastakacs.taskschedulingtool;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityTasksListFragment extends ListFragment {


    public MainActivityTasksListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_activity_tasks_list, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        ListView tasksListView = view.findViewById(R.id.tasksListView);

        ArrayList<Task> loadedList = InternalStorageHandler.getInstance().readTasksList(getContext());
        if (loadedList == null) {
            TSTApplication.getInstance().createEmptyTaskArrayList(); // empty
            Log.d("[DEBUG] AL", "created empty List");
        } else {
            TSTApplication.getInstance().setTaskArrayList(loadedList); // from file
            Log.d("[DEBUG] AL", "loaded List form File");
        }

        TaskAdapter customAdapter = new TaskAdapter(view.getContext(),0, TSTApplication.getInstance().getTaskArrayList());
        tasksListView.setAdapter(customAdapter);

        TSTApplication.getInstance().setTasksListViewAdapter(customAdapter);

        OnItemClickListener itemClickListener = createOnItemClickListener();
        tasksListView.setOnItemClickListener(itemClickListener);
    }

    private OnItemClickListener createOnItemClickListener() {
        return new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Class nextClass = null;
                try {
                     nextClass = Class.forName("tomastakacs.taskschedulingtool.DetailedTaskActivity");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                finally {
                    Intent intent = new Intent(view.getContext(), nextClass);
                    Task task = (Task) parent.getItemAtPosition(position);
                    intent.putExtra("task", task); // SERIALIZABLE
                    intent.putExtra("position", position);
                    intent.putExtra("id", id);
                    startActivity(intent);
                }
            }
        };
    }


}
