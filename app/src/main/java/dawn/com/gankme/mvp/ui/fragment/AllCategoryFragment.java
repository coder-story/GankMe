package dawn.com.gankme.mvp.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import dawn.com.gankme.R;
import dawn.com.gankme.mvp.ui.BaseSupportFragment;

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

    public static final String[] categorys = new String[]{"Android", "iOS", "福利", "休息视频", "拓展资源", "前端", "all"};

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

    }


}
