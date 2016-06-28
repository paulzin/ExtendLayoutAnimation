package com.paulzin.extendlayoutanimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private View lineView;
    private View firstImage;
    private View secondImage;

    boolean isDetailsViewShown;
    int lineHeight = 400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lineView = findViewById(R.id.lineView);
        firstImage = findViewById(R.id.firstImage);
        secondImage = findViewById(R.id.secondImage);

        findViewById(R.id.imageLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleAnimation();
            }
        });
    }

    private void toggleAnimation() {
        if (isDetailsViewShown) {
            hideDetailedLayout();
        } else {
           showDetailedLayout();
        }

        isDetailsViewShown = !isDetailsViewShown;
    }

    private void showDetailedLayout() {
        lineView.setVisibility(View.VISIBLE);

        setHeight(lineView, 0);

        ValueAnimator animator = ValueAnimator.ofInt(0, lineHeight);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setHeight(lineView, (int) animation.getAnimatedValue());
            }
        });
        animator.setInterpolator(new FastOutLinearInInterpolator());
        animator.setDuration(300);
        animator.start();

        showImageView(firstImage, 30);
        showImageView(secondImage, 160);
    }

    private void hideDetailedLayout() {
        ValueAnimator animator = ValueAnimator.ofInt(lineHeight, 0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setHeight(lineView, (int) animation.getAnimatedValue());
            }
        });
        animator.setDuration(300);
        animator.start();

        hideImageView(firstImage, 100);
        hideImageView(secondImage, 20);
    }

    private void showImageView(View view, long delay) {
        view.setVisibility(View.VISIBLE);
        view.setScaleX(0);
        view.setScaleY(0);
        view.setAlpha(1);
        view.animate().scaleY(1).scaleX(1).setStartDelay(delay).start();
    }

    private void hideImageView(View view, long delay) {
        view.animate().scaleY(0).scaleX(0).alpha(0).setStartDelay(delay).start();
    }

    private void setHeight(View view, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);
    }

    void enterReveal() {
        final View myView = findViewById(R.id.detailsLayout);

        int cx = myView.getMeasuredWidth() / 2;
        int cy = 0;

        int finalRadius = Math.max(myView.getWidth(), myView.getHeight()) / 2;

        Animator anim =
                ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);

        myView.setVisibility(View.VISIBLE);
        anim.start();
    }

    void exitReveal() {
        final View myView = findViewById(R.id.detailsLayout);

        int cx = myView.getMeasuredWidth() / 2;
        int cy = 0;

        int initialRadius = myView.getWidth() / 2;

        Animator anim =
                ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 0);

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                myView.setVisibility(View.INVISIBLE);
            }
        });
        anim.start();
    }
}
