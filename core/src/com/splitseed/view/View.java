package com.splitseed.view;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.splitseed.accessors.ViewAccessor;
import com.splitseed.objects.SpriteObject;
import com.splitseed.zoomball.Etheric;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public abstract class View extends Observable implements Screen, InputProcessor {

    protected Etheric game;
    protected Color background;
    protected Color endColor;
    private List<SpriteObject> alphaListeners;

    protected float alpha;

    public View(Etheric game, Color background) {
        this.game = game;
        this.background = background.cpy();
        endColor = background.cpy();
        alphaListeners = new ArrayList<SpriteObject>();
    }

    public abstract void update(float delta);

    @Override
    public void render(float delta) {
        // Do nothing.  This method is never going to be called.
    }

    public abstract void drawSpriteBatch(SpriteBatch spriteBatch, float runTime);

    public abstract void drawShapeRenderer(ShapeRenderer shapeRenderer, float runTime);

    public View focus(Color startColor) {
        preFade();
        game.tweenManager.killTarget(this);
        alpha = 0;
        setBackground(startColor);
        Timeline t = Timeline.createParallel();
        t.push(Tween.to(this, ViewAccessor.COLOR, 1).target(endColor.r, endColor.g, endColor.b, endColor.a).ease(TweenEquations.easeInOutQuad));
        t.push(Tween.to(this, ViewAccessor.ALPHA, 1).target(1).ease(TweenEquations.easeInOutQuad));
        for (SpriteObject so : alphaListeners) {
            t.push(so.fade(1, 1, SpriteObject.Fade.IN));
        }
        t.setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                postFade();
            }
        }).start(game.tweenManager);
        return this;
    }

    public abstract void preFade();

    public abstract void postFade();

    public void setBackground(Color color) {
        setBackground(color.r, color.g, color.b, color.a);
    }

    public void setBackground(float r, float g, float b, float a) {
        background.set(r, g, b, a);
    }

    public void setEndColor(Color color) {
        setEndColor(color.r, color.g, color.b, color.a);
    }

    public void setEndColor(float r, float g, float b, float a) {
        endColor.set(r, g, b, a);
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public float getAlpha() {
        return alpha;
    }

    public Color getBackground() {
        return background;
    }

    public void addAlphaListener(SpriteObject ...list) {
        for (SpriteObject so : list) { alphaListeners.add(so); }
    }

    public void removeAlphaListener(SpriteObject ...list) {
        for (SpriteObject so : list) { alphaListeners.remove(so); }
    }

}
