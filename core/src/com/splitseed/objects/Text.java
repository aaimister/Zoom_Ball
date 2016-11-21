package com.splitseed.objects;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.splitseed.util.Assets;
import com.splitseed.zoomball.Etheric;

public class Text extends SpriteObjectAdapter {

    private String text;

    private int align;
    private float size;

    public Text(Assets assets, TweenManager tweenManager, String text, float x, float y, float width, float height) {
        super(assets, tweenManager, x, y, width, height);
        this.text = text;
        align = Align.center;
        size = 0.19f * Etheric.SCALE_Y;
        calcSize();
    }

    @Override
    public void drawSpriteBatch(SpriteBatch spriteBatch, float runTime) {
        spriteBatch.setColor(getColor());
        assets.setLayoutText(text, getColor(), getWidth(), align, true, size);
        assets.font.draw(spriteBatch, assets.layout, getX(), getY());
    }

    private void calcSize() {
        lessThan();
        greaterThan();
    }

    private void lessThan() {
        assets.setLayoutText(text, Color.WHITE, getWidth(), align, true, size);
        if (-assets.layout.height < getHeight()) {
            size += 0.01f;
            lessThan();
        }
    }

    private void greaterThan() {
        assets.setLayoutText(text, Color.WHITE, getWidth(), align, true, size);
        if (-assets.layout.height > getHeight() && size - 0.1f > 0) {
            size -= 0.01f;
            greaterThan();
        }
    }
}
