package com.example.googlemap;

import static android.os.Build.VERSION.SDK_INT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    GPSTracker gps;
    double latitude, longitude;
    private Dialog dialog;
    private EasyPermissions permissions;
    String[] permissionsRequired = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    String[] permissionsRequiredForLower = new String[]{Manifest.permission.ACCESS_FINE_LOCATION/*, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION*/};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permissions = new EasyPermissions.Builder()
                .with(this)
                .listener(
                        new OnPermissionListener() {
                            @Override
                            public void onAllPermissionsGranted(@NonNull List<String> permissions) {
                                Log.e("allprmission", "all permission");
                            }

                            @Override
                            public void onPermissionsGranted(@NonNull List<String> permissions) {
                                Log.e("allprmission", "onPermissionsGranted");
                            }

                            @Override
                            public void onPermissionsDenied(@NonNull List<String> permissions) {
                                Log.e("allprmission", "onPermissionsDenied");

                            }
                        })
                .build();

//        checkLocationPermission();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        gps = new GPSTracker(MainActivity.this);
        gps.getLocation();
        try {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void checkLocationPermission() {
//        if (!permissions.hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
//            Toast.makeText(this, "not having location permission", Toast.LENGTH_SHORT).show();
//            dialog = new Dialog(MainActivity.this);
//            if (dialog != null) {
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                dialog.setContentView(R.layout.dialog_location_permissions);
//                dialog.setCanceledOnTouchOutside(false);
//                dialog.setCancelable(false);
//                TextView btnAllowAutoStart = dialog.findViewById(R.id.ok_btn_TV);
//                btnAllowAutoStart.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (!permissions.hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
//// permissions.request(Manifest.permission.ACCESS_FINE_LOCATION);
//                            checkPermissionss();
//                        }
////                        else if (!permissions.hasPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
////                            checkPermissionsBackgroundLocation();
////                        }
//                        dialog.dismiss();
//                    }
//                });
//                dialog.show();
//            }
//
//        } else {
//            Toast.makeText(this, "got Permission", Toast.LENGTH_SHORT).show();
//        }
//    }

//    public void checkPermissionss() {
//        if (SDK_INT >= Build.VERSION_CODES.R) {
//            if (ActivityCompat.checkSelfPermission(getApplicationContext(), permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED /*|| ActivityCompat.checkSelfPermission(getApplicationContext(), permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED*/) {
//                {
//                    ActivityCompat.requestPermissions(this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
//                }
//            }
//        } else {
//            if (ActivityCompat.checkSelfPermission(getApplicationContext(), permissionsRequiredForLower[0]) != PackageManager.PERMISSION_GRANTED /*|| ActivityCompat.checkSelfPermission(getApplicationContext(), permissionsRequiredForLower[1]) != PackageManager.PERMISSION_GRANTED*/) {
//                {
//                    ActivityCompat.requestPermissions(this, permissionsRequiredForLower, PERMISSION_CALLBACK_CONSTANT);
//                }
//            }
//        }
//    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (mMap != null) {
        LatLng calymayor = new LatLng(latitude, longitude);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(calymayor));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(calymayor, 16));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 16.0f));
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.add(calymayor);
        polylineOptions.color(Color.CYAN);
        polylineOptions.clickable(true);
        polylineOptions.width(30);

//        LatLng sydney = new LatLng(latitude,longitude);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("MArker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }}
}