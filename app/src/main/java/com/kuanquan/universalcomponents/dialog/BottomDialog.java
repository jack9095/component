package com.kuanquan.universalcomponents.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.base.library.utils.CollectionsUtil;
import com.base.library.utils.glide.invocation.ImageLoaderManager;
import com.kuanquan.universalcomponents.R;

/**
 * 底部弹出的弹框
 */
public class BottomDialog {
    private Context context;
    private Dialog dialog;
    private CardView lLayout_bg;
    private TextView txt_title;   // 谁发起的问卷
    private ImageView icon_view_tag; // 图标
    private TextView content_tx;   // 内容
    private TextView cancel;   // 取消
    private TextView submit;   // 确定
    private EditText mEditText;
    private Display display;

    public BottomDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public BottomDialog builder() {
        View contentView = LayoutInflater.from(context).inflate(
                R.layout.center_dialog_layout, null);

        lLayout_bg = contentView.findViewById(R.id.lLayout_bg);
        txt_title = (TextView) contentView.findViewById(R.id.name_quest);
        icon_view_tag = (ImageView) contentView.findViewById(R.id.icon_view_tag);
        content_tx = (TextView) contentView.findViewById(R.id.content_tx);
        mEditText = (EditText) contentView.findViewById(R.id.edit_text);
        cancel = (TextView) contentView.findViewById(R.id.cancel);
        submit = (TextView) contentView.findViewById(R.id.submit);

        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = context.getResources().getDisplayMetrics().widthPixels;
        layoutParams.height = context.getResources().getDisplayMetrics().heightPixels / 3;
        contentView.setLayoutParams(layoutParams);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        dialog.setCanceledOnTouchOutside(false);

        // 调整透明度的
        WindowManager.LayoutParams lp1 = dialog.getWindow().getAttributes();
        lp1.dimAmount = 0.8f;  // 调整透明度的
        dialog.getWindow().setAttributes(lp1);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

//        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
//                .getWidth() * 0.85), ViewGroup.LayoutParams.WRAP_CONTENT));
        setLayout();
        return this;
    }

    public BottomDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    private void setLayout() {

        submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    public BottomDialog setData(String title, String imageUrl, String content, String shareObj) {
        CollectionsUtil.setTextView(txt_title, title);
        CollectionsUtil.setTextView(content_tx, content);
        CollectionsUtil.setTextView(mEditText, shareObj);
        ImageLoaderManager.getInstance().displayImage(context, imageUrl, icon_view_tag, false);
        return this;
    }

    public void show() {
        dialog.show();
    }
}