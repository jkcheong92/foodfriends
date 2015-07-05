package com.example.myfirstapp;


import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DisplayFriendsActivity extends ActionBarActivity 
// implements ResultCallback<LoadMomentsResult> 
{
	// JK: Fetch moments
	// private GoogleApiClient mGoogleApiClient;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_friends);
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		// Get Friends
		Intent intent = getIntent();
		ArrayList<String> friendsName = intent.getStringArrayListExtra("EXTRA_FRIENDS_NAME");
		ListView friendslist = (ListView) findViewById(R.id.friendsName);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, friendsName);
		friendslist.setAdapter(arrayAdapter);
		
		// JK: Fetch moments
		// Plus.MomentsApi.load(mGoogleApiClient).setResultCallback(this);	
				
	}
/*	// JK: Fetch moments
	public void onResult(LoadMomentsResult result) {
		  if (result.getStatus().getStatusCode() == CommonStatusCodes.SUCCESS) {
		    MomentBuffer momentBuffer = result.getMomentBuffer();
		    try {
		    	int count = momentBuffer.getCount();
		    	for(int i=0; i<count; i++)
		    		Log.d("moments", "Moment Name: " + momentBuffer.get(i).getTarget().getName());
		    } finally {
		      momentBuffer.close();
		    }
		  } else {
		    Log.e("moments", "Error when loading moments: " + result.getStatus().getStatusCode());
		  }
		}
*/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.action_buttons, menu); // loads the action buttons @action_buttons.xml
        return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        // Handle presses on the action bar items
        switch (id) {
            case R.id.action_friends:
	            //need to create a friend button activity
            	
	            return true;
	        case R.id.action_my_profile:
	            //need to create a my_profile button activity
	        	//Intent intent = new Intent(this, Profile.class);
		    	//intent.putExtra(EXTRA_PERSON_NAME, personName);
		    	//intent.putExtra(EXTRA_PERSON_GOOGLE_PLUS_PROFILE, personGooglePlusProfile);
		    	//startActivity(intent);
	            return true;
        
            case R.id.action_chat:
                //need to create a chat button activity
                return true;
            case R.id.action_settings:
                //openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_display_friends,
					container, false);
			return rootView;
		}
	}
	
}
