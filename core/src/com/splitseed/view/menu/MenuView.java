package com.splitseed.view.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.splitseed.objects.Entity;
import com.splitseed.objects.Portal;
import com.splitseed.objects.Text;
import com.splitseed.view.ViewAdapter;
import com.splitseed.zoomball.Etheric;

public class MenuView extends ViewAdapter {

    private Entity entity;
    private Portal portal;
    private Text test;

    private boolean won;

    public MenuView(Etheric game, Color background) {
        super(game, background);
        float size = 25 * Etheric.SCALE_Y;
        entity = new Entity(game.assets, game.tweenManager, (Etheric.SCREEN_WIDTH - size) / 2.0f, (Etheric.SCREEN_HEIGHT - size) / 2.0f, size, size);
        size = 40 * Etheric.SCALE_Y;
        portal = new Portal(game.assets, game.tweenManager, size, Etheric.SCREEN_HEIGHT - (size * 2), size, size);

        String text = "Before the age of the expansion of industrialized science across the globe, there was a sudden extinction burst of alchemic research...";
        game.assets.setLayoutText(text, Color.WHITE, Etheric.SCREEN_WIDTH - 20 * Etheric.SCALE_Y, Align.center, true, 0.19f * Etheric.SCALE_Y);
        test = new Text(game.assets, game.tweenManager, text, 10 * Etheric.SCALE_Y, 20 * Etheric.SCALE_Y, Etheric.SCREEN_WIDTH - (20 * Etheric.SCALE_Y), -game.assets.layout.height);
    }

    @Override
    public void update(float delta) {
        portal.update(delta);
        if (!won) {
            entity.update(delta);
        } else {
            if (entity.hasEntered()) {
                float size = 25 * Etheric.SCALE_Y;
                entity.reset((Etheric.SCREEN_WIDTH - size) / 2.0f, (Etheric.SCREEN_HEIGHT - size) / 2.0f, size, size);
                won = false;
            }
        }
        checkCollisions();
    }

    @Override
    public void drawSpriteBatch(SpriteBatch spriteBatch, float runTime) {
        test.drawSpriteBatch(spriteBatch, runTime);
        portal.drawSpriteBatch(spriteBatch, runTime);
        entity.drawSpriteBatch(spriteBatch, runTime);
    }

    @Override
    public void drawShapeRenderer(ShapeRenderer shapeRenderer, float runTime) {
        super.drawShapeRenderer(shapeRenderer, runTime);
        portal.drawShapeRenderer(shapeRenderer, runTime);
        entity.drawShapeRenderer(shapeRenderer, runTime);
    }

    private void checkCollisions() {
        if (!won && entity.collidedWith(portal)) {
            won = true;
            entity.enterPortal(portal);
        }
    }

}
