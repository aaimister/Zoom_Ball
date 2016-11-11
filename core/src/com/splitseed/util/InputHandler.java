package com.splitseed.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class InputHandler implements InputProcessor {

    private InputProcessor inputProcessor;

    private int fingers;

    public InputHandler(InputProcessor inputAdapter) {
        setAdapter(inputAdapter);
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public boolean keyDown(int keycode) {
        return inputProcessor.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return inputProcessor.keyUp(keycode);
    }

    @Override
    public boolean keyTyped(char character) {
        return inputProcessor.keyTyped(character);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        fingers++;
        return inputProcessor.touchDown(screenX, screenY, fingers, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        fingers = fingers - 1 >= 0 ? fingers - 1 : 0;
        return inputProcessor.touchUp(screenX, screenY, fingers, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return inputProcessor.touchDragged(screenX, screenY, fingers);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public void setAdapter(InputProcessor inputProcessor) {
        if (inputProcessor == null) throw new IllegalArgumentException("Input Adapter cannot be null.");
        this.inputProcessor = inputProcessor;
    }
}
