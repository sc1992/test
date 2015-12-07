package com.example.sc.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;
import android.widget.ListAdapter;

import com.example.sc.adapter.AutoAdapter;

/**
 * Created by sc on 15-9-30.
 */
public class ExpandGridView extends GridView{
    public ExpandGridView(Context context) {
        super(context);
    }

    public ExpandGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
//        return super.onTouchEvent(ev);
        return  false;
    }

    public AutoAdapter getAutoAdapter(){
        ListAdapter adapter=getAdapter();
        if(adapter instanceof AutoAdapter){
            return  (AutoAdapter) adapter;
        }
        return null;
    }

}
