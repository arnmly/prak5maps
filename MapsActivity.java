<!-- --------- MapsActivity.java ----------- -->

package com.example.maps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener
{

    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location lokasiTerakhir;
    private Marker tandaLokasiUserSaatIni;
    private static final int request_lokasi_user_kode = 99;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            cekLokasiUserPermission();
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            buildGoogleApiClient();

            mMap.setMyLocationEnabled(true);
        }
        LatLng mylokasi = new LatLng(-8.0923,112.0087);
        mMap.addMarker(new MarkerOptions().position(mylokasi).title("Lokasi Anda"));
        LatLng pommini = new LatLng(-8.0930899,112.0220867);
        mMap.addMarker(new MarkerOptions().position(pommini).title("POM MINI Cemandi").snippet("Cemandi, Kunir, Wonodadi, Blitar, East Java 66155"));
        LatLng spbu1 = new LatLng(-8.0881972,112.0451939);
        mMap.addMarker(new MarkerOptions().position(spbu1).title("SPBU").snippet("Lempung, Pakisrejo, Srengat, Blitar, East Java 66152"));
        LatLng spbu2 = new LatLng(-8.0615667,112.0637333);
        mMap.addMarker(new MarkerOptions().position(spbu2).title("SPBU 54.661.27").snippet("Srengat II, Srengat, Blitar, East Java 66152"));
        LatLng spbu3 = new LatLng(-8.0594315,112.0536375);
        mMap.addMarker(new MarkerOptions().position(spbu3).title("SPBU Togogan").snippet("Togogan I, Togogan, Srengat, Blitar, East Java 66152"));
        LatLng pommini2 = new LatLng(-8.0831835,112.0063877);
        mMap.addMarker(new MarkerOptions().position(pommini2).title("POM MINI Gandekan").snippet("Gandekan, Wonodadi, Blitar, East Java"));
        LatLng pommini3 = new LatLng(-8.0676641,111.9871616);
        mMap.addMarker(new MarkerOptions().position(pommini3).title("POM MINI Ar Rizquna").snippet("Pakel, Ngantru, Tulungagung Regency, East Java 66252"));
        LatLng spbu4 = new LatLng(-8.0961637,111.9932234);
        mMap.addMarker(new MarkerOptions().position(spbu4).title("SPBU 54.662.03").snippet("Tulungagung, Karang Tengah, Pulosari, Ngunut, Tulungagung Regency, East Java 66292"));
        LatLng spbu5 = new LatLng(-8.0506569,112.0123744);
        mMap.addMarker(new MarkerOptions().position(spbu5).title("SPBU 54.661.22").snippet("Jl. Raya Pikatan Wonodadi, Gendes, Pikatan, Wonodadi, Blitar, Jawa Timur 66155"));
        LatLng pommini4 = new LatLng(-8.1057923,112.0084906);
        mMap.addMarker(new MarkerOptions().position(pommini4).title("PERTAMINI 'RECO BARONG'").snippet("Jl. Reco Barong, Lingkungan 9, Ngunut, Kec. Ngunut, Kabupaten Tulungagung, Jawa Timur 66292"));
        LatLng pommini5 = new LatLng(-8.1046804,112.0184033);
        mMap.addMarker(new MarkerOptions().position(pommini5).title("PERTAMINI Gudang Kapuk 1").snippet("Jl. Raya Tulungagung - Blitar No.115, Lingkungan 9, Ngunut, Kec. Ngunut, Kabupaten Tulungagung, Jawa Timur 66292"));

    }

    public boolean cekLokasiUserPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, request_lokasi_user_kode);
            }
            else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, request_lokasi_user_kode);
            }
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case  request_lokasi_user_kode:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                        if (googleApiClient == null){
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else{
                    Toast.makeText(this,"Akses Tidak Di Izinkan", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    protected synchronized void buildGoogleApiClient(){
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        lokasiTerakhir = location;
        if(tandaLokasiUserSaatIni != null){
            tandaLokasiUserSaatIni.remove();
        }
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Lokasi kamu jamet");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

        tandaLokasiUserSaatIni = mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.moveCamera(CameraUpdateFactory.zoomBy(12));

        if (googleApiClient != null){
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);

        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1100);
        locationRequest.setFastestInterval(1100);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
