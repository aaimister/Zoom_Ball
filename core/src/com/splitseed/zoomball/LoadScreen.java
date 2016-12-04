package com.splitseed.zoomball;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.splitseed.accessors.SpriteAccessor;
import com.splitseed.util.LoadBar;
import com.splitseed.view.ViewAdapter;

public class LoadScreen extends ViewAdapter {

    private LoadBar loadBar;
    private Sprite circle;
    private Sprite outline;
    private Sprite text;

    private boolean skip;

    public LoadScreen(Etheric game, Color background) {
        super(game, background);
        game.assets.assetManager.finishLoading();
        game.assets.setupSpload();
        game.assets.loadRest();
        float size = 60 * Etheric.SCALE_Y;
        loadBar = new LoadBar(game.assets, game.tweenManager, 10 * Etheric.SCALE_Y, Etheric.SCREEN_HEIGHT - (size - (size * (205f / 512f))) - (10 * Etheric.SCALE_Y), size, size);

        circle = new Sprite(game.assets.zoomBallLogo[0]);
        circle.setColor(1, 1, 1, 1);
        outline = new Sprite(game.assets.zoomBallLogo[1]);
        outline.setColor(1, 1, 1, 0);
        text = new Sprite(game.assets.zoomBallLogo[2]);
        text.setColor(1, 1, 1, 1);

        size = Etheric.SCREEN_WIDTH / 2.0f;
        //float textSize = size / 1.25f;
        float centerX = (Etheric.SCREEN_WIDTH - size) / 2.0f;
        float centerY = (Etheric.SCREEN_HEIGHT - size) / 2.0f;
        float offsetY = size / 2.0f;
        circle.setBounds(Etheric.SCREEN_WIDTH / 2.0f, Etheric.SCREEN_HEIGHT / 2.0f - offsetY, 0, 0);
        outline.setBounds(centerX, centerY - offsetY, size, size);
        text.setBounds(Etheric.SCREEN_WIDTH / 2.0f, Etheric.SCREEN_HEIGHT / 2.0f + offsetY, 0, 0);

        setupTween();
    }

    private void setupTween() {
        float size = (Etheric.SCREEN_WIDTH / 2.0f) / 1.25f;
        float textSize = (Etheric.SCREEN_WIDTH / 2.0f);
        Timeline.createSequence()
                .push(Timeline.createParallel()
                        .push(Tween.to(loadBar, SpriteAccessor.ALPHA, 0.2f).target(1.0f).ease(TweenEquations.easeInOutQuad))
                        .push(Tween.to(outline, SpriteAccessor.ALPHA, 0.2f).target(1.0f).ease(TweenEquations.easeInOutQuad)))
                .pushPause(1)
                .push(Timeline.createParallel()
                        .push(Tween.to(circle, SpriteAccessor.WIDTHHEIGHT, 0.75f).target(size, size).ease(TweenEquations.easeInBounce))
                        .push(Tween.to(circle, SpriteAccessor.POSITION, 0.75f).target(outline.getX() + (outline.getWidth() - size) / 2, outline.getY() + (outline.getHeight() - size) / 2.0f).ease(TweenEquations.easeInBounce))
                        .push(Tween.to(text, SpriteAccessor.WIDTHHEIGHT, 0.75f).target(textSize, textSize).ease(TweenEquations.easeInBounce))
                        .push(Tween.to(text, SpriteAccessor.POSITION, 0.75f).target(outline.getX(), outline.getY() + textSize).ease(TweenEquations.easeInBounce)))
                .pushPause(1.5f)
                .push(Timeline.createParallel()
                        .push(Tween.to(outline, SpriteAccessor.ALPHA, 0.8f).target(0).ease(TweenEquations.easeInOutQuad))
                        .push(Tween.to(circle, SpriteAccessor.ALPHA, 0.8f).target(0).ease(TweenEquations.easeInOutQuad))
                        .push(Tween.to(text, SpriteAccessor.ALPHA, 0.8f).target(0).ease(TweenEquations.easeInOutQuad)))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        endScreen();
                    }
                }).start(game.tweenManager);
    }

    @Override
    public void update(float delta) {
        loadBar.update(delta);

        if (skip && loadBar.doneLoading()) {
            endScreen();
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        skip = true;
        loadBar.touchDown(screenX, screenY, pointer, button);
        return true;
    }

    @Override
    public void drawSpriteBatch(SpriteBatch spriteBatch, float runTime) {
        outline.draw(spriteBatch);
        circle.draw(spriteBatch);
        text.draw(spriteBatch);
        loadBar.drawSpriteBatch(spriteBatch, runTime);
    }

    private void endScreen() {
        game.tweenManager.killAll();
        loadBar.setup();
        game.setScreen(new SplashScreen(game, Color.BLACK, loadBar, skip));
    }
}
