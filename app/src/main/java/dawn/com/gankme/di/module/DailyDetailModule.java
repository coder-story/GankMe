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
import dawn.com.gankme.mvp.contract.DailyContract;
import dawn.com.gankme.mvp.model.DailyDetailModel;
import dawn.com.gankme.mvp.ui.adapter.DailyDetailAdapter;


@Module
public class DailyDetailModule {
    private DailyContract.View view;

    /**
     * 构建 UserModule 时,将 View 的实现类传进来,这样就可以提供 View 的实现类给 Presenter
     *
     * @param view
     */
    public DailyDetailModule(DailyContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    DailyContract.View provideUserView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    DailyContract.Model provideUserModel(DailyDetailModel model) {
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
    List<Object> provideDailyList() {
        return new ArrayList<>();
    }

    @FragmentScope
    @Provides
    RecyclerView.Adapter provideDailyAdapter(List<Object> list){
        return new DailyDetailAdapter(list);
    }
}
