package com.example.notes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class NoteFragment extends Fragment {

    private ArrayList<Note> noteArrayList = new ArrayList<>();
    private ListView listView;
    NoteListAdapter noteListAdapter;
    //private FragmentNoteBinding binding;

    public NoteFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_note, container, false);

        noteArrayList.add(new Note("Podróż do Paryża", "Paryż był wspaniałym miastem"));
        noteArrayList.add(new Note("Zakupy", "Kup mleko i bułki"));

        noteListAdapter = new NoteListAdapter(getContext(), noteArrayList);

        //binding.noteList.setAdapter(noteListAdapter);
        listView = (ListView) view.findViewById(R.id.noteList);
        listView.setAdapter(noteListAdapter);
        //list.setOnItemClickListener(klikniecie);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}