package dawn.com.gankme.aop;

import android.view.View;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Calendar;

import dawn.com.gankme.R;

/**
 * Created by mac on 2018/5/2.
 */

@Aspect
public class SingleClickAspect {
    static int TIME_TAG = R.id.click_time;
    public static final int MIN_CLICK_DELAY_TIME = 600;//间隔时间600ms

    @Pointcut("execution(@dawn.com.gankme.aop.annotations.SingleClick * *(..))")
    public void methodAnnotated() {
    }

    @Around("methodAnnotated()")
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint) throws  Throwable{
        View view=null;
        for (Object  arg :joinPoint.getArgs()){
            if(arg instanceof  View ){
                view =(View)arg;
            }
        }

        if(view!=null){
            Object  tag=view.getTag(TIME_TAG);
            long lastClickTime= ((tag != null) ? (long) tag : 0);
            long currentTime= Calendar.getInstance().getTimeInMillis();
            if(currentTime -lastClickTime >MIN_CLICK_DELAY_TIME){//过滤掉600毫秒内的连续点击
                view.setTag(TIME_TAG,currentTime);
                joinPoint.proceed();//执行原方法

            }
        }
    }
}