package com.base.library.h5;

import android.webkit.JavascriptInterface;

import com.base.library.base.constant.SpUtils;
import com.base.library.utils.LogUtil;
import com.base.library.utils.SharedPreferencesUtils;

/**
 * Created by fei.wang on 2019/5/14.
 *
 */
public class JSInterface {

    public JSInterface(OnClickH5Listener mOnClickH5Listener) {
        this.mOnClickH5Listener = mOnClickH5Listener;
    }

    // 测试 接收H5传递过来的消息
    @JavascriptInterface
    public void showToast(String arg){
        LogUtil.e("js数据= ",arg);
    }

    // 给H5传递工号过去
    @JavascriptInterface
    public String getWorkNum(){
        LogUtil.e("js数据= ");
        return SharedPreferencesUtils.getSharePrefString(SpUtils.TOKEN);
    }

    @JavascriptInterface
    public void closePage(){
        LogUtil.e("H5 回调关闭页面");
        mOnClickH5Listener.closePage();
    }

    // 接收从h5传递过来的数据
    @JavascriptInterface
    public void share(String url,String content,String title, String picUrl,String shareWord){
        LogUtil.e("H5 分享回调数据：","url: " + url
                                            + "\n" + "content:" + content
                                            + "\n" + "title:" + title
                                            + "\n" + "picUrl:" + picUrl
                                            + "\n" + "shareWord:" + shareWord
                                            );
        mOnClickH5Listener.share(url, content, title, picUrl, shareWord);
    }

    private OnClickH5Listener mOnClickH5Listener;
    public interface OnClickH5Listener{
        void closePage();
        void share(String url,String content,String title, String picUrl,String shareWord);
    }
}
