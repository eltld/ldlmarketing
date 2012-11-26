package com.greendev.ldlmarketing;

import java.io.File;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.greendev.ldlmarketing.R;

public class FrameActivity extends Activity {

	protected String _output = "output.jpg";

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.picture_frame_layout);
		
		File path = getFilesDir();
		File outFile = new File(path, _output);

		Bitmap pic = BitmapFactory.decodeFile(outFile.getAbsolutePath());
		//Bitmap map = BitmapFactory.decodeResource(getResources(), R.drawable.frame1);
		
		int height = pic.getHeight() + 30;
		int width = pic.getWidth() + 30;
		
		// Load the image as a NinePatch drawable
		int frame = R.drawable.frame1;
		Drawable d = this.getResources().getDrawable(frame);

		NinePatchDrawable npd = (NinePatchDrawable)d;
		// Set its bound where you need
		Rect npdBounds = new Rect(0,0, width, height);
		npd.setBounds(npdBounds);

		// Finally draw on the canvas

		
		Bitmap out1 = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas comboImage = new Canvas(out1);
		//comboImage.drawBitmap(map, 0, 0, null);
		npd.draw(comboImage);
		comboImage.drawBitmap(pic, 15, 15, null);
		
		

		ImageView imgView = (ImageView) findViewById(R.id.imgView);
		imgView.setImageBitmap(out1);
	}

}