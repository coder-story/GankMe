package dawn.com.gankme.di.module;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.di.scope.FragmentScope;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

import dawn.com.gankme.mvp.contract.SearchFrgContract;
import dawn.com.gankme.mvp.model.SearchFrgModel;
import dawn.com.gankme.mvp.model.entity.Daily;
import dawn.com.gankme.mvp.model.entity.SearchResult;
import dawn.com.gankme.mvp.ui.adapter.HomeAdapter;
import dawn.com.gankme.mvp.ui.adapter.SearchAdapter;


@Module
public class SearchFrgModule {
    private SearchFrgContract.View view;

    /**
     * 构建SearchFrgModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public SearchFrgModule(SearchFrgContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    SearchFrgContract.View provideSearchFrgView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    SearchFrgContract.Model provideSearchFrgModel(SearchFrgModel model) {
        return model;
    }


    @FragmentScope
    @Provides
    RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(view.getActivity());
    }

    @FragmentScope
    @Provides
    List<SearchResult> provideDailyList() {
        return new ArrayList<>();
    }

    @FragmentScope
    @Provides
    RecyclerView.Adapter provideDailyAdapter(List<SearchResult> list){
        return new SearchAdapter(list);
    }

}