package com.splitseed.objects.capsule;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.splitseed.accessors.SpriteAccessor;
import com.splitseed.util.Assets;

public class EonCapsule extends Capsule {

    private Sprite[] dots;
    private Vector2[] origins;

    public EonCapsule(Assets assets, TweenManager tweenManager, float x, float y, float width, float height) {
        super(assets, tweenManager, x, y, width, height);
        int dotCount = 4;
        float size = width / (dotCount / 2);
        dots = new Sprite[dotCount];
        origins = new Vector2[dotCount];
        float degree = 0;
        float degreeIncrement = 360.0f / dotCount;
        float space = width / 2;
        float centerX = x + space;
        float centerY = y + space;
        for (int i = 0; i < dotCount; i++) {
            Sprite s = new Sprite(assets.zoomBallLogo[0]);
            float cos = (float) Math.cos(Math.toRadians(degree));
            float sin = (float) Math.sin(Math.toRadians(degree));
            float xOffset = (cos * space) - (size / 2) - (cos * (size / 1.25f));
            float yOffset = (sin * space) - (size / 2) - (sin * (size / 1.25f));
            s.setBounds(centerX - (size / 2), centerY - (size / 2), size, size);
            s.setColor(assets.BLACK);
            origins[i] = new Vector2(getOriginX() + xOffset, getOriginY() + yOffset);
            s.setOrigin(origins[i].x, origins[i].y);
            dots[i] = s;
            degree += degreeIncrement;
        }
        setColor(assets.BLACK);
    }

    @Override
    public void startAnimation() {
        Tween.call(rotationCallback).start(tweenManager);
    }

    @Override
    public void stopAnimation() {
        for (Sprite s : dots)
            tweenManager.killTarget(s);
    }

    @Override
    public void reset() {
        super.reset();
        setScale(1);
        for (int i = 0; i < dots.length; i++) {
            dots[i].setRotation(0);
            dots[i].setOrigin(origins[i].x, origins[i].y);
        }
    }

    @Override
    public void eat() {
        if (!eaten) {
            eaten = true;
            stopAnimation();
            float time = 0.75f;
            float size = dots[0].getWidth();
            Timeline eat = Timeline.createParallel();
            eat.push(Tween.to(this, SpriteAccessor.ALPHA, time).target(0).ease(TweenEquations.easeInOutQuad));
            eat.push(Tween.to(this, SpriteAccessor.SCALE, time).target(2.5f, 2.5f).ease(TweenEquations.easeInOutQuad));
            float degree = 0;
            float degreeIncrement = 360.0f / dots.length;
            float space = getWidth() / 2;
            for (Sprite s : dots) {
                float cos = (float) Math.cos(Math.toRadians(degree));
                float sin = (float) Math.sin(Math.toRadians(degree));
                float xOffset = (cos * space) - (size / 2) - (cos * (size / 6));
                float yOffset = (sin * space) - (size / 2) - (sin * (size / 6));
                s.setOrigin(getOriginX() + xOffset, getOriginY() + yOffset);
                s.setRotation(0);
                eat.push(Tween.to(s, SpriteAccessor.ROTATION, time).target(180).ease(TweenEquations.easeInOutQuad));
                degree += degreeIncrement;
            }
            eat.start(tweenManager);
        }
    }

    @Override
    public void drawSpriteBatch(SpriteBatch spriteBatch, float runTime) {
        spriteBatch.setColor(getColor());
        spriteBatch.draw(assets.zoomBallLogo[1], getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        for (Sprite s : dots)
            s.draw(spriteBatch);
    }

    private TweenCallback rotationCallback = new TweenCallback() {
        @Override
        public void onEvent(int type, BaseTween<?> source) {
            Timeline rotate = Timeline.createParallel();
            for (Sprite s : dots) {
                s.setRotation(0);
                rotate.push(Tween.to(s, SpriteAccessor.ROTATION, 0.75f).target(360).ease(TweenEquations.easeInOutQuad));
            }
            rotate.setCallback(rotationCallback).start(tweenManager);
        }
    };

    @Override
    public void setAlpha(float a) {
        if (dots != null) {
            for (Sprite s : dots)
                s.setAlpha(a);
        }
        super.setAlpha(a);
    }
}
