package com.splitseed.zoomball;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.splitseed.accessors.SpriteAccessor;
import com.splitseed.accessors.SpriteObjectAccessor;
import com.splitseed.accessors.ViewAccessor;
import com.splitseed.util.Assets;
import com.splitseed.util.InputHandler;
import com.splitseed.objects.SpriteObject;
import com.splitseed.view.View;

import java.awt.*;
import java.util.Stack;

public class ZoomBall extends Game {

	// The width and height the game is designed around.
	private static float DESIGN_WIDTH = 360.0f;
	private static float DESIGN_HEIGHT = 640.0f;

	public static int SCREEN_WIDTH;
	public static int SCREEN_HEIGHT;
	// The scale from the designed width and height.
	public static float SCALE_X;
	public static float SCALE_Y;

	public Assets assets;
	public TweenManager tweenManager;
	private OrthographicCamera cam;
	private SpriteBatch spriteBatch;
	private ShapeRenderer shapeRenderer;
	private Stack<View> viewStack;
	private InputHandler inputHandler;

	@Override
	public void create () {
		SCREEN_WIDTH = Gdx.graphics.getWidth();
		SCREEN_HEIGHT = Gdx.graphics.getHeight();
		SCALE_X = SCREEN_WIDTH / DESIGN_WIDTH;
		SCALE_Y = SCREEN_HEIGHT / DESIGN_HEIGHT;

		assets = new Assets();
		tweenManager = new TweenManager();
		Tween.setCombinedAttributesLimit(4);
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		Tween.registerAccessor(SpriteObject.class, new SpriteObjectAccessor());
		Tween.registerAccessor(View.class, new ViewAccessor());

		cam = new OrthographicCamera();
		cam.setToOrtho(true, SCREEN_WIDTH, SCREEN_HEIGHT);
		spriteBatch = new SpriteBatch();
		spriteBatch.setProjectionMatrix(cam.combined);
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(cam.combined);

		viewStack = new Stack<View>();
		LoadScreen ls = new LoadScreen(this, Color.BLACK);
		inputHandler = new InputHandler(ls);
		Gdx.input.setInputProcessor(inputHandler);

		setScreen(ls);
	}

	@Override
	public void render () {
		tweenManager.update(Gdx.graphics.getDeltaTime());
		View view = (View) screen;
		view.update(Gdx.graphics.getDeltaTime());

		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		// Draw shapes first.
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		view.drawShapeRenderer(shapeRenderer, Gdx.graphics.getDeltaTime());
		shapeRenderer.end();

		Gdx.gl.glDisable(GL20.GL_BLEND);

		// Draw sprites second.
		spriteBatch.begin();
		view.drawBatcher(spriteBatch, Gdx.graphics.getDeltaTime());
		spriteBatch.end();
	}
	
	@Override
	public void dispose () {
		super.dispose();
	}

	/**
	 * Sets the previous view to the current one if there is one.
	 */
	public void prevView() {
		if (viewStack.isEmpty()) return;
		setScreen(viewStack.pop());
	}

	/**
	 * Adds the given view to the previous stack.
	 * @param view - the view to add to the stack.
	 */
	public void addViewToPrevious(View view) {
		viewStack.push(view);
	}

	@Override
	public void setScreen(Screen screen) {
		// Make sure we only get a View and update the input handler.
		if (!(screen instanceof View)) { throw new IllegalArgumentException("Screen needs to be a View."); }
		inputHandler.setAdapter((View) screen);
		super.setScreen(screen);
	}
}
