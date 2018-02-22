package dawn.com.gankme.mvp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;

import dawn.com.gankme.R;
import dawn.com.gankme.mvp.ui.BaseSupportFragment;

/**
 * Created by Administrator on 2018/1/29.
 */

public class MineFragment extends BaseSupportFragment {

    public  static MineFragment  newInstance(){
        return  new MineFragment();

    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_mine,container,false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void setData(Object data) {

    }
}
