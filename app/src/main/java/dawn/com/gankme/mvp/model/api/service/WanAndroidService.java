package dawn.com.gankme.mvp.model.api.service;

import dawn.com.gankme.mvp.model.entity.ArticleListBean;
import dawn.com.gankme.mvp.model.entity.BaseJson;
import dawn.com.gankme.mvp.model.entity.User;
import io.reactivex.Observable;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by mac on 2018/5/1.
 */

public interface WanAndroidService {

    @GET("article/list/{index}/json")
    Observable<BaseJson<ArticleListBean>> getArticles(@Path("index") int index);

    @FormUrlEncoded
    @POST("/user/register")
    Observable<BaseJson<User>> register(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);

    @FormUrlEncoded
    @POST("/user/login")
    Observable<BaseJson<User>> login(@Field("username") String username, @Field("password") String password);

    @POST("/lg/collect/{id}/json")
    Observable<BaseJson> collect(@Path("id") int id);
}
