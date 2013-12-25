package com.lward.huntingpartner;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment
								implements OnDateSetListener{

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		
		return new DatePickerDialog(getActivity(), this, Hunting.date.get(Calendar.YEAR), Hunting.date.get(Calendar.MONTH), Hunting.date.get(Calendar.DATE));
	}
	
	
	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		Hunting.day = (int) dayOfMonth;
		Hunting.month = (int) monthOfYear+1;
		Hunting.year = (int) year;
		
		Hunting.date.set(year, monthOfYear, dayOfMonth);
		
		Hunting.updateDateTimeStrings();
		
	}
	
}