package com.splitseed.util;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.splitseed.objects.SpriteObjectAdapter;
import com.splitseed.zoomball.ZoomBall;

public class LoadBar extends SpriteObjectAdapter {

    private int tapCount;

    private boolean done;
    private boolean show;
    private boolean setup;

    public LoadBar(Assets assets, TweenManager tweenManager, float x, float y, int width, int height) {
        super(assets, tweenManager, x, y, width, height);
        setColor(0f, 0f, 0f, 0f);
        assets.setFontScale(0.12f * ZoomBall.SCALE_Y);
        done = false;
        show = true;
        setup = false;
        tapCount = 0;
    }

    @Override
    public void update(float delta) {
        if (!done) {
            done = assets.assetManager.update();
        } else {
            setup();
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        tapCount++;
        return true;
    }

    @Override
    public void drawSpriteBatch(SpriteBatch spriteBatch, float runTime) {
        if (show) {
            spriteBatch.setColor(assets.RED.r, assets.RED.g, assets.RED.b, getColor().a);
            spriteBatch.draw(assets.loadBar[0], getX(), getY(), getWidth(), getHeight());

            spriteBatch.setColor(assets.GREEN.r, assets.GREEN.g, assets.GREEN.b, getColor().a);
            spriteBatch.draw(assets.loadBar[0], getX(), getY(), getWidth() * assets.assetManager.getProgress(), getHeight());

            spriteBatch.setColor(getColor());
            spriteBatch.draw(assets.loadBar[1], getX(), getY(), getWidth(), getHeight());

            if (tapCount > 0) {
                spriteBatch.setColor(Color.WHITE);
                assets.layout.setText(assets.font, "" + tapCount, spriteBatch.getColor(), getWidth(), Align.center, false);
                float y = getY() + (getHeight() - -assets.layout.height) / 2f;
                assets.font.draw(spriteBatch, assets.layout, getX(), y);
            }
        }
    }

    public boolean doneLoading() {
        return done;
    }

    public void setup() {
        if (done && !setup) {
            assets.setupRest();
            show = false;
            setup = true;
        }
    }

    public int getTapCount() {
        return tapCount;
    }

}
