package com.example.code19india.NavigationNgo;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.code19india.GovtNavigation.ui.Map.singleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AsyncHelpData extends AsyncTask<Void,Void,Void> {
public static HelpData[] arrayList;
Context context;
public static int count1=0;
AsyncHelpData(Context context,HelpData[] helpData)
{
    this.context=context;
    this.arrayList=helpData;
}
    @Override
    protected Void doInBackground(Void... voids) {

        return null;
    }


}
