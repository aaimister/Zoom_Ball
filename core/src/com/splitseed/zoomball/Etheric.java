package com.splitseed.zoomball;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.splitseed.accessors.SpriteAccessor;
import com.splitseed.accessors.ViewAccessor;
import com.splitseed.util.Assets;
import com.splitseed.util.InputHandler;
import com.splitseed.objects.SpriteObject;
import com.splitseed.view.Sequence;
import com.splitseed.view.View;
import com.splitseed.view.episode.shortontime.ShortOnTime;

import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

public class Etheric extends Game implements Observer {

	// The width and height the game is designed around.
	private static final float DESIGN_WIDTH = 360.0f;
	private static final float DESIGN_HEIGHT = 640.0f;

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
	private Sequence sequence;

	//private FPSLogger logger;

	@Override
	public void create () {
		//logger = new FPSLogger();

		assets = new Assets();
		tweenManager = new TweenManager();

		SCREEN_WIDTH = Gdx.graphics.getWidth();
		SCREEN_HEIGHT = Gdx.graphics.getHeight();
		SCALE_X = SCREEN_WIDTH / DESIGN_WIDTH;
		SCALE_Y = SCREEN_HEIGHT / DESIGN_HEIGHT;

		Tween.setCombinedAttributesLimit(4);
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		Tween.registerAccessor(View.class, new ViewAccessor());

		cam = new OrthographicCamera();
		cam.setToOrtho(true, SCREEN_WIDTH, SCREEN_HEIGHT);
		spriteBatch = new SpriteBatch();
		spriteBatch.setProjectionMatrix(cam.combined);
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(cam.combined);
		shapeRenderer.setAutoShapeType(true);

		viewStack = new Stack<View>();
		LoadScreen ls = new LoadScreen(this, Color.BLACK);
		inputHandler = new InputHandler(ls);
		Gdx.input.setInputProcessor(inputHandler);

		sequence = new ShortOnTime(this);
		sequence.addObserver(this);

		setScreen(ls);
	}

	@Override
	public void render () {
		// Log FPS
		//logger.log();

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
		view.drawSpriteBatch(spriteBatch, Gdx.graphics.getDeltaTime());
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

	// Only called from the LoadScreen to start the intro of our sequence
	public void start() {
		setScreen(sequence.getCurrentView().focus(Color.BLACK));
	}

	@Override
	public void setScreen(Screen screen) {
		// Make sure we only get a View and update the input handler
		if (!(screen instanceof View)) { throw new IllegalArgumentException("Screen needs to be a View."); }
		inputHandler.setAdapter((View) screen);
		super.setScreen(screen);
	}

	@Override
	public void update(Observable o, Object arg) {
		// Notified that the screen changed
		View prev = sequence.getPreviousView();
		// If there was a previous view, use that background color otherwise black
		Color c = prev != null ? prev.getBackground() : Color.BLACK;
		setScreen(sequence.getCurrentView().focus(c));
	}
}
