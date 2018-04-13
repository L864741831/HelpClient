package com.tianjistar.help.utils;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.Window;

/**
 * Created by Administrator on 2017/12/8 0008.
 */

public class RedWardsDialog extends Dialog {
    public RedWardsDialog(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//这句话，就是决定上面的那个黑框，也就是dialog的title。
    }

    public RedWardsDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public RedWardsDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }
}
