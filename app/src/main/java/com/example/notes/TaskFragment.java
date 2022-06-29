package com.example.notes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        //Task task = null;
        //if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        //    task = new Task(LocalDateTime.now(),"task1","zrobic taski");
        //}
//        FirebaseDatabase.getInstance().getReference().child("tasks")
//                .child(user.getUid())
//                .child(task.getId())
//                .setValue(task);
//        FirebaseDatabase.getInstance().getReference().getKey();





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
}