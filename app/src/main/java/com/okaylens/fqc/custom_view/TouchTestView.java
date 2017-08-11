package com.okaylens.fqc.custom_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by charleszhang on 2017/6/1.
 */

public class TouchTestView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    public static final int TIME_IN_FRAME = 50;
    Paint mPaint = null;
    Paint mTextPaint = null;
    SurfaceHolder mSurfaceHolder = null;
    boolean mRunning = false;
    Canvas mCanvas = null;
    private Path mPath;
    private float mPosX, mPosY;


    public TouchTestView(Context context) {
        super(context);
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        mSurfaceHolder = this.getHolder();
        mSurfaceHolder.addCallback(this);
        mCanvas = new Canvas();
        mPaint = new Paint();

        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(6);
        mPath = new Path();
        mTextPaint = new Paint();
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(15);
    }

    public TouchTestView(Context context,AttributeSet attrs){
        super(context,attrs);
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        mSurfaceHolder = this.getHolder();
        mSurfaceHolder.addCallback(this);
        mCanvas = new Canvas();
        mPaint = new Paint();

        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(6);
        mPath = new Path();
        mTextPaint = new Paint();
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(15);
    }
    /*
    public TouchTestView(Context context, AttributeSet attrs, int defStyle){
        super(context);
// TODO Auto-generated constructor stub
    }
    */

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.quadTo(mPosX, mPosY, x, y);
                break;
            case MotionEvent.ACTION_UP:
                //mPath.reset();
                break;
        }
        //记录当前触摸点得当前得坐标
        mPosX = x;
        mPosY = y;
        return true;
    }

    private void Fix_onDraw() {
        mCanvas.drawColor(Color.WHITE);
        //绘制曲线
        mCanvas.drawPath(mPath, mPaint);
        mCanvas.drawText("当前触笔X：" + mPosX, 0, 20, mTextPaint);
        mCanvas.drawText("当前触笔Y:" + mPosY, 0, 40, mTextPaint);
    }

    public void run() {
// TODO Auto-generated method stub
        while (mRunning) {
            long startTime = System.currentTimeMillis();
            synchronized (mSurfaceHolder) {
                mCanvas = mSurfaceHolder.lockCanvas();
                Fix_onDraw();
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            }
            long endTime = System.currentTimeMillis();
            int diffTime = (int) (endTime - startTime);
            while (diffTime <= TIME_IN_FRAME) {
                diffTime = (int) (System.currentTimeMillis() - startTime);
                Thread.yield();
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
// TODO Auto-generated method stub
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mRunning = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
// TODO Auto-generated method stub
        mRunning = false;
    }

}