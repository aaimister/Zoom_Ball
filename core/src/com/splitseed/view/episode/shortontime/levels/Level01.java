package com.splitseed.view.episode.shortontime.levels;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.splitseed.accessors.SpriteAccessor;
import com.splitseed.accessors.ViewAccessor;
import com.splitseed.objects.*;
import com.splitseed.view.Level;
import com.splitseed.zoomball.Etheric;

public class Level01 extends Level {

    private PhoneTilt phoneTilt;

    public Level01(Etheric game, Color background, Entity entity, Portal portal) {
        super(game, background, entity, portal);
        // Remove the entity and portal from the fade in
        removeAlphaListener(entity, portal);

        // Set up the phone animation
        float size = 50 * Etheric.SCALE_Y;
        phoneTilt = new PhoneTilt(game.assets, game.tweenManager, (Etheric.SCREEN_WIDTH - size) / 2, (Etheric.SCREEN_HEIGHT - size) / 2, size, size, true);
        phoneTilt.setColor(game.assets.GREY);

        // Set up the walls
        float width = (Etheric.SCREEN_WIDTH - (Entity.DEFAULT_SIZE * 2)) / 2;
        addWall(game.assets.GREY, 0, 0, width, Etheric.SCREEN_HEIGHT);
        addWall(game.assets.GREY, Etheric.SCREEN_WIDTH - width, 0, width, Etheric.SCREEN_HEIGHT);
    }

    @Override
    public void show() {
        super.show();
        // Reset the phone animation
        phoneTilt.reset();
        // Reset entity and portal
        float offset = 15 * Etheric.SCALE_Y;
        entity.resetCapsuleCount();
        entity.reset((Etheric.SCREEN_WIDTH - Entity.DEFAULT_SIZE) / 2, Etheric.SCREEN_HEIGHT - Entity.DEFAULT_SIZE - offset, Entity.DEFAULT_SIZE, Entity.DEFAULT_SIZE);
        entity.setColor(game.assets.GREY);
        entity.setAlpha(1);
        portal.reset((Etheric.SCREEN_WIDTH - Portal.DEFAULT_SIZE) / 2, offset, Portal.DEFAULT_SIZE, Portal.DEFAULT_SIZE);
        portal.setAlpha(0);
        portal.startRotation();

        // Set walls' alpha to 0
        obstacles.get(0).setAlpha(0);
        obstacles.get(1).setAlpha(0);
    }

    @Override
    public void postFade() {
        // Fade in stuff and things
        Timeline.createParallel()
                .push(Tween.to(this, ViewAccessor.COLOR, 1).target(game.assets.OFFWHITE.r, game.assets.OFFWHITE.g, game.assets.OFFWHITE.b, 1).ease(TweenEquations.easeNone))
                .push(Tween.to(portal, SpriteAccessor.ALPHA, 1).target(1).ease(TweenEquations.easeNone))
                .push(Tween.to(obstacles.get(0), SpriteAccessor.ALPHA, 1).target(1).ease(TweenEquations.easeNone))
                .push(Tween.to(obstacles.get(1), SpriteAccessor.ALPHA, 1).target(1).ease(TweenEquations.easeNone))
                .setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                phoneTilt.startAnimation();
            }
        })
                .start(game.tweenManager);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        // Hide the phone animation when they reach halfway
        if (phoneTilt.isShowing() && entity.getY() < Etheric.SCREEN_HEIGHT / 2) {
            phoneTilt.hide();
        }
    }

    @Override
    public void drawSpriteBatch(SpriteBatch spriteBatch, float runTime) {
        phoneTilt.drawSpriteBatch(spriteBatch, runTime);
        super.drawSpriteBatch(spriteBatch, runTime);
    }

}
