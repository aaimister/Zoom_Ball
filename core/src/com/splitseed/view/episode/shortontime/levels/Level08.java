package com.splitseed.view.episode.shortontime.levels;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.gdx.graphics.Color;
import com.splitseed.accessors.SpriteAccessor;
import com.splitseed.objects.Entity;
import com.splitseed.objects.HeartBeat;
import com.splitseed.objects.Portal;
import com.splitseed.objects.SpriteObject;
import com.splitseed.objects.capsule.Capsule;
import com.splitseed.objects.capsule.EonCapsule;
import com.splitseed.objects.capsule.NourishmentCapsule;
import com.splitseed.view.Level;
import com.splitseed.zoomball.Etheric;

public class Level08 extends Level {

    private HeartBeat heartBeat;

    public Level08(Etheric game, Color background, Entity entity, Portal portal) {
        super(game, background, entity, portal);

        heartBeat = new HeartBeat(HeartBeat.PACE.RAPID);

        // Set up walls
        float width = (Etheric.SCREEN_WIDTH - Entity.DEFAULT_SIZE * 6) / 2;
        float height = Etheric.SCREEN_HEIGHT;
            // Left
        addWall(game.assets.BLACK, 0, 0, width, height);
            // Right
        addWall(game.assets.BLACK, Etheric.SCREEN_WIDTH - width, 0, width, height);
            // Bottom Left
        float width2 = Entity.DEFAULT_SIZE * 2;
        height = (Etheric.SCREEN_HEIGHT - Entity.DEFAULT_SIZE * 14) / 2;
        addWall(game.assets.BLACK, width, Etheric.SCREEN_HEIGHT - height - Entity.DEFAULT_SIZE * 4, width2, height);
            // Bottom Right
        addWall(game.assets.BLACK, Etheric.SCREEN_WIDTH - width - width2, Etheric.SCREEN_HEIGHT - height - Entity.DEFAULT_SIZE * 4, width2, height);
            // Top Left
        addWall(game.assets.BLACK, width, Entity.DEFAULT_SIZE * 4, width2, height);
            // Top Right
        addWall(game.assets.BLACK, Etheric.SCREEN_WIDTH - width - width2, Entity.DEFAULT_SIZE * 4, width2, height);
            // Left Top Gate - 6
        width2 = Entity.DEFAULT_SIZE * 3;
        addWall(game.assets.BLACK, width, 0, width2, Entity.DEFAULT_SIZE * 4);
            // Right Top Gate - 7
        addWall(game.assets.BLACK, Etheric.SCREEN_WIDTH - width - width2, 0, width2, Entity.DEFAULT_SIZE * 4);
            // Left Bottom Gate - 8
        addWall(game.assets.BLACK, width + Entity.DEFAULT_SIZE * 2, Entity.DEFAULT_SIZE * 4, Entity.DEFAULT_SIZE, height);
            // Right Bottom Gate - 9
        addWall(game.assets.BLACK, Etheric.SCREEN_WIDTH - width - Entity.DEFAULT_SIZE * 3, Entity.DEFAULT_SIZE * 4, Entity.DEFAULT_SIZE, height);

        // Set up capsules
        addCapsule(Capsule.CapsuleType.EON, (Etheric.SCREEN_WIDTH - NourishmentCapsule.DEFAULT_SIZE) / 2, (Etheric.SCREEN_HEIGHT - NourishmentCapsule.DEFAULT_SIZE) / 2, NourishmentCapsule.DEFAULT_SIZE, NourishmentCapsule.DEFAULT_SIZE);

        // Remove portal so it's not shown behind hidden walls
        removeAlphaListener(portal);
        // Add the walls and capsules to the fade in
        addAlphaListener(obstacles.toArray(new SpriteObject[obstacles.size()]));
    }

    @Override
    public void show() {
        super.show();
        // Reset entity, portal and gate
        float offset = Entity.DEFAULT_SIZE / 2;
        float size = Entity.DEFAULT_SIZE + (NourishmentCapsule.DEFAULT_GROWTH * 9);
        entity.reset((Etheric.SCREEN_WIDTH - size) / 2, Etheric.SCREEN_HEIGHT - size - offset, size, size);
        portal.reset((Etheric.SCREEN_WIDTH - Portal.DEFAULT_SIZE) / 2, offset, Portal.DEFAULT_SIZE, Portal.DEFAULT_SIZE);
        portal.startRotation();
        // 6, 7, 8, 9
        float width = (Etheric.SCREEN_WIDTH - Entity.DEFAULT_SIZE * 6) / 2;
        float width2 = Entity.DEFAULT_SIZE * 3;
            // Left Top Gate - 6
        obstacles.get(6).setPosition(width, 0);
            // Right Top Gate - 7
        obstacles.get(7).setPosition(Etheric.SCREEN_WIDTH - width - width2, 0);
            // Left Bottom Gate - 8
        obstacles.get(8).setPosition(width + Entity.DEFAULT_SIZE * 2, Entity.DEFAULT_SIZE * 4);
            // Right Bottom Gate - 9
        obstacles.get(9).setPosition(Etheric.SCREEN_WIDTH - width - Entity.DEFAULT_SIZE * 3, Entity.DEFAULT_SIZE * 4);
    }

    @Override
    public void preFade() {
        // Reset capsule
        resetCapsules();
        // Hide portal
        portal.setAlpha(0);
    }

    @Override
    public void postFade() {
        // Start the capsule animations, heartbeat, throbbing and alarm
        startCapsules();
        heartBeat.startTimer();
        entity.startThrob(0.25f);
        Tween.call(alarmCallback).start(game.tweenManager);
        // Show portal
        portal.setAlpha(1);
    }

    private void spreadGate() {
        //6, 7, 8, 9
        Timeline spread = Timeline.createParallel();
        SpriteObject gate = obstacles.get(6);
        spread.push(Tween.to(gate, SpriteAccessor.POSITION, 1.0f).target(gate.getX() - gate.getWidth(), gate.getY()).ease(TweenEquations.easeInOutQuad));
        gate = obstacles.get(7);
        spread.push(Tween.to(gate, SpriteAccessor.POSITION, 1.0f).target(gate.getX() + gate.getWidth(), gate.getY()).ease(TweenEquations.easeInOutQuad));
        gate = obstacles.get(8);
        spread.push(Tween.to(gate, SpriteAccessor.POSITION, 1.0f).target(gate.getX() - gate.getWidth(), gate.getY()).ease(TweenEquations.easeInOutQuad));
        gate = obstacles.get(9);
        spread.push(Tween.to(gate, SpriteAccessor.POSITION, 1.0f).target(gate.getX() + gate.getWidth(), gate.getY()).ease(TweenEquations.easeInOutQuad));
        spread.start(game.tweenManager);
    }

    @Override
    protected void checkCollisions() {
        for (SpriteObject so : obstacles) {
            if (so instanceof EonCapsule) {
                if (entity.collidedWith(so)) {
                    heartBeat.changePace(HeartBeat.PACE.FATAL);
                    spreadGate();
                }
            } else {
                entity.collidedWith(so);
            }
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

}
