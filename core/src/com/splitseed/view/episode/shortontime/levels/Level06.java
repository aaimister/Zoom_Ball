package com.splitseed.view.episode.shortontime.levels;

import com.badlogic.gdx.graphics.Color;
import com.splitseed.objects.*;
import com.splitseed.view.Level;
import com.splitseed.zoomball.Etheric;

public class Level06 extends Level {

    private HeartBeat heartBeat;

    public Level06(Etheric game, Color background, Entity entity, Portal portal) {
        super(game, background, entity, portal);

        heartBeat = new HeartBeat(HeartBeat.PACE.STEADY);

        // Set up the walls
        float width = (Etheric.SCREEN_WIDTH - (100 * Etheric.SCALE_Y)) / 2;
        float height = 80 * Etheric.SCALE_Y;
            // Top Left
        addWall(game.assets.BLACK, 0, Entity.DEFAULT_SIZE * 2, width, height);
            // Top Right
        addWall(game.assets.BLACK, Etheric.SCREEN_WIDTH - width, Entity.DEFAULT_SIZE * 2, width, height);
        float width2 = (Etheric.SCREEN_WIDTH - (Entity.DEFAULT_SIZE * 4));
        float height2 = (Etheric.SCREEN_HEIGHT - height - (Entity.DEFAULT_SIZE * 6) - Portal.DEFAULT_SIZE - (30 * Etheric.SCALE_Y)) / 2;
            // Center Top
        addWall(game.assets.BLACK, (Etheric.SCREEN_WIDTH - width2) / 2, Entity.DEFAULT_SIZE * 4 + height, width2, height2);
            // Center Bottom
        addWall(game.assets.BLACK, (Etheric.SCREEN_WIDTH - width2) / 2, Entity.DEFAULT_SIZE * 4 + height + height2 + Portal.DEFAULT_SIZE + 30 * Etheric.SCALE_Y, width2, height2);

        // Set up the capsules
            // Top Right
        addCapsule(Etheric.SCREEN_WIDTH - Capsule.DEFAULT_CAPSULE_SIZE - (Entity.DEFAULT_SIZE / 2), (Entity.DEFAULT_SIZE * 2 - Capsule.DEFAULT_CAPSULE_SIZE) / 2, Capsule.DEFAULT_CAPSULE_SIZE, Capsule.DEFAULT_CAPSULE_SIZE);
            // Middle Left
        float y = height + Entity.DEFAULT_SIZE * 4 + (height2 - Capsule.DEFAULT_CAPSULE_SIZE) / 2;
        addCapsule((Entity.DEFAULT_SIZE * 2 - Capsule.DEFAULT_CAPSULE_SIZE) / 2, y, Capsule.DEFAULT_CAPSULE_SIZE, Capsule.DEFAULT_CAPSULE_SIZE);
            // Middle Right
        addCapsule(Etheric.SCREEN_WIDTH - Capsule.DEFAULT_CAPSULE_SIZE - (Entity.DEFAULT_SIZE * 2 - Capsule.DEFAULT_CAPSULE_SIZE) / 2, y, Capsule.DEFAULT_CAPSULE_SIZE, Capsule.DEFAULT_CAPSULE_SIZE);
            // Bottom Center
        addCapsule((Etheric.SCREEN_WIDTH - Capsule.DEFAULT_CAPSULE_SIZE) / 2, Etheric.SCREEN_HEIGHT - Capsule.DEFAULT_CAPSULE_SIZE - (Entity.DEFAULT_SIZE * 2 - Capsule.DEFAULT_CAPSULE_SIZE) / 2, Capsule.DEFAULT_CAPSULE_SIZE, Capsule.DEFAULT_CAPSULE_SIZE);

        // Add the walls and capsules to the fade in
        addAlphaListener(obstacles.toArray(new SpriteObject[obstacles.size()]));
    }

    @Override
    public void show() {
        super.show();
        // Reset entity, portal and capsules
        float offset = Entity.DEFAULT_SIZE / 2;
        float size = Entity.DEFAULT_SIZE + (Capsule.DEFAULT_CAPSULE_GROWTH * entity.getCapsuleCount());
        entity.reset(offset, offset, size, size);
        portal.reset((Etheric.SCREEN_WIDTH - Portal.DEFAULT_SIZE) / 2, obstacles.get(2).getY() + obstacles.get(2).getHeight() + 15 * Etheric.SCALE_Y, Portal.DEFAULT_SIZE, Portal.DEFAULT_SIZE);
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
    protected void nextScreen() {
        heartBeat.cancelTimer();
        super.nextScreen();
    }

}
