package com.example.ex2;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Aviv on 20/04/2017.
 */

public class MyDialog extends DialogFragment {

    private EditText insert_Text;
    private EditText insert_Date;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.my_dialog, null);

        insert_Text = (EditText) view.findViewById(R.id.insert_text);
        insert_Date = (EditText) view.findViewById(R.id.insert_date);

        builder.setView(view);

        builder.setTitle("Insert new task");

        builder.setPositiveButton("Insert", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity main_act = ((MainActivity) getActivity());
                String todo = insert_Text.getText().toString();
                String date = insert_Date.getText().toString();

                // Gets the data repository in write mode
                SQLiteDatabase db = main_act.mDbHelper.getWritableDatabase();

// Create a new map of values, where column names are the keys
                ContentValues values = new ContentValues();
                values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, todo+ " \n\n"+date);
                values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE, "");

// Insert the new row, returning the primary key value of the new row
                long newRowId = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);

                if(todo.length() > 0)
                    main_act.mAdapter.add(todo+ " \n\n"+date);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        final AlertDialog myDialog = builder.create();

        return myDialog;
        //

    }
}
