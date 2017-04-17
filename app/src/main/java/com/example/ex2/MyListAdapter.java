package com.example.ex2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Aviv on 21/03/2017.
 */



public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {
    static List<String> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        // each data item is just a string in this case
        public TextView mTextView;
        public Context context;
        public CheckBox mCheckBox;
        public CardView mCardView;
        public RelativeLayout layout;
        public ViewHolder(View v) {

            super(v);
            mTextView = (TextView)v.findViewById(R.id.textView);
            mCheckBox = (CheckBox)v.findViewById(R.id.checkBox);
            mCardView = (CardView)v.findViewById(R.id.cardView);
            layout = (RelativeLayout)v.findViewById(R.id.inner_layout);
            context = v.getContext();
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("What would you like to do?");
            menu.add(0, v.getId(), getAdapterPosition(), "Remove");
        }
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public MyListAdapter(List<String> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout, parent, false);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset.get(position));
        if (position % 2 == 0) {
            holder.layout.setBackgroundColor(Color.argb(150, 250, 67, 67));
        } else {
            holder.layout.setBackgroundColor(Color.argb(150, 82, 116, 250));
        }

    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}