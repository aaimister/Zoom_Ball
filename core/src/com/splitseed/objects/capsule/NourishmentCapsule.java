package com.splitseed.objects.capsule;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.splitseed.accessors.SpriteAccessor;
import com.splitseed.util.Assets;
import com.splitseed.zoomball.Etheric;

public class NourishmentCapsule extends Capsule {
    public static final float DEFAULT_SIZE = 20 * Etheric.SCALE_Y;
    public static final float DEFAULT_GROWTH = 1 * Etheric.SCALE_Y;

    private Sprite left;
    private Sprite right;


    public NourishmentCapsule(Assets assets, TweenManager tweenManager, float x, float y, float width, float height) {
        super(assets, tweenManager, x, y, width, height);
        left = new Sprite(assets.rest[8]);
        left.setBounds(x, y, width, height);
        left.setOriginCenter();
        left.setColor(assets.RED);
        right = new Sprite(assets.rest[8]);
        right.flip(true, false);
        right.setBounds(x, y, width, height);
        right.setOriginCenter();
        right.setColor(assets.BLUE);
    }

    @Override
    public void startAnimation() {
        Tween.call(rotationCallback).start(tweenManager);
        Tween.call(colorCallback).start(tweenManager);
    }

    @Override
    public void stopAnimation() {
        tweenManager.killTarget(left);
        tweenManager.killTarget(right);
    }

    @Override
    public void reset() {
        super.reset();
        left.setRotation(0);
        left.setColor(assets.RED);
        left.setScale(1);
        right.setRotation(0);
        right.setColor(assets.BLUE);
        right.setScale(1);
    }

    @Override
    public void eat() {
        if (!eaten) {
            eaten = true;
            stopAnimation();
            Timeline.createParallel()
                    .push(Tween.to(left, SpriteAccessor.ROTATION, 0.75f).target(left.getRotation() + 360).ease(TweenEquations.easeInOutQuad))
                    .push(Tween.to(right, SpriteAccessor.ROTATION, 0.75f).target(right.getRotation() + 360).ease(TweenEquations.easeInOutQuad))
                    .push(Tween.to(left, SpriteAccessor.SCALE, 0.75f).target(0, 0).ease(TweenEquations.easeInOutQuad))
                    .push(Tween.to(right, SpriteAccessor.SCALE, 0.75f).target(0, 0).ease(TweenEquations.easeInOutQuad))
                    .start(tweenManager);
        }
    }

    @Override
    public void drawSpriteBatch(SpriteBatch spriteBatch, float runTime) {
        left.draw(spriteBatch);
        right.draw(spriteBatch);
    }

    private final TweenCallback rotationCallback = new TweenCallback() {
        @Override
        public void onEvent(int type, BaseTween<?> source) {
            left.setRotation(0);
            right.setRotation(0);
            Timeline.createParallel()
                    .push(Tween.to(left, SpriteAccessor.ROTATION, 3.0f).target(360).ease(TweenEquations.easeNone))
                    .push(Tween.to(right, SpriteAccessor.ROTATION, 3.0f).target(360).ease(TweenEquations.easeNone))
                    .setCallback(rotationCallback)
                    .start(tweenManager);
        }
    };

    private final TweenCallback colorCallback = new TweenCallback() {
        @Override
        public void onEvent(int type, BaseTween<?> source) {
            left.setColor(assets.RED);
            right.setColor(assets.BLUE);
            Timeline.createSequence()
                    .pushPause(0.25f)
                    .push(Timeline.createParallel()
                            .push(Tween.to(left, SpriteAccessor.COLOR, 1.25f).target(assets.BLUE.r, assets.BLUE.g, assets.BLUE.b, 1).ease(TweenEquations.easeInOutQuad))
                            .push(Tween.to(right, SpriteAccessor.COLOR, 1.25f).target(assets.RED.r, assets.RED.g, assets.RED.b, 1).ease(TweenEquations.easeInOutQuad)))
                    .pushPause(0.25f)
                    .push(Timeline.createParallel()
                            .push(Tween.to(left, SpriteAccessor.COLOR, 1.25f).target(assets.RED.r, assets.RED.g, assets.RED.b, 1).ease(TweenEquations.easeInOutQuad))
                            .push(Tween.to(right, SpriteAccessor.COLOR, 1.25f).target(assets.BLUE.r, assets.BLUE.g, assets.BLUE.b, 1).ease(TweenEquations.easeInOutQuad)))
                    .setCallback(colorCallback)
                    .start(tweenManager);
        }
    };

    @Override
    public void setAlpha(float alpha) {
        super.setAlpha(alpha);
        if (left != null)
            left.setAlpha(alpha);
        if (right != null)
            right.setAlpha(alpha);
    }
}
