package com.base.library.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import com.base.library.R;
import com.base.library.bean.JsonBean;
import com.base.library.bean.ProvinceBean;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * 滚轮选择框
 * <p>
 * 使用：
 * if (pvSexOptions != null) {
 * pvSexOptions.show();
 * }
 */
public class PickerViewUtil {

    private TimePickerView dataTime, hourTime;
    private OptionsPickerView areaOptions, sexOptions, twoOptions;
    Context context;
    // 性别
    private ArrayList<String> sexLists = new ArrayList<>();
    // 自定义时分
    private ArrayList<ProvinceBean> hourLists = new ArrayList<>();
    private ArrayList<ArrayList<String>> minLists = new ArrayList<>();
//    private ArrayList<String> hourLists = new ArrayList<>();
//    private ArrayList<String> customLists = new ArrayList<>();
//    private ArrayList<ArrayList<String>> minLists = new ArrayList<>();

    // 所在地
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    public PickerViewUtil(Context context, PickerViewListener mPickerViewListener) {
        this.mPickerViewListener = mPickerViewListener;
        this.context = context;
        initTimePicker(context, true, true, true);
        initHourPicker(context, true, true, false);
        setCustomDate();
    }

    private void setCustomDate() {
        this.hourLists.clear();
        this.minLists.clear();

        for (int i = 1; i < 25; i++) {
            //选项1
            hourLists.add(new ProvinceBean(i, i + "时 ", "", ""));

            //选项2
            ArrayList<String> options2Items_01 = new ArrayList<>();
            options2Items_01.add("00分");
            options2Items_01.add("30分");
            minLists.add(options2Items_01);
        }

        initCostom(context,8,"选择开始时间");
    }

    public void setData(ArrayList<String> datas) {
        this.sexLists.clear();
        this.sexLists.addAll(datas);
        initSexPicker(context);
    }

    public void dateTwoShow() {
        twoOptions.show();
    }

    public void dataTimeShow() {
        dataTime.show();
    }

    public void hourTimeShow() {
        hourTime.show();
    }

    public void areaOptionsShow() {
        areaOptions.show();
    }

    public void sexOptionsShow() {
        sexOptions.show();
    }

    /**
     * 在底部弹出 年月日
     */
    public void initTimePicker(Context context, boolean year, boolean month, boolean day) {
        final Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = DateUtils.getTime();
        dataTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                if (mPickerViewListener != null) {
                    mPickerViewListener.onPickerViewDataTimeClick(date);
                }
            }
        })
                .setType(new boolean[]{year, month, day, false, false, false}) //年月日时分秒 的显示与否，不设置则默认全部显示
                .isCenterLabel(false)
//                .setTitleText(title)//标题
                .setSubCalSize(15)//确定和取消文字大小
                .setTitleSize(18)//标题文字大小
                .setTitleColor(Color.parseColor("#186AC8"))//标题文字颜色
                .setSubmitColor(Color.parseColor("#333333"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#333333"))//取消按钮文字颜色
                .setTextColorCenter(Color.parseColor("#186AC8"))//设置选中项的颜色
                .setTextColorOut(Color.parseColor("#333333"))//设置没有被选中项的颜色
                .setContentTextSize(18)
                .setDate(selectedDate)
                .setLineSpacingMultiplier(2.1f)
                .setTextXOffset(-0, 0, 0, 0, 0, 0)//设置X轴倾斜角度[ -90 , 90°]
//                .setRangDate(startDate, endDate)
                .setDecorView(null)
                .build();
//        pvTime.show();
    }

    /**
     * 在底部弹出 年月日
     */
//    public void initTimePicker(Context context,boolean year,boolean month,boolean day) {
//        dataTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
//            @Override
//            public void onTimeSelect(Date date, View v) {
////                boolean distance = FTimeUtils.getDistance(getTime(date, FTimeUtils.SYS_DATE_FORMATE), FTimeUtils.toTimeFullString(FTimeUtils.getTimeSeq()));
////                if (distance) {
//////                    mToastManager.show("选择的时间不能大于当前时间哦");
////                    return;
////                }
//
//                if (mPickerViewListener != null) {
//                    mPickerViewListener.onPickerViewDataTimeClick(date);
//                }
//
////                getTime(date, "yyyy-MM-dd");
//            }
//        })
//                .setType(new boolean[]{year, month, day, false, false, false})
//                .isDialog(true)
//                .setContentTextSize(20)
//                .setLabel("", "", "", "", "", "")
//                .setLineSpacingMultiplier(2.1f)
//                .setTextXOffset(0, 0, 0, 40, 0, -40)
//                .isCenterLabel(true)
////                .setDividerColor(0xFF24AD9D)
//                .setDividerColor(Color.LTGRAY)
////                .setSubmitColor(Color.WHITE)
//                .setCancelColor(Color.parseColor("#393E4B"))
//                .build();
//
//        Dialog mDialog = dataTime.getDialog();
//        if (mDialog != null) {
//            // 宽度还没解决问题，就设置成屏幕的宽度
//            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT,
//                    Gravity.BOTTOM);
//
//            params.leftMargin = 0;
//            params.rightMargin = 0;
//            dataTime.getDialogContainerLayout().setLayoutParams(params);
//
//            Window dialogWindow = mDialog.getWindow();
//            if (dialogWindow != null) {
//                dialogWindow.setWindowAnimations(R.style.picker_view_slide_anim);
//                dialogWindow.setGravity(Gravity.BOTTOM);
//            }
//        }
//    }

    /**
     * 在底部弹出  时分
     */
    public void initHourPicker(Context context, boolean hour, boolean branch, boolean second) {
        final Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = DateUtils.getTime();
        hourTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                if (mPickerViewListener != null) {
                    mPickerViewListener.onPickerViewDataTimeClick(date);
                }
            }
        })
                .setType(new boolean[]{false, false, false, hour, branch, second}) //年月日时分秒 的显示与否，不设置则默认全部显示
                .isCenterLabel(false)
//                .setTitleText(title)//标题
                .setSubCalSize(15)//确定和取消文字大小
                .setTitleSize(18)//标题文字大小
                .setTitleColor(Color.parseColor("#186AC8"))//标题文字颜色
                .setSubmitColor(Color.parseColor("#333333"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#333333"))//取消按钮文字颜色
                .setTextColorCenter(Color.parseColor("#186AC8"))//设置选中项的颜色
                .setTextColorOut(Color.parseColor("#333333"))//设置没有被选中项的颜色
                .setContentTextSize(18)
                .setDate(selectedDate)
                .setLineSpacingMultiplier(2.1f)
                .setTextXOffset(-0, 0, 0, 0, 0, 0)//设置X轴倾斜角度[ -90 , 90°]
//                .setRangDate(startDate, endDate)
                .setDecorView(null)
                .build();
//        hourTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
//            @Override
//            public void onTimeSelect(Date date, View v) {
////                getTime(date, "yyyy-MM");
//                if (mPickerViewListener != null) {
//                    mPickerViewListener.onPickerViewDataTimeClick(date);
//                }
//            }
//        })
//                .isDialog(true)
//                .setContentTextSize(20)
//                .setType(new boolean[]{false, false, false, hour, branch, second})
//                .setLabel("", "", "", "", "", "")
//                .setLineSpacingMultiplier(2.1f)
//                .setTextXOffset(0, 0, 0, 40, 0, -40)
//                .isCenterLabel(true)
////                .setDividerColor(0xFF24AD9D)
//                .setDividerColor(Color.LTGRAY)
////                .setSubmitColor(Color.WHITE)
//                .setCancelColor(Color.parseColor("#393E4B"))
//                .build();
//
//        Dialog mDialog = hourTime.getDialog();
//        if (mDialog != null) {
//            // 宽度还没解决问题，就设置成屏幕的宽度
//            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT,
//                    Gravity.BOTTOM);
//
//            params.leftMargin = 0;
//            params.rightMargin = 0;
//            hourTime.getDialogContainerLayout().setLayoutParams(params);
//
//            Window dialogWindow = mDialog.getWindow();
//            if (dialogWindow != null) {
//                dialogWindow.setWindowAnimations(R.style.picker_view_slide_anim);
//                dialogWindow.setGravity(Gravity.BOTTOM);
//            }
//        }
    }

    /**
     * 所在地
     */
    protected void initPickerView(Context context) {
        areaOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String tx = options1Items.get(options1).getPickerViewText() +
                        options2Items.get(options1).get(options2);
//                        options3Items.get(options1).get(options2).get(options3);
            }
        })
                .setTitleText("所在地选择")
                .setDividerColor(Color.LTGRAY)
                .setLineSpacingMultiplier(2.1f)
                .setTextColorCenter(Color.BLACK)
                .setCancelColor(Color.parseColor("#393E4B"))
                .setContentTextSize(20)
                .build();

        areaOptions.setPicker(options1Items, options2Items);
    }

    /**
     * 自定义时分
     */
    public void initCostom(Context context,int position,String strTitle) {
        twoOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                LogUtil.e("自定义时分 = ", hourLists.get(options1).getPickerViewText());
                LogUtil.e("自定义时分 = ", minLists.get(options1).get(options2));
//                String tx = sexLists.get(options1);
                if (mPickerViewListener != null) {
                    mPickerViewListener.onPickerViewOtherClick(hourLists.get(options1).getPickerViewText().replace("时","").trim() + ":" + minLists.get(options1).get(options2).replace("分","").trim());
                }
            }
        })
                .setTitleText(strTitle)
                .setContentTextSize(20)
                .setDividerColor(Color.LTGRAY)
                .setCancelColor(Color.parseColor("#393E4B"))
                .setSelectOptions(0)
                .setTextColorCenter(Color.BLACK)
                .isRestoreItem(true)
                .setLineSpacingMultiplier(2.1f)
                .setSelectOptions(position)  // 设置在哪个位置显示
                .build();
        twoOptions.setPicker(hourLists, minLists);
    }


    /**
     * 性别
     */
    protected void initSexPicker(Context context) {
        sexOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String tx = sexLists.get(options1);
                if (mPickerViewListener != null) {
                    mPickerViewListener.onPickerViewOtherClick(tx);
                }
            }
        })
                .setTitleText("会议地点选择")
                .setContentTextSize(20)
                .setDividerColor(Color.LTGRAY)
                .setCancelColor(Color.parseColor("#393E4B"))
                .setSelectOptions(0)
                .setTextColorCenter(Color.BLACK)
                .isRestoreItem(true)
                .setLineSpacingMultiplier(2.1f)
                .build();

        sexOptions.setPicker(sexLists);
    }

    @SuppressLint("SimpleDateFormat")
    public String getTime(Date date, String foormat) {
        SimpleDateFormat format = new SimpleDateFormat(foormat);
        return format.format(date);
    }

    private PickerViewListener mPickerViewListener;

    public interface PickerViewListener {
        void onPickerViewDataTimeClick(Date date);

        void onPickerViewAreaClick(String province, String city, String area);

        void onPickerViewOtherClick(String data);
    }


    /**
     * 选择开始时间
     *
     * @param title 标题
     */
    public void showPopCheckTime(String title) {
        final Calendar selectedDate = Calendar.getInstance();//系统当前时间
//        Calendar startDate = Calendar.getInstance();
//        Calendar endDate = DateUtils.getTime();
        TimePickerView pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
//                DateUtils.yMdHm(date);
                if (mPickerViewListener != null) {
                    mPickerViewListener.onPickerViewDataTimeClick(date);
                }
            }
        })
                .setType(new boolean[]{true, true, true, true, true, false}) //年月日时分秒 的显示与否，不设置则默认全部显示
                .isCenterLabel(false)
                .setTitleText(title)//标题
                .setSubCalSize(15)//确定和取消文字大小
                .setTitleSize(18)//标题文字大小
                .setTitleColor(Color.parseColor("#186AC8"))//标题文字颜色
                .setSubmitColor(Color.parseColor("#333333"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#333333"))//取消按钮文字颜色
                .setTextColorCenter(Color.parseColor("#186AC8"))//设置选中项的颜色
                .setTextColorOut(Color.parseColor("#333333"))//设置没有被选中项的颜色
                .setContentTextSize(18)
                .setDate(selectedDate)
                .setLineSpacingMultiplier(2.1f)
                .setTextXOffset(-0, 0, 0, 0, 0, 0)//设置X轴倾斜角度[ -90 , 90°]
//                .setRangDate(startDate, endDate)
                .setDecorView(null)
                .build();
        pvTime.show();
    }
}
