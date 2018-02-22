package dawn.com.gankme.mvp.presenter;

import android.app.Application;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.Preconditions;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.List;

import dawn.com.gankme.mvp.model.entity.SearchResult;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import dawn.com.gankme.mvp.contract.SearchFrgContract;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@FragmentScope
public class SearchFrgPresenter extends BasePresenter<SearchFrgContract.Model, SearchFrgContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    private RecyclerView.Adapter mAdapter;
    private List<SearchResult> list;
    private int pageIndex=1;
    private int preEndIndex;
    private boolean  isFirst=true;

    @Inject
    public SearchFrgPresenter(SearchFrgContract.Model model, SearchFrgContract.View rootView,
                              List<SearchResult> list, RecyclerView.Adapter adapter) {
        super(model, rootView);
        this.list = list;
        mAdapter = adapter;
    }


    public void search(String keyword) {
        Preconditions.checkNotNull(keyword, "keyword == null");
        mModel.search(keyword, pageIndex)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<List<SearchResult>>(mErrorHandler) {
                    @Override
                    public void onNext(List<SearchResult> searchResults) {
                        ++pageIndex;
                        //list.clear();
                        list.addAll(searchResults);
                        //更新之前列表总长度,用于确定加载更多的起始位置
                        preEndIndex = searchResults.size();
                        if (isFirst) {
                            mAdapter.notifyDataSetChanged();
                            isFirst=false;
                        } else {
                            mAdapter.notifyItemRangeInserted(preEndIndex, searchResults.size());
                        }

                    }
                });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mApplication = null;
    }

}
