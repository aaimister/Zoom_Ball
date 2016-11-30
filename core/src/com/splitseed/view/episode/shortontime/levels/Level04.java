package com.splitseed.view.episode.shortontime.levels;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.gdx.graphics.Color;
import com.splitseed.accessors.SpriteAccessor;
import com.splitseed.accessors.ViewAccessor;
import com.splitseed.objects.*;
import com.splitseed.view.Level;
import com.splitseed.zoomball.Etheric;

public class Level04 extends Level {

    private HeartBeat heartBeat;

    private float timeLeft;

    public Level04(Etheric game, Color background, Entity entity, Portal portal) {
        super(game, background, entity, portal);

        heartBeat = new HeartBeat(HeartBeat.PACE.SLIGHT);

        // Set up the walls
        float width = Entity.DEFAULT_SIZE * 2;
            // Start platform
        addWall(game.assets.BLACK, 0, 15 * Etheric.SCALE_Y + Entity.DEFAULT_SIZE, width, Wall.DEFAULT_WALL_SIZE);
        float offset = 15 * Etheric.SCALE_Y;
        float x = (Etheric.SCREEN_WIDTH - Portal.DEFAULT_SIZE) / 2 - Wall.DEFAULT_WALL_SIZE - offset;
        float y = (Etheric.SCREEN_HEIGHT - Portal.DEFAULT_SIZE) / 2 - offset;
            // Left wall around portal
        addWall(game.assets.BLACK, x, y, Wall.DEFAULT_WALL_SIZE, Portal.DEFAULT_SIZE + (offset * 2));
            // Top wall around portal
        addWall(game.assets.BLACK, x - Wall.DEFAULT_WALL_SIZE, y - Wall.DEFAULT_WALL_SIZE, Portal.DEFAULT_SIZE + (offset * 2) + (Wall.DEFAULT_WALL_SIZE * 4), Wall.DEFAULT_WALL_SIZE);
        x += Wall.DEFAULT_WALL_SIZE + (offset * 2) + Portal.DEFAULT_SIZE;
            // Right wall around portal
        addWall(game.assets.BLACK, x, y, Wall.DEFAULT_WALL_SIZE, Portal.DEFAULT_SIZE + (offset * 2));
        // Add the walls to the fade in
        addAlphaListener(obstacles.toArray(new SpriteObject[obstacles.size()]));
    }

    @Override
    public void show() {
        super.show();
        // Reset entity and portal
        float offset = 15 * Etheric.SCALE_Y;
        entity.reset(offset, offset, Entity.DEFAULT_SIZE, Entity.DEFAULT_SIZE);
        portal.reset((Etheric.SCREEN_WIDTH - Portal.DEFAULT_SIZE) / 2, (Etheric.SCREEN_HEIGHT - Portal.DEFAULT_SIZE) / 2, Portal.DEFAULT_SIZE, Portal.DEFAULT_SIZE);
        portal.startRotation();

        if (timeLeft > 0) {
            Timeline t = Timeline.createParallel();
            t.push(Tween.to(entity, SpriteAccessor.COLOR, timeLeft).target(game.assets.BLACK.r, game.assets.BLACK.g, game.assets.BLACK.b, 1).ease(TweenEquations.easeInOutQuad));
            t.push(Tween.to(this, ViewAccessor.COLOR, timeLeft).target(game.assets.GREEN.r, game.assets.GREEN.g, game.assets.GREEN.b, 1).ease(TweenEquations.easeInOutQuad));
            for (SpriteObject so : obstacles) {
                so.setColor(entity.getColor());
                t.push(Tween.to(so, SpriteAccessor.COLOR, timeLeft).target(game.assets.BLACK.r, game.assets.BLACK.g, game.assets.BLACK.b, 1).ease(TweenEquations.easeInOutQuad));
            }
            Timeline.createSequence().pushPause(1).push(t).start(game.tweenManager);
        }
    }

    @Override
    public void fadeOver() {
        heartBeat.startTimer();
    }

    protected void setTimeLeft(float timeLeft) {
        this.timeLeft = timeLeft;
    }

    @Override
    protected void nextScreen() {
        entity.setColor(game.assets.BLACK);
        heartBeat.cancelTimer();
        super.nextScreen();
    }

}
