package com.example.sc2matches;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sc2matches.topPlayerFragment.OnListFragmentInteractionListener;
import com.example.sc2matches.player.topPlayerListContent.playerItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link playerItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MytopPlayerRecyclerViewAdapter extends RecyclerView.Adapter<MytopPlayerRecyclerViewAdapter.ViewHolder> {

    private final List<playerItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MytopPlayerRecyclerViewAdapter(List<playerItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_topplayer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mNameView.setText(mValues.get(position).name);
        holder.mRatingView.setText(String.valueOf(mValues.get(position).rating));
        holder.mCountryView.setText(mValues.get(position).country);

        switch (mValues.get(position).race) {
            case "Zerg":
                holder.mRaceView.setImageResource(R.drawable.zicon_small);
                break;
            case "Protoss":
                holder.mRaceView.setImageResource(R.drawable.picon_small);
                break;
            case "Terran":
                holder.mRaceView.setImageResource(R.drawable.ticon_small);
                break;
            case "Random":
                holder.mRaceView.setImageResource(R.drawable.ricon_small);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mNameView;
        public final ImageView mRaceView;
        public final TextView mRatingView;
        public final TextView mCountryView;
        public playerItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mNameView = (TextView) view.findViewById(R.id.player_name);
            mRaceView = view.findViewById(R.id.player_race);
            mRatingView = view.findViewById(R.id.player_rating);
            mCountryView = view.findViewById(R.id.player_country);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
}
