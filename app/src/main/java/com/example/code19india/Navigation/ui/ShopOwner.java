package com.example.code19india.Navigation.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.code19india.GovtNavigation.ui.Map.singleton;
import com.example.code19india.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShopOwner extends AppCompatActivity {
    ShopAdapter shopAdapter;
ArrayList<ShopDetails> arrayList;
RecyclerView recyclerView;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayList=new ArrayList<>();
        String userid=getIntent().getExtras().getString("userid");
        setContentView(R.layout.activity_shop_owner);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getData(userid);
    }

    private void getData(String userid) {
    String url="http://192.168.43.83/php//shop_items_details.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray=new JSONArray(response);
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    String items=jsonObject.getString("items");
                    String mobileno=jsonObject.getString("mobileno");
                arrayList.add(new ShopDetails(items,mobileno));
                shopAdapter=new ShopAdapter(ShopOwner.this,arrayList);
                recyclerView.setAdapter(shopAdapter);
                }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params=new HashMap<String, String>();
                params.put("id",userid);
                return params;
            }
        };
        singleton.getInstance(this).addtoRequestqueue(stringRequest);
    }
}
