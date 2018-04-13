package com.tianjistar.help.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;


/**
 * 对话框封装类
 *
 * @author Z
 */
public class DialogTool {

    public static final int NO_ICON = -1;  //无图标


    /**
     * 创建消息对话框
     *
     * @param context
     *         上下文 必填
     * @param iconId
     *         图标，如：R.drawable.icon 或 DialogTool.NO_ICON 必填
     * @param title
     *         标题 必填
     * @param message
     *         显示内容 必填
     * @param btnName
     *         按钮名称 必填
     * @param listener
     *         监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
     * @return
     */
    public static Dialog createMessageDialog(Context context, String title, String message, String btnName, OnClickListener listener, int iconId) {
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if (iconId != NO_ICON) {
            //设置对话框图标
            builder.setIcon(iconId);
        }
        //设置对话框标题
        builder.setTitle(title);
        //设置对话框消息
        builder.setMessage(message);
        //设置按钮
        builder.setPositiveButton(btnName, listener);
        //创建一个消息对话框
        dialog = builder.create();

        return dialog;
    }


    /**
     * 创建警示（确认、取消）对话框
     *
     * @param context
     *         上下文 必填
     * @param title
     *         标题 必填
     * @param message
     *         显示内容 必填
     * @param positiveBtnName
     *         确定按钮名称 必填
     * @param negativeBtnName
     *         取消按钮名称 必填
     * @param positiveBtnListener
     *         监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
     * @param negativeBtnListener
     *         监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
     * @return
     */
    public static Dialog createConfirmDialog(Context context, String title, String message, String positiveBtnName, String negativeBtnName, OnClickListener positiveBtnListener, OnClickListener negativeBtnListener) {
        Dialog dialog ;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //设置对话框标题
        builder.setTitle(title);
        //设置对话框消息
        builder.setMessage(message);
        //设置确定按钮
        builder.setPositiveButton(positiveBtnName, positiveBtnListener);
        //设置取消按钮
        builder.setNegativeButton(negativeBtnName, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        //创建一个消息对话框
        dialog = builder.create();
        return dialog;
    }


    /**
     * 创建单选对话框
     *
     * @param context
     *         上下文 必填
     * @param iconId
     *         图标，如：R.drawable.icon 或 DialogTool.NO_ICON 必填
     * @param title
     *         标题 必填
     * @param itemsString
     *         选择项 必填
     * @param positiveBtnName
     *         确定按钮名称 必填
     * @param negativeBtnName
     *         取消按钮名称 必填
     * @param positiveBtnListener
     *         监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
     * @param negativeBtnListener
     *         监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
     * @param itemClickListener
     *         监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
     * @return
     */
    public static Dialog createSingleChoiceDialog(Context context, String title, String[] itemsString, String positiveBtnName, String negativeBtnName, OnClickListener positiveBtnListener, OnClickListener negativeBtnListener, OnClickListener itemClickListener, int iconId) {
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if (iconId != NO_ICON) {
            //设置对话框图标
            builder.setIcon(iconId);
        }
        //设置对话框标题
        builder.setTitle(title);
        //设置单选选项, 参数0: 默认第一个单选按钮被选中
        builder.setSingleChoiceItems(itemsString, 0, itemClickListener);
        //设置确定按钮
        builder.setPositiveButton(positiveBtnName, positiveBtnListener);
        //设置确定按钮
        builder.setNegativeButton(negativeBtnName, negativeBtnListener);
        //创建一个消息对话框
        dialog = builder.create();

        return dialog;
    }


    /**
     * 创建复选对话框
     *
     * @param context
     *         上下文 必填
     * @param iconId
     *         图标，如：R.drawable.icon 或 DialogTool.NO_ICON 必填
     * @param title
     *         标题 必填
     * @param itemsString
     *         选择项 必填
     * @param positiveBtnName
     *         确定按钮名称 必填
     * @param negativeBtnName
     *         取消按钮名称 必填
     * @param positiveBtnListener
     *         监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
     * @param negativeBtnListener
     *         监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
     * @param itemClickListener
     *         监听器，需实现android.content.DialogInterface.OnMultiChoiceClickListener;接口 必填
     * @return
     */
    public static Dialog createMultiChoiceDialog(Context context, String title, String[] itemsString, String positiveBtnName, String negativeBtnName, OnClickListener positiveBtnListener, OnClickListener negativeBtnListener, OnMultiChoiceClickListener itemClickListener, int iconId) {
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if (iconId != NO_ICON) {
            //设置对话框图标
            builder.setIcon(iconId);
        }
        //设置对话框标题
        builder.setTitle(title);
        //设置选项
        builder.setMultiChoiceItems(itemsString, null, itemClickListener);
        //设置确定按钮
        builder.setPositiveButton(positiveBtnName, positiveBtnListener);
        //设置确定按钮
        builder.setNegativeButton(negativeBtnName, negativeBtnListener);
        //创建一个消息对话框
        dialog = builder.create();

        return dialog;
    }


    /**
     * 创建列表对话框
     *
     * @param context
     *         上下文 必填
     * @param iconId
     *         图标，如：R.drawable.icon 或 DialogTool.NO_ICON 必填
     * @param title
     *         标题 必填
     * @param itemsString
     *         列表项 必填
     * @param negativeBtnName
     *         取消按钮名称 必填
     * @param negativeBtnListener
     *         监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
     * @return
     */
    public static Dialog createListDialog(Context context, String title, String[] itemsString, String negativeBtnName, OnClickListener negativeBtnListener, OnClickListener itemClickListener, int iconId) {
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if (iconId != NO_ICON) {
            //设置对话框图标
            builder.setIcon(iconId);
        }
        //设置对话框标题
        builder.setTitle(title);
        //设置列表选项
        builder.setItems(itemsString, itemClickListener);
        //设置确定按钮
        builder.setNegativeButton(negativeBtnName, negativeBtnListener);
        //创建一个消息对话框
        dialog = builder.create();

        return dialog;
    }



    public static ProgressDialog progressDialog;


    // 定义一个显示消息的对话框
    public static void showDialog(final Context ctx, String title, String msg, boolean closeSelf) {
        // 创建一个AlertDialog.Builder对象
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage(msg);
        builder.setTitle(title);
        builder.setPositiveButton("确定", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    // 定义一个确认消息的对话框
    public static void showChooseDialog(final Context ctx, String tip, String msg, boolean b) {
        // 创建一个AlertDialog.Builder对象
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage(msg);
        builder.setPositiveButton("确认", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ((Activity) ctx).finish();
            }
        });
        builder.setNegativeButton("取消", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();

    }



    // 定义一个进度条弹出框
    public static void showPorgressDialog(final Context ctx, String msg) {
        progressDialog = new ProgressDialog(ctx);
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }


    private static AlertDialog dialog;


    public static void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    public static void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
