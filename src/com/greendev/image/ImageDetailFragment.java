/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.greendev.image;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.greendev.ldlmarketing.R;
import com.greendev.ldlmarketing.R.id;
import com.greendev.ldlmarketing.R.layout;

/**
 * This fragment will populate the children of the ViewPager from
 * {@link ImageDetailActivity}.
 */
public class ImageDetailFragment extends Fragment {
	private static final String IMAGE_DATA_EXTRA = "extra_image_data";
	private String mImageUrl;
	private ImageView mImageView;
	private ImageFetcher mImageFetcher;
	private TextView imageCaptionView;
	private String captionStr;
	private View v;

	private boolean captionDisplay = true;

	/**
	 * Factory method to generate a new instance of the fragment given an image
	 * number.
	 * 
	 * @param imageUrl
	 *            The image url to load
	 * @return A new instance of ImageDetailFragment with imageNum extras
	 */
	public static ImageDetailFragment newInstance(String imageUrl, String captionStr) {
		final ImageDetailFragment f = new ImageDetailFragment(captionStr);

		final Bundle args = new Bundle();
		args.putString(IMAGE_DATA_EXTRA, imageUrl);
		f.setArguments(args);

		return f;
	}

	/**
	 * Empty constructor as per the Fragment documentation
	 */
	public ImageDetailFragment(String captionStr) {
		this.captionStr = captionStr;
	}

	/**
	 * Populate image using a url from extras, use the convenience factory
	 * method {@link ImageDetailFragment#newInstance(String)} to create this
	 * fragment.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mImageUrl = getArguments() != null ? getArguments().getString(
				IMAGE_DATA_EXTRA) : null;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Inflate and locate the main ImageView
		final View v = inflater.inflate(R.layout.image_detail_fragment,
				container, false);
		this.v = v;
		mImageView = (ImageView) v.findViewById(R.id.imageView);

		/* image caption set up -- (1) */
		imageCaptionView = (TextView) v.findViewById(R.id.image_caption);

		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// Use the parent activity to load the image asynchronously into the
		// ImageView (so a single
		// cache can be used over all pages in the ViewPager
		if (ImageDetailActivity.class.isInstance(getActivity())) {
			mImageFetcher = ((ImageDetailActivity) getActivity())
					.getImageFetcher();
			mImageFetcher.loadImage(mImageUrl, mImageView);

			/* image description dialog -- (2) */
			imageCaptionView.setText(captionStr);
			imageCaptionView.setVisibility(1);

		}

		// Pass clicks on the ImageView to the parent activity to handle
		if (OnClickListener.class.isInstance(getActivity())
				&& Utils.hasHoneycomb()) {
			mImageView.setOnClickListener((OnClickListener) getActivity());
			imageCaptionView.setOnClickListener((OnClickListener) getActivity());
		}
		setCaptionClick();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mImageView != null) {
			// Cancel any pending image work
			ImageWorker.cancelWork(mImageView);
			mImageView.setImageDrawable(null);
		}
	}

	/**
	 * Caption display
	 */
	public void setCaptionClick() {
		imageCaptionView.setOnClickListener(new View.OnClickListener() {

			/*
			 * Caption display control -- touching image or caption causes
			 * caption to disappear
			 */
			@Override
			public void onClick(View v) {
				if (captionDisplay) {
					imageCaptionView.setVisibility(View.GONE);
					captionDisplay = false;
				} else {
					imageCaptionView.setVisibility(View.VISIBLE);
					captionDisplay = true;
				}

			}

		});
		mImageView.setOnClickListener(new View.OnClickListener() {

			/*
			 * Caption display control
			 */
			@Override
			public void onClick(View v) {
				if (captionDisplay) {
					imageCaptionView.setVisibility(View.GONE);
					captionDisplay = false;
				} else {
					imageCaptionView.setVisibility(View.VISIBLE);
					captionDisplay = true;
				}
			}

		});

	}

}
