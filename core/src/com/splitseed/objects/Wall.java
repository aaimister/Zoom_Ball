package com.splitseed.objects;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.splitseed.util.Assets;
import com.splitseed.zoomball.Etheric;

public class Wall extends SpriteObjectAdapter {

    public static float DEFAULT_WALL_SIZE = 10 * Etheric.SCALE_Y;

    public Wall(Assets assets, TweenManager tweenManager, float x, float y, float width, float height) {
        super(assets, tweenManager, x, y, width, height);
        setColor(0, 0, 0, 1);
    }

    @Override
    public void drawSpriteBatch(SpriteBatch spriteBatch, float runTime) {
//        spriteBatch.setColor(Color.MAROON);
//        Rectangle rec = getBoundingRectangle();
//        float half = Entity.DEFAULT_SIZE / 2;
//        rec.x = rec.x - half;
//        rec.y = rec.y - half;
//        rec.width = rec.width + Entity.DEFAULT_SIZE;
//        rec.height = rec.height + Entity.DEFAULT_SIZE;
//        spriteBatch.draw(assets.rest[2], rec.x, rec.y, rec.width, rec.height);
        spriteBatch.setColor(getColor());
        spriteBatch.draw(assets.rest[1], getX(), getY(), getWidth(), getHeight());
    }

}
