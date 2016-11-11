package com.splitseed.accessors;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.graphics.Color;
import com.splitseed.view.View;

public class ViewAccessor implements TweenAccessor<View> {

    public static final int ALPHA = 1;
    public static final int COLOR = 2;

    @Override
    public int getValues(View target, int tweenType, float[] returnValues) {
        switch(tweenType) {
            case ALPHA:
                returnValues[0] = target.getAlpha();
                return 1;

            case COLOR:
                Color c = target.getBackground();
                returnValues[0] = c.r;
                returnValues[1] = c.g;
                returnValues[2] = c.b;
                returnValues[3] = c.a;
                return 4;

            default:
                assert false;
                return -1;
        }
    }

    @Override
    public void setValues(View target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case ALPHA:
                target.setAlpha(newValues[0]);
                break;

            case COLOR:
                target.setBackground(newValues[0], newValues[1], newValues[2], newValues[3]);
                break;

            default:
                assert false;
        }
    }
}
