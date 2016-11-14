package com.splitseed.objects;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.splitseed.accessors.SpriteAccessor;

import java.util.ArrayList;
import java.util.List;

public class Trail {

    private TweenManager tweenManager;
    private TextureRegion textureRegion;
    private List<Sprite> trail;

    public Trail(TweenManager tweenManager, TextureRegion textureRegion) {
        this.tweenManager = tweenManager;
        this.textureRegion = textureRegion;
        trail = new ArrayList<Sprite>();
    }

    public void drawSpriteBatch(SpriteBatch spriteBatch, float runTime) {
        for (int i = 0; i < trail.size(); i++) {
            Sprite s = trail.get(i);
            s.draw(spriteBatch);
            if (s.getColor().a <= 0.09) {
                trail.remove(i);
            }
        }
    }

    public void addTail(SpriteObject parent) {
        Sprite tail = new Sprite(textureRegion);
        float size = parent.getWidth();
        tail.setBounds(parent.getX() + (parent.getWidth() - size) / 2, parent.getY() + (parent.getHeight() - size) / 2, size, size);
        Color pc = parent.getColor();
        tail.setColor(pc.r, pc.g, pc.b, 0.50f);
        trail.add(tail);
        Tween.to(tail, SpriteAccessor.ALPHA, 0.2f).target(0.0f).ease(TweenEquations.easeInOutQuad).start(tweenManager);
    }

    public int getCount() {
        return trail.size();
    }

}
