package com.splitseed.objects;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.splitseed.accessors.SpriteAccessor;
import com.splitseed.util.Assets;
import com.splitseed.util.SegementIntersector;
import com.splitseed.zoomball.Etheric;

public class Entity extends DynamicSpriteObject {

    public static final float DEFAULT_SIZE = 25 * Etheric.SCALE_Y;

    private Trail trail;

    private int capsuleCount;

    private boolean entered;

    public Entity(Assets assets, TweenManager tweenManager, float x, float y, float width, float height) {
        super(assets, tweenManager, x, y, width, height);
        trail = new Trail(tweenManager, assets.zoomBallLogo[0]);
        setColor(Color.BLACK);
    }

    // Method to handle the input for Desktop Applications
    private void handleKeys() {
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                acceleration.add(-0.5f, 0);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                acceleration.add(0.5f, 0);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                acceleration.add(0, -0.5f);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                acceleration.add(0, 0.5f);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                acceleration.setZero();
            }
        }
    }

    @Override
    public void update(float delta) {
        // Store the previous x and y position
        Vector2 prevPosition = new Vector2(getX(), getY());
        handleKeys();
        // Add acceleration
        acceleration.add(-Gdx.input.getAccelerometerX() / 5, Gdx.input.getAccelerometerY() / 5);
        // Set velocity to acceleration
        velocity.set(acceleration.x, acceleration.y);
        // Add world gravity
        velocity.add(0, 0);

        float next = getX() + velocity.x;
        if (next >= 0 && next + getWidth() <= Etheric.SCREEN_WIDTH) {
            translateX(velocity.x);
        } else {
            setX(next > Etheric.SCREEN_WIDTH - getWidth() ? Etheric.SCREEN_WIDTH - getWidth() : 0);
            acceleration.set(0, acceleration.y);
        }

        next = getY() + velocity.y;
        if (next >= 0 && next + getHeight() <= Etheric.SCREEN_HEIGHT) {
            translateY(velocity.y);
        } else {
            setY(next > Etheric.SCREEN_HEIGHT - getHeight() ? Etheric.SCREEN_HEIGHT - getHeight() : 0);
            acceleration.set(acceleration.x, 0);
        }

        // Only add a trail if the position is different from the last frame.
        if (prevPosition.x != getX() || prevPosition.y != getY()) {
            trail.addTail(this);
        }
    }

    @Override
    public void drawSpriteBatch(SpriteBatch spriteBatch, float runTime) {
        spriteBatch.setColor(getColor());
        trail.drawSpriteBatch(spriteBatch, runTime);
        spriteBatch.draw(assets.zoomBallLogo[0], getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    @Override
    public boolean collidedWith(SpriteObject other) {
        if (other instanceof Portal) {
            return other.getBoundingCircle().contains(getX() + (getWidth() / 2), getY() + (getHeight() / 2));
        } else if (other instanceof Wall) {
            Circle circ = getBoundingCircle();
            Rectangle rec = other.getBoundingRectangle();
            Rectangle recExp = new Rectangle(rec.x - circ.radius, rec.y - circ.radius, rec.width + circ.radius * 2, rec.height + circ.radius * 2);
            float centerX = getX() + (getWidth() / 2);
            float centerY = getY() + (getHeight() / 2);
            Vector2 next = new Vector2(centerX + velocity.x, centerY + velocity.y);
            Vector2 intersection = new Vector2();
            boolean isIntersection = SegementIntersector.cohenSutherlandIntersection(centerX, centerY, next.x, next.y, recExp, intersection);
            if (isIntersection) {
                boolean vertical = (getX() >= rec.x + 1 && getX() <= rec.x + rec.width - 1) || (getX() + getWidth() >= rec.x + 1 && getX() + getWidth() <= rec.x + rec.width - 1);
                boolean horizontal = (getY() >= rec.y + 1 && getY() <= rec.y + rec.height - 1) || (getY() + getHeight() >= rec.y + 1 && getY() + getHeight() <= rec.y + rec.height - 1);
                boolean resort = false;

                if (!vertical && !horizontal) {
                    resort = true;
                    vertical = true;
                    horizontal = true;
                }

                boolean isLeft = horizontal && intersection.x < rec.x;
                boolean isRight = horizontal && intersection.x > rec.x + rec.width;
                boolean isBottom = vertical && intersection.y > rec.y + rec.height;
                boolean isTop = vertical && intersection.y < rec.y;

                if (resort && vertical && horizontal) {
                    if (isBottom && isLeft) {
                        setPosition(other.getX() - getWidth() - 1, other.getY() + other.getHeight() + 1);
                        acceleration.set(0, 0);
                        return true;
                    } else if (isBottom && isRight) {
                        setPosition(other.getX() + other.getWidth() + 1, other.getY() + other.getHeight() + 1);
                        acceleration.set(0, 0);
                        return true;
                    } else if (isTop && isLeft) {
                        setPosition(other.getX() - getWidth() - 1, other.getY() - getHeight() - 1);
                        acceleration.set(0, 0);
                        return true;
                    } else if (isTop && isRight) {
                        setPosition(other.getX() + other.getWidth() + 1, other.getY() - getHeight() - 1);
                        acceleration.set(0, 0);
                        return true;
                    }
                }
                if (isLeft) {
                    setX(other.getX() - getWidth() - 1);
                    acceleration.set(0, acceleration.y);
                    return true;
                } else if (isRight) {
                    setX(other.getX() + other.getWidth() + 1);
                    acceleration.set(0, acceleration.y);
                    return true;
                } else if (isBottom) {
                    setY(other.getY() + other.getHeight() + 1);
                    acceleration.set(acceleration.x, 0);
                    return true;
                } else if (isTop) {
                    setY(other.getY() - getHeight() - 1);
                    acceleration.set(acceleration.x, 0);
                    return true;
                }
            }
        } else if (other instanceof Capsule) {
            if (!((Capsule)other).isEaten()) {
                if (other.getBoundingCircle().overlaps(getBoundingCircle())) {
                    capsuleCount++;
                    ((Capsule) other).eat();
                    setSize(getWidth() + Capsule.DEFAULT_CAPSULE_GROWTH, getHeight() + Capsule.DEFAULT_CAPSULE_GROWTH);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasEntered() {
        return entered;
    }

    public void reset(float x, float y, float width, float height) {
        entered = false;
        acceleration.setZero();
        velocity.setZero();
        setRotation(0);
        setBounds(x, y, width, height);
        setOriginCenter();
    }

    public int getCapsuleCount() {
        return capsuleCount;
    }

    public void resetCapsuleCount() {
        capsuleCount = 0;
    }

    public void enterPortal(Portal portal) {
        acceleration.setZero();
        velocity.setZero();
        setOrigin(getOriginX() + ((portal.getX() + portal.getWidth() / 2) - (getX() + getWidth() / 2)),
                getOriginY() + ((portal.getY() + portal.getHeight() / 2) - (getY() + getHeight() / 2)));
        float centerX = portal.getX() + portal.getWidth() / 2;
        float centerY = portal.getY() + portal.getHeight() / 2;
        Timeline.createParallel()
                .push(Tween.to(this, SpriteAccessor.ROTATION, 1.0f).target(360).ease(TweenEquations.easeOutCubic))
                .push(Tween.to(this, SpriteAccessor.WIDTHHEIGHT, 1.25f).target(0, 0).ease(TweenEquations.easeNone))
                .push(Tween.to(this, SpriteAccessor.POSITION, 1.0f).target(centerX, centerY).ease(TweenEquations.easeNone))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        entered = true;
                    }
                })
                .start(tweenManager);
    }

}
