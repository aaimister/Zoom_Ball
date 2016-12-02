package com.splitseed.objects;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.splitseed.accessors.SpriteAccessor;
import com.splitseed.util.Assets;
import com.splitseed.zoomball.Etheric;

public class Portal extends SpriteObjectAdapter {

    public static final float DEFAULT_SIZE = 40 * Etheric.SCALE_Y;

    public Portal(Assets assets, final TweenManager tweenManager, float x, float y, float width, float height) {
        super(assets, tweenManager, x, y, width, height);
    }

    private final TweenCallback rotationCallback = new TweenCallback() {
        @Override
        public void onEvent(int type, BaseTween<?> source) {
            setRotation(0);
            Tween.to(Portal.this, SpriteAccessor.ROTATION, 3.0f).target(360).ease(TweenEquations.easeNone).setCallback(rotationCallback).start(tweenManager);
        }
    };

    public void startRotation() {
        Tween.call(rotationCallback).start(tweenManager);
    }

    public void stopRotation() {
        tweenManager.killTarget(this);
    }

    @Override
    public void drawSpriteBatch(SpriteBatch spriteBatch, float runTime) {
        spriteBatch.setColor(getColor());
        spriteBatch.draw(assets.rest[0], getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    public void reset(float x, float y, float width, float height) {
        setRotation(0);
        setBounds(x, y, width, height);
        setOriginCenter();
    }

}
