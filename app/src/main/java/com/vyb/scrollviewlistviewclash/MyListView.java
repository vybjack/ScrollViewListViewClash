package com.vyb.scrollviewlistviewclash;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;
import android.widget.ScrollView;

/**
 * Created by loovee on 2017/6/19.
 */

public class MyListView extends ListView {

    private ScrollView parent;

    public MyListView(Context context) {
        this(context,null,0);
    }

    public MyListView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                setParentScrollAble(false);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                setParentScrollAble(true);
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }
    private void setParentScrollAble(boolean scrollAble){
        parent.requestDisallowInterceptTouchEvent(!scrollAble);
    }

    public void setParentScrollView(ScrollView scrollView){
        this.parent = scrollView;
    }

}
