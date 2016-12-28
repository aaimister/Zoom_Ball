package com.splitseed.objects.functions;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.splitseed.accessors.SpriteAccessor;
import com.splitseed.objects.button.Button;
import com.splitseed.objects.button.ButtonListener;
import com.splitseed.util.Assets;

public class Tap extends Button implements Function {

    private Circle circle;

    private boolean begin;

    private int maxTaps;
    private int tapCount;

    public Tap(Assets assets, TweenManager tweenManager, ButtonListener listener, float x, float y, float width, float height, int tapCount) {
        super(assets, tweenManager, listener, x, y, width, height);
        maxTaps = tapCount;
        circle = new Circle(assets, tweenManager, x, y, width, height);
        circle.setAlpha(0);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (begin && getBoundingCircle().contains(screenX, screenY)) {
            tapCount++;
            if (tapCount >= maxTaps) {
                begin = false;
                listener.buttonDown(this);
                return true;
            } else {
                stop();
                begin();
            }
        }
        return false;
    }

    @Override
    public void drawSpriteBatch(SpriteBatch spriteBatch, float runTime) {
        circle.drawSpriteBatch(spriteBatch, runTime);
    }

    @Override
    public void reset() {
        stop();
        tapCount = 0;
    }

    @Override
    public void begin() {
        begin = true;
        blink().start(tweenManager);
    }

    @Override
    public void stop() {
        begin = false;
        tweenManager.killTarget(circle);
        circle.setAlpha(0);
    }

    private Timeline blink() {
        Timeline t = Timeline.createSequence();
        if (tapCount < maxTaps) {
            float fadeTime = 0.25f;
            t.push(Tween.to(circle, SpriteAccessor.ALPHA, fadeTime).target(1).ease(TweenEquations.easeInOutQuad));
            for (int i = 1; i < (maxTaps - tapCount); i++) {
                t.push(Tween.to(circle, SpriteAccessor.ALPHA, fadeTime).target(0).ease(TweenEquations.easeInOutQuad));
                t.push(Tween.to(circle, SpriteAccessor.ALPHA, fadeTime).target(1).ease(TweenEquations.easeInOutQuad));
            }
            t.push(Tween.to(circle, SpriteAccessor.ALPHA, 1.20f).target(0).ease(TweenEquations.easeInOutQuad));
            t.setCallback(new TweenCallback() {
                @Override
                public void onEvent(int type, BaseTween<?> source) {
                    blink().start(tweenManager);
                }
            });
        }
        return t;
    }

}
