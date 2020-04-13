package com.example.code19india.Navigation.ui.send;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.code19india.GovtNavigation.ui.Map.singleton;
import com.example.code19india.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class SendFragment extends Fragment {

    private SendViewModel sendViewModel;
    LocationManager locationManager;
    public static double latitude=0;
    Button locationbt;
    public static double longitude=0;
String item;
EditText mobile;
   EditText editText;
   Button submit;
   public static String place;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(SendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_send, container, false);

        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
   getLocation();
   mobile=view.findViewById(R.id.mobile);
   editText=view.findViewById(R.id.comments);
   submit=view.findViewById(R.id.submit);
   submit.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           String commentvalue=editText.getText().toString();
          String mobileno=mobile.getText().toString();
           if(commentvalue.isEmpty() || mobile.getText().toString().isEmpty())
           {
               Toast.makeText(getContext(),"Please enter All fields",Toast.LENGTH_LONG).show();
           }
           else
           {
                setValue(mobileno,latitude,longitude,commentvalue,item);
           }
       }
   });
   locationbt=view.findViewById(R.id.locationbt);
       locationbt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(latitude==0) {
                   getLocation();
               }
               else{

                   Toast.makeText(getContext(),"Location updated successfully",Toast.LENGTH_LONG).show();
               }
               }
       });

        final Spinner spinner = view.findViewById(R.id.spinner);


        // Spinner click listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
 item=parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Food");
        categories.add("Money");
        categories.add("Shelter");

        // Creating adapter for spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item,categories);

        // Drop down layout style - list view with radio button
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(adapter);

    }

    private void setValue(String mobileno, double latitude, double longitude, String commentvalue, String item) {
    String url="http://192.168.43.83/php//providehelp.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
Toast.makeText(getContext(),response,Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params=new HashMap<String,String>();
                params.put("latitude",""+latitude);
                params.put("longitude",""+longitude);
                params.put("mobileno",mobileno);
                params.put("typeofhelp",item);
                params.put("comments",commentvalue);
                params.put("id", UUID.randomUUID().toString());
                params.put("count","1");
                params.put("place",place);
                return params;
            }
        };
        singleton.getInstance(getContext()).addtoRequestqueue(stringRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getLocation()
    {

        final int[] count = {0};
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
    public String getAddressFromLocation(LatLng latLng)
    {
        String city=null;
        try {
            Geocoder geo = new Geocoder(getContext(), Locale.getDefault());
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