package com.example.code19india;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.code19india.GovtNavigation.NavigationGovt;
import com.example.code19india.Navigation.NavigationDrawer;
import com.google.android.gms.maps.MapView;

public class MainActivity extends AppCompatActivity {
Button bt;
Button govt;
EditText userid;
EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        userid=findViewById(R.id.userid);
        password=findViewById(R.id.password);
    //    bt=findViewById(R.id.people);
    govt=findViewById(R.id.submit);
    govt.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent=new Intent(MainActivity.this, NavigationGovt.class);
            startActivity(intent);
        }
    });
    bt.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(MainActivity.this, NavigationDrawer.class);
            startActivity(intent);
        }
    });
    }
}
