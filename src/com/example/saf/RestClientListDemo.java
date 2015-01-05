package com.example.saf;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import cn.salesuite.saf.eventbus.EventBus;
import cn.salesuite.saf.http.rest.HttpResponseHandler;
import cn.salesuite.saf.http.rest.RestClient;
import cn.salesuite.saf.http.rest.RestException;
import cn.salesuite.saf.log.L;

import com.alibaba.fastjson.JSON;
import com.example.saf.domain.Contributor;

/**
 * 
 * RestClient and Sqlite ORM
 * 
 * @author wuyiqun
 * 
 */
public class RestClientListDemo extends ListActivity {

	private ArrayAdapter<String> adapter;
	private List<String> listData = new ArrayList<String>();

	private EventBus eventBus;
	@SuppressLint("HandlerLeak")
	private Handler handle = new Handler() {
		
		public void handleMessage(android.os.Message msg) {
			adapterNotifyDataSetChanged();
		};
	};
		

	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		eventBus = EventBusManager.getInstance();
		eventBus.register(this);
		new Contributor().delete();
		
		final String url = "https://api.github.com/repos/square/retrofit/contributors";
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				RestClient.get(url, new HttpResponseHandler() {

					public void onSuccess(String content) {
						// contentΪhttp����ɹ��󷵻ص�response
						L.d("onSuccess[" + content + "]");
						List<Contributor> list = JSON.parseArray(content, Contributor.class);
						
						for (Contributor contributor : list) {
//							listData.add(contributor.toString());
							contributor.save();
						}
						handle.sendEmptyMessage(1);
						
					}

					@Override
					public void onFail(RestException exception) {

					}
				});
			}
		}).start();


		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, listData);
		setListAdapter(adapter);
		getListView().setTextFilterEnabled(true);
	}

//	static class Contributor {
//		public String login;
//		public int contributions;
//
//		@Override
//		public String toString() {
//			return login + "," + contributions;
//		}
//	}
	
	
	private void adapterNotifyDataSetChanged() {
		List<Contributor> data = new Contributor().getAll();
		for (Contributor contributor : data) {
			listData.add(contributor.toString());
		}

		if (adapter != null) {
			adapter.notifyDataSetChanged();
		}
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		eventBus.unregister(this);
	}

}
