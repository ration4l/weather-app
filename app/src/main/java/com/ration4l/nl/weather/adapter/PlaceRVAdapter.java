package com.ration4l.nl.weather.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ration4l.nl.weather.R;
import com.ration4l.nl.weather.model.Place;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThanhLongNL on 22-Jul-20.
 */

public class PlaceRVAdapter extends RecyclerView.Adapter<PlaceRVAdapter.PlaceViewHolder> implements Filterable {

    private static final String TAG = "PlaceRVAdapter";

    private List<Place> allPlaces;

    private List<Place> places;

//    private OnCreatePlaceContextMenuListener onCreatePlaceContextMenuListener;

    private OnPlaceSelectedListener onPlaceSelectedListener;

//    public void setOnCreatePlaceContextMenuListener(OnCreatePlaceContextMenuListener onCreatePlaceContextMenuListener) {
//        this.onCreatePlaceContextMenuListener = onCreatePlaceContextMenuListener;
//    }

    public void setOnPlaceSelectedListener(OnPlaceSelectedListener onPlaceSelectedListener) {
        this.onPlaceSelectedListener = onPlaceSelectedListener;
    }

    public PlaceRVAdapter() {
        allPlaces = new ArrayList<>();
        places = new ArrayList<>();
    }


    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place, parent, false);
        return new PlaceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        if (places != null) {
            Place current = places.get(position);
            holder.tvAddress.setText(current.getAddress());
            holder.container.setOnClickListener(v -> onPlaceSelectedListener.setOnPlaceSelected(current));
        } else {
            holder.tvAddress.setText("Empty");
        }
    }

    @Override
    public int getItemCount() {
        if (places != null) {
            return places.size();
        } else {
            return 0;
        }
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<Place> tempList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
//                tempList.clear();
                tempList.addAll(allPlaces);
            } else {
                for (Place place : allPlaces
                ) {
                    if (place.getAddress().toLowerCase().contains(constraint.toString().toLowerCase().trim())) {
                        tempList.add(place);
                    }
                }
            }
            results.values = tempList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            places.clear();
            places.addAll((List) results.values);
            Log.e(TAG, "publishResults: places: "+places );
            Log.e(TAG, "publishResults: allPlaces: "+allPlaces );
            notifyDataSetChanged();
        }
    };

    class PlaceViewHolder extends RecyclerView.ViewHolder  {
        private LinearLayout container;
        private TextView tvAddress;

        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.layout_place_item);
            tvAddress = itemView.findViewById(R.id.tv_place_address);
//            itemView.setOnCreateContextMenuListener(this::onCreateContextMenu);
        }

//        @Override
//        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//            menu.add(getAdapterPosition(), MENU_ITEM_DELETE_ID, Menu.NONE, "Delete");
//            onCreatePlaceContextMenuListener.setOnCreatePlaceContextMenu(places.get(getAdapterPosition()).getAddress());
//        }

    }

    public void setData(List<Place> placeList) {
        allPlaces.clear();
        allPlaces.addAll(placeList);
        places.clear();
        places.addAll(placeList);
        notifyDataSetChanged();
    }

    public Place getPlace(int position){
        return places.get(position);
    }

//    public interface OnCreatePlaceContextMenuListener {
//        void setOnCreatePlaceContextMenu(String address);
//    }

    public interface OnPlaceSelectedListener {
        void setOnPlaceSelected(Place placeSelected);
    }
}
