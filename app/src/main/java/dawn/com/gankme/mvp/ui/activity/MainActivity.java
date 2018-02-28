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
package dawn.com.gankme.mvp.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import org.simple.eventbus.Subscriber;

import butterknife.BindView;
import dawn.com.gankme.R;
import dawn.com.gankme.app.constants.TagConstant;
import dawn.com.gankme.mvp.ui.BaseSupportActivity;
import dawn.com.gankme.mvp.ui.fragment.GirlsFragment;
import dawn.com.gankme.mvp.ui.fragment.HomeFragment;
import dawn.com.gankme.mvp.ui.fragment.MineFragment;
import me.yokeyword.fragmentation.SupportFragment;


/**
 * ================================================
 * 单Aty+多Frg
 * ================================================
 */
public class MainActivity extends BaseSupportActivity {

    @BindView(R.id.frame_content)
    FrameLayout frameContent;
    @BindView(R.id.ll_bottom)
    LinearLayout ll_bottom;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;

    private long lastBackKeyDownTick = 0;
    private static final long MAX_DOUBLE_BACK_DURATION = 1500;

    private SupportFragment[] mFragments = new SupportFragment[3];


    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        //ArmsUtils.statuInScreen(this);//开启沉浸式

        switchFragment(0);
        setupView();

    }

    private void setupView() {
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_home:
                        switchFragment(0);
                        break;
                    case R.id.item_girls:
                        switchFragment(1);
                        break;
                    case R.id.item_mine:
                        switchFragment(2);
                        break;
                    default:
                        break;
                }
                return true;//如果返回 false，则你的点击会被取消，也就是不会切换到下一个菜单
            }
        });
    }

    private void switchFragment(int index) {

        SupportFragment firstFrg = findFragment(HomeFragment.class);
        if (firstFrg == null) {
            mFragments[FIRST] = HomeFragment.newInstance();
            mFragments[SECOND] = GirlsFragment.newInstance();
            mFragments[THIRD] = MineFragment.newInstance();
            loadMultipleRootFragment(R.id.frame_content, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD]);

        } else {
            mFragments[FIRST] = firstFrg;
            mFragments[SECOND] = findFragment(GirlsFragment.class);
            mFragments[THIRD] = findFragment(MineFragment.class);
        }
        showHideFragment(mFragments[index]);


    }


    @Override
    public void onBackPressedSupport() {


        long currentTick = System.currentTimeMillis();
        if (currentTick - lastBackKeyDownTick > MAX_DOUBLE_BACK_DURATION) {
            ArmsUtils.snackbarText(getString(R.string.double_click_exit));
            lastBackKeyDownTick = currentTick;
        } else {
            finish();
        }

    }

    @Subscriber(tag = TagConstant.HIDE_BOTTOM_TAG)
    private void updateUserWithTag(boolean isHide) {
        if (isHide) {
            ll_bottom.setVisibility(View.GONE);
        } else {
            ll_bottom.setVisibility(View.VISIBLE);
        }
    }


}
