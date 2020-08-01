package com.ration4l.nl.weather;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.ration4l.nl.weather.adapter.PlaceRVAdapter;
import com.ration4l.nl.weather.model.Place;
import com.ration4l.nl.weather.viewmodel.PlaceViewModel;

import java.util.ArrayList;
import java.util.List;

public class PlacesAcitivity extends AppCompatActivity implements PlaceRVAdapter.OnPlaceSelectedListener {
    private static final String TAG = "SearchAcitivity";
    private LinearLayout rootLayout;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private PlaceRVAdapter placeRVAdapter;
    private List<Place> currentPlaceList = new ArrayList<>();
    private PlaceViewModel placeViewModel;
//    private String address;
    public static final int MENU_ITEM_DELETE_ID = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        toolbar = findViewById(R.id.toolBar_places);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Your locations");
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        rootLayout = findViewById(R.id.layout_root);

        recyclerView = findViewById(R.id.recyclerview_place);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(PlacesAcitivity.this,DividerItemDecoration.VERTICAL));
        placeRVAdapter = new PlaceRVAdapter();
        recyclerView.setAdapter(placeRVAdapter);
//        placeRVAdapter.setOnCreatePlaceContextMenuListener(this::setOnCreatePlaceContextMenu);
        placeRVAdapter.setOnPlaceSelectedListener(this::setOnPlaceSelected);

        new ItemTouchHelper(itemTouchCallback).attachToRecyclerView(recyclerView);

        placeViewModel = new ViewModelProvider(this).get(PlaceViewModel.class);
        placeViewModel.getAllPlaces().observe(this, places -> {
            Log.e(TAG, "onChanged: "+places );
            placeRVAdapter.setData(places);
            currentPlaceList.clear();
            currentPlaceList.addAll(places);
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_places_activity, menu);

        MenuItem menuItem = menu.findItem(R.id.action_place_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                placeRVAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

//    @Override
//    public boolean onContextItemSelected(@NonNull MenuItem item) {
//
//        if (item.getItemId() == MENU_ITEM_DELETE_ID) {
//            placeViewModel.deletePlace(address);
//            return true;
//        }
//        return super.onContextItemSelected(item);
//    }

//    @Override
//    public void setOnCreatePlaceContextMenu(String address) {
//        this.address = address;
//    }

    @Override
    public void setOnPlaceSelected(Place placeSelected) {
        Intent replyIntent = new Intent();
        replyIntent.putExtra("place", placeSelected);
        replyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        setResult(RESULT_OK, replyIntent);
        finish();
    }

    private ItemTouchHelper.SimpleCallback itemTouchCallback = new
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int recentPosition = viewHolder.getAdapterPosition();
            Place recentDeletedPlace = currentPlaceList.get(recentPosition);
            placeViewModel.deletePlace(recentDeletedPlace.getAddress());
            Snackbar.make(rootLayout, "Place deleted.", 10*1000)
                    .setAction("Undo", v -> placeViewModel.insert(recentDeletedPlace))
                    .show();
        }
    };
}