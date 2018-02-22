package dawn.com.gankme.app.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * Bye Bye Burger Navigation Bar Behavior
 *
 * Created by wing on 11/5/16.
 */

public class ByeBurgerBottomBehavior extends ByeBurgerBehavior {

  private TranslateAnimateHelper mAnimateHelper;

  public ByeBurgerBottomBehavior(Context context, AttributeSet attrs) {
    super(context, attrs);
  }



  //在scrolling View获得滚动事件前调用，它允许你消费部分或者全部的事件信息。
  @Override
  public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target,
                                int dx, int dy, int[] consumed) {

    if (isFirstMove) {
      isFirstMove = false;
      mAnimateHelper = TranslateAnimateHelper.get(child);
      mAnimateHelper.setStartY(child.getY());
      mAnimateHelper.setMode(TranslateAnimateHelper.MODE_BOTTOM);
    }
    if (Math.abs(dy) > mTouchSlop) {
      if (dy < 0) {

        if (mAnimateHelper.getState() == TranslateAnimateHelper.STATE_HIDE) {
          mAnimateHelper.show();
        }
      } else if (dy > 0) {
        if (mAnimateHelper.getState() == TranslateAnimateHelper.STATE_SHOW) {
          mAnimateHelper.hide();
        }
      }
    }
  }
}