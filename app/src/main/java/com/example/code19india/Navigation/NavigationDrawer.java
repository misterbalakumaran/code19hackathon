package com.example.code19india.Navigation;

import android.Manifest;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.code19india.GovtNavigation.NavigationGovt;
import com.example.code19india.GovtNavigation.ui.Map.singleton;
import com.example.code19india.Navigation.ui.Async;
import com.example.code19india.Navigation.ui.ShopOwner;
import com.example.code19india.NavigationNgo.NavigationNgo;
import com.example.code19india.R;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class NavigationDrawer extends AppCompatActivity {
LocationManager locationManager;
    private AppBarConfiguration mAppBarConfiguration;
AlertDialog alertDialog;
    public static double latitude;
public static double longitude;
    Button bt;
    public static String place;
Button add_shop;
View v1;
Button login_shop;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
    bt=findViewById(R.id.loginorganistion);
    add_shop=findViewById(R.id.add_shop);
   login_shop=findViewById(R.id.login_shop);

   login_shop.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           showLoginAlert(v);

       }
   });
    getLocation();
    add_shop.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showShopAlert();
        }
    });
    bt.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showAlert();
        }
    });
        Async async=new Async(NavigationDrawer.this);
        async.execute();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    private void showLoginAlert(View v) {
        View view=v;
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.login_shop, null);
        dialogBuilder.setView(dialogView);
        EditText userid=dialogView.findViewById(R.id.userid);
        Button bt=dialogView.findViewById(R.id.submit);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           Intent intent=new Intent(NavigationDrawer.this, ShopOwner.class);
           intent.putExtra("userid",userid.getText().toString());
           startActivity(intent);

            }
        });
        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    public void showAlert()
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_main, null);
        dialogBuilder.setView(dialogView);
        EditText userid=dialogView.findViewById(R.id.userid);
        EditText password=dialogView.findViewById(R.id.password);
        Button bt=dialogView.findViewById(R.id.submit);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userid.getText().toString().equals("ngo") && (password.getText().toString().equals("1234")))
                {
                    Intent intent=new Intent(NavigationDrawer.this, NavigationNgo.class);
                    startActivity(intent);
                }
                else if(userid.getText().toString().equals("govt") && (password.getText().toString().equals("1234")))
                {
                    Intent intent=new Intent(NavigationDrawer.this, NavigationGovt.class);
                    startActivity(intent);
                }
            }
        });
        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }
    public void showShopAlert()
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_shop, null);
        dialogBuilder.setView(dialogView);
EditText mobileno=dialogView.findViewById(R.id.mobileno);
Button bt=dialogView.findViewById(R.id.shop_location);
EditText address=dialogView.findViewById(R.id.address);
        AlertDialog alertDialog = dialogBuilder.create();
bt.setOnClickListener(new View.OnClickListener() {
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
if(latitude==0) {
    getLocation();
}
else
{
    Toast.makeText(getApplicationContext(),"Shop location added",Toast.LENGTH_LONG).show();
}
    }
});
Button submit=dialogView.findViewById(R.id.submit);
submit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
setValue(mobileno.getText().toString(),latitude,longitude,address.getText().toString(),place);
alertDialog.dismiss();
    }
});

        alertDialog.show();
    }
    private void setValue(String mobileno, double latitude, double longitude, String address, String place) {
        String url="http://192.168.43.83/php//add_shop.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(NavigationDrawer.this,response,Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Random random=new Random();
                String unique_value=String.format("%06d", random.nextInt(1000000));
               sendSms(unique_value,mobileno);
                HashMap<String,String> params=new HashMap<String,String>();
                params.put("latitude",""+latitude);
                params.put("longitude",""+longitude);
                params.put("mobileno",mobileno);
                params.put("id", unique_value);
                params.put("address",address);
                params.put("place",place);
                return params;
            }
        };
        singleton.getInstance(this).addtoRequestqueue(stringRequest);
    }

    private void sendSms(String message,String mobileno) {
        String no=mobileno;
        String msg="This is your user id"+message;
        //Getting intent and PendingIntent instance

        SmsManager sms= SmsManager.getDefault();
        sms.sendTextMessage(no, null, msg, null,null);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getLocation()
    {

        final int[] count = {0};
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if(count[0] ==0)
                {
                    latitude=location.getLatitude();
                    longitude=location.getLongitude();
                    place=getAddressFromLocation(new LatLng(latitude,longitude));
                    count[0]++;
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public String getAddressFromLocation(LatLng latLng)
    {
        String city=null;
        try {
            Geocoder geo = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses.isEmpty()) {

            }
            else {
                if (addresses.size() > 0) {
                    city=addresses.get(0).getLocality();

                    //Toast.makeText(getApplicationContext(), "Address:- " + addresses.get(0).getFeatureName() + addresses.get(0).getAdminArea() + addresses.get(0).getLocality(), Toast.LENGTH_LONG).show();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace(); // getFromLocation() may sometimes fail
        }
        return city;
    }
}
