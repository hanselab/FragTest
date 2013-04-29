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

public class NewOrderFragment extends Fragment {

	public static interface Callbacks {
		public void onInsertOrderButtonClick(Order order);
	}
	
	private Callbacks mCallbacks;
	
	public NewOrderFragment() {
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
		Log.i("Hanselog", "start fragment for new orders!");
		View rootView = inflater.inflate(R.layout.fragment_new_order, container, false);
		
		final EditText orderID = (EditText) rootView.findViewById(R.id.new_order_id_edittext);
		final EditText orderDescription = (EditText) rootView.findViewById(R.id.new_order_description_edittext);
		final EditText orderHU = (EditText) rootView.findViewById(R.id.new_order_hu_edittext);
		Button insertOrderButton = (Button) rootView.findViewById(R.id.new_order_insert_button);

		insertOrderButton.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				Order order = new Order(Integer.parseInt(orderID.getText().toString()), 
						orderDescription.getText().toString(), 
						Integer.parseInt(orderHU.getText().toString()));
				mCallbacks.onInsertOrderButtonClick(order);
			}
		});
		
		return rootView;
	}	
}
