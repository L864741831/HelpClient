package com.tianjistar.help.utils;

import android.content.Context;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;
import com.tianjistar.help.R;


/**
 * @version : v1.00
 * @Program Name : DingBangBang
 * @Copyright : www.rzhkj.com
 * @Written by : 李超凡
 * @Creation Date : 2016年03月19日 11:17
 * @Description :
 * @ModificationHistory： Who                When                 What
 * --------           ----------            ----------
 * 李超凡             2014年8月28日         TODO
 */
public class PicassoUtils {

//    public static void glideNoCache(Object context, String path, ImageView imageView) {
//        Context ctx = (Context) context;
//        DrawableRequestBuilder builder = Glide.with(ctx)
//                .load(new File(path))
//                .placeholder(R.drawable.badloadimage);
//        builder.crossFade()
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .into(imageView);
//    }

  //  普通加载图片，错误路径加载默认图片R.drawable.pic_default
//    public static void loadImage(Context context, String url, ImageView imageView) {
//        if (StringUtils.isBlank(url) || imageView == null) {
//            Glide.with(context).load(R.drawable.badloadimage).into(imageView);
//            return;
//        }
//        Glide.with(context).load(url).error(R.drawable.badloadimage).dontAnimate().into(imageView);
//    }
//  //  普通加载图片，错误路径加载默认图片R.drawable.pic_default
//    public static void loadgroupImage(Context context, String url, ImageView imageView) {
//        if (StringUtils.isBlank(url) || imageView == null) {
//            Glide.with(context).load(R.drawable.icon_addpic).into(imageView);
//            return;
//        }
//        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.badloadimage).dontAnimate().into(imageView);
//    }

    /**
     * 加载我的背景图片
     *
     * @param context
     * @param url
     * @param imageView
     */
//    public static void loadbgImage(Context context, String url, ImageView imageView) {
//        if (StringUtils.isBlank(url) || imageView == null) {
//            Picasso.with(context).load(R.drawable.bg_myhead).into(imageView);
//            return;
//        }
//        Glide.with(context).load(url).error(R.drawable.badloadimage).into(imageView);
//    }

    /**
     * 加载他人背景图片
     *
     * @param context
     * @param url
     * @param imageView
     */
//    public static void loadHisbgImage(Context context, String url, ImageView imageView) {
//        if (StringUtils.isBlank(url) || imageView == null) {
//            Glide.with(context).load(R.drawable.badloadimage).into(imageView);
//            return;
//        }
//        Glide.with(context).load(url).error(R.drawable.badloadimage).into(imageView);
//    }

    //头像专用
    public static void loadHeadImage(Context context, String url, ImageView imageView) {
        if (StringUtils.isBlank(url) || imageView == null) {
            Picasso.with(context).load(R.drawable.ic_head_defout).into(imageView);
            return;
        }
        Picasso.with(context).load(url).error(R.drawable.ic_head_defout).into(imageView);
        //  Glide.with(context).load(url).error(R.drawable.defaulthead).into(imageView);
    }

//    public static void loadListImage(Context context, String url, ImageView imageView) {
//        if (StringUtils.isBlank(url) || imageView == null) {
//            Glide.with(context).load(R.drawable.badloadimage).into(imageView);
//            return;
//        }
//        Glide.with(context).load(url).error(R.drawable.badloadimage).into(imageView);
//    }

    /**
     * 列表加载单张图片，自适应
     *        
     * @author 李超凡
     * @time 2016/12/12 15:35 @param context
     * @param url
     * @param imageView
     */
//    public static void loadWishOneImage(Context context, String url, ImageView imageView) {
//        if (StringUtils.isBlank(url) || imageView == null) {
//            Glide.with(context).load(R.drawable.badloadimage).into(imageView);
//            return;
//        }
//        Glide.with(context).load(url).error(R.drawable.badloadimage).fitCenter().into(imageView);
//    }
//
//    /**
//     * 列表加载多张图片，不变形
//     *
//     * @author 李超凡
//     * @time 2016/12/12 15:36 @param context
//     * @param url
//     * @param imageView
//     */
//    public static void loadManyListImage(Context context, String url, ImageView imageView) {
//        if (StringUtils.isBlank(url) || imageView == null) {
//            Glide.with(context).load(R.drawable.badloadimage).into(imageView);
//            return;
//        }
//        Glide.with(context).load(url).error(R.drawable.badloadimage).into(imageView);
//    }

    /**
     * 指定加载失败时加载的图片
     *
     * @param context
     * @param url
     * @param imageView
     * @param error
     */
//    public static void showLoadingImage(Context context, String url, ImageView imageView, int error) {
//        if (StringUtils.isBlank(url) || imageView == null) {
//            Glide.with(context).load(error).into(imageView);
//            return;
//        }
//        Glide.with(context).load(url).error(error).into(imageView);
//    }
//
//    public static void loadDrableImage(Context context, ImageView imageView, int drable) {
//        Glide.with(context).load(drable).into(imageView);
//    }
//    public static void loadGifDrableImage(Context context, ImageView imageView, int drable) {
//        Glide.with(context).load(drable).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
//    }

    /**
     * 加载活动详情图片和活动列表图片，暂时用picasso，不知道为什么用Glide加载不显示
     *
     * @param context
     * @param url
     * @param imageView
     * @author 李超凡
     * @time 2016/12/8 15:39
     */
//    public static void loadActivityImage(Context context, String url, ImageView imageView) {
//        if (StringUtils.isBlank(url) || imageView == null) {
//            Glide.with(context).load(R.drawable.badloadimage).into(imageView);
//            return;
//        }
//        Picasso.with(context).load(url).config(Bitmap.Config.RGB_565).error(R.drawable.badloadimage).into(imageView);
//    }
}
