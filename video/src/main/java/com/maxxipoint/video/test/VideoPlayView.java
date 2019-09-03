package com.maxxipoint.video.test;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import com.base.library.utils.LogUtil;
import com.maxxipoint.video.R;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class VideoPlayView extends FrameLayout {

    MediaPlayer mediaPlayerBig;
    TextureView textureView;
    ImageView ivPlay;
    ImageView ivChange;
    TextView tvPlayTime;
    TextView tvMaxTime;
    SeekBar seekBar;
    LinearLayout llBar;
    boolean seekbarTouchIsStop = true;
    Disposable disposable;
    boolean isPlay;
    boolean isCompeletion;

    int playStatus;
    Surface mySurface;

    public VideoPlayView(@NonNull Context context) {
        super(context);
        init();
    }

    public VideoPlayView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VideoPlayView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    AppCompatActivity activity;

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    private void init() {
        inflate(getContext(), R.layout.view_video_play, this);
        textureView = findViewById(R.id.texture);
        llBar = findViewById(R.id.ll_bar);
        ivPlay = findViewById(R.id.iv_play);
        ivChange = findViewById(R.id.iv_change_orientation);
        tvPlayTime = findViewById(R.id.tv_play_time);
        tvMaxTime = findViewById(R.id.tv_max_time);
        seekBar = findViewById(R.id.video_seek_bar);

//        setProgressDrawable(bar, R.drawable.ratingbar_drawable_heart);

        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {

                if (surfaceTextureListener != null) {
                    surfaceTextureListener.SurfaceTextureAvailable(surface, width, height);
                }
                bindSurface();
            }


            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                LogUtil.i("Hw", "onSurfaceTextureDestroyed   背上小会而来   的撒发生的发大水发吃的撒旦法大大的");
                if (surfaceTextureListener != null) {
                    surfaceTextureListener.SurfaceTextureDestroy(surface);
                }
//                pause();
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {

            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                seekbarTouchIsStop = false;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekbarTouchIsStop = true;
                if (seekbarTouchIsStop && mediaPlayerBig != null) {
                    mediaPlayerBig.seekTo(seekBar.getProgress());
                }
            }
        });

        ivPlay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlay) {
                    pause();
                } else {
                    play();
                }
            }
        });

        ivChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    VideoFrameUtils.makeUpVideoPlayingSize(VideoPlayView.this, mediaPlayerBig, 90);
                    ivChange.setImageResource(R.drawable.ic_video_change_small);
                    if (videoOnPreparedListener != null) {
                        videoOnPreparedListener.chageOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    }
                } else {
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    VideoFrameUtils.makeUpVideoPlayingSize(VideoPlayView.this, mediaPlayerBig, 0);
                    ivChange.setImageResource(R.drawable.ic_video_change_big);
                    videoOnPreparedListener.chageOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
            }
        });
    }

    public void bindSurface() {
        if (mediaPlayerBig != null) {
            mySurface = new Surface(textureView.getSurfaceTexture());
            mediaPlayerBig.setSurface(mySurface);
        }
    }

    public void unBindSurface() {
        if (mediaPlayerBig != null) {
            mediaPlayerBig.setSurface(null);
        }
    }


    public void createMedia() {
        mediaPlayerBig = new MediaPlayer();
    }

    public void setMedia(MediaPlayer media) {
        this.mediaPlayerBig = media;
    }


    public MediaPlayer getMediaPlayerBig() {
        return mediaPlayerBig;
    }

    public void initVideo(String url) {
        try {
            mediaPlayerBig.reset();
            mediaPlayerBig.setAudioStreamType(AudioManager.STREAM_MUSIC);
            String dataurl = Environment.getExternalStorageDirectory() + "/1234.mp4";
            mediaPlayerBig.setDataSource(url);
            mediaPlayerBig.prepareAsync();
            mediaPlayerBig.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Prepared(mp);
                }
            });

            mediaPlayerBig.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
//                    mediaPlayerBig;
                    pause();
                    return false;
                }
            });

            mediaPlayerBig.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    setCompeletion(mp);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Prepared(MediaPlayer mp) {
        if (videoOnPreparedListener != null) {
            videoOnPreparedListener.OnPrepared(mp);
        }
        if (disposable == null) {
            seekBar.setMax(mp.getDuration());
            setPlayProgress();
        }
        showBar();
        tvMaxTime.setText(geteplayTime(mp.getDuration() / 1000));
        if (mp.getVideoWidth() > mp.getVideoHeight()) {
            ivChange.setVisibility(View.VISIBLE);
        }
    }

    public void setCompeletion(MediaPlayer mp) {
        isPlay = false;
        if (videoOnPreparedListener != null) {
            videoOnPreparedListener.onCompletion(mp);
        }
        ivPlay.setImageResource(R.drawable.ic_video_view_play);
    }

    public String geteplayTime(int time) {
        int second = time % 60;
        int minute = time / 60;
        String strMinute = minute > 9 ? (minute + "") : ("0" + minute);
        String strSecond = second > 9 ? (second + "") : ("0" + second);
        return strMinute + ":" + strSecond;
    }

    public void play() {

        if (mediaPlayerBig != null) {
            if (isCompeletion) {
                mediaPlayerBig.seekTo(0);
                isCompeletion = false;
            }
            try {
                mediaPlayerBig.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }


            isPlay = true;
        }
        ivPlay.setImageResource(R.drawable.ic_video_view_pause);
        playStatus = 0; // 开始
        if (videoOnPreparedListener != null) {
            videoOnPreparedListener.setPlayStatus(playStatus);
        }
    }


    public void pause() {
        playStatus = 1; // 暂停
        if (videoOnPreparedListener != null) {
            videoOnPreparedListener.setPlayStatus(playStatus);
        }
        try {
            if (mediaPlayerBig != null && mediaPlayerBig.isPlaying()) {
                mediaPlayerBig.pause();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        isPlay = false;
        ivPlay.setImageResource(R.drawable.ic_video_view_play);
    }

    public void reStart() {
        if (mediaPlayerBig != null && !mediaPlayerBig.isPlaying()) {
            mediaPlayerBig.start();
        }

    }

    public void seekTo(int progress) {
        if (mediaPlayerBig != null) {
            mediaPlayerBig.seekTo(progress);
        }
    }

    public int getPlayStatus() {
        return playStatus;
    }

    public void setPlayProgress() {
        Observable.interval(0, 400, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        if (mediaPlayerBig == null) {
                            LogUtil.e("error", "seekPlayProgress mMediaPlayer == null");
                            return;
                        }
                        if (isPlay) {
                            //开启线程定时获取当前播放进度
                            int currentposition;
                            try {
                                currentposition = mediaPlayerBig.getCurrentPosition();
                                tvPlayTime.setText(geteplayTime(currentposition / 1000));
                                if (seekbarTouchIsStop) {
                                    seekBar.setProgress(currentposition);
                                }
//                                LOG.i("hw", seekBar.getMax() + "  SSSSSSS   " + currentposition);
                            } catch (Exception e) {
                                currentposition = 0;
                                e.printStackTrace();
                            }
                            //给主线程发消息更新seekbar进度
                            if (videoOnPreparedListener != null) {
                                videoOnPreparedListener.onPlayProgressTime(mediaPlayerBig, currentposition);
                            } else {
                                LogUtil.e("error", "seekPlayProgress onCompletionListener == null");
                            }
                        }
                    }
                });
    }

    public void setProgress(int currentposition) {
        try {
            tvPlayTime.setText(geteplayTime(currentposition / 1000));
            if (seekbarTouchIsStop) {
                seekBar.setProgress(currentposition);
            }
            LogUtil.i("hw", seekBar.getMax() + "  SSSSSSS   " + currentposition);
        } catch (Exception e) {
            currentposition = 0;
            e.printStackTrace();
        }
    }


    private boolean isShowBar;

    public void showBar() {
        isShowBar = true;
        llBar.setVisibility(VISIBLE);
    }

    public void hideBar() {
        isShowBar = false;
        llBar.setVisibility(INVISIBLE);
    }

    public boolean isShowBar() {
        return isShowBar;
    }

    public interface VideoOnPreparedListener {

        void OnPrepared(MediaPlayer mp);

        void onCompletion(MediaPlayer mp);

        void onPlayProgressTime(MediaPlayer mp, long time);

        void chageOrientation(int orientation);

        void setPlayStatus(int status);

    }


    public interface SurfaceTextureListener {

        void SurfaceTextureAvailable(SurfaceTexture surface, int width, int height);

        void SurfaceTextureDestroy(SurfaceTexture surface);

    }

    SurfaceTextureListener surfaceTextureListener;

    public void setSurfaceTextureListener(SurfaceTextureListener surfaceTextureListener) {
        this.surfaceTextureListener = surfaceTextureListener;
    }

    VideoOnPreparedListener videoOnPreparedListener;

    public void setVideoOnPreparedListener(VideoOnPreparedListener videoOnPreparedListener) {
        this.videoOnPreparedListener = videoOnPreparedListener;
    }


    public void onRelealse() {
        if (mediaPlayerBig != null) {
            mediaPlayerBig.stop();
            mediaPlayerBig.release();
        }
        unsubscribe();
    }

    public void unsubscribe() {
        if (disposable != null) {
            disposable.dispose();
        }

    }

}
