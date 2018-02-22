package dawn.com.gankme.mvp.model.entity;

import android.support.annotation.NonNull;

/**
 * Created by lzx on 2018/1/22 0022.
 */

public class ToolBarBean {


    private int toolbarBackgroundColor;        //背景颜色
    private int toolbarTitleIcon;             //标题文字旁边的图标
    private int toolbarTitleColor;             //标题文字颜色
    private int toolbarVisible;             //返回图标是否可见
    private int toolbarIcon;          //设置返回图标
    private int toolbarRight1;            //右边图片按钮1
    private int toolbarRight2;             //右边图片按钮2 最右边的
    private String toolbarRight;               //右边文字
    private int toolbarRightColor;               //右边文字颜色

    private ToolBarBean(Builder builder) {
        this.toolbarBackgroundColor = builder.toolbarBackgroundColor;
        this.toolbarTitleIcon = builder.toolbarTitleIcon;
        this.toolbarTitleColor = builder.toolbarTitleColor;
        this.toolbarVisible = builder.toolbarVisible;
        this.toolbarIcon = builder.toolbarIcon;
        this.toolbarRight1 = builder.toolbarRight1;
        this.toolbarRight2 = builder.toolbarRight2;
        this.toolbarRight = builder.toolbarRight;
        this.toolbarRightColor = builder.toolbarRightColor;
    }

    @NonNull
    public static Builder builder() {
        return new Builder();
    }

    public int getToolbarBackgroundColor() {
        return toolbarBackgroundColor;
    }

    public int getToolbarTitleIcon() {
        return toolbarTitleIcon;
    }

    public int getToolbarTitleColor() {
        return toolbarTitleColor;
    }

    public int getToolbarVisible() {
        return toolbarVisible;
    }

    public int getToolbarIcon() {
        return toolbarIcon;
    }

    public int getToolbarRight1() {
        return toolbarRight1;
    }

    public int getToolbarRight2() {
        return toolbarRight2;
    }

    public String getToolbarRight() {
        return toolbarRight;
    }

    public int getToolbarRightColor() {
        return toolbarRightColor;
    }

    public static class Builder {
        private int toolbarBackgroundColor;        //背景颜色
        private int toolbarTitleIcon;             //标题文字旁边的图标
        private int toolbarTitleColor;             //标题文字颜色
        private int toolbarVisible;             //返回图标是否可见
        private int toolbarIcon;          //设置返回图标
        private int toolbarRight1;            //右边图片按钮1
        private int toolbarRight2;             //右边图片按钮2 最右边的
        private String toolbarRight;               //右边文字
        private int toolbarRightColor;           //右边文字颜色

        //提供默认实现
        public Builder() {

        }

        public Builder toolbarBackgroundColor(int toolbarBackgroundColor) {
            this.toolbarBackgroundColor = toolbarBackgroundColor;
            return this;
        }

        public Builder toolbarTitleIcon(int toolbarTitleIcon) {
            this.toolbarTitleIcon = toolbarTitleIcon;
            return this;
        }

        public Builder toolbarTitleColor(int toolbarTitleColor) {
            this.toolbarTitleColor = toolbarTitleColor;
            return this;
        }


        public Builder toolbarVisible(int toolbarVisible) {
            this.toolbarVisible = toolbarVisible;
            return this;
        }

        public Builder toolbarIcon(int toolbarIcon) {
            this.toolbarIcon = toolbarIcon;
            return this;
        }

        public Builder toolbarRight1(int toolbarRight1) {
            this.toolbarRight1 = toolbarRight1;
            return this;
        }

        public Builder toolbarRight2(int toolbarRight2) {
            this.toolbarRight2 = toolbarRight2;
            return this;
        }

        public Builder toolbarRight(String  toolbarRight) {
            this.toolbarRight = toolbarRight;
            return this;
        }

        public Builder toolbarRightColor(int toolbarRightColor) {
            this.toolbarRightColor = toolbarRightColor;
            return this;
        }

        public ToolBarBean build() {
            return new ToolBarBean(this);
        }


    }

}
