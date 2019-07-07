package com.kuanquan.mine

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.View
import com.base.library.base.BaseViewModelActivity
import com.base.library.base.EventCenter
import com.base.library.utils.GsonUtils
import com.base.library.utils.LogUtil
import com.kuanquan.mine.dialog.AlbumBottomDialog
import com.kuanquan.mine.viewmodel.MineViewModel
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_mine.*
import org.greenrobot.eventbus.Subscribe
import com.luck.picture.lib.entity.LocalMedia



/**
 * https://github.com/LuckSiege/PictureSelector  图片裁剪选择框架
 * 我的模块的入口
 */
class MineActivity : BaseViewModelActivity<MineViewModel>() {
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.jump_album -> {
                AlbumBottomDialog(this).builder().show()
            }
        }
    }

    override fun createViewModel(): MineViewModel {
        return createViewModel(this,MineViewModel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_mine
    }

    override fun initView() {
        super.initView()
        jump_album.setOnClickListener(this)
        requestPermissions()
    }

    // 动态权限请求
    @SuppressLint("CheckResult")
    private fun requestPermissions() {
        val rxPermissions = RxPermissions(this)
        val permissions = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        )
        val subscribe = rxPermissions.request(*permissions).subscribe(Consumer<Boolean> {
            //                if (aBoolean) {
            //                    showToast("同意权限");
            //                } else {
            //                    showToast("拒绝权限");
            //                }
        })
    }

    override fun initData() {

    }

    override fun isBindEventBusHere(): Boolean {
        return true
    }

    @Subscribe
    fun onEventThread(event: EventCenter<Any>){
        when(event.type){
            "AlbumBottomDialog" -> {
                when(event.`object` as Int){
                    1 -> {
                        PictureSelector.create(this@MineActivity)
                            .openGallery(PictureMimeType.ofImage())
                            .maxSelectNum(4)// 最大图片选择数量 int
                            .previewImage(true)// 是否可预览图片 true or false
                            .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                            .enableCrop(true)// 是否裁剪 true or false
                            .compress(true)// 是否压缩 true or false
                            .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                            .circleDimmedLayer(true)// 是否圆形裁剪 true or false
                            .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                            .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                            .minimumCompressSize(100)// 小于100kb的图片不压缩
                            .rotateEnabled(false) // 裁剪是否可旋转图片 true or false
                            .scaleEnabled(false)// 裁剪是否可放大缩小图片 true or false
                            .forResult(PictureConfig.CHOOSE_REQUEST)
                    }
                    2 -> {

                    }
                    3 -> {

                    }
                }
            }
        }
    }

    // 图片结果回调
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PictureConfig.CHOOSE_REQUEST -> {
                    // 图片、视频、音频选择结果回调
                    val selectList = PictureSelector.obtainMultipleResult(data)
                    LogUtil.e("图片地址 = ",GsonUtils.toJson(selectList))
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
//                    adapter.setList(selectList)
//                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun dataObserver() {

    }

}
