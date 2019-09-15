package com.maxxipoint.foryouclub.wxapi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.maxxipoint.foryouclub.R;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 把这个activity设置成dialog的样式，效果会更好
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxpay_entry);

        api = WXAPIFactory.createWXAPI(this, "wxa882a980d441c4a4");
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    /**
     * 微信支付成功或失败取消支付的回调方法
     * @param resp
     */
    @Override
    public void onResp(BaseResp resp) {
        Log.e("WXPayEntryActivity", "onPayFinish, errCode = " + resp.errCode + "");
        Log.e("WXPayEntryActivity", "onPayFinish, errStr = " + resp.errStr + "");
        Log.e("WXPayEntryActivity", "onPayFinish, transaction = " + resp.transaction + "");
        Log.e("WXPayEntryActivity", "onPayFinish, openId = " + resp.openId + "");
        Log.e("WXPayEntryActivity", "onPayFinish, checkArgs = " + resp.checkArgs() + "");
        Log.e("WXPayEntryActivity", "onPayFinish, getType = " + resp.getType() + "");

//      if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//          AlertDialog.Builder builder = new AlertDialog.Builder(this);
//          builder.setTitle(R.string.app_tip);
//          builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));
//          builder.show();
//      }

        System.out.println(resp.errCode);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) {   // 0 成功  展示成功页面
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("微信支付结果");
                builder.setMessage("支付订单成功！");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.show();
            } else if (resp.errCode == -1) {  // -1 错误  可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
                Toast.makeText(WXPayEntryActivity.this, "支付出错：" + resp.errStr, Toast.LENGTH_SHORT)
                        .show();
                finish();
            } else if (resp.errCode == -2) {  // -2 用户取消    无需处理。发生场景：用户不支付了，点击取消，返回APP。
                Toast.makeText(WXPayEntryActivity.this, "取消支付：" + resp.errStr, Toast.LENGTH_SHORT)
                        .show();
                finish();
            }
        }
    }
}
