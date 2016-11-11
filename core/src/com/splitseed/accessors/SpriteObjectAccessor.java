package com.splitseed.accessors;

import aurelienribon.tweenengine.TweenAccessor;
import com.splitseed.objects.SpriteObject;

public class SpriteObjectAccessor implements TweenAccessor<SpriteObject> {

    private SpriteAccessor spriteAccessor = new SpriteAccessor();

    public static final int ROTATION = 6;

    public int getValues(SpriteObject target, int tweenType, float[] returnValues) {
        switch(tweenType) {
            case ROTATION:
                returnValues[0] = target.getRotation();
                return 1;

            default:
                return spriteAccessor.getValues(target, tweenType, returnValues);
        }
    }

    public void setValues(SpriteObject target, int tweenType, float[] newValues) {
        switch(tweenType) {
            case ROTATION:
                target.setRotation(newValues[0]);
                break;

            default:
                spriteAccessor.setValues(target, tweenType, newValues);
        }
    }
}
