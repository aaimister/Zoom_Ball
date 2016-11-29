package com.splitseed.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TrailSprite extends Sprite {

    private long startTime;

    public TrailSprite(TextureRegion region, float alive) {
        super(region);
        startTime = System.currentTimeMillis() + (long) (alive * 1000);
    }

    public boolean kill() {
        return startTime - System.currentTimeMillis() <= 0;
    }
}
