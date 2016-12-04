package com.splitseed.view.episode.shortontime;

import com.badlogic.gdx.graphics.Color;
import com.splitseed.view.Sequence;
import com.splitseed.view.episode.shortontime.levels.*;
import com.splitseed.view.episode.shortontime.story.*;
import com.splitseed.zoomball.Etheric;

import java.util.Observable;

public class ShortOnTime extends Sequence {

    public long startTime;

    public ShortOnTime(Etheric game) {
        super(game);
        setup();
    }

    @Override
    protected void setup() {
        // Add intro
        addView(new Intro(game, Color.BLACK));
        // Level 01
        addView(new Level01(game, Color.WHITE, entity, portal, this));
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
        addView(new Scene02(game, Color.BLACK));
        // Level 05
        addView(new Level05(game, game.assets.GREEN, entity, portal));
        // Level 06
        addView(new Level06(game, game.assets.GREEN, entity, portal));
        // Level 07
        addView(new Level07(game, game.assets.GREEN, entity, portal));
        // Scene 03
        addView(new Scene03(game, Color.BLACK));
        // Level 08
        addView(new Level08(game, game.assets.GREEN, entity, portal));
        // Level 09
        addView(new Level09(game, game.assets.GREEN, entity, portal));
        // Scene 04
        addView(new Scene04(game, Color.BLACK));
        // Level 10
        Scene05 scene05 = new Scene05(game, Color.BLACK, this);
        addView(new Level10(game, game.assets.OFFWHITE, entity, portal, scene05));
        // Scene 05
        addView(scene05);
    }

    @Override
    public void update(Observable o, Object arg) {
        incrementCurrentView();
        setChanged();
        notifyObservers();
    }
}
