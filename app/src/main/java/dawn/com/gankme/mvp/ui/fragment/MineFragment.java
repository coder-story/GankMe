package dawn.com.gankme.mvp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dawn.com.gankme.R;
import dawn.com.gankme.app.utils.SPUtils;
import dawn.com.gankme.mvp.ui.BaseSupportFragment;
import skin.support.SkinCompatManager;
import timber.log.Timber;

/**
 * Created by Administrator on 2018/1/29.
 */

public class MineFragment extends BaseSupportFragment {

    @BindView(R.id.tv_collect)
    TextView tvCollect;
    @BindView(R.id.tv_change_skin)
    TextView tvChangeSkin;
    private int night_mode;
    private AppComponent component;

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        component = appComponent;
        night_mode = (int) SPUtils.get(appComponent.application(), "night_node", -1);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_mine, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {


        if (night_mode == -1) {
            tvChangeSkin.setText("夜间模式");//当前是日间模式，点击切换到夜间模式
        } else {
            tvChangeSkin.setText("日间模式");
        }

    }

    @Override
    public void setData(Object data) {

    }


    @OnClick({R.id.tv_collect, R.id.tv_change_skin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_collect:
                break;
            case R.id.tv_change_skin:

                if (night_mode == -1) {
                    //切换到夜间模式
                    change2Night();
                } else {
                    change2Day();
                }

                break;
        }
    }

    private void change2Day() {
        SkinCompatManager.getInstance().restoreDefaultTheme();
        night_mode = -1;
        tvChangeSkin.setText("夜间模式");
        SPUtils.put(component.application(), "night_node", night_mode);

    }

    private void change2Night() {
        SkinCompatManager.getInstance().loadSkin("night.skin", new SkinCompatManager.SkinLoaderListener() {

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess() {
                tvChangeSkin.setText("日间模式");
                night_mode = 1;
                SPUtils.put(component.application(), "night_node", night_mode);
            }

            @Override
            public void onFailed(String errMsg) {

            }
        }, SkinCompatManager.SKIN_LOADER_STRATEGY_ASSETS);
    }
}
