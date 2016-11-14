package com.splitseed.objects;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.splitseed.util.Assets;
import com.splitseed.zoomball.ZoomBall;

public class Ball extends DynamicSpriteObject {

    private Trail trail;

    public Ball(Assets assets, TweenManager tweenManager, float x, float y, float width, float height) {
        super(assets, tweenManager, x, y, width, height);
        trail = new Trail(tweenManager, assets.zoomBallLogo[0]);
        setColor(Color.BLACK);
    }

    @Override
    public void update(float delta) {
        // Add acceleration
        acceleration.add(-Gdx.input.getAccelerometerX() / 5, Gdx.input.getAccelerometerY() / 5);
        // Set velocity to acceleration
        velocity.set(acceleration.x, acceleration.y);
        // Add world gravity
        velocity.add(0, 0);

        float next = getX() + velocity.x;
        if (next >= 0 && next + getWidth() <= ZoomBall.SCREEN_WIDTH) {
            translateX(velocity.x);
        } else {
            setX(next > ZoomBall.SCREEN_WIDTH - getWidth() ? ZoomBall.SCREEN_WIDTH - getWidth() : 0);
            acceleration.set(0, acceleration.y);
        }

        next = getY() + velocity.y;
        if (next >= 0 && next + getHeight() <= ZoomBall.SCREEN_HEIGHT) {
            translateY(velocity.y);
        } else {
            setY(next > ZoomBall.SCREEN_HEIGHT - getHeight() ? ZoomBall.SCREEN_HEIGHT - getHeight() : 0);
            acceleration.set(acceleration.x, 0);
        }

        trail.addTail(this);
    }

    @Override
    public void drawSpriteBatch(SpriteBatch spriteBatch, float runTime) {
        spriteBatch.setColor(getColor());
        trail.drawSpriteBatch(spriteBatch, runTime);
        spriteBatch.draw(assets.zoomBallLogo[0], getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public boolean collidedWith(SpriteObject other) {

        return false;
    }

}
