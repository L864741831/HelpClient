package com.tianjistar.help.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;

import com.tianjistar.help.utils.ViewUtil;


/**
 * Created by Administrator on 2017/12/8 0008.
 */
public class PwdEditText extends EditText {

    /**
     * 间隔
     */
    private final int PWD_SPACING = 0;
    /**
     * 密码大小
     */
    private final int PWD_SIZE = 10;
    /**
     * 密码长度
     */
    private final int PWD_LENGTH = 6;
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 宽度
     */
    private int mWidth;
    /**
     * 高度
     */
    private int mHeight;
    /**
     * 密码框
     */
    private Rect mRect;

    /**
     * 密码画笔
     */
    private Paint mPwdPaint;

    /**
     * 密码框画笔
     */
    private Paint mRectPaint;
    /**
     * 输入的密码长度
     */
    private int mInputLength;
    int textSize ;
    /**
     * 输入结束监听
     */
    private OnInputFinishListener mOnInputFinishListener;
    /**
     * 输入字符
     */
    String str;

    /**
     * 构造方法
     *
     * @param context
     * @param attrs
     */
    public PwdEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 初始化密码画笔
        mPwdPaint = new Paint();
        mPwdPaint.setColor(Color.BLACK);
        mPwdPaint.setStyle(Paint.Style.FILL);
        mPwdPaint.setTextSize(textSize);
        mPwdPaint.setAntiAlias(true);
        // 初始化密码框
        mRectPaint = new Paint();
        mRectPaint.setStyle(Paint.Style.STROKE);
        mRectPaint.setColor(Color.LTGRAY);
        mRectPaint.setAntiAlias(true);
        textSize= ViewUtil.dip2px(context,18);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getWidth();
        mHeight = getHeight();
        //Log.e("TAG","mWidth=="+mWidth+"mHeight=="+mHeight);
        // 这三行代码非常关键，大家可以注释点在看看效果
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawRect(0, 0, mWidth, mHeight, paint);

        // 计算每个密码框宽度
        int rectWidth = (mWidth - PWD_SPACING * (PWD_LENGTH - 1)) / PWD_LENGTH;
        // 绘制密码框
        for (int i = 0; i < PWD_LENGTH; i++) {
            int left = (rectWidth + PWD_SPACING) * i;
            int top = 2;
            int right = left + rectWidth;
            int bottom = mHeight - top;
            mRect = new Rect(left, top, right, bottom);
            canvas.drawRect(mRect, mRectPaint);
        }

        // 绘制密码
        for (int i = 0; i < mInputLength; i++) {
//            float cx = (rectWidth / 2 + (rectWidth + PWD_SPACING) * i)-(textSize/3f);
//            float cy = mHeight / 2+(textSize/2f);
//            if (i==0){
//                canvas.drawText(str.substring(0,1),cx,cy,mPwdPaint);
//            }else if (i==1){
//                canvas.drawText(str.substring(1,2),cx,cy,mPwdPaint);
//            }else if (i==2){
//                canvas.drawText(str.substring(2,3),cx,cy,mPwdPaint);
//            }else if (i==3){
//                canvas.drawText(str.substring(3,4),cx,cy,mPwdPaint);
//            }else if (i==4){
//                canvas.drawText(str.substring(4,5),cx,cy,mPwdPaint);
//            }else if (i==5){
//                canvas.drawText(str.substring(4,5),cx,cy,mPwdPaint);
//            }
            //绘制圆点的计算
            int cx = rectWidth / 2 + (rectWidth + PWD_SPACING) * i;
            int cy = mHeight / 2;
            canvas.drawCircle(cx, cy, PWD_SIZE, mPwdPaint);
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start,
                                 int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        this.mInputLength = text.toString().length();
        this.str = text.toString();
        invalidate();
        if (mInputLength == PWD_LENGTH && mOnInputFinishListener != null) {
            mOnInputFinishListener.onInputFinish(text.toString());
        }
    }


    public interface OnInputFinishListener {
        /**
         * 密码输入结束监听
         *
         * @param password
         */
        void onInputFinish(String password);
    }

    /**
     * 设置输入完成监听
     *
     * @param onInputFinishListener
     */
    public void setOnInputFinishListener(
            OnInputFinishListener onInputFinishListener) {
        this.mOnInputFinishListener = onInputFinishListener;
    }

}
