/**
 * Copyright 2017 JessYan
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dawn.com.gankme.mvp.presenter;

import android.app.Application;
import android.media.MediaScannerConnection;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.base.delegate.IFragment;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import dawn.com.gankme.app.utils.FileUtils;
import dawn.com.gankme.mvp.model.subscriber.DownLoadSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import dawn.com.gankme.mvp.contract.DailyContract;
import dawn.com.gankme.mvp.model.entity.DailyList;
import dawn.com.gankme.mvp.model.entity.DailyTitle;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import timber.log.Timber;

/**
 * ================================================
 * 展示 Presenter 的用法
 *
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki#2.4.4">Presenter wiki 官方文档</a>
 * Created by JessYan on 09/04/2016 10:59
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
@FragmentScope
public class DailyDetailPresenter extends BasePresenter<DailyContract.Model, DailyContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;
    private RecyclerView.Adapter mAdapter;
    private List<Object> list;
    private String path;


    @Inject
    public DailyDetailPresenter(DailyContract.Model model, DailyContract.View rootView, RxErrorHandler handler
            , AppManager appManager, Application application, List<Object> list, RecyclerView.Adapter adapter) {

        super(model, rootView);
        this.mApplication = application;
        this.mErrorHandler = handler;
        this.mAppManager = appManager;
        this.list = list;
        this.mAdapter = adapter;
    }


    public void requestData(String date) {
        mModel.getRecentlyGanHuo(date)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<DailyList>(mErrorHandler) {

                    @Override
                    public void onNext(DailyList recentlyBean) {

                        if (recentlyBean != null) {


                            if (recentlyBean.get休息视频() != null) {
                                list.add(new DailyTitle("休息视频"));
                                list.addAll(recentlyBean.get休息视频());
                            }
                            if (recentlyBean.getAndroid() != null) {
                                list.add(new DailyTitle("Android"));
                                list.addAll(recentlyBean.getAndroid());
                            }
                            if (recentlyBean.getIOS() != null) {
                                list.add(new DailyTitle("iOS"));
                                list.addAll(recentlyBean.getIOS());
                            }
                            if (recentlyBean.get前端() != null) {
                                list.add(new DailyTitle("前端"));
                                list.addAll(recentlyBean.get前端());
                            }
                            if (recentlyBean.getApp() != null) {
                                list.add(new DailyTitle("App"));
                                list.addAll(recentlyBean.getApp());
                            }
                            if (recentlyBean.get瞎推荐() != null) {
                                list.add(new DailyTitle("瞎推荐"));
                                list.addAll(recentlyBean.get瞎推荐());
                            }


                        }

                        //通知显示 HeaderImg
                        ((IFragment) mRootView).setData(null);
                        mAdapter.notifyDataSetChanged();

                    }
                });
    }

    public void downLoadImg(String url) {

        mModel.downLoadImg(url)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new DownLoadSubscribe(FileUtils.getSaveImagePath(mApplication), FileUtils.getFileName(url)) {


                    @Override
                    public void onProgress(double progress, long downloadByte, long totalByte) {

                    }

                    @Override
                    public void onCompleted(File file) {
                        if (file != null) {
                            ArmsUtils.snackbarText("图片保存成功：" + file.getAbsolutePath());
                            MediaScannerConnection.scanFile(mApplication, new String[]{
                                            file.getAbsolutePath()},
                                    null, null);
                        }
                    }

                });

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mAdapter = null;
        this.list = null;
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mApplication = null;
    }
}
