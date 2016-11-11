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
    private Sprite logo;

    private boolean skip;

    public LoadScreen(ZoomBall game, Color background) {
        super(game, background);
        game.assets.assetManager.finishLoading();
        game.assets.setupSpload();
        game.assets.loadRest();
        loadBar = new LoadBar(game.assets, game.tweenManager, 10, 10, (int) (100 * ZoomBall.SCALE_Y), (int) (25 * ZoomBall.SCALE_Y));
        logo = new Sprite(game.assets.zoomBallLogo[0]);
        logo.setColor(1, 1, 1, 0);
        float size = ZoomBall.SCREEN_WIDTH / 2.0f;
        logo.setBounds((ZoomBall.SCREEN_WIDTH - size) / 2.0f, (ZoomBall.SCREEN_HEIGHT - size) / 2.0f, size, size);

        setupTween();
    }

    private void setupTween() {
        Timeline.createSequence()
                .push(Tween.to(logo, SpriteAccessor.ALPHA, 0.2f).target(1.0f).ease(TweenEquations.easeInOutQuad))
                .pushPause(2.0f)
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
        loadBar.addTap();
        return true;
    }

    @Override
    public void drawBatcher(SpriteBatch batcher, float runTime) {
        logo.draw(batcher);
        loadBar.drawSpriteBatch(batcher, runTime);
    }

    private void endScreen() {
        game.tweenManager.killAll();
        loadBar.setup();
        game.setScreen(new SplashScreen(game, Color.BLACK, loadBar, skip));
    }
}
