package dawn.com.gankme.mvp.ui.activity;

import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import dawn.com.gankme.R;
import dawn.com.gankme.app.constants.KeyConstant;
import dawn.com.gankme.app.utils.FileUtils;
import dawn.com.gankme.mvp.model.api.service.CommonService;
import dawn.com.gankme.mvp.model.subscriber.DownLoadSubscribe;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/2/8.
 */

public class ShowPictureActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_picture)
    ImageView ivPicture;
    @BindView(R.id.tv_index)
    TextView tvIndex;
    @BindView(R.id.iv_download)
    ImageView ivDownload;
    private ImageLoader imageLoader;
    private AppComponent appComponent;
    private String imgUrl;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        imageLoader = appComponent.imageLoader();
        this.appComponent = appComponent;
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_show_picture;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        imgUrl = getIntent().getStringExtra(KeyConstant.IMG_URL);

        imageLoader.loadImage(appComponent.application(), ImageConfigImpl
                .builder()
                .url(imgUrl)
                .imageView(ivPicture)
                .build());
    }


    @OnClick({R.id.iv_download})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.iv_download:
                appComponent.repositoryManager().obtainRetrofitService(CommonService.class)
                        .download(imgUrl)
                        .observeOn(Schedulers.io())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new DownLoadSubscribe(FileUtils.getSaveImagePath(getApplicationContext()), FileUtils.getFileName(imgUrl)) {
                            @Override
                            public void onProgress(double progress, long downloadByte, long totalByte) {

                            }

                            @Override
                            public void onCompleted(File file) {
                                if (file != null) {
                                    ArmsUtils.snackbarText(getString(R.string.save_picture_success) + file.getAbsolutePath());
                                    MediaScannerConnection.scanFile(getApplicationContext(), new String[]{
                                                    file.getAbsolutePath()},
                                            null, null);
                                }
                            }

                        });

                break;

        }
    }
}
