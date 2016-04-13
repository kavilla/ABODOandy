package com.kawikaavilla.abodoandy.Helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.kawikaavilla.abodoandy.Models.Apartment;
import com.kawikaavilla.abodoandy.R;
import java.util.ArrayList;

/**
 * Created by Kawika Avilla on 4/13/2016
 *
 * This class is the adapter for the listview in the main activity
 * Runs the fetch for the image
 *
 * Bugs: Not really a bug but it would work faster if I don't create a new request for the image.
 * However, it's been an hour.
 */
public class MainAdapter extends ArrayAdapter<Apartment> {
    public MainAdapter(Context context, ArrayList apartments){
        super(context, R.layout.row_mainadapter, apartments);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        LayoutInflater mainLayoutInflater = LayoutInflater.from(getContext());

        View mainAdapterView = mainLayoutInflater.inflate(R.layout.row_mainadapter, parent, false);
        TextView priceRangeTextView =
                (TextView) mainAdapterView.findViewById(R.id.activity_main_tv_pricerange);
        TextView bedRangeTextView =
                (TextView) mainAdapterView.findViewById(R.id.activity_main_tv_bedrange);
        TextView propNameTextView =
                (TextView) mainAdapterView.findViewById(R.id.activity_main_tv_propname);
        final ImageView tileImageView =
                (ImageView) mainAdapterView.findViewById(R.id.activity_main_iv_tile);

        Apartment apartmentObject = getItem(position);
        Log.v("HTTP-ADAPT", apartmentObject.getPropertyName() + ", " + apartmentObject.getTileUrl());
        priceRangeTextView.setText(apartmentObject.getPriceRange());
        bedRangeTextView.setText(apartmentObject.getBedRange());
        propNameTextView.setText(apartmentObject.getPropertyName());

        ImageRequest imageRequest = new ImageRequest(apartmentObject.getTileUrl(),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        tileImageView.setImageBitmap(response);
                    }
                }, 0, 0, Bitmap.Config.RGB_565,
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        ImageLoader imageLoader = new ImageLoader(requestQueue, new BitmapLruCache());
        ImageLoader.ImageListener imageListener =
                ImageLoader.getImageListener(tileImageView, R.drawable.defaultimage, R.drawable.defaultimage);

        imageLoader.get(apartmentObject.getTileUrl(), imageListener, 200, 200);
        return mainAdapterView;
    }
}
