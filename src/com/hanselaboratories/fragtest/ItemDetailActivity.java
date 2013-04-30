package com.hanselaboratories.fragtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.hanselaboratories.fragtest.data.DatabaseHandler;
import com.hanselaboratories.fragtest.data.Order;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ItemListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link ItemDetailFragment}.
 */
public class ItemDetailActivity extends FragmentActivity 
	implements NewOrderFragment.Callbacks, DisplayOrderFragment.Callbacks {

	public static final String ARG_ITEM_ID = "item_id";
	private static final String NEW_ORDER = "1";
	private static final String DIPSLAY_ORDER = "2";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        // Show the Up button in the action bar.
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
			// Create the detail fragment and add it to the activity
			// using a fragment transaction.
			Log.i("Hanselog", "Get intent from ItemListActivity");
			Intent intent = getIntent();
			String itemSelected = intent.getStringExtra(ARG_ITEM_ID);
			Log.i("Hanselog", "itemSelected: " + itemSelected);
			if(itemSelected.equals(NEW_ORDER)) {
				Log.i("Hanselog", "Starting NewOrderFragment");
				NewOrderFragment frgNewOrder = new NewOrderFragment();
				getSupportFragmentManager().beginTransaction().add(R.id.item_detail_container, frgNewOrder).commit();
			}
			if(itemSelected.equalsIgnoreCase(DIPSLAY_ORDER)) {
				Log.i("Hanselog", "Starting DisplayOrderFragment");
				DisplayOrderFragment frgDisplayOrder = new DisplayOrderFragment();
				getSupportFragmentManager().beginTransaction().add(R.id.item_detail_container, frgDisplayOrder).commit();
			}
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpTo(this, new Intent(this, ItemListActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onInsertOrderButtonClick(Order order) {
    	Log.i("Hanselog", "Insert new order " + order.getID() + " into Orders table");
    	DatabaseHandler db = new DatabaseHandler(this);
    	db.addOrder(order);
    	
    	Log.i("Hanselog", "Writing Toast message for orderID inserted");
    	Toast.makeText(getApplicationContext(), "Order " + order.getID() + " inserted", Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public Order onDisplayOrderButtonClick(String orderID) {
		Log.i("Hanselog", "Loading order");
		DatabaseHandler db = new DatabaseHandler(this);
		Order order = db.getOrder(Integer.parseInt(orderID));
		Log.i("Hanselog", "OrderID: " + order.getID() + "Descr: " + order.getDescription());
		return order;
    }
}

