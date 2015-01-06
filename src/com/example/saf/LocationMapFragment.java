/*
 * Copyright (C) 2012 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.saf;

import static android.widget.ImageView.ScaleType.CENTER_INSIDE;

import java.net.URL;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import cn.salesuite.saf.eventbus.EventBus;
import cn.salesuite.saf.eventbus.Subscribe;

/** Display a map centered on the last known location. */
public class LocationMapFragment extends Fragment {
	private static final String URL = "https://maps.googleapis.com/maps/api/staticmap?sensor=false&size=400x400&zoom=13&center=%s,%s";
	private static DownloadTask downloadTask;
	private static EventBus eventBus;
	private ImageView imageView;

	@Override
	public void onResume() {
		super.onResume();
		eventBus.register(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		eventBus.unregister(this);

		// Stop existing download, if it exists.
		if (downloadTask != null) {
			downloadTask.cancel(true);
			downloadTask = null;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		imageView = new ImageView(getActivity());
		imageView.setScaleType(CENTER_INSIDE);
		eventBus = EventBusManager.getInstance();
		return imageView;
	}

	@Subscribe
	public void onLocationChanged(LocationChangedEvent event) {
		// Stop existing download, if it exists.
		// 图片加载imageLoader使用
		String url = "http://restapi.amap.com/v3/staticmap?scale=2&location=116.37359,39.92437&zoom=10&size=216*140&markers=mid,,A:116.37359,39.92437&key=ee95e52bf08006f63fd29bcfbcf21df0";
//		ApiDemosApp.getInstance().imageLoader.displayImage(String.format(URL, event.lat, event.lon), imageView, null);
		ApiDemosApp.getInstance().imageLoader.displayImage(url, imageView, null);
		/**
		if (downloadTask != null) {
			downloadTask.cancel(true);
		}

		// Trigger a background download of an image for the new location.
		downloadTask = new DownloadTask();
		downloadTask.execute(String.format(URL, event.lat, event.lon));
		**/
	}

	@Subscribe
	public void onImageAvailable(ImageAvailableEvent event) {
		if (imageView != null) {
			imageView.setImageDrawable(event.image);
		}
	}

	private static class ImageAvailableEvent {
		public final Drawable image;

		ImageAvailableEvent(Drawable image) {
			this.image = image;
		}
	}

	private static class DownloadTask extends AsyncTask<String, Void, Drawable> {
		@Override
		protected Drawable doInBackground(String... params) {
			try {
				return BitmapDrawable.createFromStream(
						new URL(params[0]).openStream(), "bitmap.jpg");
			} catch (Exception e) {
				Log.e("LocationMapFragment", "Unable to download image.", e);
				return null;
			}
		}

		@Override
		protected void onPostExecute(Drawable drawable) {
			if (!isCancelled() && drawable != null) {
				eventBus.post(new ImageAvailableEvent(drawable));
			}
		}
	}
}
