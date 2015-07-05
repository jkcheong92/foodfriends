package com.example.myfirstapp;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class DisplayFriendsActivity extends ActionBarActivity 
{		
	private final static String profilePicSize = "200";
	
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

		// JW
/*		ArrayList<String> friendsName = intent.getStringArrayListExtra("EXTRA_FRIENDS_NAME");
		ArrayList<String> friendProfilePicUrl = intent.getStringArrayListExtra("EXTRA_FRIENDS_PROFILE_PIC");
		
		Integer arraylistSize1 = new Integer(friendsName.size());
		Integer arraylistSize2 = new Integer(friendProfilePicUrl.size());
		Log.d("size of url arraylist 1", arraylistSize1.toString());
		Log.d("size of url arraylist 2", arraylistSize2.toString());
		for(int i =0; i<arraylistSize2;i++){
			Log.d("hello","hello");
//			Log.d("url " + i, friendProfilePicUrl.get(i) );
		}
		
		// edit the last 2 letters of the url to adjust size using profilePicSize
		for(int i = 0; i<friendProfilePicUrl.size();i++) {
			Log.d("nullPoint", friendProfilePicUrl.get(i));
			
			String newFriendProfilePicUrl = new String(friendProfilePicUrl.get(i).substring(0,friendProfilePicUrl.get(i).length()-2) + profilePicSize);
			friendProfilePicUrl.set(i,newFriendProfilePicUrl);
		}
		
		for(int i=0; i<friendProfilePicUrl.size();i++) {
			Log.d("URL", friendProfilePicUrl.get(i));
		}
		FriendDetailsAdapter friendDetailAdapter = new FriendDetailsAdapter(friendsName, friendProfilePicUrl);
		ListView friendslist = (ListView) findViewById(R.id.friendsName);
		
		//ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, friendsName);
		friendslist.setAdapter(friendDetailAdapter);
		//friendslist.setAdapter(arrayAdapter);
*/		
				
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
	            return true;
	            
	        case R.id.action_my_profile:
	            return true;
        
            case R.id.action_chat:
            	Intent chat = new Intent(this, ShareActivity.class);
			    startActivity(chat);
                return true;
                
            case R.id.action_map:
            	Intent map = new Intent(this, DisplayMapActivity.class);
			    startActivity(map);
            	return true;
            	
            case R.id.action_settings:
                //openSettings();
            	Intent i = new Intent(this, SettingsActivity.class);
            	startActivity(i);
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
	    String text = "|| Sent from FoodFriend ||";			// Automatically send out
	    waIntent.putExtra(Intent.EXTRA_TEXT, text);
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
	
	// JW
	public class FriendDetailsAdapter extends BaseAdapter {
		ArrayList<String> friendsNames = new ArrayList<String>();
		ArrayList<String> profilePicUrl = new ArrayList<String>();
		List<PersonDetails> personDetailsList = new ArrayList<PersonDetails>();
		
		public FriendDetailsAdapter(ArrayList<String> friendsNames,ArrayList<String> profilePicUrl){
			this.friendsNames = friendsNames;
			this.profilePicUrl = profilePicUrl;
			
			//loads the data into our custom adapter by using getDataForListView method
			this.personDetailsList = getDataForListView(friendsNames, profilePicUrl);
		}
				
		@Override
		public int getCount() {
			// TODO return the size of the list)
			if(friendsNames.size() == profilePicUrl.size())
				return friendsNames.size();
			else
				return 0;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return personDetailsList.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		//main method to bind the list item to the ListView
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			if(arg1==null)
            {
                LayoutInflater inflater = (LayoutInflater) DisplayFriendsActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                arg1 = inflater.inflate(R.layout.friend_custom_list_item, arg2,false);
            }

            TextView name = (TextView)arg1.findViewById(R.id.friend_name);
            ImageView pic = (ImageView)arg1.findViewById(R.id.profilePic);
           	
            PersonDetails personDetails = personDetailsList.get(arg0);
            name.setText(personDetails.name);
           	new BitmapWorkerTask(pic).execute(personDetails.profilePicUrl);

            return arg1;
		}
		
	}
	// JW
	public class PersonDetails {
		String name;
		String profilePicUrl;
		}
	
	public List <PersonDetails> getDataForListView(ArrayList <String> name, ArrayList <String> profilePicUrl ) {
		
		List <PersonDetails> personDetailsList = new ArrayList <PersonDetails> ();
		Integer size = new Integer(name.size());
		Log.d("size of arraylist name", size.toString());
		for(int i=0; i< size;i++){
			PersonDetails personDetails = new PersonDetails();
			personDetails.name = name.get(i); //copy name
			personDetails.profilePicUrl = profilePicUrl.get(i); // copy pic url
			personDetailsList.add(personDetails); // add to arraylist
					}
		
		return personDetailsList;
	}
	
	//JW
	/**
	 * Background Async task to load user profile picture from url
	 * */
	public class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
		private final WeakReference <ImageView> imageViewReference;
		private String url = "";
	 
	    public BitmapWorkerTask (ImageView imageView) {
	    	// Use a weak reference to ensure the ImageView can be garbage collected
	        imageViewReference = new WeakReference <ImageView> (imageView);
	    }
	    
	    // decode image in background
	    @Override
	    protected Bitmap doInBackground(String... urls) {
	        url = urls[0];
	        Bitmap mIcon11 = null;
	        try {
	            InputStream in = new java.net.URL(url).openStream();
	            mIcon11 = BitmapFactory.decodeStream(in);
	        } catch (Exception e) {
	            Log.e("Error", e.getMessage());
	            e.printStackTrace();
	        }
	        return mIcon11;
	    }
	 
	    protected void onPostExecute(Bitmap bitmap) {
	    	if (isCancelled()) {
	            bitmap = null;
	        }
	    	
	    	if(imageViewReference !=null && bitmap !=null) {
	    		final ImageView imageView = imageViewReference.get();
	    		final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);
	    		if(/*this == bitmapWorkerTask &&*/ imageView !=null) {
	    			imageView.setImageBitmap(bitmap);
	    		}
	    	}
	    }
	}
	
	// Create a dedicated Drawable subclass to store a reference back to the worker task for list view
	static class AsyncDrawable extends BitmapDrawable {
	    private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

	    public AsyncDrawable(Resources res, Bitmap bitmap,
	            BitmapWorkerTask bitmapWorkerTask) {
	        super(res, bitmap);
	        bitmapWorkerTaskReference =
	            new WeakReference<BitmapWorkerTask>(bitmapWorkerTask);
	    }

	    public BitmapWorkerTask getBitmapWorkerTask() {
	        return bitmapWorkerTaskReference.get();
	    }
	}
		
	// Before executing the BitmapWorkerTask, you create an AsyncDrawable 
	// and bind it to the target ImageView:
	public void loadBitmap(String url , ImageView imageView) {
	    if (cancelPotentialWork(url, imageView)) {
	        final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
	      //JW
			// creating a mobile placeholder image for listview
			final Bitmap mPlaceHolderBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_launcher);
	        final AsyncDrawable asyncDrawable =
	                new AsyncDrawable(getResources(), mPlaceHolderBitmap, task);
	        imageView.setImageDrawable(asyncDrawable);
	        task.execute(url);
	    }
	}
	
	// checks if another running task is already associated with the ImageView.
	// If so, it attempts to cancel the previous task by calling cancel().
	// In a small number of cases, the new task data matches the existing task 
	// and nothing further needs to happen.
	public static boolean cancelPotentialWork(String url, ImageView imageView) {
	    final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

	    if (bitmapWorkerTask != null) {
	        final String presentUrl = bitmapWorkerTask.url;		
	        // If bitmapData is not yet set or it differs from the new data
	        if (presentUrl == null || presentUrl != url) {
	            // Cancel previous task
	            bitmapWorkerTask.cancel(true);
	        } else {
	            // The same work is already in progress
	            return false;
	        }
	    }
	    // No task associated with the ImageView, or an existing task was cancelled
	    return true;
	}
	
	// A helper method, getBitmapWorkerTask(), is used above to retrieve the task associated
	// with a particular ImageView:
	private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
		   if (imageView != null) {
		       final Drawable drawable = imageView.getDrawable();
		       if (drawable instanceof AsyncDrawable) {
		           final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
		           return asyncDrawable.getBitmapWorkerTask();
		       }
		    }
		    return null;
		}
}
