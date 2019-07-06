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
    private TextView mine_dialog_dysfunction;
    private TextView mine_dialog_proposal;
    private TextView mine_dialog_other;

    private Display display;

    public BottomDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public BottomDialog builder() {
        View contentView = LayoutInflater.from(context).inflate(
                R.layout.bottom_dialog_layout, null);

        mine_dialog_dysfunction = contentView.findViewById(R.id.mine_dialog_dysfunction);
        mine_dialog_proposal = contentView.findViewById(R.id.mine_dialog_proposal);
        mine_dialog_other = contentView.findViewById(R.id.mine_dialog_other);

        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = context.getResources().getDisplayMetrics().widthPixels;
//        layoutParams.height = context.getResources().getDisplayMetrics().heightPixels / 3;
        contentView.setLayoutParams(layoutParams);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        dialog.setCanceledOnTouchOutside(true);

        // 调整透明度的
        WindowManager.LayoutParams lp1 = dialog.getWindow().getAttributes();
        lp1.dimAmount = 0.5f;  // 调整透明度的
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

    }

    public BottomDialog setData(String title, String imageUrl, String content, String shareObj) {

//        ImageLoaderManager.getInstance().displayImage(context, imageUrl, icon_view_tag, false);
        return this;
    }

    public void show() {
        dialog.show();
    }
}