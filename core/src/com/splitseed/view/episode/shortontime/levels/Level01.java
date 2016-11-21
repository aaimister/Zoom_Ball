package com.splitseed.view.episode.shortontime.levels;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.gdx.graphics.Color;
import com.splitseed.accessors.SpriteAccessor;
import com.splitseed.accessors.ViewAccessor;
import com.splitseed.objects.Entity;
import com.splitseed.objects.Portal;
import com.splitseed.view.Level;
import com.splitseed.zoomball.Etheric;

public class Level01 extends Level {

    public Level01(Etheric game, Color background, Entity entity, Portal portal) {
        super(game, background, entity, portal);
        removeAlphaListener(entity, portal);
    }

    @Override
    public void show() {
        super.show();
        float offset = 10 * Etheric.SCALE_Y;
        entity.reset((Etheric.SCREEN_WIDTH - Entity.DEFAULT_SIZE) / 2, Etheric.SCREEN_HEIGHT - Entity.DEFAULT_SIZE - offset, Entity.DEFAULT_SIZE, Entity.DEFAULT_SIZE);
        entity.setColor(Color.GRAY);
        entity.setAlpha(0);
        portal.reset((Etheric.SCREEN_WIDTH - Portal.DEFAULT_SIZE) / 2, offset, Portal.DEFAULT_SIZE, Portal.DEFAULT_SIZE);
        portal.setAlpha(0);
        portal.startRotation();
        Timeline.createSequence()
                .pushPause(2)
                .push(Timeline.createParallel()
                        .push(Tween.to(this, ViewAccessor.COLOR, 1).target(215f/255f, 215f/255f, 215f/255f, 1).ease(TweenEquations.easeInOutCubic))
                        .push(Tween.to(entity, SpriteAccessor.ALPHA, 1).target(1).ease(TweenEquations.easeInOutCubic))
                        .push(Tween.to(portal, SpriteAccessor.ALPHA, 1).target(1).ease(TweenEquations.easeInOutCubic)))
                .start(game.tweenManager);
        // TODO add walls
    }

}
