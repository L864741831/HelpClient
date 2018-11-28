package com.tianjistar.help.activity.persion;


import android.Manifest;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.tianjistar.help.R;
import com.tianjistar.help.adapter.CommonAdapter;
import com.tianjistar.help.api.BaseHttpCallbackListener;
import com.tianjistar.help.api.Define;
import com.tianjistar.help.api.Element;
import com.tianjistar.help.api.MyParams;
import com.tianjistar.help.api.VictorHttpUtil;
import com.tianjistar.help.app.Constants;
import com.tianjistar.help.app.MyApplication;
import com.tianjistar.help.base.Base1Activity;
import com.tianjistar.help.bean.CropOption;
import com.tianjistar.help.bean.UserInfoBean;
import com.tianjistar.help.utils.AppUtil;
import com.tianjistar.help.utils.DateUtil1;
import com.tianjistar.help.utils.DisplayUtil;
import com.tianjistar.help.utils.PhotoViewHolder;
import com.tianjistar.help.utils.PicassoUtils;
import com.tianjistar.help.utils.PreferencesUtils;
import com.tianjistar.help.utils.StringUtil;
import com.tianjistar.help.utils.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.Bind;
import cn.carbswang.android.numberpickerview.library.NumberPickerView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 基本信息
 */
public class PersionInfoActivity extends Base1Activity implements View.OnClickListener {

    @Bind(R.id.rl_info_day)
    RelativeLayout relativeLayoutDay;
    @Bind(R.id.rl_info_head)
    RelativeLayout relativeLayoutHead;
    @Bind(R.id.rl_info_realname)
    RelativeLayout relativeLayoutRealName;
    @Bind(R.id.rl_info_sex)
    RelativeLayout relativeLayoutSex;
    @Bind(R.id.rl_info_xue)
    RelativeLayout relativeLayoutXue;

    @Bind(R.id.et_info_name)
    EditText editTextName;
    @Bind(R.id.et_info_phone)
    EditText editTextPhone;

    @Bind(R.id.tv_info_xue)
    TextView textViewXue;
    @Bind(R.id.tv_info_sex)
    TextView textViewSex;
    @Bind(R.id.tv_info_day)
    TextView textViewDay;
    @Bind(R.id.tv_info_real_name)
    TextView textViewRealName;

    @Bind(R.id.iv_info_head)
    CircleImageView imageViewHead;
    @Bind(R.id.topbar_right)
    TextView textViewSave;//保存

    //性别类型:1：男，2：女，3：保密
    private String genderType;
    //0：A型。1：B型。2：AB型。3：O型。4：保密
    private  String bloodType = "0";
    private  String bloodValue = "A";

    private final int PHOTO_PICKED_FROM_CAMERA = 1; // 用来标识头像来自系统拍照
    private final int PHOTO_PICKED_FROM_FILE = 2; // 用来标识从相册获取头像
    private final int CROP_FROM_CAMERA = 3;

    private Bitmap mBitmap;
    private int CODE = 100;
    private String name;
    private LinearLayout ll_popup;
    private PopupWindow pop = null;
    private View parentView;

    private String uuid,userId;
    private String userSex,userBlood,userBirthday;
    private String state,actualName,idNumber;

    private String imgFilePath = Constants.FILE_IMG_PATH + "/avatar.png";//文件路径
    private File file2;//图片文件

    @Override
    public int getContentView() {
        return R.layout.activity_persion_info;
    }

    @Override
    public void initView() {
        setTitle("基本信息");
        textViewSave.setText("保存");
        parentView = getLayoutInflater().inflate(R.layout.activity_persion_info, null);
    }

    @Override
    public void initData() {
        getUserData();
        showPicPick();
    }

    @Override
    public void initListener() {
        relativeLayoutDay.setOnClickListener(this);
        relativeLayoutHead.setOnClickListener(this);
        relativeLayoutRealName.setOnClickListener(this);
        relativeLayoutSex.setOnClickListener(this);
        relativeLayoutXue.setOnClickListener(this);
        textViewSave.setOnClickListener(this);
        //昵称监听编辑字数
        editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(" ")) {
                    String[] str = s.toString().split(" ");
                    String str1 = "";
                    for (int i = 0; i < str.length; i++) {
                        str1 += str[i];
                    }
                    editTextName.setText(str1);
                    editTextName.setSelection(start);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
//                if (editable.length() > 10) {
//                    MyApplication.showToast( "您已超过限制，请控制在10字之内");
//                    editable.delete(10, editable.length());
//                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case  R.id.rl_info_sex://性别
                showSelectSexPopwin();
                break;
            case R.id.rl_info_xue://血型
                showBloodPopwin();
                break;
            case R.id.rl_info_day://生日
                showDatePickerDialog();
                break;
            case R.id.rl_info_head://头像
                ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.activity_translate_in));
                pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.rl_info_realname://实名
                Intent intent = new Intent(mContext,RealNameCheckActivity.class);
                intent.putExtra("state",state);
                intent.putExtra("name",actualName);
                intent.putExtra("id",idNumber);
                startActivity(intent);
                break;
            case R.id.topbar_right://保存
                String name = editTextName.getText().toString();
                if (TextUtils.isEmpty(name)){
                    MyApplication.showToast("昵称不能为空！");
                }else {
                    saveData(name,"6");
                }
                break;
        }

    }

    //保存用户信息
    private void saveData(final String fileUrl, final String type) {
        MyParams params = new MyParams();
        params.put("id", userId);//用户id
        params.put("app","yes");
        params.put("uuid",uuid);
        params.put("imei", AppUtil.getPhoneImei(mActivity));

        if ("1".equals(type)){
            params.put("photo", fileUrl);
        }else if ("2".equals(type)){
            params.put("sexId", genderType);
        }else if ("3".equals(type)){
            params.put("blood", bloodType);
        }else if ("4".equals(type)){
            params.put("catateBirthday", fileUrl);
        }else if ("5".equals(type)){
            params.put("mobile", fileUrl);
        }else if ("6".equals(type)){
            params.put("name", fileUrl);
        }
        VictorHttpUtil.doPost(false, mContext, Define.MODIFY_USER_INFO, params, false, "",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        super.callbackSuccess(url, element);
                        MyApplication.showToast("保存成功！");
                        if ("1".equals(type)){
                            PreferencesUtils.putString(mContext,Constants.USER_AVATAR,fileUrl);
                        }else if ("2".equals(type)){
                            PreferencesUtils.putString(mContext,Constants.USER_SEX,genderType);
                        }else if ("3".equals(type)){
                            PreferencesUtils.putString(mContext,Constants.USER_BLOOD,bloodType);
                        }else if ("4".equals(type)){
                            PreferencesUtils.putString(mContext,Constants.USER_BIRTHDAY,fileUrl);
                        }else if ("5".equals(type)){
                            PreferencesUtils.putString(mContext,Constants.USER_PHONE,fileUrl);
                        }else if ("6".equals(type)){
                            PreferencesUtils.putString(mContext,Constants.USER_NAME,fileUrl);
                            finish();
                        }

                    }
                });
    }

    //保存用户头像
    private void savePicData(File file) {
        MyParams params = new MyParams();
        params.put("file", file);
        params.put("app","yes");
        params.put("uuid",uuid);
        params.put("imei", AppUtil.getPhoneImei(mActivity));
        VictorHttpUtil.doPost(false, mContext, Define.UPLOAD_PIC, params, false, "",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        super.callbackSuccess(url, element);

                        UserInfoBean userInfoBean = JSON.parseObject(element.rows,UserInfoBean.class);
                        String mAvatarUrl = userInfoBean.getUrl();
                        saveData(mAvatarUrl,"1");
                    }
                });
    }

    //获取用户信息
    private void getUserData() {
        uuid =  PreferencesUtils.getString(mContext,Constants.USER_UUID);
        userId =  PreferencesUtils.getString(mContext,Constants.USER_ID);

        MyParams params = new MyParams();
        params.put("uuid",uuid);
        params.put("imei", AppUtil.getPhoneImei(mContext));

        params.put("app","yes");
        VictorHttpUtil.doPost(false, mContext, Define.GET_USER_INFO, params, false, "",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        super.callbackSuccess(url, element);
                        UserInfoBean userInfoBean = JSON.parseObject(element.rows,UserInfoBean.class);

                        if (StringUtils.isNotBlank(userInfoBean.getPhoto())){
                            PicassoUtils.loadHeadImage(mContext,userInfoBean.getPhoto(),imageViewHead);
                        }

                        userSex = userInfoBean.getSexId();
                        if (StringUtils.isNotBlank(userSex)){
                            if ("1".equals(userSex)){
                                textViewSex.setText("男");
                            }else if ("2".equals(userSex)){
                                textViewSex.setText("女");
                            }else {
                                textViewSex.setText("保密");
                            }
                        }

                        userBlood = userInfoBean.getBlood();
                        if (StringUtils.isNotBlank(userBlood)){
                            if ("0".equals(userBlood)){
                                textViewXue.setText("A");
                            }else if ("1".equals(userBlood)){
                                textViewXue.setText("B");
                            }else if ("2".equals(userBlood)){
                                textViewXue.setText("AB");
                            }else if ("3".equals(userBlood)){
                                textViewXue.setText("O");
                            }else if ("4".equals(userBlood)){
                                textViewXue.setText("保密");
                            }
                        }

                        userBirthday = userInfoBean.getBirthday();
                        if (StringUtils.isNotBlank(userBirthday)){
                            textViewDay.setText(userBirthday);
                        }

                        if (StringUtils.isNotBlank(userInfoBean.getMobile())){
                            editTextPhone.setText(userInfoBean.getMobile());
                        }

                        if (StringUtils.isNotBlank(userInfoBean.getName())){
                            editTextName.setText(userInfoBean.getName());
                        }

                        state = userInfoBean.getRealName();
                        if (StringUtils.isNotBlank(state)){
                            if ("1".equals(state)){
                                textViewRealName.setText("已认证");
                                actualName = userInfoBean.getActualName();
                                idNumber = userInfoBean.getIdNumber();
                            }else {
                                textViewRealName.setText("未认证");
                            }
                        }
                    }
                });

    }

    /**
     * 显示时间对话框
     */
    private void showDatePickerDialog() {
        // 回显时间，展示选择框
        Calendar calendar = new GregorianCalendar();
        String text = textViewDay.getText().toString().trim();
        if (!StringUtil.isEmpty(text)) {
            Date date = DateUtil1.getDateByFormat(text, DateUtil1.YMD);
            calendar.setTime(date == null ? new Date() : date);
        }
        long _200year = 200L * 365 * 1000 * 60 * 60 * 24L;//100年
        TimePickerDialog mDialogYearMonthDay = new TimePickerDialog.Builder()
                .setCallBack(new OnDateSetListener() {
                    @Override
                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                        textViewDay.setText(DateUtil1.getStringByFormat(millseconds, DateUtil1.YMD));
                        String birthday = textViewDay.getText().toString();
                        if (!birthday.equals(userBirthday)){
                            saveData(birthday,"4");
                        }
                    }
                })
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("选择日期")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setCyclic(false)
                .setMinMillseconds(System.currentTimeMillis() - _200year)//设置最小时间
                //.setMaxMillseconds(System.currentTimeMillis() + _100year)//设置最大时间+100年
                .setMaxMillseconds(System.currentTimeMillis())//设置最大时间+100年
                .setCurrentMillseconds(calendar.getTimeInMillis())//设置当前时间
                .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                .setType(Type.YEAR_MONTH_DAY)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
                .setWheelItemTextSize(16)
                .build();

        mDialogYearMonthDay.show(getSupportFragmentManager(), getClass().getSimpleName());
    }

    //选择性别弹窗
    private void showSelectSexPopwin() {
        View view = LayoutInflater.from(this).inflate(R.layout.popwin_show_gender, null);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);
        DisplayUtil.setAlpha(PersionInfoActivity.this, 0.5f);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                DisplayUtil.setAlpha(PersionInfoActivity.this, 1f);
            }
        });
        LinearLayout ll_cancel_layout = view.findViewById(R.id.ll_cancel_layout);
        LinearLayout ll_man_layout = view.findViewById(R.id.ll_man_layout);
        LinearLayout ll_woman_layout = view.findViewById(R.id.ll_woman_layout);
        LinearLayout ll_secrecy_layout = view.findViewById(R.id.ll_secrecy_layout);

        ll_man_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genderType = "1";
                textViewSex.setText("男");
                if (!genderType.equals(userSex)){
                    saveData("","2");
                }
                popupWindow.dismiss();
            }
        });
        ll_woman_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genderType = "2";
                textViewSex.setText("女");
                if (!genderType.equals(userSex)){
                    saveData("","2");
                }
                popupWindow.dismiss();
            }
        });
        ll_secrecy_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genderType = "3";
                textViewSex.setText("保密");
                if (!genderType.equals(userSex)){
                    saveData("","2");
                }
                popupWindow.dismiss();
            }
        });

        ll_cancel_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    //选择血型弹窗
    private void showBloodPopwin() {
        View view = LayoutInflater.from(this).inflate(R.layout.popwin_show_sex, null);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);
        DisplayUtil.setAlpha(PersionInfoActivity.this, 0.5f);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                DisplayUtil.setAlpha(PersionInfoActivity.this, 1f);
            }
        });
        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tvCancel = view.findViewById(R.id.tv_cancel);
        TextView tvSure = view.findViewById(R.id.tv_sure);
        NumberPickerView pickerPosition = view.findViewById(R.id.picker_sex);

        String position[] = new String[]{"A", "B","AB","O","保密"};

        pickerPosition.refreshByNewDisplayedValues(position);
        pickerPosition.setMinValue(1);
        pickerPosition.setMaxValue(position.length);
        pickerPosition.setValue(1);

        tv_title.setText("血型");

        pickerPosition.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
                bloodValue = picker.getContentByCurrValue();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (StringUtils.isNotBlank(textViewXue.getText().toString())){
//                    textViewXue.setText(textViewXue.getText().toString());
//                }
                popupWindow.dismiss();
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadBloodData(textViewXue,bloodValue);
                popupWindow.dismiss();
            }
        });
    }

    private void uploadBloodData(TextView textViewXue, String bloodValue) {
        if (StringUtils.isNotBlank(textViewXue.getText().toString())){
            textViewXue.setText(bloodValue);
        } else {
            textViewXue.setText("A");
            bloodValue = "A";
        }
        String value = textViewXue.getText().toString();
        if ("A".equals(value)){
            bloodType = "0";
        }else if ("B".equals(value)){
            bloodType = "1";
        }else if ("AB".equals(value)){
            bloodType = "2";
        }else if ("O".equals(value)){
            bloodType = "3";
        }else if ("保密".equals(value)){
            bloodType = "4";
        }
        if (!bloodType.equals(userBlood)){
            saveData("","3");
        }
    }

    private void getIconFromPhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK, Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_PICKED_FROM_FILE);
    }

    /**
     * 照片选择器
     */
    private void showPicPick() {
        setFilePath();
        View view = View.inflate(mContext, R.layout.item_popupwindows, null);
        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        pop = new PopupWindow(mContext);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);
        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
        Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
        Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });

        /**
         * 拍照
         */
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new TedPermission(MyApplication.CONTEXT)
                        .setPermissions(Manifest.permission.CAMERA)
                        .setDeniedMessage(R.string.rationale_location)
                        .setDeniedCloseButtonText("取消")
                        .setGotoSettingButtonText("设置")
                        .setPermissionListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted() {
                                getIconFromCamera();
                                pop.dismiss();
                                ll_popup.clearAnimation();
                            }

                            @Override
                            public void onPermissionDenied(ArrayList<String> deniedPermissions) {

                            }
                        }).check();
            }
        });

        /**
         * 从本地相册选择
         */
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                getIconFromPhoto(); // 从系统相册获取
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });

        /**
         * 取消
         */
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
    }

    private Uri imgUri; // 由于android手机的图片基本都会很大，所以建议用Uri，而不用Bitmap

    /**
     * 调用系统相机拍照
     */
    private void getIconFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imgUri = Uri.fromFile(file2);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imgUri);
        startActivityForResult(intent,PHOTO_PICKED_FROM_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CODE &&resultCode==200){
            //获取响应的数据内容
            name = data.getStringExtra("name");
//            mTextViewName.setText(name);
        }
        if (resultCode != RESULT_OK){
            return;
        }
        switch (requestCode) {
            case PHOTO_PICKED_FROM_CAMERA:
                doCrop();
                break;
            case PHOTO_PICKED_FROM_FILE:
                imgUri = data.getData();
                doCrop();
                setCropImg(data);
                break;
            case CROP_FROM_CAMERA:
//                if (data != null){
                setCropImg(data);
//                }
                break;
            default:
                break;
        }
    }

    /**
     * 尝试裁剪图片
     */
    private void doCrop(){
        final ArrayList<CropOption> cropOptions = new ArrayList<>();
        final Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");
        List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent,0);
        int size = list.size();
        if (size == 0){
            MyApplication.showToast("当前不支持裁剪图片!");
            return;
        }
        intent.setData(imgUri);
        intent.putExtra("outputX",300);
        intent.putExtra("outputY",300);
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        intent.putExtra("scale",true);
        intent.putExtra("return-data",true);

        // only one
        if (size == 1){
            Log.i("info","---size---");
            Intent intent1 = new Intent(intent);
            ResolveInfo res = list.get(0);
            intent1.setComponent(new ComponentName(res.activityInfo.packageName,res.activityInfo.name));
            startActivityForResult(intent1,CROP_FROM_CAMERA);
        }else {
            Log.i("info","---size--2-");
            // 很多可支持裁剪的app
            for (ResolveInfo res : list) {
                CropOption co = new CropOption();
                co.title = getPackageManager().getApplicationLabel(res.activityInfo.applicationInfo);
                co.icon = getPackageManager().getApplicationIcon(res.activityInfo.applicationInfo);
                co.appIntent = new Intent(intent);
                co.appIntent.setComponent(new ComponentName(res.activityInfo.packageName,res.activityInfo.name));
                cropOptions.add(co);
            }

            CommonAdapter<CropOption> adapter = new CommonAdapter<CropOption>(this,cropOptions,R.layout.layout_crop_selector) {
                @Override
                public void convert(PhotoViewHolder holder, CropOption item) {
                    holder.setImageDrawable(R.id.iv_icon,item.icon);
                    holder.setText(R.id.tv_name,item.title);
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("choose a app");
            builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivityForResult(cropOptions.get(which).appIntent,CROP_FROM_CAMERA);
                }
            });
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if (imgUri != null){
                        getContentResolver().delete(imgUri,null,null);
                        imgUri = null;
                    }
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private void setCropImg(Intent picData){
        Log.i("info","---setCropImg---");
        Bundle bundle = picData.getExtras();
        if (bundle != null){
            mBitmap = bundle.getParcelable("data");
            imageViewHead.setImageBitmap(mBitmap);

            saveBitmap(imgFilePath, mBitmap);
//            saveBitmap(Environment.getExternalStorageDirectory() + "/crop_ArGoImage"
//                    + ".png", mBitmap);

//            SharedPreferences sp = getSharedPreferences("sp_demo", Context.MODE_PRIVATE);
//            String token = sp.getString("token", null);
//            OkGo.post(NetConfig.NET_USER_IMAGE)
//                    .tag(this)
//                    .cacheMode(CacheMode.DEFAULT)
//                    .headers("Authorization","Bearer"+" "+token)
//                    .params("avatar",new File(Environment.getExternalStorageDirectory() + "/crop_ArGoImage" + ".png"))
//                    .execute(new StringCallback() {
//                        @Override
//                        public void onSuccess(String s, Call call, Response response) {
//
//                        }
//
//                        @Override
//                        public void onError(Call call, Response response, Exception e) {
//                            super.onError(call, response, e);
//
//                        }
//                    });
        }
    }

    private void saveBitmap(String fileName,Bitmap bitmap){
        File file = new File(fileName);
        FileOutputStream fout = null;
        try {
            file.createNewFile();
            fout = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fout);
            fout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fout!=null){
                    fout.close();
                }
                savePicData(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setFilePath(){
        //新建一个File，传入文件夹目录
        File file = new File(Constants.FILE_IMG_PATH);
        //判断文件夹是否存在，如果不存在就创建，否则不创建
        if (!file.exists()) {
            //通过file的mkdirs()方法创建目录中包含却不存在的文件夹
            file.mkdirs();
        }

        file2 = new File(imgFilePath);
        //判断文件是否存在，如果存在就删除。
        if (file2.exists()) {
            file2.delete();
        }
        try {
            //通过文件的对象file的createNewFile()方法来创建文件
            file2.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



//    private void selectPhoto() {
//        List<String> list = new ArrayList<>();
//        list.add("拍照");
//        list.add("相册");
//        showDialog(new SelectDialog.SelectDialogListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                switch (position){
//                    case 0:
//                        getIconFromCamera();
//                        break;
//                    case 1:
//                        getIconFromPhoto(); // 从系统相册获取
//                        break;
//                    default:
//                        break;
//                }
//            }
//        },list);
//
//    }

//    private SelectDialog showDialog(SelectDialog.SelectDialogListener selectDialogListener, List<String> list) {
//        SelectDialog dialog = new SelectDialog(this,
//                R.style.transparentFrameWindowStyle,selectDialogListener,list);
//        dialog.show();
//        return dialog;
//    }

    //    /**
//     * 照片选择器
//     */
//    private void showPicPick() {
//        View view = View.inflate(mContext, R.layout.item_popupwindows, null);
//        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
//        pop = new PopupWindow(mContext);
//        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        pop.setBackgroundDrawable(new BitmapDrawable());
//        pop.setFocusable(true);
//        pop.setOutsideTouchable(true);
//        pop.setContentView(view);
//        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
//        Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
//        Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
//        Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
//        parent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                pop.dismiss();
//                ll_popup.clearAnimation();
//            }
//        });
//        /**
//         * 拍照
//         */
//        bt1.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                getTakePhoto().onPickFromCapture(getFiles());
//                overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
//                pop.dismiss();
//                ll_popup.clearAnimation();
//            }
//        });
//        /**
//         * 从本地相册选择
//         */
//        bt2.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                if (selectImageType == SELECTHEADERIMAGE) {
//                    getTakePhoto().onPickFromGalleryWithCrop(getFiles(), getCropOptions());
//                    pop.dismiss();
//                    ll_popup.clearAnimation();
//                }
//
//            }
//        });
//        /**
//         * 取消
//         */
//        bt3.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                pop.dismiss();
//                ll_popup.clearAnimation();
//            }
//        });
//    }
//    /**
//     * 获取到照片
//     * @param result
//     */
//    @Override
//    public void takeSuccess(TResult result) {
//        super.takeSuccess(result);
//        if (selectImageType == SELECTHEADERIMAGE) {
//            file = new File(getFiles().getPath());
//            BitmapUtil.compressAndSaveImage(file, result.getImage().getOriginalPath(), 2);
//            personHeaderImageList.clear();
//            personHeaderImageList.add(file.getAbsolutePath());
//            imageViewHead.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
//            Executors.cacheThreadExecutor(runnableHeaderImage);
//        }
//
//    }
//
//    /**
//     * 给后台传头像，后台返回头像字符串
//     */
//    Runnable runnableHeaderImage = new Runnable() {
//        @Override
//        public void run() {
//            Message msg = new Message();
//            if (!ListUtils.isEmpty(personHeaderImageList)) {
//                ArrayList<File> localFiles = new ArrayList<File>();
//                for (String path : personHeaderImageList) {
//                    File  file = new File(path);
//                    if (file.exists()) {
//                        localFiles.add(file);
//                    }
//                }
////                /**
////                 * 给后台传图片，后台返回string 接口
////                 */
////                MyParams params=new MyParams();
////                params.put("photo",file);
////                params.put("type",5);
////                params.put("userid", SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_UID));
////                params.put("imei",SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_DEVICEID));
////                VictorHttpUtil.doPost(false,PersionInfoActivity.this, Define.URL_PostPic, params, true, "加载中...", new BaseHttpCallbackListener<Element>() {
////                    @Override
////                    public void callbackSuccess(String url, Element element) {
////                        super.callbackSuccess(url, element);
//////                        PublishImg publishImg=  JSON.parseObject(element.data, PublishImg.class);
//////                        headPic = publishImg.getUrl();
////////                        MyApplication.showToast(headPic);
//////                        SharedPreferencesHelper.getInstance().putString(AppSpContact.URL, headPic);
////                    }
////                });
//            }
//            msg.what = 6;
//            handler.sendMessage(msg);
//        }
//    };

}
