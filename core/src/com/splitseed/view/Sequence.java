package com.splitseed.view;

import com.splitseed.objects.Entity;
import com.splitseed.objects.Portal;
import com.splitseed.zoomball.Etheric;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public abstract class Sequence extends Observable implements Observer {

    protected List<View> sequence;
    protected Etheric game;
    protected Entity entity;
    protected Portal portal;

    protected int currentView;
    protected int previousView;

    protected long startTime;

    public Sequence(Etheric game) {
        this(game, new ArrayList<View>());
    }

    public Sequence(Etheric game, List<View> sequence) {
        this.game = game;
        this.sequence = sequence;
        entity = new Entity(game.assets, game.tweenManager, 0, 0, 0, 0);
        portal = new Portal(game.assets, game.tweenManager, 0, 0, 0, 0);
        currentView = 0;
        previousView = -1;
    }

    protected abstract void setup();

    public View getPreviousView() {
        if (previousView == -1) return null;
        return sequence.get(previousView);
    }

    public View getCurrentView() {
        if (currentView < 0 || currentView >= sequence.size()) return null;
        return sequence.get(currentView);
    }

    protected void addView(View view) {
        sequence.add(view);
        view.addObserver(this);
    }

    protected void incrementCurrentView() {
        previousView = currentView;
        currentView = currentView + 1 >= sequence.size() ? 0 : currentView + 1;
    }

}
