package com.tianjistar.help.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Constant {

	private static final int CMNET = 3;
	private static final int CMWAP = 2;
	private static final int WIFI = 1;

	/**
	 * 获取和保存当前屏幕的截图
	 */
	public static void GetandSaveCurrentImage(Context context) {
		// 1.构建Bitmap
		// WindowManager windowManager = getWindowManager();
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

		Display display = windowManager.getDefaultDisplay();
		int w = display.getWidth();
		int h = display.getHeight();

		Bitmap Bmp = Bitmap.createBitmap(w, h, Config.ARGB_8888);

		// 2.获取屏幕
		View decorview = ((Activity) context).getWindow().getDecorView();
		decorview.setDrawingCacheEnabled(true);
		Bmp = decorview.getDrawingCache();

		String SavePath = getSDCardPath() + "/AndyDemo/ScreenImage";

		// 3.保存Bitmap
		try {
			File path = new File(SavePath);
			// 文件
			String filepath = SavePath + "/Screen_1.png";
			File file = new File(filepath);
			if (!path.exists()) {
				path.mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}

			FileOutputStream fos = null;
			fos = new FileOutputStream(file);
			if (null != fos) {
				Bmp.compress(Bitmap.CompressFormat.PNG, 90, fos);
				fos.flush();
				fos.close();
//				Toast.makeText(context, "截屏文件已保存至SDCard/AndyDemo/ScreenImage/下", Toast.LENGTH_LONG).show();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取SDCard的目录路径功能
	 * 
	 * @return
	 */
	public static String getSDCardPath() {
		File sdcardDir = null;
		// 判断SDCard是否存在
		boolean sdcardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		if (sdcardExist) {
			sdcardDir = Environment.getExternalStorageDirectory();
		}
		return sdcardDir.toString();
	}

	// android 正则表达式 判断输入特殊字符
	public static boolean hasSpecialCharacter(String str) {
		String regEx = "@#$%^&*<>";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		if (m.find()) {
			return true;
		}
		return false;
	}

	// 压缩图片大小
	public static Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if (baos != null) {
			image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
			int options = 100;
			while (baos.toByteArray().length / 1024 > 1000) { // 循环判断如果压缩后图片是否大于1000kb,大于继续压缩
				baos.reset();// 重置baos即清空baos
				image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
				options -= 10;// 每次都减少10
			}
			ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
			Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
			return bitmap;
		}
		
		return null;
	}

	/**
	 * 方法描述：获取Android SDK版本
	 * 
	 */
	public static int getAndroidSDKVersion() {
		int version = 0;
		try {
			version = Integer.valueOf(android.os.Build.VERSION.SDK);
		} catch (NumberFormatException e) {
		}
		return version;
	}

	/**
	 * 方法描述：获取app版本号
	 * 
	 */
	public static int getVerCode(Context context) throws NameNotFoundException {
		int verCode = -1;
		verCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
		return verCode;
	}

	/**
	 * 方法描述：获取app版本名称
	 * 
	 */
	public static String getVersionName(Context context) throws NameNotFoundException {
		// 获取packagemanager的实例
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
		String version = packInfo.versionName;
		return version;
	}

	/**
	 * 方法描述：删除文件夹及里面的文件
	 * 
	 */
	public static void delete(File file) {
		if (file.isFile()) {
			file.delete();
			return;
		}

		if (file.isDirectory()) {
			File[] childFiles = file.listFiles();
			if (childFiles == null || childFiles.length == 0) {
				file.delete();
				return;
			}

			for (int i = 0; i < childFiles.length; i++) {
				delete(childFiles[i]);
			}
			file.delete();
		}
	}

	/**
	 * 日期转换成字符串
	 * 
	 * @param date
	 * @return str
	 */
	public static String DateToStr(Date date) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = format.format(date);
		return str;
	}

	/**
	 * 日期转换成字符串
	 *
	 * @param date
	 * @return str
	 */
	public static String DateToStr(Date date, String mode) {

		SimpleDateFormat format = new SimpleDateFormat(mode);
		String str = format.format(date);
		return str;
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 */
	public static Date StrToDate(String str, String mode) {

		// SimpleDateFormat format = new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat format = new SimpleDateFormat(mode);
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 去除小数点后无用的0
	 *
	 */
	public static String RemoveZreo(String str){
		if(str.indexOf(".") > 0){
			//正则表达
			str = str.replaceAll("0+?$", "");//去掉后面无用的零
			str = str.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
		}
		return str;
	}

	/**
	 * 封装提示框
	 * 
	 */
	public static void MyDialog(Context context, String message, String title, String negativeButton, String positiveButton, OnClickListener NegativeButtonListener, OnClickListener PositiveButtonListener) {
		Builder builder = new Builder(context);
		builder.setMessage(message);
		builder.setTitle(title);
		builder.setNegativeButton(negativeButton, NegativeButtonListener);
		builder.setPositiveButton(positiveButton, PositiveButtonListener);
		builder.create().show();
	}

	/**
	 * 判断当前应用是否在前台
	 * 
	 */
	// public static boolean isAppOnForeground(Context context, String
	// packageName) {
	// ActivityManager activityManager = (ActivityManager)
	// context.getSystemService(Context.ACTIVITY_SERVICE);
	// if (activityManager == null) {
	// return false;
	// }
	// List<RunningAppProcessInfo> appProcesses =
	// activityManager.getRunningAppProcesses();
	// if (appProcesses == null) {
	// return false;
	// }
	// for (RunningAppProcessInfo appProcess : appProcesses) {
	// if (appProcess.processName.equals(packageName)
	// && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND)
	// {
	// return true;
	// }
	// }
	// return false;
	// }

	/**
	 * 判断当前应用是否在前台
	 * 
	 */
	public static boolean isAppOnForeground(Context context, String packageName) {
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		if (activityManager == null) {
			return false;
		}
		ComponentName componentName = activityManager.getRunningTasks(1).get(0).topActivity;
		if (componentName.getPackageName().equals(packageName)) {
			return true;
		}
		return false;
	}

	/**
	 * 获取当前的网络状态 -1：没有网络 1：WIFI网络2：wap网络3：net网络
	 * 
	 * @param context
	 * @return
	 */
	public static int getAPNType(Context context) {
		int netType = -1;
		try {
			ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

			if (networkInfo == null) {
				return netType;
			}
			int nType = networkInfo.getType();
			System.out.println("networkInfo.getExtraInfo() is " + networkInfo.getExtraInfo());
			if (nType == ConnectivityManager.TYPE_MOBILE) {
				if (networkInfo.getExtraInfo().toLowerCase().equals("cmnet")) {
					netType = CMNET;
				} else {
					netType = CMWAP;
				}
			} else if (nType == ConnectivityManager.TYPE_WIFI) {
				netType = WIFI;
			}

		}catch (Exception e){
			e.printStackTrace();
		}
		return netType;
	}

	// 图片本身加上圆角
	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

}
