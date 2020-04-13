package com.example.code19india.Navigation.ui;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.example.code19india.GovtNavigation.ui.Map.singleton;
import com.example.code19india.Navigation.ui.home.HomeFragment;
import com.example.code19india.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

/**
 * A simple {@link Fragment} subclass.
 */
public class RouteSuggestion extends Fragment  {
    GoogleMap mMap;
    Marker marker=null;
LatLng startlatlng;
LatLng endlatlng;
String search;
ArrayList<LatLng> arrayList;
    public RouteSuggestion() {
        // Required empty public constructor
    }

EditText start;
    EditText end;
    Button bt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_route_suggestion, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       start=view.findViewById(R.id.start);
       arrayList=new ArrayList<LatLng>();
       start.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                Toast.makeText(getContext(),"Long press to Add Start point",Toast.LENGTH_LONG).show();
               openMap(0);
           }
       });
       end =view.findViewById(R.id.end);
       end.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Toast.makeText(getContext(),"Long press to Add End point",Toast.LENGTH_LONG).show();
               openMap(1);
           }
       });
       bt=view.findViewById(R.id.suggest);
       bt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               ArrayList<Marker> arrayList= HomeFragment.markerlist;
           search=getAddressFromLocation(startlatlng);
               getSuggestion(startlatlng,endlatlng,arrayList);

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
    public void openMap(int code)
    {
        Dialog dialog = new Dialog(getActivity());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_map);
        dialog.show();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
Button bt=dialog.findViewById(R.id.submit);
        MapView mMapView = (MapView) dialog.findViewById(R.id.mapView);
        MapsInitializer.initialize(getActivity());

        mMapView = (MapView) dialog.findViewById(R.id.mapView);
        mMapView.onCreate(dialog.onSaveInstanceState());
        mMapView.onResume();// needed to get the map to display immediately
        MapView finalMMapView = mMapView;
        MapView finalMMapView1 = mMapView;
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
mMap=googleMap;
                int height = 40;
                int width = 40;

                Drawable drawable = getResources().getDrawable(R.drawable.circle);
                Bitmap bd = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

                Canvas canvas = new Canvas(bd);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);

                Bitmap smallMarker = Bitmap.createScaledBitmap(bd, width, height, false);

if(startlatlng!=null)
{
    marker=googleMap.addMarker(new MarkerOptions().position(startlatlng).title("Start Position").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
}
if(code==2)
{Marker coronomarker = null;
    googleMap.addMarker(new MarkerOptions().position(startlatlng).title("Start Position").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
    googleMap.addMarker(new MarkerOptions().position(endlatlng).title("End Position").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
    Animation markerAnimation = new ScaleAnimation(0, 1, 0, 1);
    markerAnimation.setDuration(1000);
    for(int i=0;i<arrayList.size();i++)
{
   coronomarker= googleMap.addMarker(new MarkerOptions().position(arrayList.get(i)).title("Corono Affected Area").icon(BitmapDescriptorFactory.fromBitmap(smallMarker)).snippet("Places affected by corono"));
coronomarker.showInfoWindow();
}


}
                final int[] count1 = {0};
googleMap.setMyLocationEnabled(true);

                mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                    @Override
                    public void onMyLocationChange(Location location) {
if(count1[0] ==0) {
    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
    CameraUpdate zoom = CameraUpdateFactory.zoomTo(11);
    mMap.moveCamera(center);
    mMap.animateCamera(zoom);
count1[0]++;
}
                    }
                });

googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
    @Override
    public void onMapLongClick(LatLng latLng) {
        if(code==0) {
            marker = googleMap.addMarker(new MarkerOptions().position(latLng).title("Start Position").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).draggable(true));
        }
        else if(code==1)
        {
            marker = googleMap.addMarker(new MarkerOptions().position(latLng).title("End Position").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).draggable(true));
        }
    }
});
bt.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(code==0) {
            startlatlng = marker.getPosition();
        start.setText(startlatlng.latitude+","+startlatlng.longitude);
        }
        else if(code==1)
        {
            endlatlng=marker.getPosition();
            end.setText(endlatlng.latitude+","+endlatlng.longitude);
        }
    dialog.dismiss();
    }
});

            }
        });


    }
    public void getSuggestion(LatLng start,LatLng end,ArrayList<Marker> markers)
    {
        Log.e("enter","ener");
        int countvalue=0;
        for (Marker marker : markers) {
            if (SphericalUtil.computeDistanceBetween(start, marker.getPosition()) < 1000) {
            countvalue++;
                Log.e("countvalue",""+countvalue);
         arrayList.add(marker.getPosition());
            }
        }
        for (Marker marker : markers) {
            if (SphericalUtil.computeDistanceBetween(end, marker.getPosition()) < 1000) {
                countvalue++;
                Log.e("countvalue",""+countvalue);
         mMap.addMarker(new MarkerOptions().position(marker.getPosition()));
         if(!arrayList.contains(marker.getPosition())) {
             arrayList.add(marker.getPosition());
         }
            }
        }
showAlert(countvalue);

    }
    public void showAlert(int countvalues)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.bottom_sheet, null);
        dialogBuilder.setView(dialogView);
int risk=0;
if(countvalues<10 && countvalues>1)
{
    risk=20;
}
else if(countvalues>10 && countvalues<50)
{
    risk=60;
}
else if(countvalues>50)
{
    risk=80;
}
        Button bt=dialogView.findViewById(R.id.show);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap(2);
            }
        });
        TextView editText =  dialogView.findViewById(R.id.personaffected);
        editText.setText("Number of Persons Affected through this route :"+"\t"+countvalues);
        TextView textView=dialogView.findViewById(R.id.risk);
        textView.setText("Risk to Travel  :"+"\t"+risk+"%");
      TextView textView1=dialogView.findViewById(R.id.affected_persons);
      affectedPersons(textView1);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }
public void affectedPersons(TextView textView)
{
    String url="https://api.covid19india.org/state_district_wise.json";
    StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
       //  Log.e("response",response);
            try {
                JSONObject jsonObject=new JSONObject(response);
         for(int i=0;i<jsonObject.length();i++)
         {
             String state=jsonObject.getString("Tamil Nadu");
             Log.e("state",state);
             JSONObject jsonObject1=new JSONObject(state);
             for(int j=0;j<jsonObject1.length();j++)
             {

                 String district=jsonObject1.getString("districtData");
                 Log.e("district",district);
                JSONObject jsonObject2=new JSONObject(district);

//                 Log.e("search",search);
                 String city=jsonObject2.getString(search);
                 JSONObject jsonObject3=new JSONObject(city);
                 String affected=jsonObject3.getString("confirmed");
                 Log.e("city",city);
                 textView.setText("Number of people affected in your city"+"\t"+affected);

             }
         }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    });
    singleton.getInstance(getActivity()).addtoRequestqueue(stringRequest);
}
    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }


}
