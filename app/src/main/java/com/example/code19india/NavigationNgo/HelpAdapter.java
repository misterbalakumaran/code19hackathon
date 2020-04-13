package com.example.code19india.NavigationNgo;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.code19india.Navigation.ui.home.HomeFragment;
import com.example.code19india.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.ViewHolder> {
    Context context;
    ArrayList<HelpData> helpData;

    public HelpAdapter(Context context, ArrayList<HelpData> helpData) {
        this.context = context;
        this.helpData = helpData;
    }
    public void updateList(ArrayList<HelpData> list){
        this.helpData = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public HelpAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.help_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HelpAdapter.ViewHolder holder, int position) {
        HelpData helpData1 = helpData.get(position);
        holder.mobileno.setText("Mobile no :" + helpData1.getMobileno());
        holder.typeofhelp.setText("Help Provided :" + helpData1.getTypeofhelp());
        holder.description.setText("Description :" + helpData1.getDescription());
        holder.completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete entry")
                        .setMessage("Are you sure to mark it as completed?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                remove(position,helpData1);
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });
        holder.viewonmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr=" + helpData1.getLatitude() + "," + helpData1.getLongitude()));
                context.startActivity(intent);
            }
        });

        holder.contact.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + helpData1.getMobileno()));
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
    }
public void remove(int position,HelpData helpData1)
{
    String url="http://192.168.43.83/php//completed.php";
    StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            helpData.remove(helpData.get(position));
            updateList(helpData);
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
            params.put("id",helpData1.getId());
            return params;
        }
    };
    singleton.getInstance(context).addtoRequestqueue(stringRequest);
}
    @Override
    public int getItemCount() {
        return helpData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mobileno,typeofhelp,description;
        Button viewonmap,contact,completed;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            viewonmap=itemView.findViewById(R.id.viewonmap);
            contact=itemView.findViewById(R.id.contact);
            mobileno=itemView.findViewById(R.id.mobileno);
        typeofhelp=itemView.findViewById(R.id.type);
        description=itemView.findViewById(R.id.description);
        completed=itemView.findViewById(R.id.completed);
        }
    }

}
