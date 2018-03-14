package dawn.com.gankme.mvp.model.api.service;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import dawn.com.gankme.mvp.model.api.HttpResult;
import dawn.com.gankme.mvp.model.entity.Daily;
import dawn.com.gankme.mvp.model.entity.DailyList;
import dawn.com.gankme.mvp.model.entity.GanHuoData;
import dawn.com.gankme.mvp.model.entity.SearchResult;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2018/1/29.
 */

public interface GankService {

    String BASE_URL = "http://www.gank.io/api/";

    /**
     * 获取发布干货的日期
     *
     * @return
     */
    @GET("day/history")
    Single<HttpResult<List<String>>> getRecentlyDate();

    /***
     * 根据类别查询干货
     *
     * @param category
     * @param pageIndex
     * @return
     */
    @GET("data/{category}/20/{pageIndex}")
    Single<HttpResult<List<GanHuoData>>> getGanHuoByCategory(@Path("category") String category
            , @Path("pageIndex") int pageIndex);

    /**
     * 获取某天的干货
     *
     * @param date
     * @return
     */
    @GET("day/{date}")
    Observable<HttpResult<DailyList>> getRecentlyGanHuo(@Path("date") String date);

    /**
     * 搜索
     *
     * @param keyword
     * @param pageIndex
     * @return
     */
    @GET("search/query/{keyword}/category/all/count/20/page/{pageIndex}")
    Observable<HttpResult<List<SearchResult>>> search(@Path("keyword") String keyword, @Path("pageIndex") int pageIndex);




    @GET("history/content/10/{pageIndex}")
   Observable<Daily> getRecently(@Path("pageIndex") int pageIndex);


}
