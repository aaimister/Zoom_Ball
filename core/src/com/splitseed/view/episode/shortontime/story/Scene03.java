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

public class Scene03 extends ViewAdapter implements ButtonListener {

    private Text researcher45;
    private Text researcher17;
    private Text researcher83;
    private Tap tap;

    private int stage;

    public Scene03(Etheric game, Color background) {
        super(game, background);

        float x = 10 * Etheric.SCALE_Y;
        float width = Etheric.SCREEN_WIDTH - (x * 2);
        float fontSize = 0.19f * Etheric.SCALE_Y;
        String text = "Researcher 45 : Code Sanctuary.  We have a Code Sanctuary.";
        game.assets.setLayoutText(text, Color.WHITE, width, Align.center, true, fontSize);
        researcher45 = new Text(game.assets, game.tweenManager, text, x, (Etheric.SCREEN_HEIGHT - -game.assets.layout.height) / 2, width, -game.assets.layout.height);

        text = "Researcher 17 : It has grown approximately 5mm in diameter.  Researcher 83, what are the 1st of the countermeasures?";
        game.assets.setLayoutText(text, Color.WHITE, width, Align.center, true, fontSize);
        researcher17 = new Text(game.assets, game.tweenManager, text, x, (Etheric.SCREEN_HEIGHT - -game.assets.layout.height) / 2, width, -game.assets.layout.height);

        text = "Researcher 83 : We must immediately administer a Level 4 Eon Capsule.";
        game.assets.setLayoutText(text, Color.WHITE, width, Align.center, true, fontSize);
        researcher83 = new Text(game.assets, game.tweenManager, text, x, (Etheric.SCREEN_HEIGHT - -game.assets.layout.height) / 2, width, -game.assets.layout.height);

        width = 50 * Etheric.SCALE_Y;
        tap = new Tap(game.assets, game.tweenManager, this, (Etheric.SCREEN_WIDTH - width) / 2, Etheric.SCREEN_HEIGHT - width - width, width, width, 2);

        addAlphaListener(researcher45);
    }

    @Override
    public void preFade() {
        stage = 0;
        researcher17.setAlpha(0);
        researcher83.setAlpha(0);
        tap.reset();
    }

    @Override
    public void postFade() {
        tap.begin();
    }

    @Override
    public void drawSpriteBatch(SpriteBatch spriteBatch, float runTime) {
        researcher45.drawSpriteBatch(spriteBatch, runTime);
        researcher17.drawSpriteBatch(spriteBatch, runTime);
        researcher83.drawSpriteBatch(spriteBatch, runTime);
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
        float fadeTime = 0.75f;
        if (stage == 0) {
            stage++;
            Timeline.createParallel()
                    .push(Tween.to(researcher45, SpriteAccessor.ALPHA, fadeTime).target(0).ease(TweenEquations.easeInOutQuad))
                    .push(Tween.to(researcher17, SpriteAccessor.ALPHA, fadeTime).target(1).ease(TweenEquations.easeInOutQuad))
                    .setCallback(new TweenCallback() {
                        @Override
                        public void onEvent(int type, BaseTween<?> source) {
                            tap.reset();
                            tap.begin();
                        }
                    }).start(game.tweenManager);
        } else if (stage == 1) {
            stage++;
            Timeline.createParallel()
                    .push(Tween.to(researcher17, SpriteAccessor.ALPHA, fadeTime).target(0).ease(TweenEquations.easeInOutQuad))
                    .push(Tween.to(researcher83, SpriteAccessor.ALPHA, fadeTime).target(1).ease(TweenEquations.easeInOutQuad))
                    .setCallback(new TweenCallback() {
                        @Override
                        public void onEvent(int type, BaseTween<?> source) {
                            tap.reset();
                            tap.begin();
                        }
                    }).start(game.tweenManager);
        } else if (stage == 2) {
            stage++;
            Tween.to(researcher83, SpriteAccessor.ALPHA, fadeTime).target(0).ease(TweenEquations.easeInOutQuad).setCallback(new TweenCallback() {
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
