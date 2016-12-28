package com.splitseed.objects.button;

import aurelienribon.tweenengine.TweenManager;
import com.splitseed.objects.SpriteObjectAdapter;
import com.splitseed.util.Assets;

public abstract class Button extends SpriteObjectAdapter {

    protected ButtonListener listener;

    public Button(Assets assets, TweenManager tweenManager, ButtonListener listener, float x, float y, float width, float height) {
        super(assets, tweenManager, x, y, width, height);
        this.listener = listener;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (getBoundingCircle().contains(screenX, screenY)) {
            listener.buttonDown(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (getBoundingCircle().contains(screenX, screenY)) {
            listener.buttonUp(this);
            return true;
        }
        return false;
    }

}
