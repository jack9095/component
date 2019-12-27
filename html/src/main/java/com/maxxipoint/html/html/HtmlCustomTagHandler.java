package com.maxxipoint.html.html;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.util.TypedValue;

import com.maxxipoint.html.utils.ScreenUtils;
import org.xml.sax.XMLReader;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 支持Font和Span标签,
 * font:size;color
 * span:font-size;color
 * @Author: yang
 * @Time: 2019-08-26 15:04
 */
public class HtmlCustomTagHandler implements Html.TagHandler {

    //标签
    public static final String NEW_FONT = "myfont";
    public static final String HTML_FONT = "font";

    public static final String NEW_SPAN = "myspan";
    public static final String HTML_SPAN = "span";


    public static final String TAG = HtmlCustomTagHandler.class.getSimpleName();

    private List<String> mLableList;
    private List<HtmlLabelBean> labelBeanList;//顺序添加的Bean

    private List<HtmlLabelBean> tempRemoveLabelList;//已经删除的逆序(从结束标签删除)

    final HashMap<String, String> attributes = new HashMap<String, String>();

    public HtmlCustomTagHandler(String... lable) {
        mLableList = new ArrayList<>();
        mLableList.add(NEW_FONT);
        mLableList.add(NEW_SPAN);
        labelBeanList = new ArrayList<>();
        tempRemoveLabelList = new ArrayList<>();
    }

    private Context context;
    public void getContext(Context context){
        this.context = context;
    }


    @Override
    public void handleTag(boolean opening, String tag, Editable output,
                          XMLReader xmlReader) {
        processAttributes(xmlReader);

        if (mLableList.contains(tag)) {
            if (opening) {
                startFont(tag, output, xmlReader,context);
            } else {
                endFont(tag, output, xmlReader);
            }
        }


    }

    public void startFont(String tag, Editable output, XMLReader xmlReader, Context context) {
        int startIndex = output.length();
        HtmlLabelBean bean = new HtmlLabelBean();
        bean.startIndex = startIndex;
        bean.tag = tag;

        String color = null;
        String size = null;
        //字体加粗的值CSS font-weight属性:,normal,bold,bolder,lighter,也可以指定的值(100-900,其中400是normal)
        //说这么多,这里只支持bold,如果是bold则加粗,否则就不加粗
        String fontWeight = null;
        if (NEW_FONT.equals(tag)) {
            color = attributes.get("color");
            size = attributes.get("size");

        } else if (NEW_SPAN.equals(tag)) {
            String style = attributes.get("style");
            if (!TextUtils.isEmpty(style)) {
                String[] styles = style.split(";");
                for (String str : styles) {
                    if (!TextUtils.isEmpty(str)) {
                        String[] value = str.split(":");
                        if (value[0].equals("color")) {
                            color = value[1];
                        } else if (value[0].equals("font-size")) {
                            size = value[1];
                        } else if (value[0].equals("font-weight")) {
                            fontWeight = value[1];
                        }
                    }
                }
            }
        }
        try {
            if (!TextUtils.isEmpty(color)) {
                int colorInt = Color.parseColor(color);
                bean.color = colorInt;
            } else {
                bean.color = -1;
            }
        } catch (Exception e) {
            bean.color = -1;
        }

        try {
            if (!TextUtils.isEmpty(size)) {
                //这里用[A-Za-z]+)?,是为了假如单位不是px,dp,sp的话,或者无单位的话,那么还可以取出数值,给出一个默认的单位
                Pattern compile = Pattern.compile("^(\\d+)([A-Za-z]+)?$");
                Matcher matcher = compile.matcher(size);
                if (matcher.matches()) {
                    String group1 = matcher.group(1);//12--数值
                    String group2 = matcher.group(2);//px/sp/dp/无--单位-默认是px
                    if ("sp".equalsIgnoreCase(group2)) {
                        bean.size = sp2px(Integer.parseInt(group1),context);
                    } else if ("dp".equalsIgnoreCase(group2)) {
                        bean.size = dp2px(Integer.parseInt(group1),context);
                    } else if ("px".equalsIgnoreCase(group2)) {
                        bean.size = Integer.parseInt(group1);
                    } else {
                        bean.size = Integer.parseInt(group1);
                    }
                } else {
                    bean.size = -1;
                }
            } else {
                bean.size = -1;
            }
        } catch (Exception e) {
            bean.size = -1;
        }
        //设置字体粗细
        bean.fontWeight = fontWeight;

        labelBeanList.add(bean);
        Log.d(TAG, "opening:开" + "tag:<" + tag + " startIndex:" + startIndex + " 当前遍历的开的集合长度:" + labelBeanList.size());
    }

    public void endFont(String tag, Editable output, XMLReader xmlReader) {
        int stopIndex = output.length();
        Log.d(TAG, "opening:关" + "tag:" + tag + "/> endIndex:" + stopIndex);
        int lastLabelByTag = getLastLabelByTag(tag);
        if (lastLabelByTag != -1) {
            HtmlLabelBean bean = labelBeanList.get(lastLabelByTag);
            bean.endIndex = stopIndex;
            optBeanRange(bean);
            Log.d(TAG, "完整的TagBean解析完成:" + bean.toString());

            for (HtmlLabelRangeBean range : bean.ranges) {
                //设置字体颜色
                if (bean.color != -1)
                    output.setSpan(new ForegroundColorSpan(bean.color), range.start, range.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                //设置字体大小
                // 这里AbsoluteSizeSpan默所以是px
                if (bean.size != -1) {
                    output.setSpan(new AbsoluteSizeSpan(bean.size), range.start, range.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                //设置是否加粗
                if (bean.isBold()) {
                    output.setSpan(new StyleSpan(Typeface.BOLD), range.start, range.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
            //从顺序添加的集合中删除已经遍历完结束标签
            labelBeanList.remove(lastLabelByTag);
            optRemoveByAddBean(bean);
        }
    }

    /**
     * sp --> px
     *
     * @ param sp
     * @ return
     */
    private static int sp2px(int sp,Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                ScreenUtils.mContext.getResources().getDisplayMetrics());
    }

    /**
     * dp-->px
     *
     * @param dp
     * @return
     */
    private static int dp2px(int dp,Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                ScreenUtils.mContext.getResources().getDisplayMetrics());
    }

    /**
     * 操作删除的Bean,将其添加到删除的队列中
     *
     * @param removeBean
     */
    private void optRemoveByAddBean(HtmlLabelBean removeBean) {
        int isAdd = 0;
        for (int size = tempRemoveLabelList.size(), i = size - 1; i >= 0; i--) {
            HtmlLabelBean bean = tempRemoveLabelList.get(i);
            if (removeBean.startIndex <= bean.startIndex && removeBean.endIndex >= bean.endIndex) {
                if (isAdd == 0) {
                    tempRemoveLabelList.set(i, removeBean);
                    isAdd = 1;
                } else {
                    //表示已经把isAdd = 1;当前删除的bean,添加到了删除队列中,如果再次找到了可以removeBean可以替代的bean,则删除
                    tempRemoveLabelList.remove(i);
                }

            }
        }
        if (isAdd == 0) {
            tempRemoveLabelList.add(removeBean);
        }

        Log.d(TAG, "已经删除的完整开关结点的集合长度:" + tempRemoveLabelList.size());
    }


    /**
     * 计算影响的范围
     *
     * @param bean
     */
    private void optBeanRange(HtmlLabelBean bean) {

        if (bean.ranges == null) {
            bean.ranges = new ArrayList<>();
        }

        if (tempRemoveLabelList.size() == 0) {
            HtmlLabelRangeBean range = new HtmlLabelRangeBean();
            range.start = bean.startIndex;
            range.end = bean.endIndex;
            bean.ranges.add(range);
        } else {
            int size = tempRemoveLabelList.size();
            //逆向找到  第一个结束位置<=当前结束位置
            //逆向找到最后一个开始位置>=当前开始位置
            int endRangePosition = -1;
            int startRangePosition = -1;
            for (int i = size - 1; i >= 0; i--) {
                HtmlLabelBean bean1 = tempRemoveLabelList.get(i);
                if (bean1.endIndex <= bean.endIndex) {
                    //找第一个
                    if (endRangePosition == -1)
                        endRangePosition = i;
                }
                if (bean1.startIndex >= bean.startIndex) {
                    //找最后一个,符合条件的都覆盖之前的
                    startRangePosition = i;
                }
            }
            if (startRangePosition != -1 && endRangePosition != -1) {
                HtmlLabelBean lastBean = null;
                //有包含关系
                for (int i = startRangePosition; i <= endRangePosition; i++) {
                    HtmlLabelBean removeBean = tempRemoveLabelList.get(i);
                    lastBean = removeBean;
                    HtmlLabelRangeBean range;
                    if (i == startRangePosition) {
                        range = new HtmlLabelRangeBean();
                        range.start = bean.startIndex;
                        range.end = removeBean.startIndex;
                        bean.ranges.add(range);
                    } else {
                        range = new HtmlLabelRangeBean();
                        HtmlLabelBean bean1 = tempRemoveLabelList.get(i - 1);
                        range.start = bean1.endIndex;
                        range.end = removeBean.startIndex;
                        bean.ranges.add(range);
                    }
                }
                HtmlLabelRangeBean range = new HtmlLabelRangeBean();
                range.start = lastBean.endIndex;
                range.end = bean.endIndex;
                bean.ranges.add(range);
            } else {
                //表示将要并列添加,那么影响的范围就是自己的角标范围
                HtmlLabelRangeBean range = new HtmlLabelRangeBean();
                range.start = bean.startIndex;
                range.end = bean.endIndex;
                bean.ranges.add(range);
            }
        }
    }


    /**
     * 获取最后一个与当前tag匹配的Bean的位置
     * 从后往前找
     *
     * @param tag
     * @return
     */
    private int getLastLabelByTag(String tag) {
        for (int size = labelBeanList.size(), i = size - 1; i >= 0; i--) {
            if (!TextUtils.isEmpty(tag) &&
                    !TextUtils.isEmpty(labelBeanList.get(i).tag) &&
                    labelBeanList.get(i).tag.equals(tag)) {
                return i;
            }
        }

        return -1;
    }


    private void processAttributes(final XMLReader xmlReader) {
        try {
            Field elementField = xmlReader.getClass().getDeclaredField("theNewElement");
            elementField.setAccessible(true);
            Object element = elementField.get(xmlReader);
            Field attsField = element.getClass().getDeclaredField("theAtts");
            attsField.setAccessible(true);
            Object atts = attsField.get(element);
            Field dataField = atts.getClass().getDeclaredField("data");
            dataField.setAccessible(true);
            String[] data = (String[]) dataField.get(atts);
            Field lengthField = atts.getClass().getDeclaredField("length");
            lengthField.setAccessible(true);
            int len = (Integer) lengthField.get(atts);

            for (int i = 0; i < len; i++) {
                attributes.put(data[i * 5 + 1], data[i * 5 + 4]);
            }
        } catch (Exception e) {

        }
    }

}
