package com.splitseed.view.episode.shortontime;

import com.badlogic.gdx.graphics.Color;
import com.splitseed.view.Sequence;
import com.splitseed.view.episode.shortontime.levels.Level01;
import com.splitseed.view.episode.shortontime.story.Intro;
import com.splitseed.zoomball.Etheric;

import java.util.Observable;

public class ShortOnTime extends Sequence {

    public ShortOnTime(Etheric game) {
        super(game);
        setup();
    }

    @Override
    protected void setup() {
        // Add intro
        addView(new Intro(game, Color.BLACK));
        // Level 01
        addView(new Level01(game, Color.WHITE, entity, portal));
    }

    @Override
    public void update(Observable o, Object arg) {
        incrementCurrentView();
        setChanged();
        notifyObservers();
    }
}
