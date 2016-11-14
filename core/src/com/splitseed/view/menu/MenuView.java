package com.splitseed.view.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.splitseed.objects.Ball;
import com.splitseed.view.ViewAdapter;
import com.splitseed.zoomball.ZoomBall;

public class MenuView extends ViewAdapter {

    private Ball ball;

    public MenuView(ZoomBall game, Color background) {
        super(game, background);
        float size = 25 * ZoomBall.SCALE_Y;
        ball = new Ball(game.assets, game.tweenManager, (ZoomBall.SCREEN_WIDTH - size) / 2.0f, (ZoomBall.SCREEN_HEIGHT - size) / 2.0f, size, size);
    }

    @Override
    public void update(float delta) {
//        float accelX = -Gdx.input.getAccelerometerX();
//        float accelY = Gdx.input.getAccelerometerY();
//
//        float nextX = ball.getX() + accelX;
//        if (nextX >= 0 && nextX + ball.getWidth() <= ZoomBall.SCREEN_WIDTH) {
//            ball.translateX(accelX);
//        }
//
//        float nextY = ball.getY() + accelY;
//        if (nextY >= 0 && nextY + ball.getHeight() <= ZoomBall.SCREEN_HEIGHT) {
//            ball.translateY(accelY);
//        }
        ball.update(delta);
    }

    @Override
    public void drawSpriteBatch(SpriteBatch spriteBatch, float runTime) {
        ball.drawSpriteBatch(spriteBatch, runTime);
    }
}
