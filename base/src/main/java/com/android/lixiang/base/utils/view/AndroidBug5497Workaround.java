package com.android.lixiang.base.utils.view;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.BarUtils;

//public class AndroidBug5497Workaround {
//
//    // For more information, see https://code.google.com/p/android/issues/detail?id=5497
//    // To use this class, simply invoke assistActivity() on an Activity that already has its content view set.
//
//    private View mChildOfContent;
//    private int usableHeightPrevious;
//    private FrameLayout.LayoutParams frameLayoutParams;
//    private static int s;
//
//
//    public static void assistActivity(Activity activity, int statusBarHeight) {
//        s = statusBarHeight;
//        new AndroidBug5497Workaround(activity);
//    }
//
//
//    private AndroidBug5497Workaround(Activity activity) {
//        FrameLayout content = (FrameLayout) activity.findViewById(android.R.id.content);
//        mChildOfContent = content.getChildAt(0);
//        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            public void onGlobalLayout() {
//                possiblyResizeChildOfContent();
//            }
//        });
//        frameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent.getLayoutParams();
//    }
//
//    private void possiblyResizeChildOfContent() {
//        int usableHeightNow = computeUsableHeight();
//        if (usableHeightNow != usableHeightPrevious) {
//            int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
//            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
//            if (heightDifference > (usableHeightSansKeyboard/4)) {
//                // keyboard probably just became visible
//                frameLayoutParams.height = usableHeightSansKeyboard - heightDifference + s;
//            } else {
//                // keyboard probably just became hidden
//                frameLayoutParams.height = usableHeightSansKeyboard;
//            }
//            mChildOfContent.requestLayout();
//            usableHeightPrevious = usableHeightNow;
//        }
//    }
//
//    private int computeUsableHeight() {
//        Rect r = new Rect();
//        mChildOfContent.getWindowVisibleDisplayFrame(r);
//        return (r.bottom - r.top);// 全屏模式下： return r.bottom
//    }
//
//}

public class AndroidBug5497Workaround {
    public static void assistActivity(Activity activity) {
        new AndroidBug5497Workaround(activity);
    }
    private View mChildOfContent;
    private int usableHeightPrevious;
    private FrameLayout.LayoutParams frameLayoutParams;
    private int contentHeight;
    private boolean isfirst = true;
    private Activity activity;
    private int statusBarHeight;
    private AndroidBug5497Workaround(Activity activity) {
//获取状态栏的高度
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
        this.activity = activity;
        FrameLayout content = (FrameLayout)activity.findViewById(android.R.id.content);
        mChildOfContent = content.getChildAt(0);
//界面出现变动都会调用这个监听事件
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                if (isfirst) {
                    contentHeight = mChildOfContent.getHeight();//兼容华为等机型
                    isfirst = false;
                }
                possiblyResizeChildOfContent();
            }
        });
        frameLayoutParams = (FrameLayout.LayoutParams)
                mChildOfContent.getLayoutParams();
    }
    //重新调整跟布局的高度
    private void possiblyResizeChildOfContent() {
        int usableHeightNow = computeUsableHeight();
//当前可见高度和上一次可见高度不一致 布局变动
        if (usableHeightNow != usableHeightPrevious) {
//int usableHeightSansKeyboard2 = mChildOfContent.getHeight();//兼容华为等机型
            int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            if (heightDifference > (usableHeightSansKeyboard / 4)) {
// keyboard probably just became visible
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
//frameLayoutParams.height = usableHeightSansKeyboard - heightDifference;
                    frameLayoutParams.height = usableHeightSansKeyboard - heightDifference + statusBarHeight;
                } else {
                    frameLayoutParams.height = usableHeightSansKeyboard -heightDifference;
                }
            } else {
                frameLayoutParams.height = contentHeight;
            }
            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
    }
    /** * 计算mChildOfContent可见高度 ** @return */
    private int computeUsableHeight() {
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        return (r.bottom - r.top);
    }
}


