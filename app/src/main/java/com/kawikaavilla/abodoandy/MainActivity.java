package com.kawikaavilla.abodoandy;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.kawikaavilla.abodoandy.Helpers.MainAdapter;
import com.kawikaavilla.abodoandy.Models.Apartment;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MAIN";

    private static String baseUrl = "https://www.abodo.com/search/get_property_results.json?" +
            "lat=43.0752983&lng=-89.39389799999998&min_rent=&max_rent=&passed_search_area_text" +
            "=Madison,%20WI%20Apartments";
    private ArrayList<Apartment> apartmentArrayList = new ArrayList<>();
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        final ListView mainListView = (ListView) findViewById(R.id.activity_main_lv_mainlist);
        final ListAdapter mainAdapter = new MainAdapter(this, apartmentArrayList);
        mainListView.setAdapter(mainAdapter);

        requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonoArrayRequest = new JsonArrayRequest(Request.Method.GET, baseUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray responseArray) {
                        for(int i = 0; i < responseArray.length(); i++){
                            String priceRange;
                            String propertyName;
                            String bedRange;
                            String tileUrl;
                            try {
                                JSONObject object = responseArray.getJSONObject(i);
                                bedRange = object.getString("beds_range");
                                propertyName = object.getString("prop_display_name");
                                priceRange = object.getString("rent_range");
                                tileUrl = object.getString("tile_url");
                                Apartment newApartment =
                                        new Apartment(propertyName, tileUrl, priceRange, bedRange);
                                apartmentArrayList.add(newApartment);
                            } catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                        Log.v(TAG, apartmentArrayList.toString());
                        ((MainAdapter) mainListView.getAdapter()).notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.toString());
                    }
                }
        );
        requestQueue.add(jsonoArrayRequest);
    }


}
