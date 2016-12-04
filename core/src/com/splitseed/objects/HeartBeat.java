package com.splitseed.objects;

import com.badlogic.gdx.Gdx;

import java.util.Timer;
import java.util.TimerTask;

public class HeartBeat {

    public enum PACE { SLIGHT, STEADY, RAPID, FATAL, DEAD }

    private Timer timer;
    private TimerTask task;
    private PACE pace;

    private long period[];
    private int length[];

    public HeartBeat(PACE pace) {
        this.pace = pace;
        period = new long[] { 2000, 1000, 500, 250, 10000 };
        length = new int[] { 200, 200, 200, 200, 10000 };
    }

    public void startTimer() {
        startTimer(0);
    }

    public void startTimer(long delay) {
        //length = 200;
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                Gdx.input.vibrate(length[pace.ordinal()]);
            }
        };
        timer.scheduleAtFixedRate(task, delay, period[pace.ordinal()]);
    }

    public void cancelTimer() {
        if (timer != null)
            timer.cancel();
    }

    public void changePace(PACE pace) {
        changePace(pace, 0);
    }

    public void changePace(PACE pace, long delay) {
        setPace(pace);
        cancelTimer();
        startTimer(delay);
    }

    public void setPace(PACE pace) {
        this.pace = pace;
    }

}
