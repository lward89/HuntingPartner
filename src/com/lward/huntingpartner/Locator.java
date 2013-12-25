package com.lward.huntingpartner;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;

public class Locator extends Service implements LocationListener{
	
	static LocationManager locationManager;
	static LocationListener locationListener;
	private static Context mContext;
	static boolean gpsEnabled = false;
	static boolean netEnabled = false;
	static boolean canGetLocation = false;
	static Location location;
	static double latitude;
	static double longitude;
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 100;
	private static final long MIN_TIME_BW_UPDATES = 1000*60*60;
	
	public Locator (Context context){
		mContext = context;
		getLocation();
	}
	
	public static Location getLocation(){
		try{
			locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
			
			try{
				gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
			}catch(Exception ex){}
			try{
				netEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			}catch(Exception ex1){}
			
			if(!gpsEnabled && !netEnabled){
				//No Network Provider Enabled
			} else {
				canGetLocation = true;
				if(netEnabled){
					locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);
					if(locationManager != null){
						location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						if(location != null){
							latitude = location.getLatitude();
							longitude = location.getLongitude();
						}
					}
				}
				if(gpsEnabled){
					if(location == null){
						locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);
						if(locationManager != null){
							location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if(location != null){
								latitude = location.getLatitude();
								longitude = location.getLongitude();
							}
						}
					}
				}
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return location;
	}
	
	public double getLatitude(){
		if (location != null){
			latitude = location.getLatitude();
		}
		return latitude;
	}
	
	public double getLongitude(){
		if (location != null){
			longitude = location.getLongitude();
		}
		
		return longitude;
	}
	
	public boolean canGetLocation(){
		return this.canGetLocation;
	}
	
	public void showSettingsAlert(){
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
		
		alertDialog.setTitle("GPS Settings");
		alertDialog.setMessage("GPS is not Enabled. Do you want to go to settings menu?");
		alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which){
				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				mContext.startActivity(intent);
			}
		});
		
		alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which){
				dialog.cancel();
			}
		});
		
		alertDialog.show();
	}

	public void stopUsingGPS(){
		if(locationManager != null){
			locationManager.removeUpdates(locationListener);
		}
	}
	
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
}