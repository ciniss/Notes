package com.example.notes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.executor.ArchTaskExecutor;
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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class NoteFragment extends Fragment {

    private ArrayList<Note> noteList;
    private NoteListAdapter noteAdapter;
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

        noteList = new ArrayList<>();
        noteAdapter = new NoteListAdapter(getContext(), noteList);

        retrieveNotes();

        listView = (ListView) view.findViewById(R.id.noteList);
        listView.setAdapter(noteAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onNoteEdit(position);
            }
        });
        return view;
    }

    private void onNoteEdit(int position) {
        showNoteDialog(noteList.get(position), position);
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

    private void retrieveNotes() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("notes").child(user.getUid());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                noteList.clear();
                for( DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Note note = snapshot.getValue(Note.class);
                    noteList.add(note);
                }
                noteAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void saveNoteService(Note note, int position) {
        if (position == -1) {
            noteList.add(note);
            FirebaseDatabase.getInstance().getReference().child("notes")
                    .child(user.getUid())
                    .child(note.getId())
                    .setValue(note);
            FirebaseDatabase.getInstance().getReference().getKey();
            Toast.makeText(getContext(), "Dodano!", Toast.LENGTH_SHORT).show();
        } else {
            noteList.set(position, note);
            FirebaseDatabase.getInstance().getReference().child("notes")
                    .child(user.getUid())
                    .child(note.getId())
                    .setValue(note);
            FirebaseDatabase.getInstance().getReference().getKey();
            Toast.makeText(getContext(), "Edytowano", Toast.LENGTH_SHORT).show();
        }
        noteAdapter.notifyDataSetChanged();
        retrieveNotes();
    }
}