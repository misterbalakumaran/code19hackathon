package com.example.code19india.Navigation.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.code19india.GovtNavigation.ui.Map.singleton;
import com.example.code19india.NavigationNgo.HelpAdapter;
import com.example.code19india.NavigationNgo.HelpData;
import com.example.code19india.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {
    public ShopAdapter(Context context, ArrayList<ShopDetails> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    Context context;
    ArrayList<ShopDetails> arrayList;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.shop_values, parent, false);
        ShopAdapter.ViewHolder viewHolder = new ShopAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
ShopDetails shopDetails=arrayList.get(position);
holder.mobile.setText("Mobile no\t"+shopDetails.getMobileno());
holder.items.setText("Items Needed\t"+shopDetails.getItems());
    holder.contact.setOnClickListener(new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + shopDetails.getMobileno()));
            if (context.checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
            context.startActivity(intent);
        }
    });
    holder.completed.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
remove(position,shopDetails);
        }
    });
    }
    public void updateList(ArrayList<ShopDetails> list){
        this.arrayList = list;
        notifyDataSetChanged();
    }
    public void remove(int position, ShopDetails helpData1)
    {
        String url="http://192.168.43.83/php//completed_shop.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                arrayList.remove(arrayList.get(position));
                updateList(arrayList);
                Toast.makeText(context,"Record Marked as Completed",Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params=new HashMap<>();
                params.put("id",helpData1.getMobileno());
                return params;
            }
        };
        singleton.getInstance(context).addtoRequestqueue(stringRequest);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       TextView mobile,items;
Button contact,completed;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mobile=itemView.findViewById(R.id.mobileno);
            items=itemView.findViewById(R.id.items);
        contact=itemView.findViewById(R.id.contact);
        completed=itemView.findViewById(R.id.completed);
        }
    }
}
