package com.maxxipoint.foryouclub.util;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;

public class Constants {
    // APP_ID 替换为你的应用从官方网站申请到的合法appId
    public static final String APP_ID = "xxxxxxxxx";

    // 微信支付商户号
    public static final String APP_TENANT = "14854067628976432";

    // 应用签名
//    public static final String APP_TENANT_SIGN = "YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYyy";

    // 秘钥
    public static final String APP_TENANT_SIGN = "xxxxxxxxxxxxxxxxxxxxxxxxx";


    //**********************************************************************************************************************************

    /**
     * 微信支付签名算法sign
     * @param characterEncoding 签名编码（UTF-8)
     * @param parameters 要签名的参数的集合
     * @param key 商户自己设置的key
     */
    @SuppressWarnings("unchecked")
    public static String createSign(String characterEncoding,SortedMap<Object,Object> parameters, String key){
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if(null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + key);
        System.out.println(sb.toString());
        String sign = WxMd5.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        System.out.println(sign);
        return sign;
    }

    /**
     * 获取时间戳
     * @return
     */
    public static long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 获得随机字符串
     * @return
     */
    public static String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    // 生成随机号，防重复
    public static String getNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000))
                .getBytes());
    }

    public static Map<String, String> xmlToMap(String xmlstr) {
        Map<String, String> map = new HashMap<>();

        try {
            SAXReader reader = new SAXReader();
            InputStream ins = new ByteArrayInputStream(xmlstr.getBytes("UTF-8"));
            Document doc = reader.read(ins);
            Element root = doc.getRootElement();

            List<Element> list = root.elements();

            for (Element e : list) {
                map.put(e.getName(), e.getText());
            }
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}