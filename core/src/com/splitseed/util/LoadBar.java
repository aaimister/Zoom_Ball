package com.splitseed.util;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.splitseed.objects.SpriteObjectAdapter;

public class LoadBar extends SpriteObjectAdapter {

    private float startAssets;
    private float progress;

    private int tapCount;

    private boolean done;
    private boolean show;
    private boolean setup;

    public LoadBar(Assets assets, TweenManager tweenManager, float x, float y, int width, int height) {
        super(assets, tweenManager, x, y, width, height);
        setColor(0.0f, 0.0f, 0.0f, 0.0f);
        assets.assetManager.update();
        startAssets = assets.assetManager.getQueuedAssets();
        done = false;
        show = true;
        setup = false;
        tapCount = 0;
    }

    @Override
    public void update(float delta) {
        if (!done) {
            done = assets.assetManager.update();
            progress = assets.assetManager.getLoadedAssets() / startAssets;
        } else {
            setup();
        }
    }

    @Override
    public void drawSpriteBatch(SpriteBatch spriteBatch, float runTime) {
        if (show) {
            spriteBatch.setColor(assets.RED.r, assets.RED.g, assets.RED.b, getColor().a);
            spriteBatch.draw(assets.loadBar[0], getX(), getY(), getWidth(), getHeight());

            spriteBatch.setColor(assets.GREEN.r, assets.GREEN.g, assets.GREEN.b, getColor().a);
            spriteBatch.draw(assets.loadBar[0], getX(), getY(), getWidth() * progress, getHeight());

            spriteBatch.setColor(getColor());
            spriteBatch.draw(assets.loadBar[0], getX(), getY(), getWidth(), getHeight());

//            if (tapCount > 0) {
//                spriteBatch.setColor(Color.WHITE);
//                assets.font.getData().setScale(0.12f * scaleY, 0.12f * scaleY);
//                AssetLoader.layout.setText(AssetLoader.font, "" + tapCount, batcher.getColor(), getWidth(), Align.center, false);
//                float y = getY() + (getHeight() + AssetLoader.layout.height) / 2.0f;
//                AssetLoader.font.draw(batcher, AssetLoader.layout, getX(), y);
//            }
        }
    }

    public void addTap() {
        tapCount++;
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
