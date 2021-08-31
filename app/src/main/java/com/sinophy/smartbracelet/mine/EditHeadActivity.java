package com.sinophy.smartbracelet.mine;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.sinophy.smartbracelet.utils.SpUtils;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.shehuan.niv.NiceImageView;
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

    @BindView(R.id.iv_user_head)
    NiceImageView ivHead;

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
        this.cropOptions = (new CropOptions.Builder()).setAspectX(1).setAspectY(1).setWithOwnCrop(true).create();
        CompressConfig compressConfig = (new CompressConfig.Builder()).setMaxSize(200 * 1024).setMaxPixel(1200).create();
        this.compressConfig = compressConfig;
        this.takePhoto.onEnableCompress(compressConfig, false);
    }

    protected void initView() {
        ButterKnife.bind(this);
        this.actionBarCommon.setOnLeftIconClickListener(new OnActionBarChildClickListener() {
            public void onClick(View param1View) {
                EditHeadActivity.this.finish();
            }
        });
        String imagePath = SpUtils.getString(this, "UserHead");
        if(imagePath!=null) {
            Uri uri = Uri.parse(imagePath);
            Bitmap bm = getBitmapFromUri(this, uri);
            ivHead.setImageBitmap(bm);
        }
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
                finish();
                break;
            case R.id.tv_photo:
                uri = getImageCropUri();
                this.imageUri = uri;
                this.takePhoto.onPickFromCaptureWithCrop(uri, this.cropOptions);
                break;
            case R.id.tv_pic:
                uri = getImageCropUri();
                this.imageUri = uri;
                this.takePhoto.onPickFromGalleryWithCrop(uri, this.cropOptions);
                break;
            case R.id.tv_cancel:
                finish();
                break;
        }

    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(getLayoutId());
        initView();
        initData();
    }

    public void takeCancel() {
        super.takeCancel();
    }

    public void takeFail(TResult paramTResult, String paramString) {
        super.takeFail(paramTResult, paramString);
        Toast.makeText((Context)this, "Error:" + paramString, Toast.LENGTH_SHORT).show();
    }

    public void takeSuccess(TResult data) {
        super.takeSuccess(data);
        System.out.println("获取图片成功");
        TImage tImage = data.getImage();
        Glide.with(this).load(new File(tImage.getCompressPath())).into(this.ivHead);
    }

    private Bitmap getBitmapFromUri(Context context, Uri uri) {
        Bitmap bitmap = null;
        try {
            // 读取uri所在的图片
            bitmap = MediaStore.Images.Media.getBitmap(
                    context.getContentResolver(), uri);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
