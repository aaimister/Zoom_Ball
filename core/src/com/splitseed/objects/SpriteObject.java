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
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.splitseed.accessors.SpriteAccessor;
import com.splitseed.util.Assets;

import static com.badlogic.gdx.graphics.g2d.Batch.*;

public abstract class SpriteObject extends Sprite implements InputProcessor {

    public enum Fade { IN, OUT }

    protected Assets assets;
    protected TweenManager tweenManager;
    protected Circle circleBounds;
    protected Polygon polygonBounds;

    public SpriteObject(Assets assets, TweenManager tweenManager, float x, float y, float width, float height) {
        super();
        this.assets = assets;
        this.tweenManager = tweenManager;
        setFlip(false, true);
        setBounds(x, y, width, height);
        setOriginCenter();
    }

    public SpriteObject(Texture texture, Assets assets, TweenManager tweenManager, float x, float y, float width, float height) {
        super(texture);
        this.assets = assets;
        this.tweenManager = tweenManager;
        setFlip(false, true);
        setBounds(x, y, width, height);
        setOriginCenter();
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

    public abstract boolean collidedWith(SpriteObject other);

    public Circle getBoundingCircle() {
        final float[] vertices = getVertices();

        float minx = vertices[X1];
        float miny = vertices[Y1];
        float maxx = vertices[X1];
        float maxy = vertices[Y1];

        minx = minx > vertices[X2] ? vertices[X2] : minx;
        minx = minx > vertices[X3] ? vertices[X3] : minx;
        minx = minx > vertices[X4] ? vertices[X4] : minx;

        maxx = maxx < vertices[X2] ? vertices[X2] : maxx;
        maxx = maxx < vertices[X3] ? vertices[X3] : maxx;
        maxx = maxx < vertices[X4] ? vertices[X4] : maxx;

        miny = miny > vertices[Y2] ? vertices[Y2] : miny;
        miny = miny > vertices[Y3] ? vertices[Y3] : miny;
        miny = miny > vertices[Y4] ? vertices[Y4] : miny;

        maxy = maxy < vertices[Y2] ? vertices[Y2] : maxy;
        maxy = maxy < vertices[Y3] ? vertices[Y3] : maxy;
        maxy = maxy < vertices[Y4] ? vertices[Y4] : maxy;

        if (circleBounds == null) circleBounds = new Circle();
        circleBounds.x = (minx + maxx) / 2;
        circleBounds.y = (miny + maxy) / 2;
        circleBounds.radius = getWidth() / 2;
        return circleBounds;
    }

    public Polygon getBoundingPolygon() {
        if (polygonBounds == null) polygonBounds = new Polygon();
        final float[] vertices = getVertices();
        float[] boundVerts = {
                vertices[X1], vertices[Y1], vertices[X2], vertices[Y2],
                vertices[X3], vertices[Y3], vertices[X4], vertices[Y4]
        };
        polygonBounds.setVertices(boundVerts);
        return polygonBounds;
    }

    private void rotatePoint(Vector2 point, float angle) {
        angle = (float) (angle * (Math.PI / 180));
        float rotatedX = (float) (Math.cos(angle) * (point.x - getOriginX()) - Math.sin(angle) * (point.y - getOriginX()) + getOriginX());
        float rotatedY = (float) (Math.sin(angle) * (point.x - getOriginX()) + Math.cos(angle) * (point.y - getOriginY()) + getOriginY());
        point.set(rotatedX, rotatedY);
    }

    public Tween fade(float duration, float alpha, Fade fadeType) {
        setAlpha(fadeType.equals(Fade.OUT) ? 1.0f : 0.0f);
        return Tween.to(this, SpriteAccessor.ALPHA, duration).target(alpha).ease(TweenEquations.easeInOutQuad);
    }

}
