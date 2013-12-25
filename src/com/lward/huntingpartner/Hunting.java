package com.lward.huntingpartner;

import java.util.Calendar;

import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class Hunting extends Activity {

	public static Calendar date;
	public static int day;
	public static int month;
	public static int year;
	
	public static double latitude;
	public static double longitude;
	
	public static TextView dateString;
	public static TextView riseText;
	public static TextView setText;
	public static TextView startText;
	public static TextView endText;
	public static TextView latText;
	public static TextView lngText;
	
	public static String riseStr = "12:00 AM";
	public static Calendar riseTime = Calendar.getInstance();
	public static String setStr = "12:00 AM";
	public static Calendar setTime = Calendar.getInstance();

	public static Calendar startTime = riseTime;
	public static String startStr = "12:00 AM";
	public static String endStr = "12:00 AM";
	public static Calendar endTime = setTime;
	
	public static int startTimeOffset = 30;
	public static boolean startTimeBefore = true;
	
	public static int endTimeOffset = 30;
	public static boolean endTimeBefore = false;
	
	public static Context context;
	static Locator here;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hunting);
        
        dateString = (TextView)findViewById(R.id.dateString);
        riseText = (TextView)findViewById(R.id.sunriseTime);
        setText = (TextView)findViewById(R.id.sunsetTime);
        startText = (TextView)findViewById(R.id.huntinStart);
        endText = (TextView)findViewById(R.id.endHunting);
        latText = (TextView)findViewById(R.id.latText);
        lngText = (TextView)findViewById(R.id.lngText);
        
        date = Calendar.getInstance();
        
        day = date.get(Calendar.DATE);
        month = date.get(Calendar.MONTH) + 1;
        year = date.get(Calendar.YEAR);
        
        here = new Locator(this);
        setLatLng();
               	
        updateDateTimeStrings();
        
        
        
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hunting, menu);
        return true;
    }
    
    public static void setLatLng(){
    	
    	double gpsLat;
    	double gpsLng;
    	
    	if(here.canGetLocation()){
    	   	gpsLat = here.getLatitude();
	    	gpsLng = here.getLongitude();
	    	
	    	if(gpsLat == 0){
	    	   	latitude = 43.073052;
	    	} else {
	    		latitude = gpsLat;
	    	}
	    	if(gpsLng == 0){
	    		longitude = -89.40123;
	    	} else {
	    		longitude = gpsLng;
	    	}
    	} else {
	    	here.showSettingsAlert();
    	}
    }
    
    public static void updateDateTimeStrings(){
    	dateString.setText(month + "/" + day + "/" + year);
    	
    	SunTimes.calcSun(date);
    	riseText.setText(riseStr);
    	setText.setText(setStr);
    	
    	setHuntingStart();
    	startText.setText(startStr);
    	
    	setHuntingEnd();
    	endText.setText(endStr);
    	
    	String latStr = String.valueOf(Hunting.latitude);
    	String lngStr = String.valueOf(Hunting.longitude);
    	
    	latText.setText(latStr);
    	lngText.setText(lngStr);
    	
    	
    }
    
    public static void setHuntingStart(){
    	String hourStr = riseStr.substring(0,riseStr.indexOf(":"));
    	int hourInt = 12;
    	try{
    		hourInt = Integer.parseInt(hourStr);
    	} catch (NumberFormatException n){
    		System.out.println(hourStr);
    	}

    	String minStr = riseStr.substring(riseStr.indexOf(':')+1,riseStr.indexOf(" "));
    	
    	int minInt = 0;
    	
    	try{
    		minInt = Integer.parseInt(minStr);
    	} catch (NumberFormatException n){
    		System.out.println(minStr);
    	}
    	
    	startTime.set(Calendar.HOUR, hourInt);
    	startTime.set(Calendar.MINUTE, minInt);
    	
    	int offset = startTimeOffset;
    	if(startTimeBefore){
    		offset = -offset;
    	}
    	
    	startTime.add(Calendar.MINUTE, offset);
    	
    	startStr = startTime.get(Calendar.HOUR) + ":";
    	if(startTime.get(Calendar.MINUTE)<10){
    		startStr = startStr + "0" + startTime.get(Calendar.MINUTE);
    	} else {
    		startStr = startStr + startTime.get(Calendar.MINUTE);
    	}
    	if(startTime.get(Calendar.AM) == 1){
    		startStr = startStr + " AM";
    	} else {
    		startStr = startStr + " PM";
    	}
    	
    }
    
    public static void setHuntingEnd(){
    	String hourStr = setStr.substring(0,setStr.indexOf(":"));
    	int hourInt = 12;
    	try{
    		hourInt = Integer.parseInt(hourStr);
    	} catch (NumberFormatException n){
    		System.out.println(hourStr);
    	}

    	String minStr = setStr.substring(setStr.indexOf(':')+1,setStr.indexOf(" "));
    	
    	int minInt = 0;
    	
    	try{
    		minInt = Integer.parseInt(minStr);
    	} catch (NumberFormatException n){
    		System.out.println(minStr);
    	}
    	
    	endTime.set(Calendar.HOUR, hourInt);
    	endTime.set(Calendar.MINUTE, minInt);
    	
    	int offset = endTimeOffset;
    	if(endTimeBefore){
    		offset = -offset;
    	}
    	
    	endTime.add(Calendar.MINUTE, offset);
    	
    	endStr = endTime.get(Calendar.HOUR) + ":";
    	if(endTime.get(Calendar.MINUTE)<10){
    		endStr = endStr + "0" + startTime.get(Calendar.MINUTE);
    	} else {
    		endStr = endStr + endTime.get(Calendar.MINUTE);
    	}
    	if(endTime.get(Calendar.AM) == 1){
    		endStr = endStr + " AM";
    	} else {
    		endStr = endStr + " PM";
    	}
    	
    }
    
    public void nextDate(View v){
    	date.add(Calendar.DATE, 1);
    	
    	day = date.get(Calendar.DATE);
        month = date.get(Calendar.MONTH) + 1;
        year = date.get(Calendar.YEAR);
        
        Hunting.updateDateTimeStrings();
    }
    
    public void previousDate(View v){
    	date.add(Calendar.DATE, -1);
    	
    	day = date.get(Calendar.DATE);
        month = date.get(Calendar.MONTH) + 1;
        year = date.get(Calendar.YEAR);
        
        Hunting.updateDateTimeStrings();
    }
    
    public void showDatePicker(View v){
    	DatePickerFragment newFragment = new DatePickerFragment();
    	newFragment.show(getFragmentManager(), "datePicker");
    }
    
}
