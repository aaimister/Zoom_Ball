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
        period = new long[] { 2000, 1000, 500, 250, 2000 };
        length = new int[] { 200, 200, 200, 200, 2000 };
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
        Gdx.input.cancelVibrate();
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
    }

    public void changePace(PACE pace) {
        changePace(pace, 0);
    }

    public void changePace(PACE pace, long delay) {
        cancelTimer();
        setPace(pace);
        startTimer(delay);
    }

    public void setPace(PACE pace) {
        this.pace = pace;
    }

}
