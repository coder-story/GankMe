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

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import org.reactivestreams.Publisher;

import javax.inject.Inject;

import dawn.com.gankme.mvp.model.api.service.CommonService;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import dawn.com.gankme.mvp.contract.DailyContract;
import dawn.com.gankme.mvp.model.api.HttpResult;
import dawn.com.gankme.mvp.model.api.cache.CommonCache;
import dawn.com.gankme.mvp.model.api.service.GankService;
import dawn.com.gankme.mvp.model.entity.DailyList;
import okhttp3.ResponseBody;


@FragmentScope
public class DailyDetailModel extends BaseModel implements DailyContract.Model {

    @Inject
    public DailyDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<DailyList> getRecentlyGanHuo(String date) {
        return Observable.just(mRepositoryManager.
                obtainRetrofitService(GankService.class)
                .getRecentlyGanHuo(date))
                .flatMap(new Function<Observable<HttpResult<DailyList>>, ObservableSource<DailyList>>() {
                    @Override
                    public ObservableSource<DailyList> apply(Observable<HttpResult<DailyList>> httpResultObservable) throws Exception {
                        return httpResultObservable.map(new Function<HttpResult<DailyList>, DailyList>() {
                            @Override
                            public DailyList apply(HttpResult<DailyList> dailyListHttpResult) throws Exception {
                                return dailyListHttpResult.results;
                            }
                        });
                    }
                });
    }

    @Override
    public Observable<ResponseBody> downLoadImg(String url) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .download(url);

    }

}
