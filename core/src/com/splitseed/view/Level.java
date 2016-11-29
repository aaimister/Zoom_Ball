package com.splitseed.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.splitseed.objects.Entity;
import com.splitseed.objects.Portal;
import com.splitseed.objects.SpriteObject;
import com.splitseed.objects.Wall;
import com.splitseed.zoomball.Etheric;

import java.util.ArrayList;
import java.util.List;

public abstract class Level extends ViewAdapter {

    protected List<SpriteObject> obstacles;
    protected Entity entity;
    protected Portal portal;

    private boolean complete;

    public Level(Etheric game, Color background, Entity entity, Portal portal) {
        super(game, background);
        this.entity = entity;
        this.portal = portal;
        obstacles = new ArrayList<SpriteObject>();
        complete = false;
        addAlphaListener(entity, portal);
    }

    @Override
    public void show() {
        complete = false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // Here for testing purposes to skip levels
        nextScreen();
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

    // Add a wall with the given parameters.
    protected void addWall(Color color, float x, float y, float width, float height) {
        Wall w = new Wall(game.assets, game.tweenManager, x, y, width, height);
        w.setColor(color);
        obstacles.add(w);
    }

    // Kill all tweens and notify for a screen change
    protected void nextScreen() {
        complete = false;
        //game.tweenManager.update(100);
        game.tweenManager.killAll();
        setChanged();
        notifyObservers();
    }

}
