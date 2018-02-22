/**
  * Copyright 2017 JessYan
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  *      http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  */
package dawn.com.gankme.mvp.ui.holder;

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
import dawn.com.gankme.mvp.model.entity.SearchResult;
import dawn.com.gankme.mvp.model.entity.User;
import io.reactivex.Observable;
import timber.log.Timber;

/**
 * ================================================
 * 展示 {@link BaseHolder} 的用法
 * <p>
 * Created by JessYan on 9/4/16 12:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class SearchItemHolder extends BaseHolder<SearchResult> {

    @BindView(R.id.tv_title)
    TextView title;


    public SearchItemHolder(View itemView) {
        super(itemView);

    }

    @Override
    public void setData(SearchResult data, int position) {
        Timber.tag("lzx").d("data:"+data.getDesc());
        title.setText(data.getDesc());

    }


    @Override
    protected void onRelease() {

    }
}
