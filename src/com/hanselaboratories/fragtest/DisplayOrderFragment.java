package com.hanselaboratories.fragtest;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hanselaboratories.fragtest.data.Order;

public class DisplayOrderFragment extends Fragment {

	public static interface Callbacks {
		public Order onDisplayOrderButtonClick(String orderID);
	}
	
	private Callbacks mCallbacks;

	public DisplayOrderFragment() {
		
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		if(activity instanceof Callbacks) {
			mCallbacks = (Callbacks) activity;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.i("Hanselog", "start fragment for display orders!");
		View rootView = inflater.inflate(R.layout.fragment_display_order, container, false);
		
		final EditText orderIDtoDisplay = (EditText) rootView.findViewById(R.id.display_order_id_edittext);
		Button DisplayOrderButton = (Button) rootView.findViewById(R.id.display_order_button);
		
		final EditText orderID = (EditText) rootView.findViewById(R.id.modify_order_id_edittext);
		final EditText orderDescription = (EditText) rootView.findViewById(R.id.modify_order_description_edittext);
		DisplayOrderButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("Hanselog", "Loading orderID: " + orderIDtoDisplay.getText().toString());
				Order order = mCallbacks.onDisplayOrderButtonClick(orderIDtoDisplay.getText().toString());
				Log.i("Hanselog", "set widget data");
				Log.i("Hanselog", "ID: " + order.getID() + " Desc: " + order.getDescription());
				orderID.setText(Integer.toString(order.getID()));
				orderDescription.setText(order.getDescription());				
			}
		});
		
		return rootView;
	}
}
