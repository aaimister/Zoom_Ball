package com.splitseed.view.episode.shortontime;

import com.badlogic.gdx.graphics.Color;
import com.splitseed.view.Sequence;
import com.splitseed.view.episode.shortontime.levels.Level01;
import com.splitseed.view.episode.shortontime.levels.Level02;
import com.splitseed.view.episode.shortontime.levels.Level03;
import com.splitseed.view.episode.shortontime.levels.Level04;
import com.splitseed.view.episode.shortontime.story.Intro;
import com.splitseed.view.episode.shortontime.story.Scene01;
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
        // Level 02
        addView(new Level02(game, game.assets.OFFWHITE, entity, portal));
        // Scene 01
        addView(new Scene01(game, Color.BLACK));
        // Level 03
        Level04 level04 = new Level04(game, Color.BLACK, entity, portal);
        addView(new Level03(game, game.assets.OFFWHITE, entity, portal, level04));
        // Level 04
        addView(level04);
        // Scene 02

        // Level 05

        // Level 06

        // Level 07

        // Scene 03

        // Level 08

        // Level 09

        // Scene 04

        // Level 10

        // Scene 05
    }

    @Override
    public void update(Observable o, Object arg) {
        incrementCurrentView();
        setChanged();
        notifyObservers();
    }
}
