package dawn.com.gankme.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import java.util.List;
import java.util.Observable;

import dawn.com.gankme.mvp.model.entity.Daily;
import dawn.com.gankme.mvp.model.entity.SearchResult;


public interface SearchFrgContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void startLoadMore();
        void endLoadMore();
        Activity getActivity();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        io.reactivex.Observable<List<SearchResult>>  search(String keyword,int pageIndex);
    }
}
