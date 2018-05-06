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
package dawn.com.gankme.di.module;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.FragmentScope;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import dawn.com.gankme.mvp.contract.ArticleListContract;
import dawn.com.gankme.mvp.contract.GankListContract;
import dawn.com.gankme.mvp.model.ArticleListModel;
import dawn.com.gankme.mvp.model.GankListModel;
import dawn.com.gankme.mvp.model.entity.Article;
import dawn.com.gankme.mvp.model.entity.Daily;
import dawn.com.gankme.mvp.ui.adapter.ArticleAdapter;
import dawn.com.gankme.mvp.ui.adapter.HomeAdapter;

/**
 * ================================================
 * 展示 Module 的用法
 *
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki#2.4.5">Module wiki 官方文档</a>
 * Created by JessYan on 09/04/2016 11:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
@Module
public class ArticleListModule {
    private ArticleListContract.View view;

    /**
     * 构建 UserModule 时,将 View 的实现类传进来,这样就可以提供 View 的实现类给 Presenter
     *
     * @param view
     */
    public ArticleListModule(ArticleListContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    ArticleListContract.View provideUserView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    ArticleListContract.Model provideUserModel(ArticleListModel model) {
        return model;
    }

    @FragmentScope
    @Provides
    RxPermissions provideRxPermissions() {
        return new RxPermissions(view.getActivity());
    }

    @FragmentScope
    @Provides
    RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(view.getActivity());
    }

    @FragmentScope
    @Provides
    List<Article> provideDailyList() {
        return new ArrayList<>();
    }

    @FragmentScope
    @Provides
    RecyclerView.Adapter provideDailyAdapter(List<Article> list){
        return new ArticleAdapter(list);
    }
}
