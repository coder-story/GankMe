package dawn.com.gankme.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.transition.AutoTransition;
import android.support.transition.TransitionManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.Preconditions;
import com.paginate.Paginate;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.simple.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import dawn.com.gankme.R;
import dawn.com.gankme.app.constants.TagConstant;
import dawn.com.gankme.di.component.DaggerGankListComponent;
import dawn.com.gankme.di.module.GankListModule;
import dawn.com.gankme.mvp.contract.GankListContract;
import dawn.com.gankme.mvp.model.entity.Daily;
import dawn.com.gankme.mvp.presenter.GankListPresenter;
import dawn.com.gankme.mvp.ui.BaseSupportFragment;
import dawn.com.gankme.mvp.ui.adapter.HomeAdapter;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * Created by Administrator on 2018/1/29.
 */

public class GankListFragment extends BaseSupportFragment<GankListPresenter> implements GankListContract.View, SwipeRefreshLayout.OnRefreshListener, DefaultAdapter.OnRecyclerViewItemClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;



    @Inject
    RxPermissions mRxPermissions;
    @Inject
    RxErrorHandler handler;
    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    RecyclerView.Adapter mAdapter;

    private Paginate mPaginate;
    private boolean isLoadingMore;
    private boolean isExpand;
    private AutoTransition mSet;
    private int scrollY;
    private int itemHeight;


    public static GankListFragment newInstance() {
        return new GankListFragment();
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {

        DaggerGankListComponent
                .builder()
                .appComponent(appComponent)
                .gankListModule(new GankListModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected boolean isSwipeBack() {
        return   false;
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_gank_list, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initRecyclerView();
        mRecyclerView.setAdapter(mAdapter);

        ((HomeAdapter) mAdapter).setOnItemClickListener(this);
        initPaginate();

    }




//    private void expand() {
//        //设置伸展状态时的布局
//        tvSearch.setText(R.string.search_expand);
//        FrameLayout.LayoutParams LayoutParams = (FrameLayout.LayoutParams) mSearchLayout.getLayoutParams();
//        LayoutParams.width = LayoutParams.MATCH_PARENT;
//        LayoutParams.setMargins(ArmsUtils.dip2px(getContext(), 10), ArmsUtils.dip2px(getContext(), 10),
//                ArmsUtils.dip2px(getContext(), 10), ArmsUtils.dip2px(getContext(), 10));
//        mSearchLayout.setLayoutParams(LayoutParams);
//        //开始动画
//        beginDelayedTransition(mSearchLayout);
//    }
//
//    private void reduce() {
//        //设置收缩状态时的布局
//        tvSearch.setText(R.string.search);
//        FrameLayout.LayoutParams LayoutParams = (FrameLayout.LayoutParams) mSearchLayout.getLayoutParams();
//        LayoutParams.width = ArmsUtils.dip2px(getContext(), 80);
//        LayoutParams.setMargins(ArmsUtils.dip2px(getContext(), 10), ArmsUtils.dip2px(getContext(), 10),
//                ArmsUtils.dip2px(getContext(), 10), ArmsUtils.dip2px(getContext(), 10));
//        mSearchLayout.setLayoutParams(LayoutParams);
//        //开始动画
//        beginDelayedTransition(mSearchLayout);
//    }

    void beginDelayedTransition(ViewGroup view) {
        mSet = new AutoTransition();
        mSet.setDuration(300);
        TransitionManager.beginDelayedTransition(view, mSet);
    }


    /**
     * 初始化Paginate,用于加载更多
     */
    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {
                    mPresenter.requestgetRecently(false);
                }

                @Override
                public boolean isLoading() {
                    return isLoadingMore;
                }

                @Override
                public boolean hasLoadedAllItems() {
                    return false;
                }
            };

            mPaginate = Paginate.with(mRecyclerView, callbacks)
                    .setLoadingTriggerThreshold(0)
                    .build();
            mPaginate.setHasMoreDataToLoad(false);
        }
    }

    private void initRecyclerView() {

        mSwipeRefreshLayout.setOnRefreshListener(this);
        ArmsUtils.configRecyclerView(mRecyclerView, mLayoutManager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                scrollY += dy;
//
//                if (scrollY >= 650 && !isExpand) {
//                    expand();
//                    isExpand = true;
//
//                }
//
//                else if (scrollY <= 400 && isExpand) {
//                    reduce();
//                    isExpand = false;
 //               }
                super.onScrolled(recyclerView, dx, dy);

            }
        });

    }

    @Override
    public void setData(Object data) {
        mPaginate.setHasMoreDataToLoad(false);
    }

    @Override
    public void showLoading() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showMessage(String message) {
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(Intent intent) {
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        getActivity().finish();
    }

    @Override
    public void startLoadMore() {
        isLoadingMore = true;
    }

    @Override
    public void endLoadMore() {
        isLoadingMore = false;
    }

    @Override
    public RxPermissions getRxPermissions() {
        return mRxPermissions;
    }

    @Override
    public void onRefresh() {
        mPresenter.requestgetRecently(true);
    }

    @Override
    public void onDestroy() {
        DefaultAdapter.releaseAllHolder(mRecyclerView);//super.onDestroy()之后会unbind,所有view被置为null,所以必须在之前调用
        super.onDestroy();
        this.mRxPermissions = null;
        this.mPaginate = null;
    }

    @Override
    public void onItemClick(View view, int viewType, Object data, int position) {
        Preconditions.checkNotNull(data,"data cannot be null");
        String date = ((Daily) data).getDate();
        String imgUrl = ((Daily) data).getImgUrl();
        String[] dates = null;
        if (date.contains("-")) {
            dates = date.split("-");
        } else if (date.contains("/")) {
            dates = date.split("/");
        }
        EventBus.getDefault().post(true, TagConstant.HIDE_BOTTOM_TAG);
        start(DailyDetailFragment.newInstance(imgUrl, dates[0], dates[1], dates[2]));
    }



//    @OnClick(R.id.ll_search)
//    public void onViewClicked() {
//        start(SearchFragment.newInstance());
//    }


}
