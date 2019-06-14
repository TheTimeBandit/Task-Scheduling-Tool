package tomastakacs.taskschedulingtool;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivityTasksList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tasks_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabNewTask);
        OnClickListener onClickListener = createOnClickListener();
        fab.setOnClickListener(onClickListener);

    }

    private OnClickListener createOnClickListener() {
        return new OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.fabNewTask:
                        try {
                            Class nextClass = Class.forName("tomastakacs.taskschedulingtool.NewTaskActivity");
                            Intent intent = new Intent(MainActivityTasksList.this, nextClass);
                            startActivity(intent);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        break;
                }
            }
        };
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO menu->settings NotImplemented
        //getMenuInflater().inflate(R.menu.menu_main_activity_tasks_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
