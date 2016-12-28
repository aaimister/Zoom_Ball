package com.splitseed.objects.functions;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.splitseed.objects.SpriteObjectAdapter;
import com.splitseed.util.Assets;

public class Circle extends SpriteObjectAdapter {

    private TextureRegion circle;
    private TextureRegion ring;

    public Circle(Assets assets, TweenManager tweenManager, float x, float y, float width, float height) {
        super(assets, tweenManager, x, y, width, height);
        circle = assets.zoomBallLogo[0];
        ring = assets.zoomBallLogo[1];
    }

    @Override
    public void drawSpriteBatch(SpriteBatch spriteBatch, float runTime) {
        Color c = getColor();
        spriteBatch.setColor(c);
        spriteBatch.draw(circle, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        spriteBatch.setColor(0, 0, 0, c.a);
        spriteBatch.draw(ring, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
}
