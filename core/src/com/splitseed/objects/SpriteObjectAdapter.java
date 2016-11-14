package com.splitseed.objects;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.splitseed.util.Assets;

public abstract class SpriteObjectAdapter extends SpriteObject {

    public SpriteObjectAdapter(Assets assets, TweenManager tweenManager, float x, float y, float width, float height) {
        super(assets, tweenManager, x, y, width, height);
    }

    public SpriteObjectAdapter(Texture texture, Assets assets, TweenManager tweenManager, float x, float y, float width, float height) {
        super(texture, assets, tweenManager, x, y, width, height);
    }

    @Override
    public void update(float delta) {
        // Do nothing.
    }

    @Override
    public void drawShapeRenderer(ShapeRenderer shapeRenderer, float runTime) {
        // Do nothing.
    }

    @Override
    public boolean collidedWith(SpriteObject other) {
        // Do nothing.
        return false;
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
}
