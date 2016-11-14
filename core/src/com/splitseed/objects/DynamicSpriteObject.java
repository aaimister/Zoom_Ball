package com.splitseed.objects;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.splitseed.util.Assets;

public class DynamicSpriteObject extends SpriteObjectAdapter {

    public final Vector2 velocity;
    public final Vector2 acceleration;

    public DynamicSpriteObject(Assets assets, TweenManager tweenManager, float x, float y, float width, float height) {
        super(assets, tweenManager, x, y, width, height);
        velocity = new Vector2();
        acceleration = new Vector2();
    }

    @Override
    public void drawSpriteBatch(SpriteBatch spriteBatch, float runTime) {

    }
}
