package com.greendev.ldlmarketing;

import com.aviary.android.feather.FeatherActivity;
import com.greendev.camera.LDLCam2Activity;
import com.greendev.ldlmarketing.R;
import com.greendev.ldlmarketing.R.id;
import com.greendev.ldlmarketing.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LDLCamActivity extends Activity implements OnClickListener {
	private static final int RESULT_LOAD_IMAGE = 1;
	private static final int RESULT_CAMERA_IMAGE = 666;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ldlcam_layout);

		// Custom Font
		Typeface font = Typeface.createFromAsset(getAssets(), "Eurosti.TTF");

		// Texts Views
		TextView titleText1 = (TextView) findViewById(R.id.ldlcam_text1);
		titleText1.setTypeface(font);
		TextView titleText2 = (TextView) findViewById(R.id.ldlcam_text2);
		titleText2.setTypeface(font);

		// Button View
		Button pickButton = (Button) findViewById(R.id.pick_image_button);
		pickButton.setOnClickListener(this);
		
		Button cameraButton = (Button) findViewById(R.id.from_camera_button);
		cameraButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		//case R.id.take_picture_button:
			//Intent i = new Intent(Intent.Action_)
			
		case R.id.pick_image_button:
			Intent i = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

			startActivityForResult(i, RESULT_LOAD_IMAGE);
			break;
			
		case R.id.from_camera_button:
			Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
            startActivityForResult(cameraIntent, RESULT_CAMERA_IMAGE); 
            
            break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if(resultCode == RESULT_OK && null != data){
			switch(requestCode){
				case RESULT_LOAD_IMAGE:
					Uri selectedImage = data.getData();
										
					// Create the intent needed to start feather
					Intent newIntent = new Intent( this, FeatherActivity.class );
					// set the source image uri
					newIntent.setData( selectedImage );
					// pass the required api key ( http://developers.aviary.com/ )
					newIntent.putExtra( "API_KEY", "68bceceb1" );
					// pass the uri of the destination image file (optional)
					// This will be the same uri you will receive in the onActivityResult
					//newIntent.putExtra( "output", Uri.parse( "file://" + mOutputFile.getAbsolutePath() ) );
					// format of the destination image (optional)
					//newIntent.putExtra( "output-format", Bitmap.CompressFormat.JPEG.name() );
					// output format quality (optional)
					newIntent.putExtra( "output-quality", 85 );
					// you can force feather to display only a certain tools
					// newIntent.putExtra( "tools-list", new String[]{"ADJUST", "BRIGHTNESS" } );

					// enable fast rendering preview
					newIntent.putExtra( "effect-enable-fast-preview", true );
					newIntent.putExtra( "hide-exit-unsave-confirmation", true );
					startActivity(newIntent);
					break;
					
				case RESULT_CAMERA_IMAGE:
					Uri cameraImage = data.getData();
					
					// Create the intent needed to start feather
					Intent cameraIntent = new Intent( this, FeatherActivity.class );
					// set the source image uri
					cameraIntent.setData( cameraImage );
					// pass the required api key ( http://developers.aviary.com/ )
					cameraIntent.putExtra( "API_KEY", "68bceceb1" );
					// pass the uri of the destination image file (optional)
					// This will be the same uri you will receive in the onActivityResult
					//newIntent.putExtra( "output", Uri.parse( "file://" + mOutputFile.getAbsolutePath() ) );
					// format of the destination image (optional)
					//newIntent.putExtra( "output-format", Bitmap.CompressFormat.JPEG.name() );
					// output format quality (optional)
					cameraIntent.putExtra( "output-quality", 85 );
					// you can force feather to display only a certain tools
					// newIntent.putExtra( "tools-list", new String[]{"ADJUST", "BRIGHTNESS" } );

					// enable fast rendering preview
					cameraIntent.putExtra( "effect-enable-fast-preview", true );
					cameraIntent.putExtra( "hide-exit-unsave-confirmation", true );
					startActivity(cameraIntent);
					break;
			
			}
		}
		
		/*if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
				&& null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();

			/*Intent i = new Intent(this, LDLCam2Activity.class);
			i.putExtra("PIC_PATH", picturePath);
			startActivity(i);
			
			// Create the intent needed to start feather
			Intent newIntent = new Intent( this, FeatherActivity.class );
			// set the source image uri
			newIntent.setData( selectedImage );
			// pass the required api key ( http://developers.aviary.com/ )
			newIntent.putExtra( "API_KEY", "68bceceb1" );
			// pass the uri of the destination image file (optional)
			// This will be the same uri you will receive in the onActivityResult
			//newIntent.putExtra( "output", Uri.parse( "file://" + mOutputFile.getAbsolutePath() ) );
			// format of the destination image (optional)
			//newIntent.putExtra( "output-format", Bitmap.CompressFormat.JPEG.name() );
			// output format quality (optional)
			newIntent.putExtra( "output-quality", 85 );
			// you can force feather to display only a certain tools
			// newIntent.putExtra( "tools-list", new String[]{"ADJUST", "BRIGHTNESS" } );

			// enable fast rendering preview
			newIntent.putExtra( "effect-enable-fast-preview", true );
			newIntent.putExtra( "hide-exit-unsave-confirmation", true );
			startActivity(newIntent);

		}
		
		if (requestCode == RESULT_CAMERA_IMAGE && resultCode == RESULT_OK
				&& null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();

			Intent i = new Intent(this, LDLCam2Activity.class);
			i.putExtra("PIC_PATH", picturePath);
			startActivity(i);
			
			// Create the intent needed to start feather
			Intent newIntent = new Intent( this, FeatherActivity.class );
			// set the source image uri
			newIntent.setData( selectedImage );
			// pass the required api key ( http://developers.aviary.com/ )
			newIntent.putExtra( "API_KEY", "68bceceb1" );
			// pass the uri of the destination image file (optional)
			// This will be the same uri you will receive in the onActivityResult
			//newIntent.putExtra( "output", Uri.parse( "file://" + mOutputFile.getAbsolutePath() ) );
			// format of the destination image (optional)
			//newIntent.putExtra( "output-format", Bitmap.CompressFormat.JPEG.name() );
			// output format quality (optional)
			newIntent.putExtra( "output-quality", 85 );
			// you can force feather to display only a certain tools
			// newIntent.putExtra( "tools-list", new String[]{"ADJUST", "BRIGHTNESS" } );

			// enable fast rendering preview
			newIntent.putExtra( "effect-enable-fast-preview", true );
			newIntent.putExtra( "hide-exit-unsave-confirmation", true );
			startActivity(newIntent);
				
		}*/

	}

}