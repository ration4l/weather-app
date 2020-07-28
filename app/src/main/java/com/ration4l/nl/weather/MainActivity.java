package com.ration4l.nl.weather;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.ration4l.nl.weather.adapter.PlaceAutocompleteAdapter;
import com.ration4l.nl.weather.adapter.ViewPagerAdapter;
import com.ration4l.nl.weather.model.Latlng;
import com.ration4l.nl.weather.model.Place;
import com.ration4l.nl.weather.viewmodel.PlaceViewModel;
import com.ration4l.nl.weather.viewmodel.WeatherViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.ration4l.nl.weather.utils.Utils.getLocationLatLngFromAddress;
import static com.ration4l.nl.weather.utils.Utils.hideKeyboard;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final int PLACE_REQUEST_CODE = 10001;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TabLayout tabLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ViewPager viewPager;
    private SearchView searchView;
    private SearchView.SearchAutoComplete searchAutoComplete;
    private WeatherViewModel weatherViewModel;
    private PlaceViewModel placeViewModel;
    private List<Place> currentPlaceList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherViewModel = new ViewModelProvider(MainActivity.this).get(WeatherViewModel.class);
        weatherViewModel.init();
        placeViewModel = new ViewModelProvider(MainActivity.this).get(PlaceViewModel.class);
        placeViewModel.getAllPlaces().observe(this, places -> {
            currentPlaceList.clear();
            currentPlaceList.addAll(places);
        });
        setupDrawer();
        setupTabs();
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            Log.e(TAG, "onRefresh: refreshing");
            fetchData();
            swipeRefreshLayout.setRefreshing(false);
        });

        checkGPS();
        fetchData();

        /*
         Places.initialize(this, "AIzaSyCA3Atkye7dnmN6OXS7vNC6YJDRkhLty78");
         PlacesClient placesClient = Places.createClient(this);

         AutocompleteSupportFragment autocompleteSupportFragment = (AutocompleteSupportFragment)
         getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

         autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
         autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
        @Override public void onPlaceSelected(@NonNull Place place) {
        Log.e(TAG, "onPlaceSelected: "+place.getName()+", "+place.getId() );
        }

        @Override public void onError(@NonNull Status status) {
        Log.e(TAG, "onError: "+status );
        }
        });
         */
    }

    private void fetchData() {
        Latlng location = getCurrentLocationLatLng();
        Log.e(TAG, "fetchData: latlng: " + location);
        if (location != null) {
            weatherViewModel.getWeatherData(location.getLat(), location.getLng());
        }
    }

    private void checkGPS() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
        }
        GpsTracker gpsTracker = new GpsTracker(MainActivity.this);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if (locationManager != null && !locationManager.isLocationEnabled()) {
                gpsTracker.showSettingAlert();
            }
        }
    }

    private void setupTabs() {
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),
                ViewPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout){
            @Override
            public void onPageScrollStateChanged(int state) {
                if (swipeRefreshLayout != null && !swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setEnabled(state == ViewPager.SCROLL_STATE_IDLE);
                }
            }
        });
    }

    private void setupDrawer() {
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.navigationView);

        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_home:
                    MainActivity.this.checkGPS();
                    MainActivity.this.fetchData();
                    searchAutoComplete.setText("");
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.action_location_list:
                    Intent placeIntent = new Intent(MainActivity.this, PlacesAcitivity.class);
                    MainActivity.this.startActivityForResult(placeIntent, PLACE_REQUEST_CODE);
                    break;
            }
            return true;
        });
    }

    @Nullable
    private Latlng getCurrentLocationLatLng() {
        Latlng latlng = new Latlng();
        GpsTracker gpsTracker = new GpsTracker(MainActivity.this);
        if (gpsTracker.canGetLocation()) {
            latlng.setLat(gpsTracker.getLatitude());
            latlng.setLng(gpsTracker.getLongitude());
            return latlng;
        } else {
            Snackbar.make(drawerLayout, "Error.", Snackbar.LENGTH_LONG).show();
            return null;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fetchData();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) menuItem.getActionView();
//        searchView.setIconifiedByDefault(false);
//        searchView.findViewById(R.id.search_mag_icon).setVisibility(View.GONE);
        searchAutoComplete = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        PlaceAutocompleteAdapter placeAutocompleteAdapter = new PlaceAutocompleteAdapter(this, R.layout.item_place_matching_name);
        searchAutoComplete.setAdapter(placeAutocompleteAdapter);
        searchAutoComplete.setSelectAllOnFocus(true);
        searchAutoComplete.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchAutoComplete.setInputType(EditorInfo.TYPE_TEXT_FLAG_AUTO_COMPLETE);
        searchAutoComplete.setOnItemClickListener((parent, view, position, id) -> {
            String address = parent.getItemAtPosition(position).toString();
            Latlng latlng = getLocationLatLngFromAddress(getApplicationContext(), address);
            if (latlng != null) {
                weatherViewModel.getWeatherData(latlng.getLat(), latlng.getLng());
                searchAutoComplete.setText(parent.getItemAtPosition(position).toString());
                Place place = new Place(address, latlng.getLat(), latlng.getLng());
                boolean isDuplicated = false;
                for (Place p : currentPlaceList
                ) {
                    if (p.getAddress().equalsIgnoreCase(place.getAddress())) {
                        isDuplicated = true;
                        break;
                    }
                }
                if (!isDuplicated) {
                    Snackbar.make(drawerLayout, "Add this location to your list?", 10 * 1000)
                            .setAction("Add", v -> {
                                placeViewModel.insert(place);
                                Log.e(TAG, "onClick: Added");
                            })
                            .show();
                }
            } else {
                Snackbar.make(drawerLayout, "Error, try again.", Snackbar.LENGTH_LONG).show();
            }
            hideKeyboard(MainActivity.this, view);
            searchAutoComplete.clearFocus();
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            checkGPS();
            fetchData();
            searchAutoComplete.setText("");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                Place place = (Place) data.getSerializableExtra("place");
                Log.e(TAG, "onActivityResult: " + place);
                if (place != null) {
                    weatherViewModel.getWeatherData(place.getLat(), place.getLng());
                    searchAutoComplete.setText(place.getAddress());
                }
            } else {
                Log.e(TAG, "onActivityResult: null");
            }
        } else {
            Log.e(TAG, "onActivityResult: failed");
        }
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
}