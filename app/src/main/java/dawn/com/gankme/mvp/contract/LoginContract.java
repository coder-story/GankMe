package dawn.com.gankme.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import dawn.com.gankme.mvp.model.entity.BaseJson;
import dawn.com.gankme.mvp.model.entity.User;
import io.reactivex.Observable;


public interface LoginContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void showLoading();
        void hideLoading();
        Activity getActivity();

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<BaseJson<User>>  register(String username,String psw,String rePsw);

        Observable<BaseJson<User>>  login(String username,String psw);



    }
}