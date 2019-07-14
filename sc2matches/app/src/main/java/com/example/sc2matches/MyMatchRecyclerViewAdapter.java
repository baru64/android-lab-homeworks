package com.example.sc2matches;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sc2matches.MatchFragment.OnListFragmentInteractionListener;
import com.example.sc2matches.persons.MatchListContent;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link MatchListContent.Match} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyMatchRecyclerViewAdapter extends RecyclerView.Adapter<MyMatchRecyclerViewAdapter.ViewHolder> {

    private final List<MatchListContent.Match> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyMatchRecyclerViewAdapter(List<MatchListContent.Match> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_match, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {
        MatchListContent.Match match = mValues.get(position);
        holder.mItem = match;
        holder.eventView.setText(match.event);
        holder.scoreView.setText(match.score);
        holder.p1View.setText(match.p1_name);
        holder.p2View.setText(match.p2_name);
        holder.dateView.setText(match.date);
        switch(match.p1_race) {
            case "Zerg":
                holder.p1raceView.setImageResource(R.drawable.zicon_small);
                break;
            case "Protoss":
                holder.p1raceView.setImageResource(R.drawable.ticon_small);
                break;
            case "Terran":
                holder.p1raceView.setImageResource(R.drawable.picon_small);
                break;
        }
        switch(match.p2_race) {
            case "Zerg":
                holder.p2raceView.setImageResource(R.drawable.zicon_small);
                break;
            case "Protoss":
                holder.p2raceView.setImageResource(R.drawable.ticon_small);
                break;
            case "Terran":
                holder.p2raceView.setImageResource(R.drawable.picon_small);
                break;
        }

        holder.mView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(null != mListener){
                    mListener.onListFragmentClickInteraction(holder.mItem, position);
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
        public final TextView eventView;
        public final TextView scoreView;
        public final TextView p1View;
        public final TextView p2View;
        public final TextView dateView;
        public final ImageView p1raceView;
        public final ImageView p2raceView;
        public MatchListContent.Match mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            eventView = view.findViewById(R.id.eventText);
            scoreView = view.findViewById(R.id.scoreText);
            p1View = view.findViewById(R.id.player1Name);
            p2View = view.findViewById(R.id.player2Name);
            p1raceView = view.findViewById(R.id.player1Race);
            p2raceView = view.findViewById(R.id.player2Race);
            dateView = view.findViewById(R.id.dateText);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + eventView.getText() + "'";
        }
    }
}
