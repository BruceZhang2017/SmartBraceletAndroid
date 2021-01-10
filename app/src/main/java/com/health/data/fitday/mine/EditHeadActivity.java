package com.health.data.fitday.mine;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.health.data.fitday.utils.SpUtils;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.sinophy.smartbracelet.R;

import java.io.File;
import per.goweii.actionbarex.common.ActionBarCommon;
import per.goweii.actionbarex.common.OnActionBarChildClickListener;

public class EditHeadActivity extends TakePhotoActivity {
    @BindView(R.id.simple_action_bar)
    ActionBarCommon actionBarCommon;

    private CompressConfig compressConfig;

    private CropOptions cropOptions;

    private Uri imageUri;

    @BindView(R.id.iv_head)
    ImageView ivHead;

    private TakePhoto takePhoto;

    private Uri getImageCropUri() {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        return Uri.fromFile(file);
    }

    protected int getLayoutId() {
        return R.layout.activity_edit_head;
    }

    protected void initData() {
        this.takePhoto = getTakePhoto();
        this.cropOptions = (new CropOptions.Builder()).setAspectX(1).setAspectY(1).setWithOwnCrop(false).create();
        CompressConfig compressConfig = (new CompressConfig.Builder()).setMaxSize(51200).setMaxPixel(800).create();
        this.compressConfig = compressConfig;
        this.takePhoto.onEnableCompress(compressConfig, true);
    }

    protected void initView() {
        this.actionBarCommon.setOnLeftIconClickListener(new OnActionBarChildClickListener() {
            public void onClick(View param1View) {
                System.out.println("点击事件");
                        EditHeadActivity.this.finish();
            }
        });
    }

    @OnClick({R.id.tv_save, R.id.tv_photo, R.id.tv_pic, R.id.tv_cancel})
    public void onClick(View paramView) {
        Uri uri;
        switch (paramView.getId()) {
            case R.id.tv_save:
                uri = this.imageUri;
                if (uri != null) {
                    SpUtils.putString((Context) this, "UserHead", uri.toString());
                }
                break;
            case R.id.tv_photo:
                uri = getImageCropUri();
                this.imageUri = uri;
                this.takePhoto.onPickFromGalleryWithCrop(uri, this.cropOptions);
                break;
            case R.id.tv_pic:
                uri = getImageCropUri();
                this.imageUri = uri;
                this.takePhoto.onPickFromCaptureWithCrop(uri, this.cropOptions);
                break;
            case R.id.tv_cancel:

                break;
        }
        finish();
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(getLayoutId());
        setTransparentStatusBar((Activity)this);
        ButterKnife.bind((Activity)this);
        initView();
        initData();
    }

    public void setTransparentStatusBar(Activity activity) {
        //5.0及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            //4.4到5.0
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = activity.getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }

    public void takeCancel() {
        super.takeCancel();
    }

    public void takeFail(TResult paramTResult, String paramString) {
        super.takeFail(paramTResult, paramString);
        Toast.makeText((Context)this, "Error:" + paramString, Toast.LENGTH_SHORT).show();
    }

    public void takeSuccess(TResult paramTResult) {
        super.takeSuccess(paramTResult);
        TImage tImage = paramTResult.getImage();
        Glide.with((Activity)this).load(new File(tImage.getCompressPath())).into(this.ivHead);
    }
}
