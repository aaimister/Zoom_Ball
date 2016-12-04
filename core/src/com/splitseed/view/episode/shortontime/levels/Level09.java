package com.splitseed.view.episode.shortontime.levels;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.gdx.graphics.Color;
import com.splitseed.accessors.ViewAccessor;
import com.splitseed.objects.*;
import com.splitseed.objects.capsule.NourishmentCapsule;
import com.splitseed.view.Level;
import com.splitseed.zoomball.Etheric;

public class Level09 extends Level {

    private HeartBeat heartBeat;

    private int stage;

    public Level09(Etheric game, Color background, Entity entity, Portal portal) {
        super(game, background, entity, portal);

        heartBeat = new HeartBeat(HeartBeat.PACE.FATAL);

        // Set up walls
        float width = Etheric.SCREEN_WIDTH - (Entity.DEFAULT_SIZE * 2);
        float height = (Etheric.SCREEN_HEIGHT - (Entity.DEFAULT_SIZE * 8)) / 3;
            // Top
        addWall(game.assets.BLACK, 0, Entity.DEFAULT_SIZE * 2, width, height);
            // Middle
        addWall(game.assets.BLACK, Entity.DEFAULT_SIZE * 2, height + (Entity.DEFAULT_SIZE * 4), width, height);
            // Bottom
        addWall(game.assets.BLACK, 0, Etheric.SCREEN_HEIGHT - (Entity.DEFAULT_SIZE * 2) - height, width, height);

        // Add the walls to the fade in
        addAlphaListener(obstacles.toArray(new SpriteObject[obstacles.size()]));
    }

    @Override
    public void show() {
        super.show();
        stage = 0;
        // Reset entity and portal
        float offset = Entity.DEFAULT_SIZE / 2;
        float size = Entity.DEFAULT_SIZE + (NourishmentCapsule.DEFAULT_GROWTH * 9);
        entity.reset(offset, offset, size, size);
        entity.resetCast(Entity.DEFAULT_CAST_SIZE, Entity.DEFAULT_CAST_SIZE);
        portal.reset(offset, Etheric.SCREEN_HEIGHT - Portal.DEFAULT_SIZE - ((Entity.DEFAULT_SIZE * 2 - Portal.DEFAULT_SIZE) / 2), Portal.DEFAULT_SIZE, Portal.DEFAULT_SIZE);
        portal.startRotation();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        float y = entity.getY();
        for (int i = 0; i < 3; i++) {
            Wall w = (Wall) obstacles.get(i);
            if (y > w.getY() && y < w.getY() + w.getHeight()) {
                nextStage(i + 1);
            }
        }
    }

    @Override
    public void postFade() {
        // Start heartbeat, throbbing and alarm
        heartBeat.startTimer();
        entity.startThrob(0.125f);
        Tween.call(alarmCallback).start(game.tweenManager);
    }

    @Override
    protected void checkCollisions() {
        for (SpriteObject so : obstacles) {
            entity.collidedWith(so);
        }
        if (!complete && entity.collidedWith(portal)) {
            complete = true;
            entity.stopThrob();
            entity.enterPortal(portal);
        }
    }

    @Override
    protected void nextScreen() {
        heartBeat.cancelTimer();
        super.nextScreen();
    }

    private void nextStage(int next) {
        if (stage != next && next > stage) {
            stage = next;
            switch(next) {
                case 1:
                    heartBeat.changePace(HeartBeat.PACE.STEADY);
                    break;
                case 2:
                    heartBeat.changePace(HeartBeat.PACE.SLIGHT);
                    break;
                case 3:
                    heartBeat.changePace(HeartBeat.PACE.DEAD);
                    game.tweenManager.killTarget(this);
                    Color c = game.assets.OFFWHITE;
                    Tween.to(this, ViewAccessor.COLOR, 1.75f).target(c.r, c.g, c.b, 1).ease(TweenEquations.easeInOutQuad).start(game.tweenManager);
                    break;
                default:
            }
            entity.setStage(next);
        }
    }

}
