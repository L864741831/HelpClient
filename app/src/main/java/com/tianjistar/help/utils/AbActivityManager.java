package com.tianjistar.help.utils;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * © 2012 amsoft.cn 名称：AbActivityManager.java
 * 描述：用于处理退出程序时可以退出所有的activity，而编写的通用类
 * 
 * @author 还如一梦中
 * @date 2015年4月10日 下午6:10:28
 * @version v1.0
 */
public class AbActivityManager {

	private List<Activity> activityList = new LinkedList<>();
	private static AbActivityManager instance;

	private AbActivityManager() {
	}

	/**
	 * 单例模式中获取唯一的AbActivityManager实例.
	 * 
	 * @return
	 */
	public static AbActivityManager getInstance() {
		if (null == instance) {
			instance = new AbActivityManager();
		}
		return instance;
	}

	/**
	 * 添加Activity到容器中.
	 * 
	 * @param activity
	 */
	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	/**
	 * 移除Activity从容器中.
	 * 
	 * @param activity
	 */
	public void removeActivity(Activity activity) {
		activityList.remove(activity);
	}

	/**
	 * 遍历所有Activity并finish.
	 */
	public void clearAllActivity() {
		try {
			for (Activity activity : activityList) {
				if (activity != null) {
					activity.finish();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	/**
	 * 结束指定的Activity(重载)
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			// 为与系统Activity栈保持一致，且考虑到手机设置项里的"不保留活动"选项引起的
			// Activity生命周期调用onDestroy()方法所带来的问题,此处需要作出如下修正
//			if(activity.isFinishing()){
//				activityList.remove(activity);
//				activity.finish();
//				activity = null;
//			}

			activityList.remove(activity);
			activity.finish();
			activity = null;
		}

	}

	/**
	 * 结束指定类名的Activity
	 */
	public void finishActivity(Class<?> cls) {
		for (Activity activity : activityList) {
			if (activity.getClass().equals(cls)) {
				finishActivity(activity);
			}
		}
	}


}