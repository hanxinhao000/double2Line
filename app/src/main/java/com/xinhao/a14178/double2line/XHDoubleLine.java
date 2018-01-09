package com.xinhao.a14178.double2line;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * XINHAO_HAN双曲线
 */

public class XHDoubleLine extends View {
    private Context context;
    //画笔
    private Paint paint;
    //路径
    private Path path;
    //View的高
    private int viewH;
    //View的宽
    private int viewW;
    //起点的X放向
    private int startX = -30;
    //起点的Y放向
    private int startY = 120;
    //控制点1的X方向
    private int k1X = viewW / 4;
    //控制点1的Y方向
    private int k1Y = 60;
    //控制点2的X方向
    private int k2X = viewW - (viewW / 4);
    //控制点2的Y方向
    private int k2Y = startY + 60;
    //终点X
    private int endX = viewW + 30;
    //终点Y
    private int endY = startY;
    //倍数值
    private int sizeInt = 1;//放大5倍
    //控制View移动的变量
    private int moveView = -1;
    //主线程切换
    private Handler handler;


    //起点的X放向
    private int startX2 = -30;
    //起点的Y放向
    private int startY2 = 120;
    //控制点1的X方向
    private int k1X2 = viewW / 4;
    //控制点1的Y方向
    private int k1Y2 = 60;
    //控制点2的X方向
    private int k2X2 = viewW - (viewW / 4);
    //控制点2的Y方向
    private int k2Y2 = startY + 60;
    //终点X
    private int endX2 = viewW + 30;
    //终点Y
    private int endY2 = startY;


    public XHDoubleLine(Context context) {
        super(context);
        initView(context);
    }

    public XHDoubleLine(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public XHDoubleLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    //初始化
    private void initView(Context context) {
        this.context = context;
        paint = new Paint();
        path = new Path();
        paint.setColor(Color.parseColor("#AFDEE4"));
        handler = new Handler();
        // invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        viewH = h;
        viewW = w;
        startNumber();
        startNewThread();
    }

    //开始计算值
    private void startNumber() {

        if (indexH == -1) {
            //第一个曲线

            //起点的X放向
            startX = -30;
            //起点的Y放向
            startY = 120;
            //控制点1的X方向
            k1X = viewW / 4;
            //控制点1的Y方向
            k1Y = 0;
            //控制点2的X方向
            k2X = viewW - (viewW / 4);
            //控制点2的Y方向
            k2Y = startY + 120;
            //终点X
            endX = viewW + 30;
            //终点Y
            endY = startY;

            //第二个取曲线

            //起点的X放向
            startX2 = endX;
            //起点的Y放向
            startY2 = endY;
            //控制点1的X方向
            k1X2 = ((viewW / 4) + viewW);
            //控制点1的Y方向
            k1Y2 = k1Y;
            //控制点2的X方向
            k2X2 = ((viewW - (viewW / 4)) + viewW);
            //控制点2的Y方向
            k2Y2 = startY + 120;
            //终点X
            endX2 = ((viewW + 30) + endX);
            //终点Y
            endY2 = startY;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    invalidate();
                }
            });
        } else {




            //第一个曲线

            //起点的X放向
            startX = -30;
            //起点的Y放向
            startY = indexH;
            //控制点1的X方向
            k1X = viewW / 4;
            //控制点1的Y方向
            k1Y = indexH - 120;
            //控制点2的X方向
            k2X = viewW - (viewW / 4);
            //控制点2的Y方向
            k2Y = indexH + 120;
            //终点X
            endX = viewW + 30;
            //终点Y
            endY = indexH;

            //第二个取曲线

            //起点的X放向
            startX2 = endX;
            //起点的Y放向
            startY2 = indexH;
            //控制点1的X方向
            k1X2 = ((viewW / 4) + viewW);
            //控制点1的Y方向
            k1Y2 = indexH - 120;
            //控制点2的X方向
            k2X2 = ((viewW - (viewW / 4)) + viewW);
            //控制点2的Y方向
            k2Y2 = indexH + 120;
            //终点X
            endX2 = ((viewW + 30) + endX);
            //终点Y
            endY2 = indexH;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    invalidate();
                }
            });
        }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //起点 画好第一段双曲线
        path.moveTo(startX, startY);
        path.cubicTo(k1X, k1Y, k2X, k2Y, endX, endY);
        //开始画第二段双曲线,从上一段末尾开始画
        path.lineTo(startX2, startY2);
        path.cubicTo(k1X2, k1Y2, k2X2, k2Y2, endX2, endY2);
        path.close();
        //闭合,不然很难看

        path.lineTo(endX2, endY2);
        path.lineTo(endX2, viewH);
        path.lineTo(startX, viewH);


        canvas.drawPath(path, paint);
        path.reset();

    }

    private int indexH = -1;

    //设置高低
    public void setIndex(int indexH) {

        this.indexH = indexH;

        startY = indexH;
        startY2 = indexH;


        k1Y = indexH - 120;
        k2Y = indexH + 120;

        k1Y2 = indexH - 120;
        k2Y2 = indexH + 120;

        endY2 = indexH;
        endY = indexH;
    }

    //启用新线程

    private void startNewThread() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            try {
                                Thread.sleep(5);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            //第一段曲线起点
                            startX = startX - 1;
                            //第一段曲线----控制点1X
                            k1X--;
                            //第一段曲线----控制点2X
                            k2X--;
                            //第一段曲线----终点X
                            endX--;


                            //第二段曲线----控制点1X
                            k1X2--;
                            //第二算曲线----控制点2X
                            k2X2--;
                            //第二段曲线----终点
                            endX2--;
                            //第二段曲线----起点
                            startX2--;

                            if (endX2 < viewW + 30) {

                                startNumber();

                            }

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    invalidate();
                                }
                            });
                        }
                    }
                }

        ).start();
    }
}
