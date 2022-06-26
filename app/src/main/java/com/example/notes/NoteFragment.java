package com.example.notes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class NoteFragment extends Fragment {

    private ArrayList<Note> noteArrayList;
    private NoteListAdapter noteListAdapter;
    private ListView listView;
    private Button addNoteButton;
    private FirebaseUser user;

    public NoteFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = FirebaseAuth.getInstance().getCurrentUser();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_note, container, false);
        addNoteButton = (Button) view.findViewById(R.id.add_note_btn);

        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNoteDialog(null, -1);

            }
        });

        noteArrayList = new ArrayList<>();
        noteListAdapter = new NoteListAdapter(getContext(), noteArrayList);

        listView = (ListView) view.findViewById(R.id.noteList);
        listView.setAdapter(noteListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onNoteEdit(position);
            }
        });
        return view;
    }

    private void onNoteEdit(int position) {
        showNoteDialog(noteArrayList.get(position), position);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void showNoteDialog(Note note, int position) {

        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.note_dialog);
        boolean isEdit;
        final Note editNote;
        if (note == null) {
            editNote = new Note("", "");
            isEdit = false;
        } else {
            isEdit = true;
            editNote = new Note(note.getShortcut(), note.getDesc());
            editNote.setId(note.getId());
        }


        EditText title = dialog.findViewById(R.id.note_title);
        EditText desc = dialog.findViewById(R.id.note_desc);
        Button addBtn = dialog.findViewById(R.id.confirm_note_btn);
        Button cancelBtn = dialog.findViewById(R.id.cancel_note_btn);

        title.setText(editNote.getShortcut());
        desc.setText(editNote.getDesc());

        addBtn.setOnClickListener(v -> {
            //setNoteData(note,,);
            editNote.setShortcut(title.getText().toString());
            editNote.setDesc(desc.getText().toString());
            saveNoteService(editNote, position);
            dialog.dismiss();

        });
        cancelBtn.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }

    private void setNoteData(Note note, String title, String desc) {
        note.setShortcut(title);
        note.setDesc(desc);
    }

    private ArrayList<Note> fetchNotes() {
//        FirebaseDatabase.getInstance().getReference().child("notes")
//                .child(user.getUid()).get().getResult().;
        return null;
    }

    private void saveNoteService(Note note, int position) {
        if (position == -1) {
            noteArrayList.add(note);
            FirebaseDatabase.getInstance().getReference().child("notes")
                    .child(user.getUid())
                    .setValue("Hi");
            FirebaseDatabase.getInstance().getReference().getKey();
            Toast.makeText(getContext(), "Dodano!", Toast.LENGTH_SHORT).show();
        } else {
            noteArrayList.set(position, note);
        }
        noteListAdapter.notifyDataSetChanged();
    }
}