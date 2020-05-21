package com.example.myapplication;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.os.Handler;
import android.os.Message;
import android.os.Process;

import androidx.annotation.Nullable;

public class PulseView extends View {

    // For painting the circles
    private Paint paint;
    private float maxRadius;
    private float currentRadius;
    private float initialRadius;
    private float pulseGap;
    Resources res = getResources();
    int color = res.getColor(R.color.turquoise);

    private float centerX;
    private float centerY;

    // For animation interaction
    private int concentration;
    private int minFade;
    private int maxFade;
    private long maxDuration;

    // For animation
    private ValueAnimator pulseAnimator;
    private Handler animationHandler;
    private float pulseOffset;
    private int initialAlpha;
    private int currentAlpha;
    private int fade;
    private long duration;

    public PulseView(Context context) {
        super(context);

        init(null);
    }

    public PulseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    public PulseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

    private void init(@Nullable AttributeSet set) {
        // For painting the circles
        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.paint.setStrokeWidth(4);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setColor(color);
        this.maxRadius = 0f;
        this.initialRadius = 0f;
        this.pulseGap = 100f;

        // For animation interaction
        this.animationHandler = animationHandler;
        this.concentration = 1;
        this.minFade = 40;
        this.maxFade = 100;
        this.maxDuration = 1500L;

        // For animating the circles
        this.pulseOffset = 0f;
        this.duration = maxDuration / concentration;
        this.initialAlpha = 255;
        this.fade = minFade + ((maxFade - minFade) / concentration);

        // Cause safety first
        if (set == null) {
            return;
        }
    }

    // Setter for changing the concentration level depending on EEG
    public void setConcentration(int concentration) {
        this.concentration = concentration;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        pulseAnimator = ValueAnimator.ofFloat(0f, pulseGap);
        pulseAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator updatedAnimation) {
                pulseOffset = (float) updatedAnimation.getAnimatedValue();
                postInvalidateOnAnimation();
            }
        });
        pulseAnimator.setDuration(duration);
        pulseAnimator.setRepeatMode(ValueAnimator.RESTART);
        pulseAnimator.setRepeatCount(ValueAnimator.INFINITE);
        pulseAnimator.setInterpolator(new LinearInterpolator());

        pulseAnimator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        pulseAnimator.cancel();
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        animationHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                do {
                    paint.setAlpha(currentAlpha);
                    currentAlpha -= fade;

                    canvas.drawCircle(centerX, centerY, currentRadius, paint);
                    currentRadius += pulseGap;
                } while (currentRadius < maxRadius);
            }
        };

        Runnable animationLoop = new Runnable() {
            @Override
            public void run() {

                Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

                maxRadius = getWidth() / 3 * 2;
                centerX = getX() + getWidth() / 2;
                centerY = getY() + getHeight() / 2;

                currentRadius = initialRadius + pulseOffset;
                currentAlpha = initialAlpha;

                animationHandler.sendEmptyMessage(0);
            }
        };

        Thread animationLoopThread = new Thread(animationLoop);
        animationLoopThread.start();
    }
}