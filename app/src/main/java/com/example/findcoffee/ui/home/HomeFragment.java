package com.example.findcoffee.ui.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.findcoffee.MainActivity;
import com.example.findcoffee.R;
import com.example.findcoffee.ui.explore.ExploreFragment;
import com.example.findcoffee.zomatoApiGetter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    //    private HomeViewModel homeViewModel;
    private static RecyclerView recyclerView;
    private static ArrayList<HomeViewModel> data;
    static ProgressBar bar;
    //    private int area = 1;
    public RequestQueue queue;

    public static double currentLong;

    public static double currentLat;

    int radius = 1000;

    ArrayList<String> cafeNames = new ArrayList<>();
    ArrayList<String> cafeAddresses = new ArrayList<>();
    ArrayList<LatLng> cafeCoordinates = new ArrayList<>();

    GoogleMap mGoogleMap;

    static SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    FusedLocationProviderClient mFusedLocationClient;
    Geocoder coder;

    boolean locationCheck = false;

    public static Fragment newInstance() {
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = root.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        final zomatoApiGetter zomato = new zomatoApiGetter(this);

        queue = Volley.newRequestQueue(getActivity());
        data = new ArrayList<HomeViewModel>();

        final Map<String, String> map = new HashMap<String, String>();
        map.put("entity_id=", "259");
        map.put("entity_type=", "city");
        map.put("establishment_type=", "1");
        map.put("category=", "6");
        map.put("radius=", "500");

        //sorting by real distance
        map.put("sort=", "real_distance");
        //location added below

        coder = new Geocoder(root.getContext());
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(root.getContext());
        mapFrag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        assert mapFrag != null;
        mapFrag.getMapAsync(this);

        bar = root.findViewById(R.id.progressBar);
        bar.setVisibility(VISIBLE);
        mapFrag.getView().setVisibility(GONE);
        recyclerView.setVisibility(GONE);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        if (currentLong == 0.0 && currentLat == 0.0) {
                            new android.os.Handler().postDelayed(this, 300);
                        } else {
                            map.put("lon=", currentLong + "&lat=" + currentLat);
                            zomato.search(map, queue, 100);
                        }

                    }
                },
                300);

        return root;
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        if (checkPermissions()) {
            locationCheck = true;
            GoogleMapWorking();
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }


    }

    private void GoogleMapWorking() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(120000); // two minute interval
        mLocationRequest.setFastestInterval(120000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());

        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Uri navigation = Uri.parse("google.navigation:q=" + marker.getPosition().latitude + "," + marker.getPosition().longitude + "");
                Intent navigationIntent = new Intent(Intent.ACTION_VIEW, navigation);
                navigationIntent.setPackage("com.google.android.apps.maps");
                startActivity(navigationIntent);

                return true;
            }
        });
    }


    private boolean checkPermissions()
    {
        return ActivityCompat
                .checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat
                .checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationCheck = true;
                GoogleMapWorking();
            }
        }
    }



    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {
                //The last location in the list is the newest
                Location location = locationList.get(locationList.size() - 1);
                Log.i("MapsActivity", "Location: " + location.getLatitude() + " " + location.getLongitude());
                mLastLocation = location;
                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker.remove();
                }

                //Place current location marker
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                currentLong = location.getLongitude();

                currentLat = location.getLatitude();
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("Your current location");
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);

                //move map camera
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
            }
        }
    };

    public void drawShop(String name, String address, String thumb,String addressLon,String addressLat,String cuisines,String featured_image,String menu_url, String photos_url,String price_range,String timings,String storeUrl,String events_url, String aggregate_rating){


        try {
            List<Address> address_obj = coder.getFromLocationName(address, 5);
            Address location = address_obj.get(0);
            LatLng coordinates = new LatLng(location.getLatitude(), location.getLongitude());

            float[] dist = new float[1];
            Location.distanceBetween(location.getLatitude(), location.getLongitude(), currentLat, currentLong, dist);

            if (dist[0] <= radius) {
                mGoogleMap.addMarker(new MarkerOptions()
                        .position(coordinates)
                        .title(name)
                );
            }

            data.add(
                    new HomeViewModel(
                            name,
                            address,
                            thumb,
                            addressLon,
                            addressLat,
                            cuisines,
                            featured_image,
                            menu_url,
                            photos_url,
                            price_range,
                            timings,
                            storeUrl,
                            events_url,
                            aggregate_rating,
                            dist
            ));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public static ArrayList<HomeViewModel> getData() {

        if(! data.isEmpty()){
            Log.d("getData1",data+"");
            bar.setVisibility(GONE);
            recyclerView.setVisibility(VISIBLE);
            mapFrag.getView().setVisibility(VISIBLE);
        }
        return data;
    }
}