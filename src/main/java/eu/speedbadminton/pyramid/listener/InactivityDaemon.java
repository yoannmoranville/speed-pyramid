package eu.speedbadminton.pyramid.listener;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
/**
 * User: Yoann Moranville
 *
 * @author Bastiaan Verhoef
 */
public class InactivityDaemon {
    private static final Logger LOGGER = Logger.getLogger(InactivityDaemon.class);
    private static ScheduledExecutorService scheduler;
    private static boolean queueProcessing = false;

    private static final boolean forTest = true;

    public static synchronized void start() {
        if (scheduler == null && !queueProcessing) {
            scheduler = Executors.newScheduledThreadPool(1);

            if(forTest) {
                addTask(new Duration(0, 1, 0), new Duration(0, 10, 0), new Duration(24, 0, 0));
            } else {
                Calendar calendar = GregorianCalendar.getInstance();
                calendar.setTime(new Date());
                int startTonight = 23 - calendar.get(Calendar.HOUR_OF_DAY);
                if(startTonight < 0)
                    startTonight = 0;
                LOGGER.info("Next Inactivity task will be started in " + startTonight + " hours.");
                addTask(new Duration(startTonight, 1, 0), new Duration(0, 10, 0), new Duration(24, 0, 0));
            }

            LOGGER.info("Inactivity daemon started");
        } else if (queueProcessing) {
            LOGGER.info("Could not start Inactivity daemon");
        }

    }

    public static void addTask(Duration startDelay, Duration maxDuration, Duration delay){
        if (scheduler != null){
            LOGGER.info("Add inactivity task");
//            InactivityTask inactivityTask = new InactivityTask(scheduler, maxDuration, delay);
//            scheduler.schedule(inactivityTask, startDelay.getMilliseconds(), TimeUnit.MILLISECONDS);
        }
    }

    public static synchronized void stop(){
        if (scheduler != null){
            scheduler.shutdownNow();
            scheduler = null;
            LOGGER.info("Inactivity daemon stopped");
        }
    }

    public static boolean isActive(){
        return scheduler != null;
    }

    public static boolean isQueueProcessing() {
        return queueProcessing;
    }

    protected static void setQueueProcessing(boolean queueProcessing) {
        InactivityDaemon.queueProcessing = queueProcessing;
    }
}