package dawn.com.gankme.mvp.model.entity;

/**
 * Created by Administrator on 2018/2/5.
 */

public class DailyTitle {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;

    public DailyTitle(String title) {
        this.title = title;
    }
}
