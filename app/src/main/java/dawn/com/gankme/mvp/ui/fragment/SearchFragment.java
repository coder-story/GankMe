package dawn.com.gankme.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.paginate.Paginate;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import dawn.com.gankme.R;
import dawn.com.gankme.app.utils.ViewUtils;
import dawn.com.gankme.di.component.DaggerSearchFrgComponent;
import dawn.com.gankme.di.module.SearchFrgModule;
import dawn.com.gankme.mvp.contract.SearchFrgContract;
import dawn.com.gankme.mvp.model.entity.GanHuoData;
import dawn.com.gankme.mvp.model.entity.SearchResult;
import dawn.com.gankme.mvp.presenter.SearchFrgPresenter;
import dawn.com.gankme.mvp.ui.BaseSupportFragment;
import dawn.com.gankme.mvp.ui.adapter.HomeAdapter;
import dawn.com.gankme.mvp.ui.adapter.SearchAdapter;
import timber.log.Timber;

/**
 * Created by Administrator on 2018/1/29.
 */

public class SearchFragment extends BaseSupportFragment<SearchFrgPresenter> implements SearchFrgContract.View, SwipeRefreshLayout.OnRefreshListener, DefaultAdapter.OnRecyclerViewItemClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.searchView)
    EditText searchView;
    @BindView(R.id.img_back)
    ImageView img_back;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    RecyclerView.Adapter mAdapter;
    private Paginate mPaginate;
    boolean isLoadingMore;
    private String keyWord;

    public static SearchFragment newInstance() {

        Bundle args = new Bundle();
        //args.putString("keyword", keyword);
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerSearchFrgComponent.builder()
                .appComponent(appComponent)
                .searchFrgModule(new SearchFrgModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_search, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        initRecyclerView();
        searchView.postDelayed(new Runnable() {
            @Override
            public void run() {
                ViewUtils.showSoftInputFromWindow(getActivity(), searchView);
            }
        }, 500);

        searchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //以下方法防止两次发送请求
                keyWord = v.getText().toString();
                if (actionId == EditorInfo.IME_ACTION_SEND ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    Timber.tag("lzx").d("onEditorAction");
                    mPresenter.search(true, keyWord);
                    ViewUtils.closeKeyboard(getActivity());
                    return false;


                }

                return false;
            }
        });
        initPaginate();

    }

    /**
     * 初始化Paginate,用于加载更多
     */
    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {
                    mPresenter.search(false, keyWord);
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
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        ((SearchAdapter) mAdapter).setOnItemClickListener(this);
    }

    @Override
    public void setData(Object data) {
        mPaginate.setHasMoreDataToLoad(false);
    }


    @OnClick(R.id.img_back)
    public void onViewClicked() {
        pop();
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

    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

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
    public void onRefresh() {
        mPresenter.search(true, keyWord);
    }

    @Override
    public void onItemClick(View view, int viewType, Object data, int position) {
        String url = ((SearchResult) data).getUrl();
        String title = ((SearchResult) data).getDesc();
        start(WebFragment.newInstance(url, title));
    }

    @Override
    protected boolean isSwipeBack() {
        return true;
    }

    @Override
    public boolean onBackPressedSupport() {
        pop();
        return true;
    }
}
