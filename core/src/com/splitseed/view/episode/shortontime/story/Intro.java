package com.splitseed.view.episode.shortontime.story;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.splitseed.accessors.SpriteAccessor;
import com.splitseed.objects.Text;
import com.splitseed.view.ViewAdapter;
import com.splitseed.zoomball.Etheric;

public class Intro extends ViewAdapter {

    private Text before;
    private Text entity;
    private Text codename;

    public Intro(Etheric game, Color background) {
        super(game, background);

        float x = 10 * Etheric.SCALE_Y;
        float halfY = Etheric.SCREEN_HEIGHT / 2;
        float width = Etheric.SCREEN_WIDTH - (x * 2);
        float fontSize = 0.19f * Etheric.SCALE_Y;
        String text = "Before the age of the expansion of industrialized science across the globe, there was a sudden extinction burst of alchemic research...";
        game.assets.setLayoutText(text, Color.WHITE, width, Align.center, true, fontSize);
        before = new Text(game.assets, game.tweenManager, text, x, halfY - (-game.assets.layout.height / 2), width, -game.assets.layout.height);

        text = "...This taboo research resulted in Earth's 1st known record of a conciously aware transcendent entity...";
        game.assets.setLayoutText(text, Color.WHITE, width, Align.center, true, fontSize);
        entity = new Text(game.assets, game.tweenManager, text, x, halfY - (-game.assets.layout.height / 2), width, -game.assets.layout.height);

        text = "...It was labeled as CODENAME : ETHERIC";
        game.assets.setLayoutText(text, Color.WHITE, width, Align.center, true, fontSize);
        codename = new Text(game.assets, game.tweenManager, text, x, halfY - (-game.assets.layout.height / 2), width, -game.assets.layout.height);

        addAlphaListener(before);
    }

    @Override
    public void show() {
        entity.setAlpha(0);
        codename.setAlpha(0);
        float fadeTime = 0.85f;
        Timeline.createSequence()
                .pushPause(6)
                .push(Tween.to(before, SpriteAccessor.ALPHA, fadeTime).target(0).ease(TweenEquations.easeInOutQuad))
                .pushPause(0.25f)
                .push(Tween.to(entity, SpriteAccessor.ALPHA, fadeTime).target(1).ease(TweenEquations.easeInOutQuad))
                .pushPause(4.5f)
                .push(Tween.to(entity, SpriteAccessor.ALPHA, fadeTime).target(0).ease(TweenEquations.easeInOutQuad))
                .pushPause(0.25f)
                .push(Tween.to(codename, SpriteAccessor.ALPHA, fadeTime).target(1).ease(TweenEquations.easeInOutQuad))
                .pushPause(3)
                .push(Tween.to(codename, SpriteAccessor.ALPHA, fadeTime).target(0).ease(TweenEquations.easeInOutQuad))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        nextScreen();
                    }
                }).start(game.tweenManager);
    }

    @Override
    public void drawSpriteBatch(SpriteBatch spriteBatch, float runTime) {
        before.drawSpriteBatch(spriteBatch, runTime);
        entity.drawSpriteBatch(spriteBatch, runTime);
        codename.drawSpriteBatch(spriteBatch, runTime);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
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

}
