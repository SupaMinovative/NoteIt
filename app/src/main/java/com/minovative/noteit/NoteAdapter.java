package com.minovative.noteit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NoteAdapter extends ArrayAdapter<String> {

    public NoteAdapter(Context context,List<String> notes) {
        super(context, 0, notes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent,false);

        }
         TextView textView = convertView.findViewById(R.id.textViewItem);

        String fullText = getItem(position);

        String firstLine = fullText.split("\n")[0];

        textView.setText(firstLine);

        return convertView;
    }
}
