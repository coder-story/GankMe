package dawn.com.gankme.app.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import dawn.com.gankme.mvp.model.api.HttpResult;

/**
 * Created by Administrator on 2018/1/31.
 */

public class ComposeString2List<T> implements ObservableTransformer<String, List<T>> {

    public TypeToken<HttpResult<List<T>>> typeToken;

    public static ComposeString2List newCompose(TypeToken typeToken) {
        return new ComposeString2List(typeToken);
    }

    public ComposeString2List(TypeToken<HttpResult<List<T>>> typeToken) {
        this.typeToken = typeToken;
    }


    @Override
    public ObservableSource<List<T>> apply(Observable<String> upstream) {
        return upstream.flatMap(new ReadListFunc<>(typeToken))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    private static class ReadListFunc<E> implements Function<String, Observable<List<E>>> {

        private TypeToken<HttpResult<List<E>>> typeToken;

        public ReadListFunc(TypeToken<HttpResult<List<E>>> typeToken) {
            this.typeToken = typeToken;
        }


        @Override
        public Observable<List<E>> apply(String responseReply) {
            HttpResult<List<E>> response = new Gson().fromJson(responseReply, typeToken.getType());
            if (response != null && !response.error) {
                return Observable.just(response.results);
            } else {
                return Observable.error(new Throwable());

            }
        }
    }
}
