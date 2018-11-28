package com.tianjistar.help.aaa;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import com.tianjistar.help.activity.MainActivity;

import java.util.Stack;

/**
 * Created by zfh on 2015/12/12.
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 */
public class AppManager2 {

    private static Stack<Activity> activityStack;
    private static AppManager2 instance;

    public AppManager2() {
    }

    /**
     * 单一实例
     */
    public static AppManager2 getAppManager() {
        if (instance == null) {
            instance = new AppManager2();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }

        if (!activity.getClass().equals(MainActivity.class)) {
            activityStack.add(activity);
        }

    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null && !activity.getClass().equals(MainActivity.class)) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                break;
            }
        }
    }


    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }


    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
        }
    }

}
