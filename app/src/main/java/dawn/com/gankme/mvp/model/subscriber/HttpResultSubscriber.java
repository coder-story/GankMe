package dawn.com.gankme.mvp.model.subscriber;

import java.util.concurrent.CancellationException;

import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import dawn.com.gankme.mvp.model.api.HttpResult;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandlerFactory;

/**
 * Created by _SOLID
 * Date:2016/7/27
 * Time:21:27
 */
public abstract class HttpResultSubscriber<T> implements Observer<HttpResult<T>> {



    private ErrorHandlerFactory mHandlerFactory;

    public HttpResultSubscriber(RxErrorHandler rxErrorHandler){
        this.mHandlerFactory = rxErrorHandler.getHandlerFactory();
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }


    @Override
    public void onNext(HttpResult<T> result) {
        if (!result.error)
            _onSuccess(result.results);

    }


    @Override
    public void onError(Throwable e) {

        if (e != null) {
            e.printStackTrace();
            //如果你某个地方不想使用全局错误处理,则重写 onError(Throwable) 并将 super.onError(e); 删掉
            //如果你不仅想使用全局错误处理,还想加入自己的逻辑,则重写 onError(Throwable) 并在 super.onError(e); 后面加入自己的逻辑
            mHandlerFactory.handleError(e);
        }
    }


    @Override
    public void onComplete() {

    }

    public abstract void _onSuccess(T t);


}
