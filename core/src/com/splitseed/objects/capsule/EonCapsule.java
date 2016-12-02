package com.splitseed.objects.capsule;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.splitseed.util.Assets;

public class EonCapsule extends Capsule {


    public EonCapsule(Assets assets, TweenManager tweenManager, float x, float y, float width, float height) {
        super(assets, tweenManager, x, y, width, height);
    }

    @Override
    public void startAnimation() {

    }

    @Override
    public void stopAnimation() {

    }

    @Override
    public void eat() {
        if (!eaten) {
            eaten = true;
        }
    }

    @Override
    public void drawSpriteBatch(SpriteBatch spriteBatch, float runTime) {
        spriteBatch.draw(assets.zoomBallLogo[0], getX(), getY(), getWidth(), getHeight());
    }
}
