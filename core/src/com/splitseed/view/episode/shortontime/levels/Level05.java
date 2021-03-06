package com.splitseed.view.episode.shortontime.levels;

import com.badlogic.gdx.graphics.Color;
import com.splitseed.objects.*;
import com.splitseed.objects.capsule.*;
import com.splitseed.objects.capsule.Capsule;
import com.splitseed.view.Level;
import com.splitseed.zoomball.Etheric;

public class Level05 extends Level {

    private HeartBeat heartBeat;

    public Level05(Etheric game, Color background, Entity entity, Portal portal) {
        super(game, background, entity, portal);

        heartBeat = new HeartBeat(HeartBeat.PACE.STEADY);

        // Set up the walls
        float width = (Etheric.SCREEN_WIDTH - (Entity.DEFAULT_SIZE * 2)) / 2;
        float height = 80 * Etheric.SCALE_Y;
            // Top Left Corner
        addWall(game.assets.BLACK, 0, 0, width, height);
            // Top Right Corner
        addWall(game.assets.BLACK, Etheric.SCREEN_WIDTH - width, 0, width, height);
            // Bottom Left Corner
        addWall(game.assets.BLACK, 0, Etheric.SCREEN_HEIGHT - height, width, height);
            // Bottom Right Corner
        addWall(game.assets.BLACK, Etheric.SCREEN_WIDTH - width, Etheric.SCREEN_HEIGHT - height, width, height);
            // Middle
        addWall(game.assets.BLACK, (Etheric.SCREEN_WIDTH - width) / 2, (Etheric.SCREEN_HEIGHT - height) / 2, width, height);

        // Set up the capsules
            // Bottom
        addCapsule(Capsule.CapsuleType.NOURISHMENT, (Etheric.SCREEN_WIDTH - NourishmentCapsule.DEFAULT_SIZE) / 2, Etheric.SCREEN_HEIGHT - (height * 1.5f) - NourishmentCapsule.DEFAULT_SIZE / 2, NourishmentCapsule.DEFAULT_SIZE, NourishmentCapsule.DEFAULT_SIZE);
            // Left
        addCapsule(Capsule.CapsuleType.NOURISHMENT, (Etheric.SCREEN_WIDTH - width) / 4 - NourishmentCapsule.DEFAULT_SIZE / 2, (Etheric.SCREEN_HEIGHT - NourishmentCapsule.DEFAULT_SIZE) / 2, NourishmentCapsule.DEFAULT_SIZE, NourishmentCapsule.DEFAULT_SIZE);
            // Right
        addCapsule(Capsule.CapsuleType.NOURISHMENT, (Etheric.SCREEN_WIDTH - (Etheric.SCREEN_WIDTH - width) / 4) - NourishmentCapsule.DEFAULT_SIZE / 2, (Etheric.SCREEN_HEIGHT - NourishmentCapsule.DEFAULT_SIZE) / 2, NourishmentCapsule.DEFAULT_SIZE, NourishmentCapsule.DEFAULT_SIZE);
            // Top
        addCapsule(Capsule.CapsuleType.NOURISHMENT, (Etheric.SCREEN_WIDTH - NourishmentCapsule.DEFAULT_SIZE) / 2, (Etheric.SCREEN_HEIGHT - height) / 4 - NourishmentCapsule.DEFAULT_SIZE / 2, NourishmentCapsule.DEFAULT_SIZE, NourishmentCapsule.DEFAULT_SIZE);

        // Add the walls and capsule to the fade in
        addAlphaListener(obstacles.toArray(new SpriteObject[obstacles.size()]));
    }

    @Override
    public void show() {
        super.show();
        // Reset entity and portal
        float offset = 15 * Etheric.SCALE_Y;
        entity.reset((Etheric.SCREEN_WIDTH - Entity.DEFAULT_SIZE) / 2, Etheric.SCREEN_HEIGHT - Entity.DEFAULT_SIZE - offset, Entity.DEFAULT_SIZE, Entity.DEFAULT_SIZE);
        portal.reset((Etheric.SCREEN_WIDTH - Portal.DEFAULT_SIZE) / 2, offset, Portal.DEFAULT_SIZE, Portal.DEFAULT_SIZE);
        portal.startRotation();
    }

    @Override
    public void preFade() {
        // Reset capsules
        resetCapsules();
    }

    @Override
    public void postFade() {
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
