package dawn.com.gankme.mvp.model.entity;

import java.util.List;

/**
 * Created by mac on 2018/5/6.
 */

public class CollectionListBean {

    /**
     * curPage : 1
     * datas : [{"author":"李通Tookie","chapterId":78,"chapterName":"性能优化","courseId":13,"desc":"","envelopePic":"","id":10383,"link":"https://www.jianshu.com/p/307ba8911799","niceDate":"刚刚","origin":"","originId":2883,"publishTime":1525600235000,"title":"Android性能全面分析与优化方案研究&mdash;几乎是史上最全最实用的","userId":5302,"visible":0,"zan":0}]
     * offset : 0
     * over : true
     * pageCount : 1
     * size : 20
     * total : 1
     */

    private int curPage;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    private List<Collection> datas;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Collection> getDatas() {
        return datas;
    }

    public void setDatas(List<Collection> datas) {
        this.datas = datas;
    }

   
}
