package com.example.googlemap;

import static android.os.Build.VERSION.SDK_INT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class PermissionScreen extends AppCompatActivity {

    private Dialog dialog;
    private EasyPermissions permissions;
    String[] permissionsRequired = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    String[] permissionsRequiredForLower = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION/*, Manifest.permission.ACCESS_BACKGROUND_LOCATION*/};
    Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_screen);

        done = findViewById(R.id.button);

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

        checkLocationPermission();

        onClickDone();
    }

    private void onClickDone() {
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PermissionScreen.this,MainActivity.class));
                finish();
            }
        });
    }

    private void checkLocationPermission() {
        if (!permissions.hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            Toast.makeText(this, "not having location permission", Toast.LENGTH_SHORT).show();
            dialog = new Dialog(PermissionScreen.this);
            if (dialog != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_location_permissions);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
                TextView btnAllowAutoStart = dialog.findViewById(R.id.ok_btn_TV);
                btnAllowAutoStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!permissions.hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
// permissions.request(Manifest.permission.ACCESS_FINE_LOCATION);
                            checkPermissionss();
                        }
//                        else if (!permissions.hasPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
//                            checkPermissionsBackgroundLocation();
//                        }
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }

        } else {
            Toast.makeText(this, "got Permission", Toast.LENGTH_SHORT).show();
        }
    }

    public void checkPermissionss() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getApplicationContext(), permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED) {
                {
                    ActivityCompat.requestPermissions(this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                }
            }
        } else {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), permissionsRequiredForLower[0]) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getApplicationContext(), permissionsRequiredForLower[1]) != PackageManager.PERMISSION_GRANTED) {
                {
                    ActivityCompat.requestPermissions(this, permissionsRequiredForLower, PERMISSION_CALLBACK_CONSTANT);
                }
            }
        }
    }
}