package com.ttinc.android.apps.heartemiterview.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ttinc.android.apps.heartemiterview.R;

import java.util.Random;


/**
 * Created by thangn on 3/7/17.
 */

public class HeartEmitterView extends RelativeLayout {
    private int mHeartSize;

    public HeartEmitterView(Context context) {
        super(context);
        init(context);
    }

    public HeartEmitterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HeartEmitterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void emitImage(int resId) {
        try {
            final ImageView img = generateHeartView(resId);
            if (img != null) {
                addView(img, getParams());
                AnimationSet animation = (AnimationSet) AnimationUtils.loadAnimation(getContext(), R.anim.anim_heart_fly);
                generateTranslateAnim(animation);

                Animation animTY = animation.getAnimations().get(1);
                long transYDur = Math.max(3600, new Random().nextInt(4000));
                animTY.setDuration(transYDur);

                Animation animA = animation.getAnimations().get(2);
                animA.setStartOffset(Math.max(0, transYDur - animA.getDuration()));

                img.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        removeHeart(img);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
            }
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
    }

    private void init(Context context) {
        this.mHeartSize = context.getResources().getDimensionPixelSize(R.dimen.heart_size);
    }

    private void removeHeart(final ImageView img) {
        post(new Runnable() {
            @Override
            public void run() {
                removeView(img);
            }
        });
    }

    private void generateTranslateAnim(AnimationSet animationSet) {

        Random random = new Random();
        boolean firstLeft = random.nextInt() % 2 == 0;
        int deltaX = random.nextInt(this.mHeartSize / 2);
        deltaX = firstLeft ? deltaX : -deltaX;

        long firstDur = Math.max(1200, random.nextInt(2000));
        Animation first = new TranslateAnimation(0, deltaX, 0, 0);
        first.setDuration(firstDur);
        first.setStartOffset(500);
        animationSet.addAnimation(first);
    }

    private ImageView generateHeartView(int resId) {
        try {
            ImageView view = new ImageView(getContext());
            view.setImageResource(resId);
            return view;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    private LayoutParams getParams() {
        LayoutParams params = new LayoutParams(this.mHeartSize, this.mHeartSize);
        params.rightMargin = 0;
        params.bottomMargin = 0;
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        return params;
    }
}
