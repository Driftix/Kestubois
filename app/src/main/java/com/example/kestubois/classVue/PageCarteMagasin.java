package com.example.kestubois.classVue;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.kestubois.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class PageCarteMagasin extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private double mapx;
    private double mapy;
    private MarkerOptions markerOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carte_magasin);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = this;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission")
                    .setMessage("Accepte si t'as envie que ça marche")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(PageCarteMagasin.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                            initialisatonGPS(locationManager, locationListener);
                        }
                    })
                    .create()
                    .show();
        } else {
            initialisatonGPS(locationManager, locationListener);
        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(this.mapx, this.mapy), 0));

        //LatLng Torderes = new LatLng(this.mapx, this.mapy);
        ////mMap.getUiSettings().setCompassEnabled(true);
        //mMap.getUiSettings().setZoomControlsEnabled(true);
        //mMap.addMarker(new MarkerOptions().position(Torderes).title("Tordères"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Torderes, 0));

        LatLng maPosition = new LatLng(this.mapx, this.mapy);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(maPosition, 0));
    }

    @Override
    public void onLocationChanged(Location location) {

        this.mapx = location.getLatitude();
        this.mapy = location.getLongitude();
        LatLng maPosition = new LatLng(this.mapx, this.mapy);
        float zoom = 15;

        if(this.markerOptions == null){
            this.markerOptions = new MarkerOptions().position(maPosition).title("Votre position");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
        }else{
            zoom = mMap.getCameraPosition().zoom;
            mMap.clear();
            this.markerOptions = new MarkerOptions().position(maPosition).title("Votre position");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
            //markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.stickman));
        }

        mMap.addMarker(this.markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(maPosition, zoom));
    }

    public void initialisatonGPS(LocationManager locationManager, LocationListener locationListener) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
        }
    }
}