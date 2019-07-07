package com.kuanquan.mine.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.*;
import android.widget.TextView;
import com.base.library.base.EventCenter;
import com.kuanquan.mine.R;
import org.greenrobot.eventbus.EventBus;

/**
 * 底部弹出的弹框
 */
public class AlbumBottomDialog implements View.OnClickListener{
    private Context context;
    private Dialog dialog;
    private TextView mine_dialog_dysfunction;
    private TextView mine_dialog_proposal;
    private TextView mine_dialog_other;

    private Display display;

    public AlbumBottomDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public AlbumBottomDialog builder() {
        View contentView = LayoutInflater.from(context).inflate(
                R.layout.album_bottom_dialog_layout, null);

        mine_dialog_dysfunction = contentView.findViewById(R.id.mine_dialog_dysfunction);
        mine_dialog_dysfunction.setOnClickListener(this);
        mine_dialog_proposal = contentView.findViewById(R.id.mine_dialog_proposal);
        mine_dialog_proposal.setOnClickListener(this);
        mine_dialog_other = contentView.findViewById(R.id.mine_dialog_other);
        mine_dialog_other.setOnClickListener(this);

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

    public AlbumBottomDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    private void setLayout() {

    }

    public AlbumBottomDialog setData(String title, String imageUrl, String content, String shareObj) {

//        ImageLoaderManager.getInstance().displayImage(context, imageUrl, icon_view_tag, false);
        return this;
    }

    public void show() {
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        int content = 0;
        if (v.getId() == R.id.mine_dialog_dysfunction) {   // 拍照
            content = 1;
        } else if (v.getId() == R.id.mine_dialog_proposal){  // 相册
            content = 2;
        } else if (v.getId() == R.id.mine_dialog_other){  // 取消
            content = 3;
        }
        EventBus.getDefault().post(new EventCenter<Object>("AlbumBottomDialog",content));
        dialog.dismiss();
    }
}