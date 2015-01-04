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

import java.util.Random;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import cn.salesuite.saf.eventbus.EventBus;
import cn.salesuite.saf.inject.Injector;
import cn.salesuite.saf.inject.annotation.OnClick;

import com.example.android.apis.R;

public class LocationActivity extends FragmentActivity {
	public static final float DEFAULT_LAT = 40.440866f;
	public static final float DEFAULT_LON = -79.994085f;
	private static final float OFFSET = 0.1f;
	private static final Random RANDOM = new Random();

	private static float lastLatitude = DEFAULT_LAT;
	private static float lastLongitude = DEFAULT_LON;
	private EventBus eventBus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.saf_location_history);
		eventBus = EventBusManager.getInstance();
		eventBus.register(this);
		Injector.injectInto(this);
	}

	@OnClick(id = R.id.move_location)
	public void btnMoveLocationOnClickListener() {
		lastLatitude += (RANDOM.nextFloat() * OFFSET * 2) - OFFSET;
		lastLongitude += (RANDOM.nextFloat() * OFFSET * 2) - OFFSET;
		eventBus.post(new LocationChangedEvent(lastLatitude, lastLongitude));
	}
	
	
	@OnClick(id = R.id.clear_location)
	public void btnClearLoacationOnClickListener() {
		// Tell everyone to clear their location history.
		eventBus.post(new LocationClearEvent());

		// Post new location event for the default location.
		lastLatitude = DEFAULT_LAT;
		lastLongitude = DEFAULT_LON;
		eventBus.post(new LocationChangedEvent(lastLatitude, lastLongitude));
	}
	

	@Override
	protected void onResume() {
		super.onResume();
		// Register ourselves so that we can provide the initial value.
//		eventBus.register(this);
	}

	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		eventBus.unregister(this);
	}

}
