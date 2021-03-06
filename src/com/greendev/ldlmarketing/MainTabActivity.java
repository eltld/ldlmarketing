package com.greendev.ldlmarketing;

import com.greendev.flickr.FetchSetsTask;
import com.greendev.flickr.FlickrLibrary;
import com.greendev.flickr.FlickrSetsLibrary;
import com.greendev.flickr.FlickrStartService;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class MainTabActivity extends TabActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setTabs();
		/* flickr prep */
		// Start fetching sets from Flickr
		new Thread(new FetchSetsTask(responseHandler)).start();

		// Intent intent = new Intent(this, FlickrStartService.class);
		// Messenger msg = new Messenger(handler);
		// intent.putExtra("MESSENGER", msg);
		// startService(intent);
	}

	Handler responseHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			prepSets(msg);
		};
	};

	private void prepSets(Message msg) {
		final FlickrLibrary lib = (FlickrLibrary) msg.getData().get(
				FetchSetsTask.LIBRARY);
		final FlickrSetsLibrary setslib = FlickrSetsLibrary.getInstance();
		new Thread(new Runnable() {
			public void run() {
				setslib.setFlickrSets(lib.fetchSets());
				setslib.createUrlPortfolio();
				setslib.createUrlGallery();
			}
		}).start();
	}

	private void setTabs() {
		addTab("", R.drawable.tab_home, HomeActivity.class);
		addTab("", R.drawable.tab_ldlcam, LDLCamActivity.class);
		addTab("", R.drawable.tab_gallery, GalleryActivity.class);
		addTab("", R.drawable.tab_tweet, TwitterActivity2.class);
	}

	private void addTab(String labelId, int drawableId, Class<?> c) {
		Intent intent = new Intent(this, c);

		TabHost tabHost = getTabHost();

		TabHost.TabSpec spec = tabHost.newTabSpec("tab" + labelId);

		View tabIndicator = LayoutInflater.from(this).inflate(
				R.layout.tab_indicator, getTabWidget(), false);

		TextView title = (TextView) tabIndicator.findViewById(R.id.title);
		title.setText(labelId);
		ImageView icon = (ImageView) tabIndicator
				.findViewById(R.id.ic_launcher);
		icon.setImageResource(drawableId);

		spec.setIndicator(tabIndicator);
		spec.setContent(intent);
		tabHost.addTab(spec);
	}
}