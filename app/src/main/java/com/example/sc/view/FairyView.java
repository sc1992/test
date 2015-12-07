package com.example.sc.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import com.example.sc.util.ViewUtil;

/**
 * Created by sc on 15-9-30.
 */
public class FairyView extends ViewGroup implements SurfaceHolder.Callback {
    private static final String TAG="FairyView";

    private SurfaceHolder holder;

    private float copyValue=1.5f;

    private ViewRunnable mViewRunnable;
    public FairyView(Context context) {
        super(context);
        init(context,null,-1);
    }

    public FairyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs,-1);
    }

    public FairyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }
    private  void init(Context context,AttributeSet attrs,int defStyleAttr){
        SurfaceView s=new SurfaceView(context,attrs,defStyleAttr);
        holder=s.getHolder();
        holder.addCallback(this);
        s.setZOrderOnTop(true);//设置画布，背景透明
        holder.setFormat(PixelFormat.TRANSLUCENT);
        mViewRunnable=new ViewRunnable(holder);
        addView(s);
        new Thread(mViewRunnable,TAG).start();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if(null!=mViewRunnable){
            synchronized (mViewRunnable){
                mViewRunnable.notifyAll();
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count=getChildCount();
        for(int i=0;i<count;i++){
            View child=getChildAt(i);
            if(child.getVisibility()!=GONE){
                child.layout(l,t,r,b);
            }
        }
    }



    public void moveView(final float x,final float y){
        if(getVisibility()==View.VISIBLE){
            mViewRunnable.move(x,y);
        }
    }

    public void copyViewAndMove(View v,final float x,final float y){
        mViewRunnable.copyView(v);
        moveView(x,y);
    }

    public void setCopyValue(final float value){
        this.copyValue=value;
    }


    public void clear(){
        copyViewAndMove(null,-1,-1);
        setVisibility(View.GONE);
    }



    class ViewRunnable implements Runnable{
        private SurfaceHolder holder;

        private boolean isRun=true;

        private float curX=-1;

        private float curY=-1;

        private float nextX=-1;

        private float nextY=-1;

        private float offset=1;

        private Bitmap bitmap;

        private int offsetX=0;

        private int offsetY=0;

        public ViewRunnable(SurfaceHolder holder){
            this.holder=holder;
            this.isRun=true;
        }

        public void move(final float x,final float y){
            if(canMove(x,curX)||canMove(y,curY)){
                Log.d(TAG,"move");
                nextX=x;
                nextY=y;
                synchronized (this){
                    this.notifyAll();
                }
            }
        }

        public void copyView(View v){
            DisplayMetrics mMetrics=getResources().getDisplayMetrics();
            float destiny=mMetrics.density;
            if(null!=bitmap){
                bitmap.recycle();
                bitmap=null;
            }
            if(null!=v){
                offsetX=(int)(v.getMeasuredWidth()*copyValue/2);
                offsetY=(int)(v.getMeasuredHeight()*copyValue/2+25*destiny+0.5f);
                bitmap= ViewUtil.convertViewToBitmap(v,v.getMeasuredWidth(),v.getMeasuredHeight(),copyValue);
            }
        }

        private boolean canMove(final float v1,final float v2){
            return Math.abs(v1-v2)>offset;
        }



        @Override
        public void run() {
            while(isRun){
                do{
                    Log.d(TAG,"run");
                    curX=nextX;
                    curY=nextY;
                    Canvas c=null;
                    try{
                        synchronized (holder){
                            c=holder.lockCanvas();
                            c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                            Paint p=new Paint();
                            p.setColor(Color.WHITE);
                            if(null!=bitmap){
                                c.drawBitmap(bitmap,curX-offsetX,curY-offsetY,p);
                                c.drawText("X = "+curX+"Y = "+curY,100,310,p);
                            }else{
                                c.drawText("Can't find drawing cache",100,310,p);
                            }
                        }

                    }catch (Exception e){
                                e.printStackTrace();
                    }finally {
                        if(c!=null){
                            holder.unlockCanvasAndPost(c);//结束锁定画布，并提交改变
                        }
                    }
                }while (canMove(nextX,curX)||canMove(nextY,curY));
                try{
                    synchronized (this){
                        this.wait();
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

        }
    }
}
