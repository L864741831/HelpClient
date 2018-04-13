package com.tianjistar.help.utils;

import android.text.TextUtils;
import android.widget.Chronometer;

/**
 * Created by Administrator on 2017/6/8.
 */

public  class TimeUtil {
    /**
     *
     * @param cmt  Chronometer控件
     * @return 小时+分钟+秒数  的所有秒数
     */
    public  static String getChronometerSeconds(Chronometer cmt) {
        int totalss = 0;
        String string = cmt.getText().toString();
        if(string.length()==7){

            String[] split = string.split(":");
            String string2 = split[0];
            int hour = Integer.parseInt(string2);
            int Hours =hour*3600;
            String string3 = split[1];
            int min = Integer.parseInt(string3);
            int Mins =min*60;
            int  SS =Integer.parseInt(split[2]);
            totalss = Hours+Mins+SS;
            return String.valueOf(totalss);
        }

        else if(string.length()==5){

            String[] split = string.split(":");
            String string3 = split[0];
            int min = Integer.parseInt(string3);
            int Mins =min*60;
            int  SS =Integer.parseInt(split[1]);

            totalss =Mins+SS;
            return String.valueOf(totalss);
        }
        return String.valueOf(totalss);


    }
    public static int getTimeInSecond3(String second){
        if(TextUtils.isEmpty(second)){
            return 0;
        } else {
            String time[] = second.split(":");
            int hour = Integer.valueOf(time[0]);
            int min = Integer.valueOf(time[1]);
            int sec = Integer.valueOf(time[2]);
            sec = hour*3600 + min*60 +sec;
            return sec;
        }
    }

}
