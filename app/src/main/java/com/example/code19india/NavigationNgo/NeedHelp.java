package com.example.code19india.NavigationNgo;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NeedHelp extends Fragment {
    ArrayList<HelpData> arrayList;
public static HelpAdapter adapter;
   EditText search;
    public NeedHelp() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_need_help, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);arrayList=new ArrayList<>();
        search=view.findViewById(R.id.search);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getData(recyclerView);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
filter(s.toString().toLowerCase());
            }
        });

    }
    public void getData(RecyclerView recyclerView)
    {
        String url="http://192.168.43.83/php//helpdata.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray=new JSONArray(response);

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String latitude=jsonObject.getString("latitude");
                        String longitude=jsonObject.getString("longitude");
                        String mobileno=jsonObject.getString("mobileno");
                        String description=jsonObject.getString("description");
                        String typeofhelp=jsonObject.getString("typeofhelp");
                        String count=jsonObject.getString("count");
                        String id=jsonObject.getString("id");
                        String place=jsonObject.getString("place");

                        if(count.equals("2")) {
                            arrayList.add(new HelpData(latitude, longitude, mobileno, description, typeofhelp, count, id,place.toLowerCase()));
                             adapter = new HelpAdapter(getContext(), arrayList);

                            recyclerView.setAdapter(adapter);
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
        singleton.getInstance(getContext()).addtoRequestqueue(stringRequest);

    }
    void filter(String text){

        ArrayList<HelpData> temp = new ArrayList();
        for(HelpData d: arrayList){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(d.getPlace().contains(text)){
                temp.add(d);
            }
        }
        //update recyclerview
        adapter.updateList(temp);
    }
}
