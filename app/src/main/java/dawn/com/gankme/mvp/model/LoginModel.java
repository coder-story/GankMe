package dawn.com.gankme.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import dawn.com.gankme.mvp.contract.LoginContract;
import dawn.com.gankme.mvp.model.api.service.WanAndroidService;
import dawn.com.gankme.mvp.model.entity.BaseJson;
import dawn.com.gankme.mvp.model.entity.User;
import io.reactivex.Observable;


@ActivityScope
public class LoginModel extends BaseModel implements LoginContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public LoginModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseJson<User>> register(String username, String psw, String rePsw) {
        return mRepositoryManager.obtainRetrofitService(WanAndroidService.class).register(username,psw,rePsw);
    }

    @Override
    public Observable<BaseJson<User>> login(String username, String psw) {
        return mRepositoryManager.obtainRetrofitService(WanAndroidService.class).login(username,psw);
    }
}