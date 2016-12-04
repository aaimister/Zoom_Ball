package com.splitseed.view;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.splitseed.accessors.ViewAccessor;
import com.splitseed.objects.Entity;
import com.splitseed.objects.Portal;
import com.splitseed.objects.SpriteObject;
import com.splitseed.objects.Wall;
import com.splitseed.objects.capsule.Capsule;
import com.splitseed.zoomball.Etheric;

import java.util.ArrayList;
import java.util.List;

public abstract class Level extends ViewAdapter {

    protected List<SpriteObject> obstacles;
    protected List<com.splitseed.objects.capsule.Capsule> capsules;
    protected Entity entity;
    protected Portal portal;

    protected boolean complete;

    public Level(Etheric game, Color background, Entity entity, Portal portal) {
        super(game, background);
        this.entity = entity;
        this.portal = portal;
        obstacles = new ArrayList<SpriteObject>();
        capsules = new ArrayList<com.splitseed.objects.capsule.Capsule>();
        complete = false;
        addAlphaListener(entity, portal);
    }

    protected TweenCallback alarmCallback = new TweenCallback() {
        @Override
        public void onEvent(int type, BaseTween<?> source) {
            Timeline.createSequence()
                    .push(Tween.to(Level.this, ViewAccessor.COLOR, 0.75f).target(game.assets.RED.r, game.assets.RED.g, game.assets.RED.b, 1).ease(TweenEquations.easeInOutQuad))
                    .push(Tween.to(Level.this, ViewAccessor.COLOR, 0.75f).target(game.assets.GREEN.r, game.assets.GREEN.g, game.assets.GREEN.b, 1).ease(TweenEquations.easeInOutQuad))
                    .setCallback(alarmCallback)
                    .start(game.tweenManager);
        }
    };

    @Override
    public void show() {
        complete = false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // Here for testing purposes to skip levels
        //nextScreen();
        return true;
    }

    @Override
    public void update(float delta) {
        portal.update(delta);
        if (!complete && !entity.hasEntered()) {
            entity.update(delta);
            checkCollisions();
        } else {
            if (complete && entity.hasEntered()) {
                nextScreen();
            }
        }
    }

    @Override
    public void drawSpriteBatch(SpriteBatch spriteBatch, float runTime) {
        portal.drawSpriteBatch(spriteBatch, runTime);
        for (SpriteObject so : obstacles)
            so.drawSpriteBatch(spriteBatch, runTime);
        entity.drawSpriteBatch(spriteBatch, runTime);
    }

    // Check to see if the entity has collided with anything
    protected void checkCollisions() {
        for (SpriteObject so : obstacles)
            entity.collidedWith(so);
        if (!complete && entity.collidedWith(portal)) {
            complete = true;
            entity.enterPortal(portal);
        }
    }

    // Add a wall with the given parameters
    protected void addWall(Color color, float x, float y, float width, float height) {
        Wall w = new Wall(game.assets, game.tweenManager, x, y, width, height);
        w.setColor(color);
        obstacles.add(w);
    }

    // Add a capsule with the given parameters
    protected void addCapsule(Capsule.CapsuleType type, float x, float y, float width, float height) {
        Capsule c = Capsule.getCapsule(game.assets, game.tweenManager, x, y, width, height, type);
//        Capsule c = new Capsule(game.assets, game.tweenManager, x, y, width, height);
        capsules.add(c);
        obstacles.add(c);
    }

    // Start the capsule aniamtions
    protected void startCapsules() {
        for (Capsule c : capsules) {
            c.startAnimation();
        }
    }

    // Reset the capsules
    protected void resetCapsules() {
        for (Capsule c : capsules) {
            c.reset();
        }
    }

    // Kill all tweens and notify for a screen change
    protected void nextScreen() {
        complete = false;
        game.tweenManager.killAll();
        setChanged();
        notifyObservers();
    }

}
