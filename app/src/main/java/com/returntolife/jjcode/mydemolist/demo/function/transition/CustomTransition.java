package com.returntolife.jjcode.mydemolist.demo.function.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.Property;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tools.jj.tools.utils.LogUtil;

/**
 * @author: hejiajun02@lizhi.fm
 * @date: 10/19/23
 * des:
 */
public class CustomTransition extends Transition {

    private static String PROPNAME_TEXT_COLOR = "xiaweizi:changeTextColor:color";
    private static final String PROPNAME_BOUNDS = "android:changeBounds:bounds";

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    private void captureValues(TransitionValues transitionValues){
        if (transitionValues == null) return;

        View view = transitionValues.view;
        if (view.isLaidOut() || view.getWidth() != 0 || view.getHeight() != 0) {
            transitionValues.values.put(PROPNAME_BOUNDS, new Rect(view.getLeft(), view.getTop(),
                    view.getRight(), view.getBottom()));
        }
        transitionValues.values.put(PROPNAME_TEXT_COLOR, transitionValues.view.getWidth());
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        if (startValues == null || endValues == null) {
            return null;
        }
//        if (!(endValues.view instanceof TextView)) {
//            return super.createAnimator(sceneRoot, startValues, endValues);
//        }
        View endView = endValues.view;

        int startValue = (int) startValues.values.get(PROPNAME_TEXT_COLOR);
        int endValue = (int) endValues.values.get(PROPNAME_TEXT_COLOR);

        Rect startBounds = (Rect) startValues.values.get(PROPNAME_BOUNDS);
        Rect endBounds = (Rect) endValues.values.get(PROPNAME_BOUNDS);

        final int startLeft = startBounds.left;
        final int endLeft = endBounds.left;
        final int startTop = startBounds.top;
        final int endTop = endBounds.top;
        final int startRight = startBounds.right;
        final int endRight = endBounds.right;
        final int startBottom = startBounds.bottom;
        final int endBottom = endBounds.bottom;
        final int startWidth = startRight - startLeft;
        final int startHeight = startBottom - startTop;
        final int endWidth = endRight - endLeft;
        final int endHeight = endBottom - endTop;

        final ViewBounds viewBounds = new ViewBounds(endValues.view);
        Path topLeftPath = getPathMotion().getPath(startLeft, startTop,
                endLeft, endTop);
        ObjectAnimator topLeftAnimator = ObjectAnimator
                .ofObject(viewBounds, TOP_LEFT_PROPERTY, null, topLeftPath);

        Path bottomRightPath = getPathMotion().getPath(startRight, startBottom,
                endRight, endBottom);
        ObjectAnimator bottomRightAnimator = ObjectAnimator.ofObject(viewBounds,
                BOTTOM_RIGHT_PROPERTY, null, bottomRightPath);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(topLeftAnimator, bottomRightAnimator);

        set.addListener(new AnimatorListenerAdapter() {
            // We need a strong reference to viewBounds until the
            // animator ends.
            private ViewBounds mViewBounds = viewBounds;
        });

        LogUtil.d("createAnimator startValue="+startValue +"--endValue="+endValue);

//        ViewGroup.LayoutParams params = endView.getLayoutParams();
//        params.width = startTextColor;
//        params.height = startTextColor;
//        endView.setLayoutParams(params);

//        ValueAnimator animator = ValueAnimator.ofInt(startValue, endValue);
//        animator.setDuration(300);
//
//        ViewGroup.LayoutParams params = endView.getLayoutParams();
//
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
////                endView.setAlpha(1);
//                int animatedValue = (int) animation.getAnimatedValue();
//                LogUtil.d("onAnimationUpdate animatedValue="+animatedValue + "--endView="+endView.getWidth());
//
//                params.width = animatedValue;
//                params.height = animatedValue;
//                endView.setLayoutParams(params);
//            }
//        });
        return set;
    }

    private static class ViewBounds {
        private int mLeft;
        private int mTop;
        private int mRight;
        private int mBottom;
        private View mView;
        private int mTopLeftCalls;
        private int mBottomRightCalls;

        public ViewBounds(View view) {
            mView = view;
        }

        public void setTopLeft(PointF topLeft) {
            mLeft = Math.round(topLeft.x);
            mTop = Math.round(topLeft.y);
            mTopLeftCalls++;
            if (mTopLeftCalls == mBottomRightCalls) {
                setLeftTopRightBottom();
            }
        }

        public void setBottomRight(PointF bottomRight) {
            mRight = Math.round(bottomRight.x);
            mBottom = Math.round(bottomRight.y);
            mBottomRightCalls++;
            if (mTopLeftCalls == mBottomRightCalls) {
                setLeftTopRightBottom();
            }
        }

        private void setLeftTopRightBottom() {
            mView.setLeftTopRightBottom(mLeft, mTop, mRight, mBottom);
            mTopLeftCalls = 0;
            mBottomRightCalls = 0;
        }
    }

    private static final Property<ViewBounds, PointF> TOP_LEFT_PROPERTY =
            new Property<ViewBounds, PointF>(PointF.class, "topLeft") {
                @Override
                public void set(ViewBounds viewBounds, PointF topLeft) {
                    viewBounds.setTopLeft(topLeft);
                }

                @Override
                public PointF get(ViewBounds viewBounds) {
                    return null;
                }
            };

    private static final Property<ViewBounds, PointF> BOTTOM_RIGHT_PROPERTY =
            new Property<ViewBounds, PointF>(PointF.class, "bottomRight") {
                @Override
                public void set(ViewBounds viewBounds, PointF bottomRight) {
                    viewBounds.setBottomRight(bottomRight);
                }

                @Override
                public PointF get(ViewBounds viewBounds) {
                    return null;
                }
            };

}
