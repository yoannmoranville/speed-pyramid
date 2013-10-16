package eu.speedbadminton.pyramid.listener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
/**
 * User: Bastiaan
 *
 * @author Bastiaan
 */
public class InactivityDaemon {
    private static final Logger LOGGER = Logger.getLogger(InactivityDaemon.class);
    private static ScheduledExecutorService scheduler;
    private static boolean queueProcessing = false;

    public static synchronized void start() {
        if (scheduler == null && !queueProcessing) {
            scheduler = Executors.newScheduledThreadPool(1);
            addTask(new Duration(0, 0, 0),new Duration(0, 1, 0) , new Duration(24, 0, 0));
            LOGGER.info("Inactivity daemon started");
        } else if (queueProcessing) {
            LOGGER.info("Could not start Inactivity daemon");
        }

    }

    public static void addTask(Duration startDelay, Duration maxDuration, Duration delay){
        if (scheduler != null){
            LOGGER.info("Add inactivity task");
//            QueueTask queueTask = new QueueTask(scheduler, maxDuration, delay);
//            scheduler.schedule(queueTask, startDelay.getMilliseconds(), TimeUnit.MILLISECONDS);
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