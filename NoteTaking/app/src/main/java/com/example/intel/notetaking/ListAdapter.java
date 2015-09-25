package com.example.intel.notetaking;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.Data;

import java.util.ArrayList;


/**
 * Created by ymoswal on 9/15/2015.
 */
public class ListAdapter extends ArrayAdapter {
   // private
    private Data data;
    private ArrayList<String> noteList;
    public ListAdapter(Activity context, ArrayList<String> notes, Data d){
        super(context,0,notes);
        noteList = notes;
        data =d;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        String key = noteList.get(position);//(String) getItem(position);
        String noteText = data.getAllNotes().get(key);
        if (convertView == null) {
            convertView =  LayoutInflater.from(getContext()).inflate(
                    R.layout.note_list_item, parent, false);
        }

        TextView tv = (TextView) convertView.findViewById(R.id.tvNote);
        tv.setText(noteText);
        return convertView;
    }

    @Override
    public int getCount(){
        return data.getAllNotes().size();
    }
}
