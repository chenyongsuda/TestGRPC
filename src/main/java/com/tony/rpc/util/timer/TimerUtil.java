package com.tony.rpc.util.timer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by chnho02796 on 2017/10/18.
 */
public class TimerUtil {
    private TimerTask task;
    private Timer timer;

    public void scheduleAtFixedRate(final ITimer task, long delay, long intevalPeriod){
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                // task to run goes here
                task.timeup();
            }
        };
        timer = new Timer();
        // schedules the task to be run in an interval
        timer.scheduleAtFixedRate(timerTask, delay, intevalPeriod);
    }


    public void stop(){
        System.out.println("stop");
        if(null == timer){
            return;
        }
        timer.cancel();
    }
}
