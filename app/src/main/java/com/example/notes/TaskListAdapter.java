package com.example.notes;

    import android.content.Context;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ArrayAdapter;
    import android.widget.TextView;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;

    import java.util.ArrayList;

public class TaskListAdapter extends ArrayAdapter<Task> {
    public TaskListAdapter(Context context, ArrayList<Task> taskArrayList) {
        super(context, R.layout.note_element, taskArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Task task = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_element, parent, false);
        }
        ///TO DO
        TextView title, desc, addDate, dueDate;
        title = convertView.findViewById(R.id.task_title);
        desc = convertView.findViewById(R.id.task_desc);
        addDate = convertView.findViewById(R.id.task_created_date);
        dueDate = convertView.findViewById(R.id.task_due_date);

        title.setText(task.getShortcut());
        desc.setText(task.getDesc());
        if(task.getTaskAdded() == null){
            addDate.setText("Top secret");
        }
        else{
            addDate.setText(task.getTaskAdded());
        }
        if(task.getTaskDue() == null){
            dueDate.setText("dont hurry...");
        }
        else{
            dueDate.setText(task.getTaskDue());
        }

        dueDate.setText(task.getTaskDue());
        //return super.getView(position, convertView, parent);
        return convertView;
    }
}