package com.drive.safe;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import static com.drive.safe.R.layout.popup;

/**
 * Created by mohanad.khouli on 09/11/2016.
 */

public class Popup extends FrameLayout {

    public interface Listener {
        void onCancel(final Popup popup);
        void onCloseAppSelected(final Popup popup);
    }

    private Listener listener;

    public Popup(Activity context, CharSequence message, Listener listener) {
        super(context);
        this.listener = listener;
        init(message);
    }

    public Popup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Popup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("NewApi")
    public Popup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void cancel() {
        if ( listener != null ) listener.onCancel(this);
    }

    private void init(CharSequence message) {
        View.inflate(getContext(), popup, this);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onCancel(Popup.this);
                }
            }
        });

        ((TextView) findViewById(R.id.message)).setText(message);
        findViewById(R.id.frame).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /* catch clicks happening on the frame to avoid dismissing */
            }
        });

        findViewById(R.id.cancel).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( listener != null ) listener.onCancel(Popup.this);
            }
        });

        findViewById(R.id.action).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( listener != null ) listener.onCloseAppSelected(Popup.this);
            }
        });

    }

    public void performAnimationToShow() {
       View frame = findViewById(R.id.frame);
        frame.setAnimation(AnimationUtils.loadAnimation(this.getContext(),R.anim.slide_up));
        frame.animate();

    }

    public void performAnimationToHide() {
        View frame = findViewById(R.id.frame);
        Animation animation =AnimationUtils.loadAnimation(this.getContext(),R.anim.slide_down);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ((ViewGroup)Popup.this.getParent()).removeView(Popup.this);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //frame.setAnimation(animation);
        frame.startAnimation(animation);

    }



}

