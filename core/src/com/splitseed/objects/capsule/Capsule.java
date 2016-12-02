package com.splitseed.objects.capsule;

import aurelienribon.tweenengine.*;
import com.splitseed.objects.SpriteObjectAdapter;
import com.splitseed.util.Assets;

public abstract class Capsule extends SpriteObjectAdapter {

    public enum CapsuleType { NOURISHMENT, EON }

    protected boolean eaten;

    public Capsule(Assets assets, TweenManager tweenManager, float x, float y, float width, float height) {
        super(assets, tweenManager, x, y, width, height);
    }

    public static Capsule getCapsule(Assets assets, TweenManager tweenManager, float x, float y, float width, float height, CapsuleType type) {
        switch(type) {
            case NOURISHMENT:
                return new NourishmentCapsule(assets, tweenManager, x, y, width, height);
            case EON:
                return new EonCapsule(assets, tweenManager, x, y, width, height);
            default:
                return null;
        }
    }

    public abstract void startAnimation();

    public abstract void stopAnimation();

    public void reset() {
        eaten = false;
    }

    public abstract void eat();

    public boolean isEaten() {
        return eaten;
    }
}
