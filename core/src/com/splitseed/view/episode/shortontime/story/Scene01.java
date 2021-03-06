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

public class Scene01 extends ViewAdapter implements ButtonListener {

    private Text research1;
    private Tap tap;

    public Scene01(Etheric game, Color background) {
        super(game, background);

        float x = 10 * Etheric.SCALE_Y;
        float width = Etheric.SCREEN_WIDTH - (x * 2);
        float fontSize = 0.19f * Etheric.SCALE_Y;
        String text = "Researcher 1 : We have signs of movement. Let us continue the experiment without barriers in hopes of progress with its spatial awareness.";
        game.assets.setLayoutText(text, Color.WHITE, width, Align.center, true, fontSize);
        research1 = new Text(game.assets, game.tweenManager, text, x, (Etheric.SCREEN_HEIGHT - -game.assets.layout.height) / 2, width, -game.assets.layout.height);

        width = 50 * Etheric.SCALE_Y;
        tap = new Tap(game.assets, game.tweenManager, this, (Etheric.SCREEN_WIDTH - width) / 2, Etheric.SCREEN_HEIGHT - width - width, width, width, 2);

        addAlphaListener(research1);
    }

    @Override
    public void preFade() {
        tap.reset();
    }

    @Override
    public void postFade() {
        tap.begin();
    }

    @Override
    public void drawSpriteBatch(SpriteBatch spriteBatch, float runTime) {
        research1.drawSpriteBatch(spriteBatch, runTime);
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
        Tween.to(research1, SpriteAccessor.ALPHA, 0.75f).target(0).ease(TweenEquations.easeInOutQuad)
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        nextScreen();
                    }
                }).start(game.tweenManager);
    }

    @Override
    public void buttonUp(Button button) {
        // Do nothing
    }
}
