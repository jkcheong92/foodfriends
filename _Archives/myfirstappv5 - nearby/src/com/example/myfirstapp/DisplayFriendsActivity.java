package com.example.myfirstapp;

import java.util.ArrayList;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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
{		
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
		
				
		// JK: Click on list
/*		friendslist.setOnItemClickListener(new OnItemClickListener() {  
			@Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// Pass intent
				String url = "https://plus.google.com/u/0/circles";
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
            }
         }); 
*/	}

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
	
	// JK: EDIT friends list
	public void editList(View view) {  		
		// String url = "https://m.google.com/app/basic/stream/nearby";	// Open nearby page
		Intent i = new Intent(Intent.ACTION_VIEW);
		String url = "https://plus.google.com/u/0/circles";
		//String url = "geo:"+ 0 + "," + 0 + "?q=yung ho road";			// Open google map
		i.setData(Uri.parse(url));
		if(isAppInstalled("com.google.android.apps.plus")) {
			i.setPackage("com.google.android.apps.plus");				// Open Google+ app
		}
		startActivity(i);								// if no google+ open with browser
	}
	
	// JK: Chat with friends
	public void onClickWhatsApp(View view) {
	    Intent waIntent = new Intent(Intent.ACTION_SEND);
	    waIntent.setType("text/plain");
	    // String text = "Sent from FoodFriends";			// Automatically send out
	    // waIntent.putExtra(Intent.EXTRA_TEXT, text);
/*	    if(isAppInstalled("com.whatsapp")) {
	    	waIntent.setPackage("com.whatsapp");				// Forcestart whatsapp
	    	startActivity(waIntent);
	    }
	    else
*/	    	startActivity(Intent.createChooser(waIntent, "Start a chat with"));
	}
	
	// JK: Check if app is installed
	private boolean isAppInstalled(String packageName) {
	    PackageManager pm = getPackageManager();
	    boolean installed = false;
	    try {
	        pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
	        installed = true;
	    } catch (PackageManager.NameNotFoundException e) {
	        installed = false;
	    }
	    return installed;
	}
		
}
