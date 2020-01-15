package com.maxxipoint.camera.camera.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Size;
import java.util.Comparator;

/**
 * 根据他们的区域比较两个Size
 */
public class CompareSizesByArea implements Comparator<Size> {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public int compare(Size lhs, Size rhs) {
        // 我们在这里投放，以确保乘法不会溢出
        return Long.signum((long) lhs.getWidth() * lhs.getHeight() -
                (long) rhs.getWidth() * rhs.getHeight());
    }

}
