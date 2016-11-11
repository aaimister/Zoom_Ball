package com.splitseed.accessors;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpriteAccessor implements TweenAccessor<Sprite> {

    public static final int ALPHA = 1;
    public static final int POSITION = 2;
    public static final int ROTATION = 3;
    public static final int COLOR = 4;
    public static final int SCALE = 5;
    public static final int WIDTH = 6;
    public static final int HEIGHT = 7;
    public static final int WIDTHHEIGHT = 8;
    public static final int RGB = 9;

    @Override
    public int getValues(Sprite target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case ALPHA:
                returnValues[0] = target.getColor().a;
                return 1;

            case POSITION:
                returnValues[0] = target.getX();
                returnValues[1] = target.getY();
                return 2;

            case ROTATION:
                returnValues[0] = target.getRotation();
                return 1;

            case COLOR:
                returnValues[0] = target.getColor().r;
                returnValues[1] = target.getColor().g;
                returnValues[2] = target.getColor().b;
                returnValues[3] = target.getColor().a;
                return 4;

            case SCALE:
                returnValues[0] = target.getScaleX();
                returnValues[1] = target.getScaleY();
                return 2;

            case WIDTH:
                returnValues[0] = target.getWidth();
                return 1;

            case HEIGHT:
                returnValues[0] = target.getHeight();
                return 1;

            case WIDTHHEIGHT:
                returnValues[0] = target.getWidth();
                returnValues[1] = target.getHeight();
                return 2;

            case RGB:
                returnValues[0] = target.getColor().r;
                returnValues[1] = target.getColor().g;
                returnValues[2] = target.getColor().b;
                return 3;

            default:
                assert false;
                return -1;
        }
    }

    @Override
    public void setValues(Sprite target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case ALPHA:
                target.setAlpha(newValues[0]);
                break;

            case POSITION:
                target.setPosition(newValues[0], newValues[1]);
                break;

            case ROTATION:
                target.setRotation(newValues[0]);
                break;

            case COLOR:
                target.setColor(newValues[0], newValues[1], newValues[2], newValues[3]);
                break;

            case SCALE:
                target.setScale(newValues[0], newValues[1]);
                break;

            case WIDTH:
                target.setSize(newValues[0], target.getHeight());
                break;

            case HEIGHT:
                target.setSize(target.getWidth(), newValues[0]);
                break;

            case WIDTHHEIGHT:
                target.setSize(newValues[0], newValues[1]);
                break;

            case RGB:
                target.setColor(newValues[0], newValues[1], newValues[2], target.getColor().a);
                break;

            default:
                assert false;
        }
    }
}
