package com.splitseed.view.episode.shortontime.story;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.splitseed.accessors.SpriteAccessor;
import com.splitseed.objects.Text;
import com.splitseed.objects.button.Button;
import com.splitseed.objects.button.ButtonListener;
import com.splitseed.objects.functions.Tap;
import com.splitseed.view.ViewAdapter;
import com.splitseed.zoomball.Etheric;

public class Intro extends ViewAdapter implements ButtonListener {

    private Text doubleTap;
    private Text before;
    private Text entity;
    private Text codename;
    private Tap tap;

    private int stage;

    public Intro(Etheric game, Color background) {
        super(game, background);

        float x = 10 * Etheric.SCALE_Y;
        float halfY = Etheric.SCREEN_HEIGHT / 2;
        float width = Etheric.SCREEN_WIDTH - (x * 2);
        float fontSize = 0.19f * Etheric.SCALE_Y;

        String text = "Double Tap";
        game.assets.setLayoutText(text, Color.WHITE, width, Align.center, true, 0.12f * Etheric.SCALE_Y);
        doubleTap = new Text(game.assets, game.tweenManager, text, x, Etheric.SCREEN_HEIGHT - (-game.assets.layout.height * 3) - (100 * Etheric.SCALE_Y), width, -game.assets.layout.height);

        text = "Before the age of the expansion of industrialized science across the globe, there was a sudden extinction burst of alchemic research...";
        game.assets.setLayoutText(text, Color.WHITE, width, Align.center, true, fontSize);
        before = new Text(game.assets, game.tweenManager, text, x, halfY - (-game.assets.layout.height / 2), width, -game.assets.layout.height);

        text = "...This taboo research resulted in Earth's 1st known record of a conciously aware transcendent entity...";
        game.assets.setLayoutText(text, Color.WHITE, width, Align.center, true, fontSize);
        entity = new Text(game.assets, game.tweenManager, text, x, halfY - (-game.assets.layout.height / 2), width, -game.assets.layout.height);

        text = "...It was labeled as CODENAME : ETHERIC";
        game.assets.setLayoutText(text, Color.WHITE, width, Align.center, true, fontSize);
        codename = new Text(game.assets, game.tweenManager, text, x, halfY - (-game.assets.layout.height / 2), width, -game.assets.layout.height);

        width = 50 * Etheric.SCALE_Y;
        tap = new Tap(game.assets, game.tweenManager, this, (Etheric.SCREEN_WIDTH - width) / 2, Etheric.SCREEN_HEIGHT - width - width, width, width, 2);

        addAlphaListener(before);
    }

    @Override
    public void preFade() {
        stage = 0;
        doubleTap.setAlpha(0);
        entity.setAlpha(0);
        codename.setAlpha(0);
        tap.reset();
    }

    @Override
    public void postFade() {
        Tween.to(doubleTap, SpriteAccessor.ALPHA, 1.20f).target(1).ease(TweenEquations.easeInOutQuad).start(game.tweenManager);
        tap.begin();
    }

    @Override
    public void drawSpriteBatch(SpriteBatch spriteBatch, float runTime) {
        doubleTap.drawSpriteBatch(spriteBatch, runTime);
        before.drawSpriteBatch(spriteBatch, runTime);
        entity.drawSpriteBatch(spriteBatch, runTime);
        codename.drawSpriteBatch(spriteBatch, runTime);
        tap.drawSpriteBatch(spriteBatch, runTime);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        tap.touchDown(screenX, screenY, pointer, button);
        // Touch to skip for testing purposes
        //nextScreen();
        return false;
    }

    // Kill all tweens and notify for a screen change
    private void nextScreen() {
        game.tweenManager.killAll();
        setChanged();
        notifyObservers();
    }

    @Override
    public void buttonDown(Button button) {
        float fadeTime = 0.85f;
        if (stage == 0) {
            stage++;
            Timeline.createSequence()
                    .push(Timeline.createParallel()
                            .push(Tween.to(doubleTap, SpriteAccessor.ALPHA, fadeTime).target(0).ease(TweenEquations.easeInOutQuad))
                            .push(Tween.to(before, SpriteAccessor.ALPHA, fadeTime).target(0).ease(TweenEquations.easeInOutQuad)))
                    .pushPause(0.25f)
                    .push(Tween.to(entity, SpriteAccessor.ALPHA, fadeTime).target(1).ease(TweenEquations.easeInOutQuad))
                    .pushPause(0.25f)
                    .setCallback(new TweenCallback() {
                        @Override
                        public void onEvent(int type, BaseTween<?> source) {
                            tap.reset();
                            tap.begin();
                        }
                    }).start(game.tweenManager);
        } else if (stage == 1) {
            stage++;
            Timeline.createSequence()
                    .push(Tween.to(entity, SpriteAccessor.ALPHA, fadeTime).target(0).ease(TweenEquations.easeInOutQuad))
                    .pushPause(0.25f)
                    .push(Tween.to(codename, SpriteAccessor.ALPHA, fadeTime).target(1).ease(TweenEquations.easeInOutQuad))
                    .pushPause(0.25f)
                    .setCallback(new TweenCallback() {
                        @Override
                        public void onEvent(int type, BaseTween<?> source) {
                            tap.reset();
                            tap.begin();
                        }
                    }).start(game.tweenManager);
        } else if (stage == 2) {
            stage++;
            Timeline.createSequence()
                    .push(Tween.to(codename, SpriteAccessor.ALPHA, fadeTime).target(0).ease(TweenEquations.easeInOutQuad))
                    .pushPause(0.25f)
                    .setCallback(new TweenCallback() {
                        @Override
                        public void onEvent(int type, BaseTween<?> source) {
                            nextScreen();
                        }
                    }).start(game.tweenManager);
        }
    }

    @Override
    public void buttonUp(Button button) {
        // Do nothing
    }
}
