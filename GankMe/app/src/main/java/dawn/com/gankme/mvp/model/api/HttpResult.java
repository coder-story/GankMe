package dawn.com.gankme.mvp.model.api;

/**
 * Created by Administrator on 2018/1/29.
 */

public class HttpResult<T> {

    public boolean error;
    //@SerializedName(value = "results", alternate = {"result"})
    public T results;
}