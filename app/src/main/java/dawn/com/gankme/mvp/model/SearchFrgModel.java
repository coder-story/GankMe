package dawn.com.gankme.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import java.util.List;

import javax.inject.Inject;

import dawn.com.gankme.mvp.contract.SearchFrgContract;
import dawn.com.gankme.mvp.model.api.HttpResult;
import dawn.com.gankme.mvp.model.api.service.GankService;
import dawn.com.gankme.mvp.model.entity.SearchResult;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.functions.Function;


@FragmentScope
public class SearchFrgModel extends BaseModel implements SearchFrgContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public SearchFrgModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<List<SearchResult>> search(String keyword, int pageIndex) {
        return Observable.just(mRepositoryManager.obtainRetrofitService(GankService.class)
                .search(keyword, pageIndex))
                .flatMap(new Function<Observable<HttpResult<List<SearchResult>>>, ObservableSource<List<SearchResult>>>() {
                    @Override
                    public ObservableSource<List<SearchResult>> apply(Observable<HttpResult<List<SearchResult>>> httpResultObservable) throws Exception {
                        return httpResultObservable.map(new Function<HttpResult<List<SearchResult>>, List<SearchResult>>() {
                            @Override
                            public List<SearchResult> apply(HttpResult<List<SearchResult>> listHttpResult) throws Exception {
                                return listHttpResult.results;
                            }
                        });
                    }
                });
    }
}