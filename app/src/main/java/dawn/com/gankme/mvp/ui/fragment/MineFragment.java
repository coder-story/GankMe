package dawn.com.gankme.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageConfig;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.DataHelper;

import butterknife.BindView;
import butterknife.OnClick;
import dawn.com.gankme.R;
import dawn.com.gankme.app.constants.KeyConstant;
import dawn.com.gankme.app.utils.SPUtils;
import dawn.com.gankme.mvp.ui.BaseSupportFragment;
import dawn.com.gankme.mvp.ui.activity.LoginActivity;

/**
 * Created by Administrator on 2018/1/29.
 */

public class MineFragment extends BaseSupportFragment {

    @BindView(R.id.tv_collect)
    TextView tvCollect;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.img_avatar)
    ImageView  img_avatar;
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



    }

    @Override
    public void onResume() {
        super.onResume();
        String  username= DataHelper.getStringSF(component.application(), KeyConstant.USERNAME);
        if(!TextUtils.isEmpty(username)){
            tvLogin.setText(username);
            component.imageLoader().loadImage(getActivity(),
                    ImageConfigImpl
                            .builder()
                            .isCircle(true)
                            .url("http://ww1.sinaimg.cn/large/610dc034ly1fhyeyv5qwkj20u00u0q56.jpg")
                            .imageView(img_avatar)
                            .build());

        }else{
            tvLogin.setText("登陆");
            img_avatar.setImageResource(R.mipmap.ic_launcher);
        }
    }

    @Override
    public void setData(Object data) {

    }


    @OnClick({R.id.tv_collect, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_collect:
                break;
            case R.id.tv_login:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
        }
    }


}
