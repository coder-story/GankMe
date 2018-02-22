/**
 * Copyright 2017 JessYan
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dawn.com.gankme.mvp.model;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import dawn.com.gankme.mvp.contract.GankListContract;
import dawn.com.gankme.mvp.model.api.cache.CommonCache;
import dawn.com.gankme.mvp.model.api.service.GankService;
import dawn.com.gankme.mvp.model.entity.Daily;
import timber.log.Timber;

/**
 * ================================================
 * 展示 Model 的用法
 * @author lzx
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki#2.4.3">Model wiki 官方文档</a>
 * Created by JessYan on 09/04/2016 10:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
@FragmentScope
public class GankListModel extends BaseModel implements GankListContract.Model {
    public static final int USERS_PER_PAGE = 10;

    @Inject
    public GankListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause() {
        Timber.d("Release Resource");
    }

    @Override
    public Observable<List<Daily>> getRecently(int pageIndex, boolean update) {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(GankService.class)
                .getRecently(pageIndex))
                .flatMap(new Function<Observable<Daily>, ObservableSource<List<Daily>>>() {
                    @Override
                    public ObservableSource<List<Daily>> apply(Observable<Daily> dailyObservable) throws Exception {
                        return mRepositoryManager.obtainCacheService(CommonCache.class)
                                .getRecently(dailyObservable,new DynamicKey(pageIndex),new EvictDynamicKey(update))
                                .map(daily -> daily.results);
                    }
                });
    }
}
