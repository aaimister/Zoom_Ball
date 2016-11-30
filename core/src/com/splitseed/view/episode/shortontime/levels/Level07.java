package com.splitseed.view.episode.shortontime.levels;

import aurelienribon.tweenengine.Tween;
import com.badlogic.gdx.graphics.Color;
import com.splitseed.objects.*;
import com.splitseed.view.Level;
import com.splitseed.zoomball.Etheric;

public class Level07 extends Level {

    private HeartBeat heartBeat;

    private boolean alarm;

    public Level07(Etheric game, Color background, Entity entity, Portal portal) {
        super(game, background, entity, portal);

        heartBeat = new HeartBeat(HeartBeat.PACE.STEADY);

        // Set up the walls
        float width = (Etheric.SCREEN_WIDTH - Entity.DEFAULT_SIZE * 2) / 2;
        float height = (Etheric.SCREEN_HEIGHT - Entity.DEFAULT_SIZE * 6) / 2;
            // Top Right
        addWall(game.assets.BLACK, Etheric.SCREEN_WIDTH - width, Entity.DEFAULT_SIZE * 2, width, height);
            // Bottom Right
        addWall(game.assets.BLACK, Etheric.SCREEN_WIDTH - width, Entity.DEFAULT_SIZE * 4 + height, width, height);
            // Center
        float width2 = Etheric.SCREEN_WIDTH - Portal.DEFAULT_SIZE - (30 * Etheric.SCALE_Y) - width - Entity.DEFAULT_SIZE * 4;
        float height2 = Etheric.SCREEN_HEIGHT - Entity.DEFAULT_SIZE * 4;
        float x = Portal.DEFAULT_SIZE + (30 * Etheric.SCALE_Y) + Entity.DEFAULT_SIZE * 2;
        addWall(game.assets.BLACK, x, Entity.DEFAULT_SIZE * 2, width2, height2);
            // Top Center
        addWall(game.assets.BLACK, Entity.DEFAULT_SIZE * 2, Entity.DEFAULT_SIZE * 2 + width2, x - Entity.DEFAULT_SIZE * 2, width2);
            // Bottom Center
        addWall(game.assets.BLACK, Entity.DEFAULT_SIZE * 2, Etheric.SCREEN_HEIGHT - width2 * 2 - Entity.DEFAULT_SIZE * 2, x - Entity.DEFAULT_SIZE * 2, width2);
            // Left Top Center
        addWall(game.assets.BLACK, 0, Entity.DEFAULT_SIZE * 4 + width2 * 2, x - Entity.DEFAULT_SIZE * 2, width2);
            // Left Bottom Center
        addWall(game.assets.BLACK, 0, Etheric.SCREEN_HEIGHT - width2 * 3 - Entity.DEFAULT_SIZE * 4, x - Entity.DEFAULT_SIZE * 2, width2);

        // Set up the capsules
        addCapsule((Etheric.SCREEN_WIDTH - Capsule.DEFAULT_CAPSULE_SIZE) / 2, (Etheric.SCREEN_HEIGHT - Capsule.DEFAULT_CAPSULE_SIZE) / 2, Capsule.DEFAULT_CAPSULE_SIZE, Capsule.DEFAULT_CAPSULE_SIZE);

        // Add the walls and capsules to the fade in
        addAlphaListener(obstacles.toArray(new SpriteObject[obstacles.size()]));
    }

    @Override
    public void show() {
        super.show();
        alarm = false;
        // Reset entity, portal and capsules
        float offset = 15 * Etheric.SCALE_Y;
        float size = Entity.DEFAULT_SIZE + (Capsule.DEFAULT_CAPSULE_GROWTH * entity.getCapsuleCount());
        entity.reset(Etheric.SCREEN_WIDTH - Entity.DEFAULT_SIZE - offset, (Etheric.SCREEN_HEIGHT - Entity.DEFAULT_SIZE) / 2, size, size);
        portal.reset(offset, (Etheric.SCREEN_HEIGHT - Portal.DEFAULT_SIZE) / 2, Portal.DEFAULT_SIZE, Portal.DEFAULT_SIZE);
        portal.startRotation();
        resetCapsules();
    }

    @Override
    public void fadeOver() {
        // Start the capsule animations and heartbeat
        startCapsules();
        heartBeat.startTimer();
    }

    @Override
    protected void checkCollisions() {
        for (SpriteObject so : obstacles) {
            if (so instanceof Capsule) {
                if (entity.collidedWith(so)) {
                    if (!alarm) {
                        alarm = true;
                        entity.setSize(Entity.DEFAULT_SIZE + 9 * Capsule.DEFAULT_CAPSULE_GROWTH, Entity.DEFAULT_SIZE + 9 * Capsule.DEFAULT_CAPSULE_GROWTH);
                        Tween.call(alarmCallback).start(game.tweenManager);
                        heartBeat.changePace(HeartBeat.PACE.RAPID, 500);
                    }
                }
            } else {
                entity.collidedWith(so);
            }
        }
        if (!complete && entity.collidedWith(portal)) {
            complete = true;
            entity.enterPortal(portal);
        }
    }

    @Override
    protected void nextScreen() {
        heartBeat.cancelTimer();
        super.nextScreen();
    }
}
