package com.example.android;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class AddItemizedOverlay2 extends ItemizedOverlay<OverlayItem> {
 
       private ArrayList<OverlayItem> mapOverlays = new ArrayList<OverlayItem>();
       private int pos=0;
       private Context context;
       String s;
       String stemp;
       String templat,templong;
       static double lt=0,lng=0;
      
       
       
       
       
       
       public AddItemizedOverlay2(Drawable defaultMarker) {
            super(boundCenterBottom(defaultMarker));
       }
 
       public AddItemizedOverlay2(Drawable defaultMarker, Context context) {
            this(defaultMarker);
            this.context = context;
       }
       
       @Override
       public boolean onTouchEvent(MotionEvent event, MapView mapView)
       {   
 
           if (event.getAction() == 1) {
               GeoPoint geopoint = mapView.getProjection().fromPixels(
                   (int) event.getX(),
                   (int) event.getY());
               // latitude
             //  double lat = geopoint.getLatitudeE6() / 1E6;
               double lat = geopoint.getLatitudeE6() / 1E6;
               // longitude
             //  double lon = geopoint.getLongitudeE6() / 1E6;
               double lon = geopoint.getLongitudeE6() / 1E6;
               Toast.makeText(context, "Lat: " + lat + ", Lon: "+lon, Toast.LENGTH_SHORT).show();
           }
           return false;
       } 
 
       @Override
       protected OverlayItem createItem(int i) {
          return mapOverlays.get(i);
       }
 
       @Override
       public int size() {
          return mapOverlays.size();
       }
 
       @Override
       protected boolean onTap(int index) {
         OverlayItem item = mapOverlays.get(index);
        
         AlertDialog.Builder dialog = new AlertDialog.Builder(this.context);
         dialog.setTitle(item.getTitle());
         dialog.setMessage(item.getSnippet());
         dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
    public void onClick(DialogInterface dialog, int which) {
    }
   });
         dialog.show();
         return true;
       }

          
          
          
        
      
         
 
       public void addOverlay(OverlayItem overlay) {
          mapOverlays.add(overlay);
       }
       
       public void populateNow(){
        this.populate();
       }
       
        
       
 
    }
 