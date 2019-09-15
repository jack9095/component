package com.maxxipoint.foryouclub;

import java.io.Serializable;

public class OrderPayBean implements Serializable {

    public String appid; //appid
    public String body; //商品描述
    public String mch_id; //商户ID
    public String nonce_str; //随机字符串
    public String notify_url; //微信通知后台支付结果url
    public String out_trade_no; //我们自己的订单号
    public String spbill_create_ip; //客户端IP
    public int total_fee; //总的支付金额
    public String trade_type; //因为是移动应用 所以是APP
    public String sign; //以上所有参数的MD5签名

}
