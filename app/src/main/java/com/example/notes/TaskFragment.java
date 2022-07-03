package com.example.notes;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class TaskFragment extends Fragment {

    private ArrayList<Task> taskList;
    private TaskListAdapter taskAdapter;
    private ListView taskListView;
    private Button addTaskButton;
    private FirebaseUser user;

    public TaskFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        user = FirebaseAuth.getInstance().getCurrentUser();
        taskList = new ArrayList<>();
        taskAdapter = new TaskListAdapter(getContext(), taskList);
        taskListView = view.findViewById(R.id.task_listView);
        taskListView.setAdapter(taskAdapter);
        retriveTasks();
        addTaskButton = view.findViewById(R.id.addTaskButton);

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                showTaskDialog(null,-1);
            }
        });
        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showTaskDialog(taskList.get(position),position);
            }
        });


        return view;
    }

    private void retriveTasks(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("tasks").child(user.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                taskList.clear();
                for( DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Task task = snapshot.getValue(Task.class);
                    taskList.add(task);
                }
                taskAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showTaskDialog(Task task, int position) {

        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.task_dialog);
        boolean isEdit;
        final Task editTask;
        if (task == null) {
            editTask = new Task("", "","");
            isEdit = false;
        } else {
            isEdit = true;
            editTask = new Task(task.getTaskDue(),task.getShortcut(), task.getDesc());
            editTask.setId(task.getId());
            editTask.setTaskAdded(task.getTaskAdded());
        }


        EditText title = dialog.findViewById(R.id.task_title);
        EditText desc = dialog.findViewById(R.id.task_desc);
        Button addBtn = dialog.findViewById(R.id.confirm_task_btn);
        Button cancelBtn = dialog.findViewById(R.id.cancel_task_btn);
        ImageButton delBtn = dialog.findViewById(R.id.delete_task);
        DatePicker datePicker = dialog.findViewById(R.id.datePicker1);;
        title.setText(editTask.getShortcut());
        desc.setText(editTask.getDesc());

        addBtn.setOnClickListener(v -> {
            if(title.getText().toString().isEmpty() ||
                desc.getText().toString().isEmpty()){
                Toast.makeText(getContext(), "Uzupełnij Dane!", Toast.LENGTH_SHORT).show();
            }
            else{
                editTask.setShortcut(title.getText().toString());
                editTask.setDesc(desc.getText().toString());
                editTask.setTaskDue(LocalDate.of(datePicker.getYear(),
                        datePicker.getMonth(),
                        datePicker.getDayOfMonth()).toString());
                saveTaskService(editTask, position);
                dialog.dismiss();
            }

        });
        cancelBtn.setOnClickListener(v -> {
            dialog.dismiss();
        });
        delBtn.setOnClickListener(v -> {
            if(task != null){
                FirebaseDatabase.getInstance().getReference().child("tasks")
                        .child(user.getUid())
                        .child(task.getId())
                        .removeValue();
            }
            dialog.dismiss();
        });

        dialog.show();
    }

    private void saveTaskService(Task task, int position) {
        if (position == -1) {
            taskList.add(task);
            FirebaseDatabase.getInstance().getReference().child("tasks")
                    .child(user.getUid())
                    .child(task.getId())
                    .setValue(task);
            FirebaseDatabase.getInstance().getReference().getKey();
            Toast.makeText(getContext(), "Dodano!", Toast.LENGTH_SHORT).show();
        } else {
            taskList.set(position, task);
            FirebaseDatabase.getInstance().getReference().child("tasks")
                    .child(user.getUid())
                    .child(task.getId())
                    .setValue(task);
            FirebaseDatabase.getInstance().getReference().getKey();
            Toast.makeText(getContext(), "Edytowano", Toast.LENGTH_SHORT).show();
        }
        taskAdapter.notifyDataSetChanged();
        retriveTasks();
    }
}