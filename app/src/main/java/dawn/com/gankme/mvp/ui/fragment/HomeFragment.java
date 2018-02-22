package dawn.com.gankme.mvp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.di.component.AppComponent;

import dawn.com.gankme.R;
import dawn.com.gankme.mvp.ui.BaseSupportFragment;
import timber.log.Timber;

/**
 * Created by Administrator on 2018/1/29.
 */

public class HomeFragment extends BaseSupportFragment {

    public  static HomeFragment newInstance(){
        return  new HomeFragment();

    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_home,container,false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (findFragment(GankListFragment.class) == null) {
            loadRootFragment(R.id.fl_root_home, GankListFragment.newInstance());
        }
    }

    @Override
    public void setData(Object data) {

    }


    @Override
    protected boolean isSwipeBack() {
        return false;
    }


}
