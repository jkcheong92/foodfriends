package com.example.myfirstapp;


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
import android.widget.TextView;


public class DisplayProfileActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_profile);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		Intent intent = getIntent();
		String personName = intent.getStringExtra(MainActivity.EXTRA_PERSON_NAME);
		String personGooglePlusProfile = intent.getStringExtra(MainActivity.EXTRA_PERSON_GOOGLE_PLUS_PROFILE);
		//String profilePicUrl = intent.getStringExtra(MainActivity.EXTRA_PERSON_PHOTO);
		
		TextView username = (TextView) findViewById(R.id.personName);
		username.setText(personName);
		
		TextView googlePlusProfile = (TextView) findViewById(R.id.personGooglePlusProfile);
		googlePlusProfile.setText(personGooglePlusProfile);
		
		/*iv = (ImageView) findViewById(R.id.profile_pic);
		InputStream is = getResources().openRawResource(R.drawable.ic_action_chat);
		//bitmap = getBitmapFromURL("http://images5.fanpop.com/image/photos/30400000/Kira-and-Lacus-gundam-seed-30412325-576-404.jpg");
		bitmap = BitmapFactory.decodeStream(is);
		iv.setImageBitmap(bitmap);
		Log.d("bitmap", "1");
		Log.d("url", profilePicUrl);*/
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
			View rootView = inflater.inflate(R.layout.fragment_display_profile,
					container, false);
			return rootView;
		}
	}
	
	/*public Bitmap getBitmapFromURL(String src){
		try{
			URL url = new URL(src);
			HttpURLConnection connection =(HttpURLConnection)url.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			return myBitmap;
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}*/

}
