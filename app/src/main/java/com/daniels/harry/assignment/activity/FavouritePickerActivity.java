package com.daniels.harry.assignment.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bluelinelabs.logansquare.LoganSquare;
import com.daniels.harry.assignment.R;
import com.daniels.harry.assignment.adapter.FavouriteTeamListViewAdapter;
import com.daniels.harry.assignment.databinding.ActivityFavouritePickerBinding;
import com.daniels.harry.assignment.model.FavouriteTeam;
import com.daniels.harry.assignment.model.User;
import com.daniels.harry.assignment.viewmodel.FavouriteTeamViewModel;
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FavouritePickerActivity
        extends AppCompatActivity
        implements LocationListener, GoogleApiClient.ConnectionCallbacks,
                   GoogleApiClient.OnConnectionFailedListener, SearchView.OnQueryTextListener {

    private static final Comparator<FavouriteTeamViewModel> DISTANCE_COMPARATOR = new Comparator<FavouriteTeamViewModel>() {
        @Override
        public int compare(FavouriteTeamViewModel a, FavouriteTeamViewModel b) {
            return a.getDistance().compareTo(b.getDistance());
        }
    };

    private static final int REQUESTCODE_LOCATION_PERMISSION = 53;

    private List<FavouriteTeamViewModel> mViewModels = new ArrayList<>();
    private FavouriteTeamViewModel mSelectedViewModel;
    private FavouriteTeamListViewAdapter mListViewAdapter;
    private ActivityFavouritePickerBinding mBinding;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;

    private boolean mLocationReceived;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_favourite_picker);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        mListViewAdapter = new FavouriteTeamListViewAdapter(this, DISTANCE_COMPARATOR, new FavouriteTeamListViewAdapter.Listener() {
            @Override
            public void onClick(FavouriteTeamViewModel vm) {

                mSelectedViewModel = vm;

                new AlertDialog.Builder(FavouritePickerActivity.this)
                        .setTitle("Confirm")
                        .setMessage("Are you sure you wish to change your favourite team to " + vm.getName())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                User user = User.first(User.class);
                                FavouriteTeam team = user.favouriteTeam;
                                team.name = mSelectedViewModel.getName();
                                team.apiId = mSelectedViewModel.getId();
                                team.save();
                                user.save();
                                finish();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

        mBinding.listFavouritePicker.setLayoutManager(new LinearLayoutManager(this));
        mBinding.listFavouritePicker.setAdapter(mListViewAdapter);

        handleHttpRequest();
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        mLocationReceived = false;

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop()
    {
        mLocationReceived = false;

        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }

        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        final List<FavouriteTeamViewModel> filteredModelList = filter(mViewModels, query);

        resetListAdapter(filteredModelList);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private static List<FavouriteTeamViewModel> filter (List<FavouriteTeamViewModel> models, String query)
    {
        final String lowerCaseQuery = query.toLowerCase();

        final List<FavouriteTeamViewModel> filteredModelList = new ArrayList<>();
        for (FavouriteTeamViewModel model : models) {
            final String name = model.getName().toLowerCase();
            if (name.contains(lowerCaseQuery)) {
                filteredModelList.add(model);
            }
        }

        return filteredModelList;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (!mLocationReceived)
        {
            mLocationReceived = true;
            updateViewModels(location);
            resetListAdapter(mViewModels);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        requestLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        //TODO: Handle connection
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //TODO: Handle connection
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case  REQUESTCODE_LOCATION_PERMISSION : {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requestLocationUpdates();
                } else {
                    //TODO: Handle permission denied
                }
            }
        }
    }

    private void requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {

            mLocationRequest = LocationRequest.create();
            mLocationRequest.setInterval(100);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUESTCODE_LOCATION_PERMISSION);
        }
    }

    //TODO: Maybe abstract method to separate class?
    private void handleHttpRequest(){
        RequestQueue queue = Volley.newRequestQueue(this);
        //TODO: Move URL strings to string resources
        String url ="http://fifabuddy.net/api/team";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject o = response.getJSONObject(i);
                                FavouriteTeamViewModel vm = LoganSquare.parse(o.toString(), FavouriteTeamViewModel.class);
                                mViewModels.add(vm);

                                //TODO: Handle catch
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        resetListAdapter(mViewModels);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO: Add error handling
            }
        });

        queue.add(request);
    }

    private void resetListAdapter(List<FavouriteTeamViewModel> items){
        mListViewAdapter.edit().removeAll().commit();
        mListViewAdapter.edit().add(items).commit();
        mBinding.listFavouritePicker.scrollToPosition(0);
    }

    private void updateViewModels(Location location)
    {
        for (FavouriteTeamViewModel vm : mViewModels) {
            vm.setUserLatitude(location.getLatitude());
            vm.setUserLongitude(location.getLongitude());
            vm.calculateDistance();
        }
    }
}
