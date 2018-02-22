package dawn.com.gankme.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import dawn.com.gankme.R;
import dawn.com.gankme.app.utils.ViewUtils;
import dawn.com.gankme.di.component.DaggerSearchFrgComponent;
import dawn.com.gankme.di.module.SearchFrgModule;
import dawn.com.gankme.mvp.contract.SearchFrgContract;
import dawn.com.gankme.mvp.presenter.SearchFrgPresenter;
import dawn.com.gankme.mvp.ui.BaseSupportFragment;

/**
 * Created by Administrator on 2018/1/29.
 */

public class SearchFragment extends BaseSupportFragment<SearchFrgPresenter> implements SearchFrgContract.View {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.searchView)
    EditText searchView;
    @BindView(R.id.img_back)
    ImageView img_back;
    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    RecyclerView.Adapter mAdapter;
    Unbinder unbinder;

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
                ViewUtils.showSoftInputFromWindow(getActivity(),searchView);
            }
        },500);

        searchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                mPresenter.search(v.getText().toString());
                ViewUtils.closeKeyboard(getActivity());
                return false;
            }
        });


    }

    private void initRecyclerView() {
        ArmsUtils.configRecyclerView(mRecyclerView, mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    @Override
    public void setData(Object data) {

    }


    @OnClick(R.id.img_back)
    public void onViewClicked() {

        pop();

    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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




}
