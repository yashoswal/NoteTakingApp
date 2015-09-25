package com.example.intel.notetaking;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.Data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


public class MainActivity extends ActionBarActivity {
    Data data;
    ArrayList<String> objects;
    private String DEFAULT_TEXT = "New Note";
    private static final int EDITOR_REQUEST_CODE = 1001;
    private ListAdapter listAdapter;
    String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        data = new Data(baseDir);
        objects = new ArrayList<String>();
        makeObjects();
        ListView list = (ListView) findViewById(android.R.id.list);

        listAdapter = new ListAdapter(this,objects,data);
        list.setAdapter(listAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                data.setCurrentKey(objects.get(position));
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);

               // Uri uri = Uri.parse(NotesProvider.CONTENT_URI + "/" + id);
                intent.putExtra(DEFAULT_TEXT, data.getAllNotes().get(data.getCurrentKey()));
                startActivityForResult(intent, EDITOR_REQUEST_CODE);
            }
        });
    }

    public void makeObjects(){
        //objects = //new ArrayList<String>();
        objects.clear();
        objects.addAll(data.getAllNotes().keySet());
        Collections.sort(objects, Collections.reverseOrder());
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
  public void openEditorForNewNote(View view) {
      insertNewObject();
      Intent intent = new Intent(this, EditorActivity.class);
      intent.putExtra(DEFAULT_TEXT, DEFAULT_TEXT);
      startActivityForResult(intent, EDITOR_REQUEST_CODE);
  }

    public void insertNewObject() {
        String date = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss.SSS").format(new Date());
        objects.add(0, date);
        data.setNoteForKey(date, DEFAULT_TEXT);
        data.setCurrentKey(date);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent note) {
        if (requestCode == EDITOR_REQUEST_CODE && resultCode == RESULT_CANCELED) {
            //restartLoader();

                String stredittext=note.getStringExtra(DEFAULT_TEXT);
                if(stredittext.equals(DEFAULT_TEXT)){
                    data.removeNoteForKey(data.getCurrentKey());
                }else {
                    data.setNoteForCurrentKey(stredittext);
                }
                makeObjects();
                listAdapter.notifyDataSetChanged();
                data.saveFile();
        }
    }


}