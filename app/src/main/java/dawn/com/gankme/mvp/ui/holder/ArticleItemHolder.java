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
package dawn.com.gankme.mvp.ui.holder;

import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import dawn.com.gankme.R;
import dawn.com.gankme.mvp.model.entity.Article;
import dawn.com.gankme.mvp.model.entity.Daily;

/**
 * ================================================
 * 展示 {@link BaseHolder} 的用法
 * <p>
 * Created by JessYan on 9/4/16 12:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class ArticleItemHolder extends BaseHolder<Article> {


    @BindView(R.id.tv_name)
    TextView mName;
    @BindView(R.id.tv_type)
    TextView mType;
    private AppComponent mAppComponent;


    public ArticleItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到 Context 的地方,拿到 AppComponent,从而得到用 Dagger 管理的单例对象
        mAppComponent = ArmsUtils.obtainAppComponentFromContext(itemView.getContext());
    }

    @Override
    public void setData(Article data, int position) {


        //itemView 的 Context 就是 Activity, Glide 会自动处理并和该 Activity 的生命周期绑定

        mName.setText(Html.fromHtml(data.getTitle()));
        mType.setText(data.getChapterName());
    }


    @Override
    protected void onRelease() {

    }


}
