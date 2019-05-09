package com.example.homework2w1j;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.homework2w1j.PersonFragment.OnListFragmentInteractionListener;
import com.example.homework2w1j.persons.PersonListContent.Person;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Person} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyPersonRecyclerViewAdapter extends RecyclerView.Adapter<MyPersonRecyclerViewAdapter.ViewHolder> {

    private final List<Person> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyPersonRecyclerViewAdapter(List<Person> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_person, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {
        Person person = mValues.get(position);
        holder.mItem = person;
        holder.mContentView.setText(person.name);
        final String picPath = person.picPath;
        Context context = holder.mView.getContext();
        if(picPath != null && !picPath.isEmpty()){
            if(picPath.contains("drawable")){
                Drawable taskDrawable;
                switch (picPath){
                    case "drawable 1":
                        taskDrawable = context.getResources().getDrawable(R.drawable.circle_drawable_green);
                        break;
                    case "drawable 2":
                        taskDrawable = context.getResources().getDrawable(R.drawable.circle_drawable_orange);
                        break;
                    case "drawable 3":
                        taskDrawable = context.getResources().getDrawable(R.drawable.circle_drawable_red);
                        break;
                    default:
                        taskDrawable = context.getResources().getDrawable(R.drawable.circle_drawable_green);
                }
                holder.mItemImageView.setImageDrawable(taskDrawable);
            }else{
                Bitmap cameraImage = PicUtils.decodePic(person.picPath,
                        60,60);
                holder.mItemImageView.setImageBitmap(cameraImage);
            }
        }else{
            holder.mItemImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.circle_drawable_green));
        }

        holder.mView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(null != mListener){
                    mListener.onListFragmentClickInteraction(holder.mItem, position);
                }
            }
        });

        holder.mView.findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onListFragmentDeleteInteraction(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public final ImageView mItemImageView;
        public Person mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mItemImageView = view.findViewById(R.id.item_image);
            mContentView = view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
