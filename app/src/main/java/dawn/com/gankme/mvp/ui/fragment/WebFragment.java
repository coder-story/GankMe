package dawn.com.gankme.mvp.ui.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.style.Wave;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import org.simple.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import dawn.com.gankme.R;
import dawn.com.gankme.app.constants.KeyConstant;
import dawn.com.gankme.app.constants.TagConstant;
import dawn.com.gankme.app.utils.RxUtils;
import dawn.com.gankme.mvp.model.api.service.WanAndroidService;
import dawn.com.gankme.mvp.model.entity.BaseJson;
import dawn.com.gankme.mvp.ui.BaseSupportFragment;
import dawn.com.gankme.mvp.ui.activity.MainActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/2/7.
 */

public class WebFragment extends BaseSupportFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fl_root_web)
    FrameLayout root_web;
    @BindView(R.id.textSwitcher)
    TextSwitcher mTextSwitcher;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.spin_kit)
    SpinKitView spinKitView;
    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;

    private String url;
    private String title;
    private WebView mWebView;
    private int articleId;

    public static WebFragment newInstance(String url, String title,int articleId) {

        Bundle args = new Bundle();
        args.putString("url", url);
        args.putString("title", title);
        args.putInt(KeyConstant.ARTICLE_ID,articleId);
        WebFragment fragment = new WebFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static WebFragment newInstance(String url, String title) {

        Bundle args = new Bundle();
        args.putString("url", url);
        args.putString("title", title);
        WebFragment fragment = new WebFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_webview, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        url = getArguments().getString("url");
        title = getArguments().getString("title");
        articleId = getArguments().getInt(KeyConstant.ARTICLE_ID,0);
        setNavigationOnClickListener(toolbar);
        initWebView();
        initTextSwitch();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArmsUtils.obtainAppComponentFromContext(getActivity())
                        .repositoryManager()
                        .obtainRetrofitService(WanAndroidService.class)
                        .collect(articleId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<BaseJson>() {
                            @Override
                            public void accept(BaseJson baseJson) throws Exception {
                                if(baseJson.isSuccess()){
                                    ArmsUtils.snackbarText("收藏成功。");
                                }else {
                                    ArmsUtils.snackbarText(baseJson.getErrorMsg());
                                }
                            }
                        });
            }
        });
    }

    private void initTextSwitch() {

        mTextSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @SuppressWarnings("deprecation")
            @Override
            public View makeView() {
                Context context = getActivity();
                TextView textView = new TextView(context);
                textView.setTextAppearance(context, R.style.WebTitle);
                textView.setSingleLine(true);
                textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        v.setSelected(!v.isSelected());
                    }
                });
                return textView;
            }
        });
        mTextSwitcher.setInAnimation(getActivity(), android.R.anim.fade_in);
        mTextSwitcher.setOutAnimation(getActivity(), android.R.anim.fade_out);
        mTextSwitcher.setText(title);
        mTextSwitcher.setSelected(true);
        Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) mTextSwitcher.getLayoutParams();
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
    }


    private void initWebView() {
        //toolbar.setTitle(title);

        Wave wave = new Wave();
        progressBar.setIndeterminateDrawable(wave);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView = new WebView(getActivity().getApplicationContext());
        mWebView.setLayoutParams(params);
        root_web.addView(mWebView);

        mWebView.loadUrl(url);

        WebSettings webSettings = mWebView.getSettings();

        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebView.setVerticalScrollBarEnabled(true);//显示滚动条

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //设定加载开始的操作
                if(progressBar!=null)
                progressBar.setVisibility(View.VISIBLE);


            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //设定加载结束的操作
                if(progressBar!=null)
                progressBar.setVisibility(View.GONE);
                if(spinKitView!=null)
                spinKitView.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();    //表示等待证书响应
                // handler.cancel();      //表示挂起连接，为默认方式
                // handler.handleMessage(null);    //可做其他处理
            }


        });

        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (progressBar != null) {
                    if (newProgress < 75) {
                        progressBar.setProgress(newProgress);
                    } else {
                        progressBar.setVisibility(View.GONE);
                        spinKitView.setVisibility(View.GONE);
                        //floatingActionButton.setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void onReceivedTitle(WebView view, String title) {

            }
        });


    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void onDestroy() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }

        super.onDestroy();
    }

    @Override
    public boolean onBackPressedSupport() {
        pop();
        return true;
    }

    @Override
    protected boolean isSwipeBack() {
        return true;
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().post(false, TagConstant.HIDE_BOTTOM_TAG);

    }
}
