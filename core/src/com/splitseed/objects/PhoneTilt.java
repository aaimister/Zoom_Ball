package com.splitseed.objects;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.splitseed.accessors.SpriteAccessor;
import com.splitseed.util.Assets;

public class PhoneTilt extends SpriteObjectAdapter {

    private Sprite neutral;
    private Sprite tiltOne;
    private Sprite tiltTwo;

    private boolean showing;

    public PhoneTilt(Assets assets, TweenManager tweenManager, float x, float y, float width, float height, boolean vertical) {
        super(assets, tweenManager, x, y, width, height);
        neutral = new Sprite(assets.rest[3]);
        neutral.setBounds(x, y, width, height);
        neutral.setColor(0, 0, 0, 0);
        tiltOne = new Sprite(assets.rest[vertical ? 4 : 6]);
        tiltOne.setBounds(x, y, width, height);
        tiltOne.setColor(0, 0, 0, 0);
        tiltTwo = new Sprite(assets.rest[vertical ? 5 : 7]);
        tiltTwo.setBounds(x, y, width, height);
        tiltTwo.setColor(0, 0, 0, 0);
    }

    @Override
    public void drawSpriteBatch(SpriteBatch spriteBatch, float runTime) {
        neutral.draw(spriteBatch);
        tiltOne.draw(spriteBatch);
        tiltTwo.draw(spriteBatch);
    }

    public void startAnimation() {
        showing = true;
        neutral.setAlpha(0);
        tiltOne.setAlpha(0);
        tiltTwo.setAlpha(0);
        Tween.to(neutral, SpriteAccessor.ALPHA, 0.75f).target(1).ease(TweenEquations.easeInOutQuad).setCallback(tiltCallback).start(tweenManager);
    }

    private final TweenCallback tiltCallback = new TweenCallback() {
        @Override
        public void onEvent(int type, BaseTween<?> source) {
            Timeline.createSequence()
                    .pushPause(0.25f)
                    .push(Timeline.createParallel()
                            .push(Tween.to(neutral, SpriteAccessor.ALPHA, 0.25f).target(0).ease(TweenEquations.easeInCubic))
                            .push(Tween.to(tiltOne, SpriteAccessor.ALPHA, 0.35f).target(1).ease(TweenEquations.easeInOutQuad)))
                    .pushPause(0.25f)
                    .push(Timeline.createParallel()
                            .push(Tween.to(tiltOne, SpriteAccessor.ALPHA, 0.25f).target(0).ease(TweenEquations.easeInCubic))
                            .push(Tween.to(neutral, SpriteAccessor.ALPHA, 0.35f).target(1).ease(TweenEquations.easeInOutQuad)))
                    .pushPause(0.25f)
                    .push(Timeline.createParallel()
                            .push(Tween.to(neutral, SpriteAccessor.ALPHA, 0.25f).target(0).ease(TweenEquations.easeInCubic))
                            .push(Tween.to(tiltTwo, SpriteAccessor.ALPHA, 0.35f).target(1).ease(TweenEquations.easeInOutQuad)))
                    .pushPause(0.25f)
                    .push(Timeline.createParallel()
                            .push(Tween.to(tiltTwo, SpriteAccessor.ALPHA, 0.25f).target(0).ease(TweenEquations.easeInCubic))
                            .push(Tween.to(neutral, SpriteAccessor.ALPHA, 0.35f).target(1).ease(TweenEquations.easeInOutQuad)))
                    .setCallback(tiltCallback)
                    .start(tweenManager);
        }
    };

    public void stopAnimation() {
        tweenManager.killTarget(neutral);
        tweenManager.killTarget(tiltOne);
        tweenManager.killTarget(tiltTwo);
    }

    public void hide() {
        showing = false;
        stopAnimation();
        Timeline.createParallel()
                .push(Tween.to(neutral, SpriteAccessor.ALPHA, 0.75f).target(0).ease(TweenEquations.easeInOutQuad))
                .push(Tween.to(tiltOne, SpriteAccessor.ALPHA, 0.75f).target(0).ease(TweenEquations.easeInOutQuad))
                .push(Tween.to(tiltTwo, SpriteAccessor.ALPHA, 0.75f).target(0).ease(TweenEquations.easeInOutQuad))
                .start(tweenManager);
    }

    public boolean isShowing() {
        return showing;
    }

    public void reset() {
        neutral.setAlpha(0);
        tiltOne.setAlpha(0);
        tiltTwo.setAlpha(0);
    }
}
