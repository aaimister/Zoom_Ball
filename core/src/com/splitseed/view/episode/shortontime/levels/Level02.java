package com.splitseed.view.episode.shortontime.levels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.splitseed.objects.*;
import com.splitseed.view.Level;
import com.splitseed.zoomball.Etheric;

public class Level02 extends Level {

    private PhoneTilt phoneTilt;

    public Level02(Etheric game, Color background, Entity entity, Portal portal) {
        super(game, background, entity, portal);

        // Set up the phone animation
        float size = 50 * Etheric.SCALE_Y;
        phoneTilt = new PhoneTilt(game.assets, game.tweenManager, (Etheric.SCREEN_WIDTH - size) / 2, (Etheric.SCREEN_HEIGHT - size) / 2, size, size, false);

        // Set up the walls
        float height = (Etheric.SCREEN_HEIGHT - (75 * Etheric.SCALE_Y)) / 2;
        Wall w = new Wall(game.assets, game.tweenManager, 0, 0, Etheric.SCREEN_WIDTH, height);
        w.setColor(Color.GRAY);
        obstacles.add(w);
        w = new Wall(game.assets, game.tweenManager, 0, Etheric.SCREEN_HEIGHT - height, Etheric.SCREEN_WIDTH, height);
        w.setColor(Color.GRAY);
        obstacles.add(w);
        // Add the walls to the fade in
        addAlphaListener(obstacles.toArray(new SpriteObject[obstacles.size()]));
    }

    @Override
    public void show() {
        super.show();
        // Reset the phone animation
        phoneTilt.startAnimation();
        // Reset the entity and portal
        float offset = 15 * Etheric.SCALE_Y;
        entity.reset(offset, (Etheric.SCREEN_HEIGHT - Entity.DEFAULT_SIZE) / 2, Entity.DEFAULT_SIZE, Entity.DEFAULT_SIZE);
        portal.reset(Etheric.SCREEN_WIDTH - Portal.DEFAULT_SIZE - offset, (Etheric.SCREEN_HEIGHT - Portal.DEFAULT_SIZE) / 2, Portal.DEFAULT_SIZE, Portal.DEFAULT_SIZE);
        portal.startRotation();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        // Hide the phone animation when they reach halfway
        if (phoneTilt.isShowing() && entity.getX() > Etheric.SCREEN_WIDTH / 2) {
            phoneTilt.hide();
        }
    }

    @Override
    public void drawSpriteBatch(SpriteBatch spriteBatch, float runTime) {
        phoneTilt.drawSpriteBatch(spriteBatch, runTime);
        super.drawSpriteBatch(spriteBatch, runTime);
    }
}
