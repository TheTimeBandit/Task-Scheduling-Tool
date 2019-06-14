package tomastakacs.taskschedulingtool;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class InternalStorageHandler {

    private static final InternalStorageHandler INSTANCE = new InternalStorageHandler();
    public static InternalStorageHandler getInstance() {
        return INSTANCE;
    }


    public boolean saveTasksList(Context context, ArrayList<Task> tasksList) {
        String internalStorageFileName = context.getString(R.string.internal_storage_filename);
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(internalStorageFileName, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(tasksList);

            objectOutputStream.close();
            fileOutputStream.close();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public ArrayList<Task> readTasksList(Context context){
        String internalStorageFileName = context.getString(R.string.internal_storage_filename);
        ArrayList<Task> tasksList =  null;
        try {
            FileInputStream fileInputStream = context.openFileInput(internalStorageFileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            tasksList = new ArrayList<>();
            ArrayList arrayList = (ArrayList) objectInputStream.readObject();
            for (Object obj: arrayList){
                tasksList.add((Task) obj);
            }

            objectInputStream.close();
            fileInputStream.close();
            return tasksList;

        } catch (Exception e){
            e.printStackTrace();
        }
        return tasksList;
    }

}
