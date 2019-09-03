package com.maxxipoint.video.test;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.constraint.ConstraintLayout;
import android.util.DisplayMetrics;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import com.base.library.utils.LogUtil;

public class VideoFrameUtils {

    public static void makeUpVideoPlayingSize(View textureView, MediaPlayer mediaPlayer, int rotation) {
        int screenWidth = 0, screenHeight = 0, videoWidth, videoHeight, displayWidth, displayHeight;
        float screenAspectRatio, videoAspectRatio;

//        if (rotation == 0) {
//            screenWidth =BaseActivity.mScreenWidth;//            screenWidth =BaseActivity.mScreenWidth;
//            screenHeight = BaseActivity.mScreenHeight;//textureView.getHeight();//
//        } else {
            screenWidth = textureView.getHeight(); //
            screenHeight = textureView.getWidth();
//        }

//        if (ImmersionBar.getNavigationBarHeight())
        screenAspectRatio = (float) screenHeight / screenWidth;
        LogUtil.i("makeUpVideoPlayingSize", "Screen size: " + screenWidth + " × " + screenHeight);
        videoWidth = mediaPlayer.getVideoWidth();
        videoHeight = mediaPlayer.getVideoHeight();
        videoAspectRatio = (float) videoHeight / videoWidth;
        LogUtil.i("makeUpVideoPlayingSize", "Video size: " + videoWidth + " × " + videoHeight);

        if (screenAspectRatio > videoAspectRatio) {
            displayWidth = screenWidth;
            displayHeight = (int) ((float) screenWidth / videoWidth * videoHeight);
        } else {
            displayWidth = (int) ((float) screenHeight / videoHeight * videoWidth);
            displayHeight = screenHeight;
        }
//        if (displayWidth > displayHeight) {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) textureView.getLayoutParams();
            layoutParams.width = displayWidth;
            layoutParams.height = displayHeight;
            textureView.requestLayout();
//        }
    }



    public static void makeUpVideoPlayingSize(View v, int[] videoSize ,int[] layoutSize, int rotation) {
        int screenWidth, screenHeight, videoWidth, videoHeight, displayWidth, displayHeight;
        float screenAspectRatio, videoAspectRatio;

        if (rotation == 0) {
            screenWidth =layoutSize[0]; //  BaseActivity.mScreenWidth;//            screenWidth =BaseActivity.mScreenWidth;
            screenHeight =layoutSize[1];//; BaseActivity.mScreenHeight;//textureView.getHeight();//
        } else {
            screenWidth = layoutSize[1];//BaseActivity.mScreenHeight;//textureView.getHeight();//
            screenHeight = layoutSize[0];//BaseActivity.mScreenWidth;//textureView.getWidth();
        }
//        if (ImmersionBar.getNavigationBarHeight())
        screenAspectRatio = (float) screenHeight / screenWidth;
        LogUtil.i("makeUpVideoPlayingSize", "Screen size: " + screenWidth + " × " + screenHeight);
        videoWidth =videoSize[0];
        videoHeight =videoSize[1];
        videoAspectRatio = (float) videoHeight / videoWidth;
        LogUtil.i("makeUpVideoPlayingSize", "Video size: " + videoWidth + " × " + videoHeight);

        if (screenAspectRatio > videoAspectRatio) {
            displayWidth = screenWidth;
            displayHeight = (int) ((float) screenWidth / videoWidth * videoHeight);
        } else {
            displayWidth = (int) ((float) screenHeight / videoHeight * videoWidth);
            displayHeight = screenHeight;
        }
//        if (displayWidth > displayHeight) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
        layoutParams.width = displayWidth;
        layoutParams.height = displayHeight;
        v.requestLayout();
//        }
    }

}
