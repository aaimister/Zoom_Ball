package com.splitseed.view.episode.shortontime.levels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.splitseed.objects.Entity;
import com.splitseed.objects.Portal;
import com.splitseed.objects.SpriteObject;
import com.splitseed.objects.capsule.NourishmentCapsule;
import com.splitseed.view.Level;
import com.splitseed.view.episode.shortontime.story.Scene05;
import com.splitseed.zoomball.Etheric;

public class Level10 extends Level {

    private Portal bluePortal;
    private Scene05 scene05;

    public Level10(Etheric game, Color background, Entity entity, Portal portal, Scene05 scene05) {
        super(game, background, entity, portal);
        this.scene05 = scene05;

        bluePortal = new Portal(game.assets, game.tweenManager, 0, 0, Portal.DEFAULT_SIZE, Portal.DEFAULT_SIZE);
        addAlphaListener(bluePortal);

        // Set up walls
        float width = (Etheric.SCREEN_WIDTH - (Entity.DEFAULT_SIZE * 2)) / 2;
        float height = (Etheric.SCREEN_HEIGHT - (Entity.DEFAULT_SIZE * 2)) / 2;
            // Top
        addWall(game.assets.BLACK, 0, 0, Etheric.SCREEN_WIDTH, height);
            // Bottom Left
        addWall(game.assets.BLACK, 0, height + Entity.DEFAULT_SIZE * 2, width, height);
            // Bottom Right
        addWall(game.assets.BLACK, width + Entity.DEFAULT_SIZE * 2, height + Entity.DEFAULT_SIZE * 2, width, height);

        // Add the walls to the fade in
        addAlphaListener(obstacles.toArray(new SpriteObject[obstacles.size()]));
    }

    @Override
    public void preFade() {
        // Reset entity and portal
        float offset = Entity.DEFAULT_SIZE / 2;
        float size = Entity.DEFAULT_SIZE + (NourishmentCapsule.DEFAULT_GROWTH * 9);
        entity.reset((Etheric.SCREEN_WIDTH - size) / 2, Etheric.SCREEN_HEIGHT - size - offset, size, size);
        entity.resetCast(0, 0);
        portal.reset(offset, (Etheric.SCREEN_HEIGHT - Portal.DEFAULT_SIZE) / 2, Portal.DEFAULT_SIZE, Portal.DEFAULT_SIZE);
        portal.setColor(game.assets.RED.r, game.assets.RED.g, game.assets.RED.b, 0);
        portal.startRotation();
        bluePortal.reset(Etheric.SCREEN_WIDTH - Portal.DEFAULT_SIZE - offset, (Etheric.SCREEN_HEIGHT - Portal.DEFAULT_SIZE) / 2, Portal.DEFAULT_SIZE, Portal.DEFAULT_SIZE);
        bluePortal.setColor(game.assets.BLUE.r, game.assets.BLUE.g, game.assets.BLUE.b, 0);
        bluePortal.startRotation();
    }

    @Override
    public void drawSpriteBatch(SpriteBatch spriteBatch, float runTime) {
        bluePortal.drawSpriteBatch(spriteBatch, runTime);
        super.drawSpriteBatch(spriteBatch, runTime);
    }

    @Override
    protected void checkCollisions() {
        for (SpriteObject so : obstacles) {
            entity.collidedWith(so);
        }
        if (!complete) {
            if (entity.collidedWith(portal)) {
                complete = true;
                entity.enterPortal(portal);
                scene05.setShow(1);
            } else if (entity.collidedWith(bluePortal)) {
                complete = true;
                entity.enterPortal(bluePortal);
                scene05.setShow(2);
            }
        }
    }
}
