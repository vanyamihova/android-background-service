package com.fos.trackingapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fos.trackingapplication.R;
import com.fos.trackingapplication.model.LocationModel;

import java.util.List;

/**
 * Created by Vanya Mihova on 11/19/2015.
 */
public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.CardViewHolder> {

    List<LocationModel> allLocations;

    public CardViewAdapter(List<LocationModel> list) {
        this.allLocations = list;
    }


    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_location, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        LocationModel model = allLocations.get(position);
        holder.text.setText(model.getLatitude() + " / " + model.getLongitude());
    }

    @Override
    public int getItemCount() {
        return allLocations.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {

        TextView text;

        public CardViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.txt_location);
        }
    }

    public void notifyItemInserted(LocationModel newModel) {
//        allLocations.add(0, newModel);
        notifyItemInserted(0);
    }

}
