package eu.speedbadminton.pyramid.listener;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * User: Yoann Moranville
 * Date: 05/11/2013
 *
 * @author Yoann Moranville
 */
public class InactivityJobTask extends QuartzJobBean {
    private static final Logger LOGGER = Logger.getLogger(InactivityJobTask.class);
    private InactivityTask inactivityTask;

    public void setInactivityTask(InactivityTask inactivityTask) {
        this.inactivityTask = inactivityTask;
    }

    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("Start execution");
        inactivityTask.run();
    }
}
