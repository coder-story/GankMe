package dawn.com.gankme.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.style.Wave;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;

import javax.inject.Inject;

import butterknife.BindView;
import dawn.com.gankme.R;
import dawn.com.gankme.di.component.DaggerDailyDetailComponent;
import dawn.com.gankme.di.module.DailyDetailModule;
import dawn.com.gankme.mvp.contract.DailyContract;
import dawn.com.gankme.mvp.model.entity.GanHuoData;
import dawn.com.gankme.mvp.presenter.DailyDetailPresenter;
import dawn.com.gankme.mvp.ui.BaseSupportFragment;
import dawn.com.gankme.mvp.ui.activity.ShowPictureActivity;
import dawn.com.gankme.mvp.ui.adapter.DailyDetailAdapter;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * Created by Administrator on 2018/2/6.
 */

public class DailyDetailFragment extends BaseSupportFragment<DailyDetailPresenter> implements DailyContract.View, DefaultAdapter.OnRecyclerViewItemClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.spin_kit)
    SpinKitView spinKitView;

    @Inject
    RxErrorHandler handler;
    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    RecyclerView.Adapter mAdapter;

    private String date;
    private ImageLoader imageLoader;
    private String imgUrl;


    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerDailyDetailComponent.builder()
                .appComponent(appComponent)
                .dailyDetailModule(new DailyDetailModule(this))
                .build()
                .inject(this);
        imageLoader = appComponent.imageLoader();
    }

    public static DailyDetailFragment newInstance(String imgUrl, String year, String mouth, String day) {

        Bundle args = new Bundle();
        args.putString("url", imgUrl);
        args.putString("year", year);
        args.putString("month", mouth);
        args.putString("day", day);
        DailyDetailFragment fragment = new DailyDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_daily, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        date = getArguments().getString("year") + "/"
                + getArguments().getString("month") + "/"
                + getArguments().getString("day");
        imgUrl = getArguments().getString("url");

        setNavigationOnClickListener(toolbar);
        initRecyclerView();
        mPresenter.requestData(date);
        mRecyclerView.setAdapter(mAdapter);

        ((DailyDetailAdapter) mAdapter).setOnItemClickListener(this);

    }

    private void initHeaderImg() {
        imageLoader.loadImage(getActivity(),
                ImageConfigImpl
                        .builder()
                        .url(imgUrl)
                        .imageView(ivImage)
                        .build());
        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent  =new Intent(getActivity(), ShowPictureActivity.class);
                intent.putExtra("imgUrl",imgUrl);
                ArmsUtils.startActivity(getActivity(),intent);
//                if (mPresenter != null && imgUrl != null) {
//                    mPresenter.downLoadImg(imgUrl);
//
//                }
            }
        });


    }


    private void initRecyclerView() {
        ArmsUtils.configRecyclerView(mRecyclerView, mLayoutManager);
    }

    @Override
    public void setData(Object data) {
        initHeaderImg();
    }


    @Override
    public void showLoading() {
        Wave wave = new Wave();
        progressBar.setIndeterminateDrawable(wave);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        spinKitView.setVisibility(View.GONE);
        toolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @Override
    public void onDestroy() {
        DefaultAdapter.releaseAllHolder(mRecyclerView);//super.onDestroy()之后会unbind,所有view被置为null,所以必须在之前调用
        progressBar = null;
        spinKitView = null;
        super.onDestroy();

    }
    @Override
    public boolean onBackPressedSupport() {
        pop();
        return true;
    }

    @Override
    protected boolean isSwipeBack() {
        return   true;
    }

    @Override
    public void onItemClick(View view, int viewType, Object data, int position) {
        if (data instanceof GanHuoData) {
            String url = ((GanHuoData) data).getUrl();
            String title = ((GanHuoData) data).getDesc();
            start(WebFragment.newInstance(url, title));

        }
    }
}
