package com.example.sc.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.sc.adapter.AutoAdapter;

/**
 * Created by sc on 15-9-30.
 */
public class AutoGridView extends RelativeLayout{
    private static final String TAG="AutoGridView";

    private ExpandGridView mGridView;

    private FairyView mFairyView;

    private boolean isMove=false;

    private boolean isKeyDown=false;

    private Context mContext;

    private LongClickRunnable mLongClickRunnable=new LongClickRunnable();

    public AutoGridView(Context context) {
        super(context);
        init(context,null,-1);
        mContext=context;
    }

    public AutoGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, -1);
        mContext=context;
    }

    public AutoGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
        mContext=context;
    }
    private void init(Context context,AttributeSet attrs,int defStyle){
        mGridView=new ExpandGridView(context,attrs,defStyle);
//        mGridView.setId(R.id.normal);
        this.addView(mGridView);

        mFairyView=new FairyView(context);
//        mFairyView.setId(R.id.none);
        this.addView(mFairyView);
        mFairyView.setVisibility(View.GONE);


    }
    public void setAdapter(ListAdapter adapter){
        mGridView.setAdapter(adapter);
    }
    public ExpandGridView getGridView(){
        return mGridView;
    }

    private void showFairyAt(View v,final float x,final float y){
        mFairyView.setVisibility(View.VISIBLE);
        mFairyView.copyViewAndMove(v,x,y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                isMove=false;
                isKeyDown=true;
                final int index=findViewPos(event);
                Toast.makeText(mContext,index+"",Toast.LENGTH_SHORT).show();
                mLongClickRunnable.setEvent(index,event);
                if(index > -1){
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(mFairyView.getVisibility()==View.VISIBLE){
                    isMove=true;
                    moveView(event);
                    mFairyView.moveView(event.getRawX(),event.getRawY());
                    return  true;

                }
                break;
            case MotionEvent.ACTION_UP:
                isMove=false;
                isKeyDown=false;
                if(mFairyView.getVisibility()==View.VISIBLE){
                    mLongClickRunnable.cancelEvent();
                    mFairyView.clear();
                    AutoAdapter autoAdapter=mGridView.getAutoAdapter();
                    if(autoAdapter!=null){
                        autoAdapter.setHidePos(-1);
                    }
                }else{

                }
                break;
            default:
                isMove=false;
                isKeyDown=false;
                break;

        }
        return super.onTouchEvent(event);
    }

    private void doMoveAnim(final int selectPos,final int movePos){
        if(selectPos!=movePos){
            int startAnim;
            int endAnim;
            //选中的index>移动到的index，从start开始往前移，end就是移动的index
            boolean moveBack=selectPos>=movePos;
            if(moveBack){
                startAnim=movePos+1;
                endAnim=selectPos+1;
            }else {
                startAnim=selectPos;
                endAnim=movePos;
            }
            if(startAnim>-1&&endAnim>-1) {
                final int count = mGridView.getCount();
                if (endAnim < count) {
                    for (int i = startAnim; i < endAnim; i++) {
                        final View view = mGridView.getChildAt(i);
                        if (null != view) {
                            int index;
                            if (moveBack) {
                                index = i - 1;
                            } else {
                                index = i + 1;
                            }
                            View copyView = getView(index);
                            if (null != copyView) {
                                final float x = copyView.getX() - view.getX();
                                final float y = copyView.getY() - view.getY();
                                TranslateAnimation t = new TranslateAnimation(x, 0f, y, 0f);
                                t.setDuration(400);
                                t.setInterpolator(interpolator);
                                view.clearAnimation();
                                view.startAnimation(t);
                            }
                        }
                    }

                }
            }

        }
    }

    private final DecelerateInterpolator interpolator=new DecelerateInterpolator(2.0f);

    private void moveView(MotionEvent event){
        final int index=findViewPos(event);
        if(index>-1){
            AutoAdapter autoAdapter=mGridView.getAutoAdapter();
            if(null!=autoAdapter){
                final int selectPos=autoAdapter.getHidePos();
                final boolean needAnim=autoAdapter.movePos(index);
                if(needAnim){//判断是否需要做移动动画
                    doMoveAnim(selectPos,index);
                }
            }
        }
    }


    private View selectView(int index){
        final int count=mGridView.getCount();
        if(index>-1&&index<count){
            AutoAdapter adapter=mGridView.getAutoAdapter();
            if(null!=adapter){
                adapter.setHidePos(index);
            }
            return  mGridView.getChildAt(index);
        }
        return null;
    }


    private View getView(int index){
        final int count=mGridView.getCount();
        if(index>-1 && index<count){
            return mGridView.getChildAt(index);
        }
        return  null;
    }

    private View getView(MotionEvent event){
        final int index =findViewPos(event);
        return  getView(index);
    }



    private int findViewPos(MotionEvent event){
        int index=-1;
        final int count=mGridView.getCount();
        final float eventX=event.getX();
        final float eventY=event.getY();
        for(int i=0;i< count; i++){
            final View view=mGridView.getChildAt(i);
            if(null !=view && view.getVisibility() == View.VISIBLE){
                float viewX=view.getX();
                float viewY=view.getY();
                if(eventX > viewX && eventY > viewY){
                    viewX+= view.getMeasuredWidth();
                    viewY+= view.getMeasuredHeight();
                    if(eventX< viewX && eventY< viewY){
                        if(view.getVisibility()==View.VISIBLE){
                            index=i;
                        }
                        break;
                    }
                }
            }
        }
        return  index;
    }

    class LongClickRunnable implements  Runnable{

        private final long longClickDelay=200;

        private MotionEvent event;

        private int selectPos=-1;

        public void setEvent(int index,MotionEvent e){
            this.selectPos=index;
            this.event=e;
            if(selectPos>-1){
                postDelayed(this,longClickDelay);
            }
        }
        public void cancelEvent(){
            this.selectPos=-1;
            removeCallbacks(this);
        }

        @Override
        public void run() {
            if(event!=null&&selectPos>-1){
                if(mFairyView.getVisibility()!=View.VISIBLE&&isKeyDown&&!isMove){
                    View view=selectView(selectPos);
                    if(null!=view){
                        showFairyAt(view,event.getRawX(),event.getRawY());
                    }
                }

            }

        }
    }
}
