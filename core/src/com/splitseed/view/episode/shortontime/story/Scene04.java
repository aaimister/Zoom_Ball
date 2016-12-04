package com.splitseed.view.episode.shortontime.story;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.splitseed.accessors.SpriteAccessor;
import com.splitseed.objects.Text;
import com.splitseed.view.ViewAdapter;
import com.splitseed.zoomball.Etheric;

public class Scene04 extends ViewAdapter {

    private Text researcher17;

    public Scene04(Etheric game, Color background) {
        super(game, background);

        float x = 10 * Etheric.SCALE_Y;
        float width = Etheric.SCREEN_WIDTH - (x * 2);
        float fontSize = 0.19f * Etheric.SCALE_Y;
        String text = "Researcher 17 : All known vital signs have been lost.  No movement, no pulse.";
        game.assets.setLayoutText(text, Color.WHITE, width, Align.center, true, fontSize);
        researcher17 = new Text(game.assets, game.tweenManager, text, x, (Etheric.SCREEN_HEIGHT - -game.assets.layout.height) / 2, width, -game.assets.layout.height);

        addAlphaListener(researcher17);
    }

    @Override
    public void postFade() {
        float fadeTime = 0.75f;
        Timeline.createSequence()
                .pushPause(3)
                .push(Tween.to(researcher17, SpriteAccessor.ALPHA, fadeTime).target(0).ease(TweenEquations.easeInOutQuad))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        nextScreen();
                    }
                }).start(game.tweenManager);
    }

    @Override
    public void drawSpriteBatch(SpriteBatch spriteBatch, float runTime) {
        researcher17.drawSpriteBatch(spriteBatch, runTime);
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
