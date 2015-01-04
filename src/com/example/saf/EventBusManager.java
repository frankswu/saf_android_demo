package com.example.saf;

import cn.salesuite.saf.eventbus.EventBus;

public final class EventBusManager {

	private static final EventBus EVENT_BUS = new EventBus();
	
	public static EventBus getInstance() {
		return EVENT_BUS;
	}
	
	
}
