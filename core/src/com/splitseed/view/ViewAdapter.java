package com.splitseed.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.splitseed.zoomball.Etheric;

public abstract class ViewAdapter extends View {

    public ViewAdapter(Etheric game, Color background) {
        super(game, background);
    }

    @Override
    public void update(float delta) {
        // Do nothing
    }

    @Override
    public void preFade() {
        // Do nothing
    }

    @Override
    public void postFade() {
        // Do nothing
    }

    @Override
    public void drawShapeRenderer(ShapeRenderer shapeRenderer, float runTime) {
        // Draw a rectangle that fits the screen with the current background color
        shapeRenderer.setColor(background);
        shapeRenderer.rect(0, 0, Etheric.SCREEN_WIDTH, Etheric.SCREEN_HEIGHT);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public void show() {
        // Do nothing
    }

    @Override
    public void resize(int width, int height) {
        // Do nothing
    }

    @Override
    public void pause() {
        // Do nothing
    }

    @Override
    public void resume() {
        // Do nothing
    }

    @Override
    public void hide() {
        // Do nothing
    }

    @Override
    public void dispose() {
        // Do nothing
    }
}
