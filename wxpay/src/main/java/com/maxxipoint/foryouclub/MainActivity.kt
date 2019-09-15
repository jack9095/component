package com.maxxipoint.foryouclub

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.maxxipoint.foryouclub.util.Constants
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.tencent.mm.opensdk.modelpay.PayReq



class MainActivity : AppCompatActivity() {

    lateinit var api: IWXAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun onClick(v: View){

        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID)  // 参数二是申请的APPID
        // 将该app注册到微信
//        api.registerApp("wxa882a980d441c4a4")  // 参数是申请的APPID
//
//        val request = PayReq()
//        request.appId = Constants.APP_ID
//        request.partnerId = "14854067628976432"
//        request.prepayId = "wx1918221941789643309071000"
//        request.packageValue = "Sign=WXPay"
//        request.nonceStr = "UXFpcU2rkJ99ffSR"
//        request.timeStamp = "1563531739"
//        request.sign = "B5EC25FB0796D415FC7251A86628D73A4C8380F66F9F5594F5100C0E19DCA872"
//        val sendReq = api.sendReq(request)  // 发起支付
//        Toast.makeText(this,"正常支付$sendReq",Toast.LENGTH_SHORT).show()

        startActivity(Intent(this,PayActivity::class.java))
    }
}
