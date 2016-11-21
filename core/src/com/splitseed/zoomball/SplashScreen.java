package com.splitseed.zoomball;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.splitseed.accessors.SpriteAccessor;
import com.splitseed.util.LoadBar;
import com.splitseed.view.ViewAdapter;
import com.splitseed.view.menu.MenuView;

public class SplashScreen extends ViewAdapter {

    private LoadBar loadBar;
    private Sprite logoWhole;
    private Sprite logoSplit;
    private Sprite logoText;

    private boolean skip;

    public SplashScreen(Etheric game, Color background, LoadBar loadBar, boolean skip) {
        super(game, background);
        this.loadBar = loadBar;
        this.skip = skip;

        logoWhole = new Sprite(game.assets.splitSeedLogo[0]);
        logoWhole.setColor(game.assets.GREEN);
        logoWhole.setAlpha(0.0f);
        logoSplit = new Sprite(game.assets.splitSeedLogo[1]);
        logoSplit.setColor(game.assets.getColor(139.0f, 69.0f, 19.0f, 0.0f));
        logoText = new Sprite(game.assets.splitSeedLogo[2]);
        logoText.setColor(1.0f, 1.0f, 1.0f, 0.0f);

        float size = Etheric.SCREEN_WIDTH / 2.0f;
        float centerX = (Etheric.SCREEN_WIDTH - size) / 2.0f;
        float centerY = (Etheric.SCREEN_HEIGHT - size) / 2.0f;
        float offsetY = size / 2.0f;
        logoWhole.setBounds(centerX, centerY - offsetY, size, size);
        logoSplit.setBounds(centerX, centerY - offsetY, size, size);
        logoText.setBounds(centerX, centerY + offsetY, size, size);

        setupTween();
    }

    private void setupTween() {
        Timeline.createSequence()
                .push(Tween.to(logoWhole, SpriteAccessor.ALPHA, 0.8f).target(1.0f).ease(TweenEquations.easeInOutQuad))
                .pushPause(0.5f)
                .push(Timeline.createParallel()
                        .push(Tween.to(logoWhole, SpriteAccessor.ALPHA, 0.8f).target(0.0f).ease(TweenEquations.easeInOutQuad))
                        .push(Tween.to(logoSplit, SpriteAccessor.ALPHA, 0.8f).target(1.0f).ease(TweenEquations.easeInOutQuad))
                        .push(Tween.to(logoText, SpriteAccessor.ALPHA, 0.8f).target(1.0f).ease(TweenEquations.easeInOutQuad)))
                .pushPause(1.5f)
                .push(Timeline.createParallel()
                        .push(Tween.to(logoSplit, SpriteAccessor.ALPHA, 0.8f).target(0.0f).ease(TweenEquations.easeInOutQuad))
                        .push(Tween.to(logoText, SpriteAccessor.ALPHA, 0.8f).target(0.0f).ease(TweenEquations.easeInOutQuad)))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        skip = true;
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
        loadBar.drawSpriteBatch(spriteBatch, runTime);
        logoWhole.draw(spriteBatch);
        logoSplit.draw(spriteBatch);
        logoText.draw(spriteBatch);
    }

    private void endScreen() {
        game.tweenManager.killAll();
        loadBar.setup();
        game.start();
        //game.setScreen(new MenuView(game, Color.WHITE).focus(Color.BLACK, game.assets.GREEN));
    }
}
