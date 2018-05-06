package dawn.com.gankme.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.Preconditions;
import com.jess.arms.utils.RxLifecycleUtils;

import dawn.com.gankme.app.constants.KeyConstant;
import dawn.com.gankme.mvp.model.entity.BaseJson;
import dawn.com.gankme.mvp.model.entity.User;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import dawn.com.gankme.mvp.contract.LoginContract;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import timber.log.Timber;


@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.View rootView) {
        super(model, rootView);
    }

    public void register(String username, String psw, String rePsw) {
        mModel.register(username, psw, rePsw)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mRootView.showLoading();
                    }
                })
                .doOnNext(new Consumer<BaseJson<User>>() {
                    @Override
                    public void accept(BaseJson<User> s) throws Exception {
                        if (s.isSuccess()) {
                            ArmsUtils.snackbarTextWithLong("注册成功：" + s);
                        }
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<BaseJson<User>, ObservableSource<BaseJson<User>>>() {
                    @Override
                    public ObservableSource<BaseJson<User>> apply(BaseJson<User> s) throws Exception {
                        if (!s.isSuccess()) {
                            ArmsUtils.snackbarTextWithLong(s.getErrorMsg());
                            return Observable.empty();
                        }
                        Timber.d("flatmap  thread:"+Thread.currentThread().getName());
                        return mModel.login(s.getData().getUsername(), s.getData().getPassword());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseJson<User>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseJson<User> userBaseJson) {
                        if (userBaseJson.isSuccess()) {
                            ArmsUtils.snackbarText("登陆成功！");
                            Preconditions.checkNotNull(userBaseJson.getData(), "User is null!");
                            DataHelper.setStringSF(mRootView.getActivity(), KeyConstant.USERNAME, userBaseJson.getData().getUsername());
                            DataHelper.setStringSF(mRootView.getActivity(), KeyConstant.PASSWORD, userBaseJson.getData().getPassword());

                        } else {
                            ArmsUtils.snackbarText("登陆失败，请重试。");
                        }ArmsUtils.snackbarText(userBaseJson.getErrorMsg());

                    }
                });

    }

    public void login(String username, String psw) {
        mModel.login(username, psw)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mRootView.showLoading();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseJson<User>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseJson<User> userBaseJson) {
                        if (userBaseJson.isSuccess()) {
                            ArmsUtils.snackbarText("登陆成功！");
                            Preconditions.checkNotNull(userBaseJson.getData(), "User is null!");
                            DataHelper.setStringSF(mRootView.getActivity(), KeyConstant.USERNAME, userBaseJson.getData().getUsername());
                            DataHelper.setStringSF(mRootView.getActivity(), KeyConstant.PASSWORD, userBaseJson.getData().getPassword());
                            mRootView.killMyself();

                        } else {
                            ArmsUtils.snackbarText("登陆失败，请重试。");
                        }

                    }
                });

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
