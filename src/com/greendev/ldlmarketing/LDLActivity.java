/**
 * Basic configs for app activities. 
 */

package com.greendev.ldlmarketing;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

public class LDLActivity extends SherlockActivity {
	protected Typeface fontreg;
	protected Typeface fontbold;
	private final int LEFT = 7;
	private final int TOP = 0;
	private final int RIGHT = 10;
	private final int BOTTOM = 0;
	private boolean useLogo = false;
	private boolean showHomeUp = false;
  
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// fonts
		fontreg = Typeface.createFromAsset(getAssets(), "Eurosti.TTF");
		fontbold = Typeface.createFromAsset(getAssets(), "Eurostib.TTF");

        final ActionBar ab = getSupportActionBar();
       
		// padding around ic_launcher
		//ItemView view = (ItemView) findViewById(android.R.id.home);
		//((View) view).setPadding(LEFT, TOP, RIGHT, BOTTOM);

		 // customizing font in action bar
		 ab.setDisplayShowCustomEnabled(true);
		 ab.setDisplayShowTitleEnabled(false);
		 LayoutInflater inflator = (LayoutInflater) this
		 .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 View v = inflator.inflate(R.layout.title, null);
		 TextView title = ((TextView) v.findViewById(R.id.title));
		 title.setText(this.getTitle());
		 title.setTypeface(fontreg);
		 // assign the view to the actionbar
		 ab.setCustomView(v);   
	}

}
