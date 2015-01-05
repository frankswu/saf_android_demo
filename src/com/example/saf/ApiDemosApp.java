package com.example.saf;

import cn.salesuite.saf.orm.DBManager;
import cn.salesuite.saf.route.Router;
import android.app.Application;

/**
 * 
 * @author shwuyiqun
 *
 */
public class ApiDemosApp extends Application {

	
	@Override
	public void onCreate() {
		super.onCreate();
		// Sqlite ORM 初始化
		DBManager.initialize(this);
//		Router 初始化
		Router.getInstance().setContext(getApplicationContext());
		// 设置route映射规则
		Router.getInstance().map("FinishAffinity/nesting", FinishAffinity.class);
	}
}
