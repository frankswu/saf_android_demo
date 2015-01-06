package com.example.saf;

import cn.salesuite.saf.app.SAFApp;
import cn.salesuite.saf.orm.DBManager;
import cn.salesuite.saf.route.Router;
import android.app.Application;

/**
 * 
 * @author shwuyiqun
 *
 */
public class ApiDemosApp extends SAFApp {

	private static SAFApp app;
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		app = this;
		// Sqlite ORM 初始化
		DBManager.initialize(this);
//		Router 初始化
		Router.getInstance().setContext(getApplicationContext());
		// 设置route映射规则
		Router.getInstance().map("FinishAffinity/nesting", FinishAffinity.class);
	}
	
	public static SAFApp getInstance() {
		return app;
	}
	
}
