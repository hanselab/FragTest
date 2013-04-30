package com.hanselaboratories.fragtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.hanselaboratories.fragtest.data.DatabaseHandler;
import com.hanselaboratories.fragtest.data.Order;


/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ItemListFragment} and the item details
 * (if present) is a {@link ItemDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link ItemListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class ItemListActivity extends FragmentActivity
        implements ItemListFragment.Callbacks, NewOrderFragment.Callbacks,
        			DisplayOrderFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
	public static final String ARG_ITEM_ID = "item_id";
	private static final String NEW_ORDER = "1";
	private static final String DISPLAY_ORDER = "2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((ItemListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.item_list))
                    .setActivateOnItemClick(true);
        }

        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * Callback method from {@link ItemListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
		if(mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			if(id.equals(NEW_ORDER)) {
				NewOrderFragment frgNewOrder = new NewOrderFragment();
				getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container, frgNewOrder).commit();
			}
			if(id.equals(DISPLAY_ORDER)) {
				DisplayOrderFragment frgDisplayOrder = new DisplayOrderFragment();
				getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container, frgDisplayOrder).commit();
			}
		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, ItemDetailActivity.class);
			detailIntent.putExtra(ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
    }
	/*
	 * Callback method from {@link NewOrderFragment}
	 * @see com.hanselaboratories.fragtest01.NewOrderFragment.Callbacks#onInsertButtonClick(java.lang.String)
	 */
	@Override
	public void onInsertOrderButtonClick(Order order) {
		/*
		 * Save data into device database
		 */
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
