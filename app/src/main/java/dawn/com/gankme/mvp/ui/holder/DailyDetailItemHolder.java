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

import android.text.TextPaint;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;

import butterknife.BindView;
import dawn.com.gankme.R;
import dawn.com.gankme.mvp.model.entity.DailyTitle;
import dawn.com.gankme.mvp.model.entity.GanHuoData;

/**
 * ================================================
 * 展示 {@link BaseHolder} 的用法
 * <p>
 * Created by JessYan on 9/4/16 12:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class DailyDetailItemHolder extends BaseHolder<Object> {


    @BindView(R.id.tv_title)
    TextView mTitle;

    public DailyDetailItemHolder(View itemView) {
        super(itemView);

    }

    @Override
    public void setData(Object data, int position) {

        TextPaint tp = mTitle.getPaint();
        if(data  instanceof DailyTitle){
            mTitle.setText(((DailyTitle)data).getTitle());
            mTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP,17);

            tp.setFakeBoldText(true);
        }else if(data instanceof GanHuoData){
            mTitle.setText(((GanHuoData)data).getDesc());
            mTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
            tp.setFakeBoldText(false);
            //设置text左边显示图标
        }

    }


    @Override
    protected void onRelease() {

    }


}
