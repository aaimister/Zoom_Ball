package com.splitseed.view.episode.shortontime.story;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.splitseed.accessors.SpriteAccessor;
import com.splitseed.objects.Text;
import com.splitseed.view.ViewAdapter;
import com.splitseed.view.episode.shortontime.ShortOnTime;
import com.splitseed.zoomball.Etheric;

public class Scene05 extends ViewAdapter {

    private ShortOnTime sequence;
    private Text show1;
    private Text show2;
    private Text terminated;
    private Text tap;
    private Text elapsed;

    private int show;

    private long totalTime;

    private boolean canTap;

    public Scene05(Etheric game, Color background, ShortOnTime sequence) {
        super(game, background);
        this.sequence = sequence;

        float x = 10 * Etheric.SCALE_Y;
        float width = Etheric.SCREEN_WIDTH - (x * 2);
        float fontSize = 0.19f * Etheric.SCALE_Y;
        String text = "Unfortunately, yet another cast has been sent to the other side in replace of the being that we all summoned here.  Our efforts continue to produce the same result : failure.";
        game.assets.setLayoutText(text, Color.WHITE, width, Align.center, true, fontSize);
        show1 = new Text(game.assets, game.tweenManager, text, x, (Etheric.SCREEN_HEIGHT - -game.assets.layout.height) / 2, width, -game.assets.layout.height);

        text = "Unfortunately, we have lost the life of the being we all summed here from the other side.  Once again, we have been left with no results and nothing more than an empty cast.";
        game.assets.setLayoutText(text, Color.WHITE, width, Align.center, true, fontSize);
        show2 = new Text(game.assets, game.tweenManager, text, x, (Etheric.SCREEN_HEIGHT - -game.assets.layout.height) / 2, width, -game.assets.layout.height);

        text = "PROJECT LABELED CODENAME: ETHERIC TERMINATED";
        game.assets.setLayoutText(text, Color.WHITE, width, Align.center, true, fontSize);
        terminated = new Text(game.assets, game.tweenManager, text, x, (Etheric.SCREEN_HEIGHT - -game.assets.layout.height) / 2, width, -game.assets.layout.height);

        text = "Tap For Rebirth";
        game.assets.setLayoutText(text, Color.WHITE, width, Align.center, true, fontSize);
        tap = new Text(game.assets, game.tweenManager, text, x, (Etheric.SCREEN_HEIGHT - -game.assets.layout.height - x), width, -game.assets.layout.height);
    }

    @Override
    public void show() {
        Timeline.createSequence()
                .pushPause(6)
                .push(Timeline.createParallel()
                        .push(Tween.to(show1, SpriteAccessor.ALPHA, 0.75f).target(0).ease(TweenEquations.easeInOutQuad))
                        .push(Tween.to(show2, SpriteAccessor.ALPHA, 0.75f).target(0).ease(TweenEquations.easeInOutQuad)))
                .pushPause(1)
                .push(Tween.to(terminated, SpriteAccessor.ALPHA, 0.75f).target(1).ease(TweenEquations.easeInOutQuad))
                .pushPause(3)
                .push(Tween.to(tap, SpriteAccessor.ALPHA, 0.75f).target(1).ease(TweenEquations.easeInOutQuad))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        canTap = true;
                    }
                }).start(game.tweenManager);
    }

    @Override
    public void drawSpriteBatch(SpriteBatch spriteBatch, float runTime) {
        show1.drawSpriteBatch(spriteBatch, runTime);
        show2.drawSpriteBatch(spriteBatch, runTime);
        terminated.drawSpriteBatch(spriteBatch, runTime);
        tap.drawSpriteBatch(spriteBatch, runTime);
        elapsed.drawSpriteBatch(spriteBatch, runTime);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (canTap)
            nextScreen();
        return false;
    }

    // Kill all tweens and notify for a screen change
    private void nextScreen() {
        game.tweenManager.killAll();
        setChanged();
        notifyObservers();
    }

    public void setShow(int show) {
        this.show = show;
        canTap = false;
        show1.setAlpha(0);
        show2.setAlpha(0);
        terminated.setAlpha(0);
        tap.setAlpha(0);

        totalTime = System.currentTimeMillis() - sequence.startTime;
        long milliseconds = totalTime % 1000;
        long seconds = (totalTime / 1000) % 60;
        long minutes = (totalTime / (1000 * 60)) % 60;
        long hours = (totalTime / (1000 * 60 * 60)) % 24;
        String text = "Age : " + hours + " h : " + minutes + " m : " + seconds + "." + milliseconds + " s";
        float x = 10 * Etheric.SCALE_Y;
        float width = Etheric.SCREEN_WIDTH - (x * 2);
        float fontSize = 0.19f * Etheric.SCALE_Y;
        game.assets.setLayoutText(text, Color.WHITE, width, Align.center, true, fontSize);
        elapsed = new Text(game.assets, game.tweenManager, text, x, 10 * Etheric.SCALE_Y, width, -game.assets.layout.height);

        clearAlphaListeners();
        addAlphaListener((show == 1 ? show1 : show2), elapsed);
    }
}
