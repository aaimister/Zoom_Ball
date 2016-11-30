package com.splitseed.view.episode.shortontime.levels;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.gdx.graphics.Color;
import com.splitseed.accessors.SpriteAccessor;
import com.splitseed.accessors.ViewAccessor;
import com.splitseed.objects.Entity;
import com.splitseed.objects.HeartBeat;
import com.splitseed.objects.Portal;
import com.splitseed.view.Level;
import com.splitseed.zoomball.Etheric;

public class Level03 extends Level {

    private Level04 level04;
    private HeartBeat heartBeat;
    private long startTime;

    public Level03(Etheric game, Color background, Entity entity, Portal portal, Level04 level04) {
        super(game, background, entity, portal);
        this.level04 = level04;
        heartBeat = new HeartBeat(HeartBeat.PACE.SLIGHT);
    }

    @Override
    public void show() {
        super.show();
        setBackground(game.assets.OFFWHITE);
        // Reset the entity and portal
        float offset = 15 * Etheric.SCALE_Y;
        entity.reset(offset, Etheric.SCREEN_HEIGHT - Entity.DEFAULT_SIZE - offset, Entity.DEFAULT_SIZE, Entity.DEFAULT_SIZE);
        entity.setColor(Color.GRAY);
        portal.reset(Etheric.SCREEN_WIDTH - Portal.DEFAULT_SIZE - offset, offset, Portal.DEFAULT_SIZE, Portal.DEFAULT_SIZE);
        portal.startRotation();
    }

    @Override
    public void fadeOver() {
        heartBeat.startTimer();
        float colorFadeTime = 14;
        Timeline.createParallel()
            .push(Tween.to(entity, SpriteAccessor.COLOR, colorFadeTime).target(game.assets.BLACK.r, game.assets.BLACK.g, game.assets.BLACK.b, 1).ease(TweenEquations.easeInOutQuad))
            .push(Tween.to(this, ViewAccessor.COLOR, colorFadeTime).target(game.assets.GREEN.r, game.assets.GREEN.g, game.assets.GREEN.b, 1).ease(TweenEquations.easeInOutQuad))
            .start(game.tweenManager);
        startTime = System.currentTimeMillis() + 14000;
    }

    @Override
    protected void nextScreen() {
        heartBeat.cancelTimer();
        // Set the next level's background color to this level's background color in case the fade has not completed.
        level04.setEndColor(background);
        float timeLeft = (startTime - System.currentTimeMillis()) / 1000;
        if (timeLeft > 0)
            level04.setTimeLeft(timeLeft);
        super.nextScreen();
    }
}
