package com.maxxipoint.foryouclub;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.maxxipoint.foryouclub.util.Constants;
import com.maxxipoint.foryouclub.util.Util;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.*;

public class PayActivity extends AppCompatActivity {

    private IWXAPI api;
    private OrderPayBean orderPaybean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        // 商户APP工程中引入微信JAR包，调用API前，需要先向微信注册您的APPID
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);  // 参数二是申请的APPID

        // 将该app注册到微信
        api.registerApp(Constants.APP_ID);  // 参数是申请的APPID
        orderPaybean = new OrderPayBean();
        initData();
//        if (!TextUtils.isEmpty(IpUtil.getIPAddress(this))) {
//            String ipAddress = IpUtil.getIPAddress(this);
//            String s = ipAddress.substring(1,ipAddress.length()).trim();
//            Log.e("手机当前ip地址 = ", s);
//        }
//        Log.e("手机当前ip地址", IpUtil.getIPAddress(this));
    }

    private void initData() {
        orderPaybean.appid = Constants.APP_ID;
        orderPaybean.body = "微信支付";
        orderPaybean.mch_id = Constants.APP_TENANT;  // 商户号
        orderPaybean.nonce_str = Constants.genNonceStr();  // 获得随机字符串
        orderPaybean.notify_url = "https://www.google.com"; // //微信通知后台支付结果url
        orderPaybean.total_fee = 1;
        orderPaybean.trade_type = "APP";
        orderPaybean.out_trade_no = Constants.getNonceStr();
        orderPaybean.spbill_create_ip = "196.168.1.1";

        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
        parameters.put("appid", orderPaybean.appid);
        parameters.put("body", orderPaybean.body);
        parameters.put("mch_id", orderPaybean.mch_id);
        parameters.put("nonce_str", orderPaybean.nonce_str);
        parameters.put("notify_url", orderPaybean.notify_url);
        parameters.put("out_trade_no", orderPaybean.out_trade_no);
        parameters.put("total_fee", orderPaybean.total_fee);
        parameters.put("trade_type", orderPaybean.trade_type);
        parameters.put("spbill_create_ip", orderPaybean.spbill_create_ip);
        parameters.put("sign", Constants.createSign("UTF-8", parameters, Constants.APP_TENANT_SIGN));//传入签名好的参数值


        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<xml>");
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            xmlBuilder.append("<").append(k).append(">");
            xmlBuilder.append(v);
            xmlBuilder.append("</").append(k).append(">");
        }
        xmlBuilder.append("</xml>");
        Log.e("TAG",xmlBuilder.toString());
        System.out.println(xmlBuilder.toString());
        try {
            // 这一步非常重要，不这样转换编码的话，传递中文就会报“签名错误”，这是很多人都会遇到的错误。
            new GetPrepayId(new String(xmlBuilder.toString().getBytes(), "ISO8859-1")).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class GetPrepayId extends AsyncTask {
        String str;

        public GetPrepayId(String str) {
            this.str = str;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] params) {
            String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";

            Log.e("参数",str);

            byte[] buf = Util.httpPost(url, str);   // 发起订单向微信获取预支付交易会话标识prepay_id
            String content = null;

            if (buf != null) {
                content = new String(buf);
            }
//            String content = new String(buf);

            Log.e("返回数据", ">>>>" + content);

            Map<String, String> map = Constants.xmlToMap(content);
            String nonceStr = Constants.genNonceStr();
            String timeStamp = String.valueOf(Constants.genTimeStamp());
            SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
            parameters.put("appid", orderPaybean.appid);
            parameters.put("partnerid", orderPaybean.mch_id);
            parameters.put("prepayid", map.get("prepay_id"));
            parameters.put("package", "Sign=WXPay");
            parameters.put("noncestr", nonceStr);
            parameters.put("timestamp", timeStamp);

            PayReq request = new PayReq();
            request.appId = orderPaybean.appid;
            request.partnerId = orderPaybean.mch_id;
            request.prepayId = map.get("prepay_id");
            request.packageValue = "Sign=WXPay";
            request.nonceStr = nonceStr;
            request.timeStamp = timeStamp;
            request.sign = Constants.createSign("UTF-8", parameters, Constants.APP_TENANT_SIGN);
            api.sendReq(request);  // 发起支付
            return content;
        }
    }
}
