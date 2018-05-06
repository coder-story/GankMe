package dawn.com.gankme.mvp.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dawn.com.gankme.R;
import dawn.com.gankme.app.utils.PagerChangeListener;
import dawn.com.gankme.mvp.ui.BaseSupportFragment;
import dawn.com.gankme.mvp.ui.adapter.FragmentAdapter;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by Administrator on 2018/1/29.
 */

public class AllCategoryFragment extends BaseSupportFragment {

    @BindView(R.id.toolbar_iv_outgoing)
    ImageView toolbarIvOutgoing;
    @BindView(R.id.toolbar_iv_target)
    ImageView toolbarIvTarget;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    Unbinder unbinder;


    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> tabList = new ArrayList<>();


    public static AllCategoryFragment newInstance() {
        return new AllCategoryFragment();

    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_category, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void setData(Object data) {
        setupTabs();
    }

    private void setupTabs() {
        tabList.add("Android");
        tabList.add("iOS");
        tabList.add("福利");
        tabList.add("休息视频");
        tabList.add("拓展资源");
        tabList.add("前端");
        tabList.add("all");

        Observable.fromIterable(tabList)
                .map(s -> {
                    return CategoryFragment.newInstance(s);
                })
                .toList()
                .map(new Function<List<CategoryFragment>, FragmentAdapter>() {
                    @Override
                    public FragmentAdapter apply(List<CategoryFragment> categoryFragments) throws Exception {
                        return FragmentAdapter.newInstance(getFragmentManager(), categoryFragments, tabList);
                    }
                })
                .subscribe(new Consumer<FragmentAdapter>() {
                    @Override
                    public void accept(FragmentAdapter fragmentAdapter) throws Exception {
                        viewpager.setAdapter(fragmentAdapter);
                    }
                });


        PagerChangeListener mPagerChangeListener = PagerChangeListener.newInstance(getContext(), collapsingToolbar, toolbarIvTarget, toolbarIvOutgoing);
        viewpager.addOnPageChangeListener(mPagerChangeListener);
        tabs.setupWithViewPager(viewpager);

    }

}
