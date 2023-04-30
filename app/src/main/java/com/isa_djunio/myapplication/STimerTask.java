package com.isa_djunio.myapplication;

import java.util.TimerTask;

public class STimerTask extends TimerTask {

    private MainActivity activity;

    public STimerTask(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void run() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.updateTimerText();
            }
        });
    }
}
