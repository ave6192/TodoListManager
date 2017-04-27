package com.example.ex2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final List<String> todo_list = new ArrayList<>();

    public RecyclerView mRecyclerView;
    public MyListAdapter mAdapter;
    public RecyclerView.LayoutManager mLayoutManager;

    FeedReaderDbHelper mDbHelper;


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

        //-------------------------------------------------------------------------------
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView

        mDbHelper = new FeedReaderDbHelper(this);
        ArrayList<String> array_list = mDbHelper.getDbText();


        mRecyclerView = (RecyclerView)findViewById(R.id.my_recycle_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyListAdapter(array_list);
//        mAdapter = new MyListAdapter(todo_list);
        mRecyclerView.setAdapter(mAdapter);

        //-------------------------------------------------------------------------------

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                createMyDialog();
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
    public boolean onContextItemSelected(MenuItem item) {

        int position = item.getOrder();
        String title = MyListAdapter.mDataset.get(position);
        if(item.getTitle().toString().toLowerCase().equals("call")) {
            String contact = title.substring(4).trim();
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + contact));
            startActivity(intent);

        }
        if(item.getTitle().toString().toLowerCase().equals("remove")) {
            remove(position);
        }
        return super.onContextItemSelected(item);
    }

    private void remove(int position) {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ArrayList<Item> array_list = mDbHelper.getDbValues();

        Item t = array_list.get(position);
        mDbHelper.delete(Integer.parseInt(t.getId()));
        FragmentManager fragmentManager = getSupportFragmentManager();
//        RemoveDialog removeDialog = new RemoveDialog();
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putString("todo", mAdapter.get(position));
        mAdapter.remove(position);
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
    private void createMyDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        MyDialog insert_dialog= new MyDialog();
        insert_dialog.show(fragmentManager, "insert_dialog");
    }

    @Override
    protected void onDestroy() {
        mDbHelper.close();
        super.onDestroy();
    }

}



