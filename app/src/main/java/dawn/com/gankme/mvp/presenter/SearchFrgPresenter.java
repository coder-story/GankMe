package dawn.com.gankme.mvp.presenter;

import android.app.Application;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.base.delegate.IFragment;
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
import timber.log.Timber;


@FragmentScope
public class SearchFrgPresenter extends BasePresenter<SearchFrgContract.Model, SearchFrgContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    private RecyclerView.Adapter mAdapter;
    private List<SearchResult> list;
    private int pageIndex = 1;
    private int preEndIndex;
    private boolean isFirst = true;

    @Inject
    public SearchFrgPresenter(SearchFrgContract.Model model, SearchFrgContract.View rootView,
                              List<SearchResult> list, RecyclerView.Adapter adapter) {
        super(model, rootView);
        this.list = list;
        mAdapter = adapter;
    }


    public void search(boolean pullToRefresh, String keyword) {
        Preconditions.checkNotNull(keyword, "keyword == null");
        mModel.search(keyword, pageIndex)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {//允许我们在观察着订阅时立即执行动作
                    if (pullToRefresh) {
                        mRootView.showLoading();//显示下拉刷新的进度条
                    } else {
                        mRootView.startLoadMore();//显示上拉加载更多的进度条
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (pullToRefresh) {
                        mRootView.hideLoading();//隐藏下拉刷新的进度条
                    } else {
                        mRootView.endLoadMore();//隐藏上拉加载更多的进度条
                    }
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<List<SearchResult>>(mErrorHandler) {
                    @Override
                    public void onNext(List<SearchResult> searchResults) {
                        //  Timber.tag("lzx").d("result size:"+searchResults.size());
                        if (searchResults.size() <= 0) {
                            ((IFragment) mRootView).setData(null);//通知没有更多数据了
                            return;
                        }
                        if (pullToRefresh) {
                            list.clear();
                        } //如果是下拉刷新则清空列表

                        ++pageIndex;
                        list.addAll(searchResults);
                        //更新之前列表总长度,用于确定加载更多的起始位置
                        preEndIndex = searchResults.size();
                        if (isFirst) {
                            mAdapter.notifyDataSetChanged();
                            isFirst = false;
                        } else {
                            mAdapter.notifyItemRangeInserted(preEndIndex, searchResults.size());
                        }


                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        ((IFragment) mRootView).setData(null);//通知没有更多数据了
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
