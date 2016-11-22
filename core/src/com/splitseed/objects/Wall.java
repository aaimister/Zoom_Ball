package com.splitseed.objects;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.splitseed.util.Assets;

public class Wall extends SpriteObjectAdapter {

    public Wall(Assets assets, TweenManager tweenManager, float x, float y, float width, float height) {
        super(assets, tweenManager, x, y, width, height);
        setColor(0, 0, 0, 1);
    }

    @Override
    public void drawSpriteBatch(SpriteBatch spriteBatch, float runTime) {
        spriteBatch.setColor(getColor());
        spriteBatch.draw(assets.rest[2], getX(), getY(), getWidth(), getHeight());
    }

}
