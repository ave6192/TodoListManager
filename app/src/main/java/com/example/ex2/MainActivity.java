package com.example.ex2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final List<String> todo_list = new ArrayList<>();
    private myAdapter adapter;
    private ListView listView;
    private EditText editText;
    private int i = 1;


//    private RecyclerView mRecyclerView;
//    private RecyclerView.Adapter mAdapter;
//    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
// Set other dialog properties



        this.adapter = new myAdapter(this, android.R.layout.simple_list_item_1, todo_list);
        this.listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        editText = (EditText)findViewById(R.id.edit_Text);

//        editText = (EditText) findViewById(R.id.editText);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              String message = String.valueOf(editText.getText());)
                String message = editText.getText().toString();
                if(message.length() > 0)
                {
                    todo_list.add(message);
                    adapter.notifyDataSetChanged();
                    editText.setText("");
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int pos, long id) {
                String item = listView.getItemAtPosition(pos).toString();

                // Add the buttons
                builder.setPositiveButton("Delete Item", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        todo_list.remove(pos);
                        adapter.notifyDataSetChanged();
                    }
                });

                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage("Would you like to delete the selected item?").setTitle(item);

                // 3. Get the AlertDialog from create()
                AlertDialog dialog = builder.create();
                dialog.show();


                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
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
    }



    class myAdapter<T> extends ArrayAdapter<T> {

        float textSize = 0;
        int unit = 0;

        myAdapter(Context context, int resource, List<T> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            if (position % 2 == 1) {
                view.setBackgroundColor(Color.argb(150, 82, 116, 250));
            } else {
                view.setBackgroundColor(Color.argb(150, 250, 67, 67));
            }

            return view;
        }
    }
}



