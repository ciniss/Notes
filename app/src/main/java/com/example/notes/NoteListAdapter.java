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

public class NoteListAdapter extends ArrayAdapter<Note> {
    public NoteListAdapter(Context context, ArrayList<Note> noteArrayList) {
        super(context, R.layout.note_element, noteArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Note note = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.note_element, parent, false);
        }

        TextView title, desc;
        title = convertView.findViewById(R.id.note_short);
        desc = convertView.findViewById(R.id.note_desc);

        title.setText(note.getShortcut());
        desc.setText(note.getDesc());
        //return super.getView(position, convertView, parent);
        return convertView;
    }
}
