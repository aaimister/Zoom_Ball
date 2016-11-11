package com.splitseed.objects;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.splitseed.accessors.SpriteAccessor;
import com.splitseed.util.Assets;

public abstract class SpriteObject extends Sprite implements InputProcessor {

    public enum Fade { IN, OUT }

    protected Assets assets;
    protected TweenManager tweenManager;
    protected Circle bounds;

    public SpriteObject(Assets assets, TweenManager tweenManager, float x, float y, int width, int height) {
        super();
        this.assets = assets;
        this.tweenManager = tweenManager;
        setFlip(false, true);
        setBounds(x, y, width, height);
        setOrigin(x + (width / 2.0f), y + (height / 2.0f));
    }

    public SpriteObject(Texture texture, Assets assets, TweenManager tweenManager, float x, float y, int width, int height) {
        super(texture);
        this.assets = assets;
        this.tweenManager = tweenManager;
        setFlip(false, true);
        setBounds(x, y, width, height);
        setOrigin(x + (width / 2.0f), y + (height / 2.0f));
    }

    public abstract void update(float delta);

    /**
     * Draws the sprites for the given SpriteObject.
     * @param spriteBatch - the SpriteBatch to use to draw.
     * @param runTime - the run time of the current game.
     */
    public abstract void drawSpriteBatch(SpriteBatch spriteBatch, float runTime);

    /**
     * Draws the shapes for the given SpriteObject.
     * @param shapeRenderer - the ShapeRenderer to use to draw.
     * @param runTime - the run time of the current game.
     */
    public abstract void drawShapeRenderer(ShapeRenderer shapeRenderer, float runTime);

    public Circle getBoundingCircle() {
        if (bounds == null) { bounds = new Circle(); }
        Rectangle rec = getBoundingRectangle();
        bounds.x = rec.x + (getWidth() / 2.0f);
        bounds.y = rec.y + (getHeight() / 2.0f);
        bounds.radius = rec.width / 2.0f;
        return bounds;
    }

//    public float getRotation() {
//        return rotation;
//    }
//
//    public void setRotation(float rotation) {
//        this.rotation = rotation;
//    }

    public Tween fade(float duration, float alpha, Fade fadeType) {
        setAlpha(fadeType.equals(Fade.OUT) ? 1.0f : 0.0f);
        return Tween.to(this, SpriteAccessor.ALPHA, duration).target(alpha).ease(TweenEquations.easeInOutQuad);
    }

}
