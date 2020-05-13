package com.milushifa.miplayer.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.milushifa.miplayer.R;

public class SplashScreenActivity extends AppCompatActivity {
    public static final int STORAGE_PERMISSION_REQUEST_CODE = 101;

    private Intent mainActivityIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mainActivityIntent = new Intent(this, MainActivity.class);

        StorageReadPermission();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==STORAGE_PERMISSION_REQUEST_CODE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                proceedToNext();
                return;
            }
        }
        finish();
    }

    private void StorageReadPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, STORAGE_PERMISSION_REQUEST_CODE);
        }else{
            proceedToNext();
        }
    }

    private void proceedToNext(){
        startActivity(mainActivityIntent);
        finish();
    }
}
