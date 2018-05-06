package dawn.com.gankme.mvp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.delegate.IFragment;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.integration.cache.CacheType;
import com.jess.arms.integration.lifecycle.FragmentLifecycleable;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.utils.ArmsUtils;
import com.trello.rxlifecycle2.android.FragmentEvent;

import javax.inject.Inject;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * 继承第三方的Fragmentation库
 * Created by Administrator on 2018/2/5.
 */

public abstract class BaseSupportFragment<P extends IPresenter> extends SwipeBackFragment implements IFragment, FragmentLifecycleable {
    protected final String TAG = this.getClass().getSimpleName();
    private final BehaviorSubject<FragmentEvent> mLifecycleSubject = BehaviorSubject.create();
    private Cache<String, Object> mCache;
    @Inject
    protected P mPresenter;

    @NonNull
    @Override
    public synchronized Cache<String, Object> provideCache() {
        if (mCache == null) {
            mCache = ArmsUtils.obtainAppComponentFromContext(getActivity()).cacheFactory().build(CacheType.FRAGMENT_CACHE);
        }
        return mCache;
    }


    @NonNull
    @Override
    public final Subject<FragmentEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(isSwipeBack()){
            return attachToSwipeBack(initView(inflater, container, savedInstanceState));
        }
        return initView(inflater, container, savedInstanceState);
    }

    protected boolean isSwipeBack() {
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDestroy();//释放资源
        this.mPresenter = null;
    }


    /**
     * 是否使用eventBus,默认为使用(true)，
     *
     * @return
     */
    @Override
    public boolean useEventBus() {
        return true;
    }

    public void setNavigationOnClickListener(Toolbar toolbar) {
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pop();
                }
            });
        }
    }
}
