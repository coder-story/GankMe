package dawn.com.gankme.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.OnClick;
import dawn.com.gankme.di.component.DaggerLoginComponent;
import dawn.com.gankme.di.module.LoginModule;
import dawn.com.gankme.mvp.contract.LoginContract;
import dawn.com.gankme.mvp.presenter.LoginPresenter;

import dawn.com.gankme.R;


import static com.jess.arms.utils.Preconditions.checkNotNull;


public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {


    @BindView(R.id.tiet_password)
    TextInputEditText tiet_password;
    @BindView(R.id.et_account)
    EditText et_account;

    //注册界面以dialog的形式展现

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_login; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @OnClick({R.id.btn_login,R.id.tv_register})
    public void click(View view){
        if(view.getId()==R.id.btn_login){
            String username=et_account.getText().toString();
            String password=tiet_password.getText().toString();
            mPresenter.login(username,password);
        }else if(R.id.tv_register==view.getId()){

        }
    }
}
