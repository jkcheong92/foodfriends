package com.example.myfirstapp;

import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
		
		// JK: Fetch moments
		//fetchMoments("https://www.googleapis.com/plus/v1/people/me/moments/vault");
		friendslist.setOnItemClickListener(new OnItemClickListener() {  
			@Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// Pass intent
				String url = "https://plus.google.com/u/0/apps/activities/of/116272059231340270090/from/e19ce1be51a";
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
            }
         }); 
	}

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
	
	// JK: Fetch moments
	public static void fetchMoments(String url) {
		  InputStream content = null;
		  try {
			Log.d("try","try");
		    HttpClient httpclient = new DefaultHttpClient();
		    HttpResponse response = httpclient.execute(new HttpGet(url));
		    content = response.getEntity().getContent();
		  } catch (Exception e) {
		    Log.d("[GET REQUEST]", "Network exception", e);
		  }
		  	String moments = content.toString();
		    Log.d("moments",moments);
		}
	
}
